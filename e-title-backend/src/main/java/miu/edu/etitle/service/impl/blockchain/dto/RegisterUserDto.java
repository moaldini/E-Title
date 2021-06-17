package miu.edu.etitle.service.impl.blockchain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RegisterUserDto {
    private boolean success;
    private String message;
    private String token;
}
