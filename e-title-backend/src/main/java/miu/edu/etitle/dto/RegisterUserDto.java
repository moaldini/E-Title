package miu.edu.etitle.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDto {
    private String firstName;
    private String lastName;
    private String mobile;
    private String ssn;
    private String email;
    private String password;
    private AddressDto address;
    private String identifyUrl;
}