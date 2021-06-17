package miu.edu.etitle.service.impl.blockchain;

import miu.edu.etitle.domain.Role;
import miu.edu.etitle.domain.User;
import miu.edu.etitle.domain.address.Address;
import miu.edu.etitle.domain.address.City;
import miu.edu.etitle.domain.address.State;
import miu.edu.etitle.dto.AddressDto;
import miu.edu.etitle.dto.RegisterUserDto;
import miu.edu.etitle.error.CanNotRegisterUserOnBlockchainException;
import miu.edu.etitle.error.UserAlreadyExist;
import miu.edu.etitle.service.UserService;
import miu.edu.etitle.service.impl.UserServiceImpl;
import miu.edu.etitle.util.BlockchainUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.Duration;
import java.util.Optional;

@Service("BlockchainUserServiceImpl")
public class BlockchainUserServiceImpl extends UserServiceImpl implements UserService {

    @Autowired
    BlockchainUtil blockchainUtil;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User registerUser(RegisterUserDto userDto, String roleName) {
        Optional<User> userOptional = repository.findByEmail(userDto.getEmail());
        if (userOptional.isPresent()) throw new UserAlreadyExist("User already exist");

        Optional<miu.edu.etitle.service.impl.blockchain.dto.RegisterUserDto> blockchainResponse = blockchainUtil.registerUser(userDto.getEmail(), "Org1").blockOptional(Duration.ofSeconds(30));
        if (!blockchainResponse.isPresent() || !blockchainResponse.get().isSuccess()) {
            throw new CanNotRegisterUserOnBlockchainException();
        }

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
        user.setBlockchainToken(blockchainResponse.get().getToken());
        return repository.save(user);
    }

}
