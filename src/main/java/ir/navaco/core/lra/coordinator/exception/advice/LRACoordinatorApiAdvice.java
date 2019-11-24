package ir.navaco.core.lra.coordinator.exception.advice;


import ir.navaco.core.lra.coordinator.exception.LRAException;
import ir.navaco.core.lra.coordinator.exception.LRARequestException;
import ir.navaco.core.lra.coordinator.exception.SystemException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@ControllerAdvice
public class LRACoordinatorApiAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({LRARequestException.FieldNotExistException.class})
    public ResponseEntity<String> handleFieldNotExistException(LRARequestException.FieldNotExistException e) {
        return error(UNPROCESSABLE_ENTITY, e);
    }

    @ExceptionHandler({LRARequestException.BadSizeMapException.class})
    public ResponseEntity<String> handleBadSizeMapException(LRARequestException.BadSizeMapException e) {
        return error(UNPROCESSABLE_ENTITY, e);
    }

    @ExceptionHandler({SystemException.InternalException.class})
    public ResponseEntity<String> handleInternalException(SystemException.InternalException e) {
        return error(UNPROCESSABLE_ENTITY, e);
    }

    @ExceptionHandler({SystemException.PropertyFileException.class})
    public ResponseEntity<String> handlePropertyFileException(SystemException.PropertyFileException e) {
        return error(UNPROCESSABLE_ENTITY, e);
    }

    @ExceptionHandler({LRAException.InstanceNotFoundException.class})
    public ResponseEntity<String> handleInstanceNotFoundException(LRAException.InstanceNotFoundException e) {
        return error(UNPROCESSABLE_ENTITY, e);
    }

    private ResponseEntity<String> error(HttpStatus status, Exception e) {
        return ResponseEntity.status(status).body(e.getMessage());
    }

}
