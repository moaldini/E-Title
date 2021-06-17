package miu.edu.etitle.controller;

import miu.edu.etitle.config.JPAUserDetails;
import miu.edu.etitle.domain.User;
import miu.edu.etitle.dto.LoginRequestDto;
import miu.edu.etitle.dto.JwtTokenDto;
import miu.edu.etitle.config.JPAUserDetailsService;
import miu.edu.etitle.dto.UserDataDto;
import miu.edu.etitle.dto.RegisterUserDto;
import miu.edu.etitle.error.BadCredentialDto;
import miu.edu.etitle.security.JwtUtil;
import miu.edu.etitle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JPAUserDetailsService userDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    @Qualifier("BlockchainUserServiceImpl")
//    @Qualifier("UserServiceImpl")
    UserService userService;

    @PostMapping("/register_org")
    public ResponseEntity<JwtTokenDto> registerOrg(@RequestBody RegisterUserDto registerUserDto) {
        User savedUser = userService.registerUser(registerUserDto, "ORGANIZATION");
        JPAUserDetails userDetails = new JPAUserDetails(savedUser);
        UserDataDto userData = new UserDataDto(userDetails);
        String token = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtTokenDto(token, userData));
    }

    @PostMapping("/register")
    public ResponseEntity<JwtTokenDto> register(@RequestBody RegisterUserDto registerUserDto) {
        User savedUser = userService.registerUser(registerUserDto, "USER");
        JPAUserDetails userDetails = new JPAUserDetails(savedUser);
        UserDataDto userData = new UserDataDto(userDetails);
        String token = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtTokenDto(token, userData));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequestDto loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new BadCredentialDto("Invalid username and password"));
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        UserDataDto userData = new UserDataDto((JPAUserDetails) userDetails);
        String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtTokenDto(token, userData));
    }
}
