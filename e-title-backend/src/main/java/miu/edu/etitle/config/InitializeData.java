package miu.edu.etitle.config;

import lombok.extern.log4j.Log4j2;
import miu.edu.etitle.domain.Role;
import miu.edu.etitle.domain.User;
import miu.edu.etitle.dto.AddCarDto;
import miu.edu.etitle.dto.AddressDto;
import miu.edu.etitle.dto.RegisterUserDto;
import miu.edu.etitle.repository.CarRepository;
import miu.edu.etitle.repository.RoleRepository;
import miu.edu.etitle.repository.UserRepository;
import miu.edu.etitle.service.CarService;
import miu.edu.etitle.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@Log4j2
public class InitializeData {
    @Bean
    public CommandLineRunner loadData(RoleRepository repository, UserRepository userRepository, CarRepository carRepository,
                                      @Qualifier("BlockchainUserServiceImpl") UserService userService,
                                      @Qualifier("BlockchainCarServiceImpl") CarService carService) {
        return (args) -> {
            userRepository.deleteAll();
            userRepository.deleteAll();
            repository.deleteAll();

            // save roles
            repository.save(new Role("ORGANIZATION"));
            repository.save(new Role("USER"));

            // fetch all roles
            log.info("Roles found with findAll():");
            log.info("--------------------------------------------------------------");
            for (Role role : repository.findAll()) {
                log.info(role.toString());
            }

            // add organization user
            RegisterUserDto orgUser = new RegisterUserDto("Dang", "Nguyen", "+84989302594",
                    "9999-999-99", "haidang063@gmail.com", "123",
                    new AddressDto("407 N 6th st", "", "52556", "jeferson", "FairField", "IA"), "");
            userService.registerUser(orgUser, "ORGANIZATION");

            // add a normal user
            RegisterUserDto normalUser = new RegisterUserDto("Huu Viet", "Nguyen", "+6415555555",
                    "8888-888-88", "viet@gmail.com", "123",
                    new AddressDto("407 N 6th st", "", "52556", "jeferson", "FairField", "IA"), "");

            User savedNormalUser = userService.registerUser(normalUser, "USER");

            log.info("Registered Users:");
            log.info("--------------------------------------------------------------");
            userRepository.findAll().forEach(
                    user1 -> log.info(user1)
            );

            JPAUserDetails userDetails = new JPAUserDetails(savedNormalUser);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            AddCarDto carDto = new AddCarDto("VIN-111", "Toyota", "Camry", "Red", normalUser.getSsn(), "150000", "100000", "2021", "NEW", "8", "Automatic", "Mid-size", "GAS", "CLEAN", "4wd","SUV", "IA", "");
            carService.addCar(carDto);
            AddCarDto carDto2 = new AddCarDto("VIN-222", "Ford", "Escape", "Blue", normalUser.getSsn(), "6000", "75000", "2021", "USED", "8", "Automatic", "BIG-size", "GAS", "CLEAN", "awd","SUV", "IA", "");
            carService.addCar(carDto2);

            log.info("Registered Cars:");
            log.info("--------------------------------------------------------------");
            carRepository.findAll().forEach(car -> log.info(new AddCarDto(car)));
        };
    }
}