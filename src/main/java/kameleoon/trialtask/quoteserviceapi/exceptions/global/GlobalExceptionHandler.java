package kameleoon.trialtask.quoteserviceapi.exceptions.global;

import kameleoon.trialtask.quoteserviceapi.exceptions.EmptyFieldException;
import kameleoon.trialtask.quoteserviceapi.exceptions.RegisterDataAlreadyUsed;
import kameleoon.trialtask.quoteserviceapi.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<RequestError> catchResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(new RequestError(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<RequestError> catchRegisterDataAlreadyUsedException(RegisterDataAlreadyUsed e) {
        return new ResponseEntity<>(new RequestError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<RequestError> catchEmptyFieldException(EmptyFieldException e) {
        return new ResponseEntity<>(new RequestError(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
