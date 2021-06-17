package miu.edu.etitle.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AddressDto {
    private String addressLine1;
    private String addressLine2;
    private String zipCode;
    private String county;
    private String city;
    private String state;
}