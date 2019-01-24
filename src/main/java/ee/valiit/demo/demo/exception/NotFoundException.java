package ee.valiit.demo.demo.exception;

import lombok.Data;

@Data
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message, Error.Code code) {
        this.message = message;
        this.code = code;
    }
    String message;
    Error.Code code;
}
