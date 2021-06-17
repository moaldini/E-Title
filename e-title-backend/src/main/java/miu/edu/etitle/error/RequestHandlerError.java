package miu.edu.etitle.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Locale;

@ControllerAdvice
public class RequestHandlerError {

    @Autowired
    MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorDTO processValidationError(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldError = bindingResult.getFieldErrors();
        return processFieldErrors(fieldError);
    }

    private ValidationErrorDTO processFieldErrors(List<FieldError> fieldError) {
        ValidationErrorDTO dto = new ValidationErrorDTO("ValidationError");

        fieldError.forEach(fieldError1 ->
                dto.addFieldError(fieldError1.getField(),
                        resolveLocalizedErrorMessage(fieldError1)));
        return dto;
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public AccessDeniedExeptionDto processAccessDeniedException(AccessDeniedException ex) {
        ex.printStackTrace();
        return new AccessDeniedExeptionDto();

    }

    @ExceptionHandler(VinIdAlreadyExist.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public VinIdAlreadyExistDto processVinIdAlreadyExistError(Exception ex) {
        return new VinIdAlreadyExistDto(ex.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public EntityNotFoundExceptionDto processEntityNotFoundException(Exception ex) {
        return new EntityNotFoundExceptionDto(ex.getMessage());
    }

    @ExceptionHandler(UserAlreadyExist.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public UserAlreadyExistDto processUserAlreadyExistError(Exception ex) {
        return new UserAlreadyExistDto(ex.getMessage());

    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<Object> processValidationError(Exception ex) {
        ex.printStackTrace();

        if (ex instanceof NullPointerException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    private String resolveLocalizedErrorMessage(FieldError fieldError) {
        Locale currentLocale = LocaleContextHolder.getLocale();
        String localizedErrorMessage = messageSource.getMessage(fieldError, currentLocale);

        // If the message was not found, return the most accurate field error code
        // instead.
        // You can remove this check if you prefer to get the default error message.
        if (localizedErrorMessage.equals(fieldError.getDefaultMessage())) {
            String[] fieldErrorCodes = fieldError.getCodes();
            localizedErrorMessage = fieldErrorCodes[0];
        }
        return localizedErrorMessage;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private class EntityNotFoundExceptionDto {
        private String entity;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private class VinIdAlreadyExistDto {
        private String vinId;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public class UserAlreadyExistDto{
        private String email;
    }

    @Getter
    class AccessDeniedExeptionDto {
        private String error = "Access denied exception";
    }
}
