package miu.edu.etitle.error;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ValidationErrorDTO {

    private String errorType;
    List<FieldErrorDTO> fieldErrors = new ArrayList<FieldErrorDTO>();
    public ValidationErrorDTO(String errorType) {
        this.errorType = errorType;
    }

    public void addFieldError(String path, String message) {
        fieldErrors.add(new FieldErrorDTO(path, message));
    }


}
