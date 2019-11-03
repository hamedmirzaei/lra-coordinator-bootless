package ir.navaco.core.lra.coordinator.exception.advice;


import ir.navaco.core.lra.coordinator.exception.LRAException;
import ir.navaco.core.lra.coordinator.exception.LRARequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@ControllerAdvice
public class LRACoordinatorApiAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({LRARequestException.FieldNotExist.class})
    public ResponseEntity<String> handleFieldNotExist(LRARequestException.FieldNotExist e) {
        return error(UNPROCESSABLE_ENTITY, e);
    }

    @ExceptionHandler({LRARequestException.BadSizeMap.class})
    public ResponseEntity<String> handleBadSizeMap(LRARequestException.BadSizeMap e) {
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
