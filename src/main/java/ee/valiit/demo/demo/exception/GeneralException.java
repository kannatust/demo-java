package ee.valiit.demo.demo.exception;

import lombok.Data;

@Data
public class GeneralException extends RuntimeException {
    public GeneralException(String message, Error.Code code) {
        this.message = message;
        this.code = code;
    }
    String message;
    Error.Code code;
}
