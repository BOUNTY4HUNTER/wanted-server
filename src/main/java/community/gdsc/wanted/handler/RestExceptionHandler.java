package community.gdsc.wanted.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import community.gdsc.wanted.dto.ErrorResponseDTO;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    protected final ErrorResponseDTO handle(Exception e) {
        logger.error(e);
        return new ErrorResponseDTO(e.getMessage());
    }
}
