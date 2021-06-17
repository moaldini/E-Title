package miu.edu.etitle.service.impl;

import com.github.javafaker.Faker;
import miu.edu.etitle.domain.CarOwner;
import miu.edu.etitle.domain.address.Address;
import miu.edu.etitle.repository.AddressRepository;
import miu.edu.etitle.repository.CarOwnerRepository;
import miu.edu.etitle.repository.CityRepository;
import miu.edu.etitle.repository.StateRepository;
import miu.edu.etitle.service.CarOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
public class CarOwnerServiceImpl implements CarOwnerService {

    @Autowired
    CarOwnerRepository carOwnerRepository;

    @Autowired
    StateRepository stateRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    CityRepository cityRepository;

    @Override
    public CarOwner findBySSN(String ssn) {
        Optional<CarOwner> carOwner = carOwnerRepository.findBySsn(ssn);
        if (!carOwner.isPresent()) {
            CarOwner carOwner1 = getCarOwnerInformation(ssn);
            carOwnerRepository.save(carOwner1);
            carOwner = carOwnerRepository.findBySsn(ssn);
        }
        return carOwner.get();
    }

    private CarOwner getCarOwnerInformation(String ssn) {
        Faker faker = new Faker(Locale.US);
        CarOwner carOwner = new CarOwner();
        carOwner.setFirstName(faker.name().firstName());
        carOwner.setLastName(faker.name().lastName());
        carOwner.setSsn(ssn);
        com.github.javafaker.Address fakeAddress = faker.address();
        Address address = new Address();
        address.setAddressLine1(fakeAddress.streetAddress());
        address.setCity(cityRepository.findByName(fakeAddress.city()));
        address.setState(stateRepository.findByName(fakeAddress.state()));
        address = addressRepository.save(address);
        carOwner.setAddress(address);
        return carOwner;
    }
}
