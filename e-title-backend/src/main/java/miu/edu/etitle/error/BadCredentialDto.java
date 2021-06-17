package miu.edu.etitle.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BadCredentialDto {
    private String error;
    public BadCredentialDto(String message) {
        this.error = message;
    }
}