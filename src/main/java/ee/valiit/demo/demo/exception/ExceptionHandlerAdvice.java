
package ee.valiit.demo.demo.exception;

import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GeneralException.class)

    public final ResponseEntity<ErrorMessage> exception(GeneralException ex) {
        ErrorMessage exceptionResponse = new ErrorMessage(ex.getMessage(), ex.getCode());

        return new ResponseEntity<ErrorMessage>(exceptionResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotFoundException.class)

    public final ResponseEntity<ErrorMessage> exception(NotFoundException ex) {
        ErrorMessage exceptionResponse = new ErrorMessage(ex.getMessage(), ex.getCode());

        return new ResponseEntity<ErrorMessage>(exceptionResponse, new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE);
    }
}


@Data
class ErrorMessage {

    ErrorMessage(String message, Error.Code code) {
        this.message = message;
        this.code = code;
    }

    private String message;
    private Error.Code code;
}
