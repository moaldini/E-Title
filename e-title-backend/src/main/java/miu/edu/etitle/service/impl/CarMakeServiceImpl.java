package miu.edu.etitle.service.impl;

import miu.edu.etitle.domain.CarMake;
import miu.edu.etitle.repository.CarMakeRepository;
import miu.edu.etitle.service.CarMakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarMakeServiceImpl implements CarMakeService {
    @Autowired
    CarMakeRepository carMakeRepository;

    @Override
    public CarMake getCarMake(String name) {
        Optional<CarMake> carMake = carMakeRepository.findByName(name);
        if (!carMake.isPresent()) {
            CarMake carMake1 = new CarMake();
            carMake1.setCode(name.toUpperCase());
            carMake1.setName(name);
            carMakeRepository.save(carMake1);
            carMake = carMakeRepository.findByName(name);
        }
        return carMake.get();
    }
}
