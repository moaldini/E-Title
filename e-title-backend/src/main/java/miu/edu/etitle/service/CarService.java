package miu.edu.etitle.service;

import miu.edu.etitle.domain.Car;
import miu.edu.etitle.domain.CarOwner;
import miu.edu.etitle.dto.AddCarDto;
import miu.edu.etitle.dto.CreateCarDto;
import java.util.List;
import java.util.Optional;

public interface CarService {
    AddCarDto addCar(AddCarDto addCarDto);
    AddCarDto updateCar(AddCarDto addCarDto, String vinId);
    Car create(CreateCarDto createCarDto);
    List<AddCarDto> getAllCarsByOwner(CarOwner owner);
    Iterable<AddCarDto> getAllCars(String query);
    Optional<Car> findByVin(String vinId);
}
