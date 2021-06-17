package miu.edu.etitle.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAlreadyExist extends RuntimeException{
    public UserAlreadyExist(String message) {
        super(message);
    }
}
