package miu.edu.etitle.service.impl.blockchain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class RegisterUserRequestDto {
    String username;
    String orgName;
}
