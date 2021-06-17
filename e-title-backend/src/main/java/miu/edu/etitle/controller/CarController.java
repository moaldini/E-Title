package miu.edu.etitle.controller;

import miu.edu.etitle.domain.Car;
import miu.edu.etitle.domain.CarOwner;
import miu.edu.etitle.dto.AddCarDto;
import miu.edu.etitle.dto.CreateCarDto;
import miu.edu.etitle.error.VinIdAlreadyExist;
import miu.edu.etitle.repository.CarRepository;
import miu.edu.etitle.security.SecurityUtils;
import miu.edu.etitle.service.CarOwnerService;
import miu.edu.etitle.service.CarService;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cars")
public class CarController {
    @Autowired
//    @Qualifier("carServiceImpl")
    @Qualifier("BlockchainCarServiceImpl")
    CarService carService;

    @Autowired
    CarOwnerService carOwnerSerivce;

    @Autowired
    CarRepository carRepository;

    //    @Secured("ROLE_ORGANIZATION")
    @PostMapping
    public AddCarDto registerCar(@RequestBody AddCarDto addCarDto) {
        if (carService.findByVin(addCarDto.getVin()).isPresent()) {
            throw new VinIdAlreadyExist("Vin ID: '" + addCarDto.getVin() + "' already exist!");
        }
        return carService.addCar(addCarDto);
    }

    //    @Secured("ROLE_ORGANIZATION")
    @PutMapping("/{vinId}")
    public AddCarDto updateCar(@RequestBody AddCarDto dto, @PathVariable("vinId") String vinId) {
        return carService.updateCar(dto, vinId);
    }

    @PostMapping("/fabcar")
    public Car createCar(@RequestBody CreateCarDto createCarDto) {
        return carService.create(createCarDto);
    }

    @GetMapping
    List<AddCarDto> getCars(@RequestParam(required = false) String q) {
        if (SecurityUtils.isOrganizationUser()) {
            if(StringUtils.isNotBlank(q)) {
                return IteratorUtils.toList(carRepository.findByAllColumns(q).iterator()).stream().map(car -> new AddCarDto(car)).collect(Collectors.toList());
            } else {
                return IteratorUtils.toList(carService.getAllCars(q).iterator());
            }
        } else {
            String ssn = SecurityUtils.getSSN();
            CarOwner owner = carOwnerSerivce.findBySSN(ssn);
            return carService.getAllCarsByOwner(owner);
        }
    }
}
