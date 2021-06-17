package miu.edu.etitle.service.impl;

import miu.edu.etitle.domain.Car;
import miu.edu.etitle.domain.CarOwner;
import miu.edu.etitle.domain.CarTitleRevision;
import miu.edu.etitle.domain.ModelFuelType;
import miu.edu.etitle.dto.AddCarDto;
import miu.edu.etitle.dto.CreateCarDto;
import miu.edu.etitle.repository.CarRepository;
import miu.edu.etitle.repository.CarTitleRevisionRepository;
import miu.edu.etitle.repository.StateRepository;
import miu.edu.etitle.service.CarMakeService;
import miu.edu.etitle.service.CarOwnerService;
import miu.edu.etitle.service.CarService;
import miu.edu.etitle.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    protected CarMakeService carMakeService;

    @Autowired
    protected ModelService modelService;

    @Autowired
    protected CarOwnerService carOwnerService;

    @Autowired
    protected StateRepository stateRepository;

    @Autowired
    protected CarRepository carRepository;

    @Autowired
    protected CarTitleRevisionRepository carTitleRevisionRepository;

    @Override
    @Transactional
    public AddCarDto addCar(AddCarDto addCarDto) {
        Car car = new Car();
        car.setVin(addCarDto.getVin());
        CarOwner owner = carOwnerService.findBySSN(addCarDto.getOwner());
        car.setCarOwner(owner);
        setCarAttributes(addCarDto, car);
        setTitleRevision(addCarDto, owner, car);
        carRepository.save(car);
        return new AddCarDto(car);
    }

    @Override
    @Transactional
    public AddCarDto updateCar(AddCarDto addCarDto, String vinId) {

        Car car = carRepository.findByVin(vinId).orElseThrow(() -> new EntityNotFoundException("Vin ID not found" + vinId));

        car.setStatus(addCarDto.getStatus());
        CarOwner owner = carOwnerService.findBySSN(addCarDto.getOwner());
        car.setCarOwner(owner);
        setTitleRevision(addCarDto, owner, car);

        carRepository.save(car);
        return new AddCarDto(car);
    }

    protected void setCarAttributes(AddCarDto addCarDto, Car car) {
        car.setMake(carMakeService.getCarMake(addCarDto.getMake()));
        car.setModel(modelService.getModel(addCarDto.getModel()));
        car.setColor(addCarDto.getColor());
        car.setYear(addCarDto.getYear());
        car.setCylinders(addCarDto.getCylinders());
        car.setTransmission(addCarDto.getTransmission());
        car.setBodyType(addCarDto.getType());
        car.setStatus(addCarDto.getStatus());
        car.setModelFuelType(ModelFuelType.valueOf(addCarDto.getFuel() != null ? addCarDto.getFuel().toUpperCase() : "GAS"));
        car.setFwd(addCarDto.getVehicle());
        car.setSize(addCarDto.getSize());
    }

    protected void setTitleRevision(AddCarDto addCarDto, CarOwner owner, Car car) {
        CarTitleRevision titleRevision = new CarTitleRevision();
        // set owner of current revision
        titleRevision.setOwner(owner);

        titleRevision.setMiles(addCarDto.getMiles());
        titleRevision.setPrice(addCarDto.getPrice());
        titleRevision.setStatus(addCarDto.getTitle());
        titleRevision.setCondition(addCarDto.getCondition());
        titleRevision.setState(stateRepository.findByCodeIgnoreCase(addCarDto.getState()));

        titleRevision = carTitleRevisionRepository.save(titleRevision);
        car.setCurrentRevision(titleRevision);
        titleRevision.setCar(car);
        carTitleRevisionRepository.save(titleRevision);

        List<CarTitleRevision> carTitleRevisions = car.getCarTitleList();
        if (carTitleRevisions == null || carTitleRevisions.isEmpty()) {
            carTitleRevisions = new ArrayList<>();
        }
        carTitleRevisions.add(titleRevision);
        car.setCarTitleList(carTitleRevisions);
    }

    @Override
    @Transactional
    public Car create(CreateCarDto createCarDto) {
        List<String> args = createCarDto.getArgs();
//         args ["CAR5","Mitsubisihi","Pajero","black","Qatawneh","100000","30000","2016","excellent","6","automatic","mid-size","gas","clean", "4wd", "suv","ia", "VINID"]
//         car id, make, model, color, owner, miles, price, year, condition, cylinders, transmission, size, fuelType, title, vehicle, type, state, vinid
//                                                                           [model]    [model]       [model] [model]
        String vin = args.get(0);
        CarOwner owner = carOwnerService.findBySSN(args.get(4));
        Optional<Car> carOptional = carRepository.findByVin(vin);
        if (!carOptional.isPresent()) {
            Car car = new Car();
            car.setMake(carMakeService.getCarMake(args.get(1)));
            System.out.println("data carMake " + carMakeService.getCarMake(args.get(1)));
            car.setModel(modelService.getModel(args.get(2)));
            car.setColor(args.get(3));
            car.setVin(vin);
            car.setCarOwner(carOwnerService.findBySSN(args.get(4)));
            car.setYear(args.get(7));
            carRepository.save(car);
            carOptional = carRepository.findByVin(vin);
        }
        Car car = carOptional.get();

        // change car owner of current car
        car.setCarOwner(owner);

        CarTitleRevision titleRevision = new CarTitleRevision();
        // set owner of current revision
        titleRevision.setOwner(owner);

        titleRevision.setMiles(args.get(5));
        titleRevision.setPrice(args.get(6));
        titleRevision.setCondition(args.get(8));
        titleRevision.setState(stateRepository.findByCodeIgnoreCase(args.get(16)));

        titleRevision = carTitleRevisionRepository.save(titleRevision);
        car.setCurrentRevision(titleRevision);
        titleRevision.setCar(car);
        carTitleRevisionRepository.save(titleRevision);

        List<CarTitleRevision> carTitleRevisions = car.getCarTitleList();
        if (carTitleRevisions == null || carTitleRevisions.isEmpty()) {
            carTitleRevisions = new ArrayList<>();
        }
        carTitleRevisions.add(titleRevision);
        car.setCarTitleList(carTitleRevisions);
        car = carRepository.save(car);


        return car;
    }

    @Override
    public List<AddCarDto> getAllCarsByOwner(CarOwner owner) {
        List<Car> cars = carRepository.findByCarOwner(owner).orElse(Collections.emptyList());
        return StreamSupport.stream(cars.spliterator(), false)
                .map(AddCarDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<AddCarDto> getAllCars(String query) {
        return StreamSupport.stream(carRepository.findAll().spliterator(), false)
                .map(AddCarDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Car> findByVin(String vinId) {
        return carRepository.findByVin(vinId);
    }
}
