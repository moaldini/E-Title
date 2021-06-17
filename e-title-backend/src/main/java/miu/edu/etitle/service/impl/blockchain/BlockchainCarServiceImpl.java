package miu.edu.etitle.service.impl.blockchain;

import miu.edu.etitle.domain.Car;
import miu.edu.etitle.domain.CarOwner;
import miu.edu.etitle.domain.CarTitleRevision;
import miu.edu.etitle.domain.ModelFuelType;
import miu.edu.etitle.dto.AddCarDto;
import miu.edu.etitle.dto.CreateCarDto;
import miu.edu.etitle.error.CanNotRegisterUserOnBlockchainException;
import miu.edu.etitle.error.VinIdAlreadyExist;
import miu.edu.etitle.repository.CarRepository;
import miu.edu.etitle.repository.CarTitleRevisionRepository;
import miu.edu.etitle.repository.StateRepository;
import miu.edu.etitle.security.SecurityUtils;
import miu.edu.etitle.service.CarMakeService;
import miu.edu.etitle.service.CarOwnerService;
import miu.edu.etitle.service.CarService;
import miu.edu.etitle.service.ModelService;
import miu.edu.etitle.service.impl.CarServiceImpl;
import miu.edu.etitle.service.impl.blockchain.dto.FabCarRequestDto;
import miu.edu.etitle.service.impl.blockchain.dto.FabCarResponseDto;
import miu.edu.etitle.util.BlockchainUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service("BlockchainCarServiceImpl")
public class BlockchainCarServiceImpl extends CarServiceImpl implements CarService {

    @Autowired
    BlockchainUtil blockchainUtil;

    @Override
    @Transactional
    public AddCarDto addCar(AddCarDto addCarDto) {
        Optional<FabCarResponseDto> blockchainResponse = blockchainUtil.fabcar(SecurityUtils.getUserDetail().getBlockchainToken(),
                new FabCarRequestDto(addCarDto)).blockOptional(Duration.ofSeconds(30));
        if (!blockchainResponse.isPresent() || blockchainResponse.get().getError() != null) {
            throw new CanNotRegisterUserOnBlockchainException();
        }
        return super.addCar(addCarDto);
    }

    @Override
    @Transactional
    public AddCarDto updateCar(AddCarDto addCarDto, String vinId) {
        carRepository.findByVin(vinId).orElseThrow(() -> new EntityNotFoundException("Vin ID not found: " + vinId));
        addCarDto.setVin(vinId);

        Optional<FabCarResponseDto> blockchainResponse = blockchainUtil.fabcar(SecurityUtils.getUserDetail().getBlockchainToken(),
                new FabCarRequestDto(addCarDto)).blockOptional(Duration.ofSeconds(30));
        if (!blockchainResponse.isPresent() || blockchainResponse.get().getError() != null) {
            throw new CanNotRegisterUserOnBlockchainException();
        }
        return super.updateCar(addCarDto, vinId);
    }

    @Override
    @Transactional
    public Car create(CreateCarDto createCarDto) {
        Optional<FabCarResponseDto> blockchainResponse = blockchainUtil.fabcar(SecurityUtils.getUserDetail().getBlockchainToken(),
                new FabCarRequestDto(createCarDto)).blockOptional(Duration.ofSeconds(30));
        if (!blockchainResponse.isPresent() || blockchainResponse.get().getError() != null) {
            throw new CanNotRegisterUserOnBlockchainException();
        }

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
}
