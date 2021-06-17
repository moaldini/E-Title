package miu.edu.etitle.service.impl;

import miu.edu.etitle.domain.Role;
import miu.edu.etitle.domain.User;
import miu.edu.etitle.domain.address.Address;
import miu.edu.etitle.domain.address.City;
import miu.edu.etitle.domain.address.State;
import miu.edu.etitle.dto.AddressDto;
import miu.edu.etitle.dto.RegisterUserDto;
import miu.edu.etitle.error.UserAlreadyExist;
import miu.edu.etitle.repository.*;
import miu.edu.etitle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;

@Service("UserServiceImpl")
public class UserServiceImpl implements UserService {
    @Autowired
    protected UserRepository repository;

    @Autowired
    protected RoleRepository roleRepository;

    @Autowired
    protected StateRepository stateRepository;

    @Autowired
    protected CityRepository cityRepository;

    @Autowired
    protected AddressRepository addressRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User registerUser(RegisterUserDto userDto, String roleName) {
        Optional<User> userOptional = repository.findByEmail(userDto.getEmail());
        if(userOptional.isPresent()) throw new UserAlreadyExist("User already exist");
        Role role = roleRepository.findByName(roleName).orElseThrow(() -> new EntityNotFoundException("USER Role not found"));

        AddressDto addressDto = userDto.getAddress();
        State state = stateRepository.findByCodeIgnoreCase(addressDto.getState());
        City city = new City(addressDto.getCity()); // cityRepository.findByName(addressDto.getCity());
        cityRepository.save(city);
        Address address = new Address(addressDto.getAddressLine1(), addressDto.getAddressLine2(), addressDto.getZipCode(), "US", city, state);
        addressRepository.save(address);

        User user = new User(userDto, address);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setActive(true);
        user.setRole(role);
        return repository.save(user);
    }

}
