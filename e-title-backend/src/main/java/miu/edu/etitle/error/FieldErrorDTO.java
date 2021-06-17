package miu.edu.etitle.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FieldErrorDTO {
    private String field;
    private String message;

    @Override
    public String toString() {
        return "FieldErrorDTO{" +
                "field='" + field + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
