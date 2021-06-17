package miu.edu.etitle.service;

import miu.edu.etitle.domain.User;
import miu.edu.etitle.dto.RegisterUserDto;

public interface UserService {
    User registerUser(RegisterUserDto userDto, String roleName);

}
