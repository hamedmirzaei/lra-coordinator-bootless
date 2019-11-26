package ir.navaco.core.lra.coordinator.exception.advice;


import ir.navaco.core.lra.coordinator.vo.LRAResponseVo;
import ir.navaco.core.lra.coordinator.exception.LRAException;
import ir.navaco.core.lra.coordinator.exception.LRARequestException;
import ir.navaco.core.lra.coordinator.exception.SystemException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class LRACoordinatorApiAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({LRARequestException.BadSizeMapException.class})
    public ResponseEntity<LRAResponseVo> handleBadSizeMapException(LRARequestException.BadSizeMapException e) {
        return ResponseEntity.ok(new LRAResponseVo("LRA-0001", e.getMessage(), 1l));
    }

    @ExceptionHandler({SystemException.InternalException.class})
    public ResponseEntity<LRAResponseVo> handleInternalException(SystemException.InternalException e) {
        return ResponseEntity.ok(new LRAResponseVo("LRA-0002", e.getMessage(), 2l));
    }

    @ExceptionHandler({SystemException.PropertyFileException.class})
    public ResponseEntity<LRAResponseVo> handlePropertyFileException(SystemException.PropertyFileException e) {
        return ResponseEntity.ok(new LRAResponseVo("LRA-0003", e.getMessage(), 3l));
    }

    @ExceptionHandler({LRAException.InstanceNotFoundException.class})
    public ResponseEntity<LRAResponseVo> handleInstanceNotFoundException(LRAException.InstanceNotFoundException e) {
        return ResponseEntity.ok(new LRAResponseVo("LRA-0004", e.getMessage(), 4l));
    }

    @ExceptionHandler({LRARequestException.FieldNotExistException.class})
    public ResponseEntity<LRAResponseVo> handleFieldNotExistException(LRARequestException.FieldNotExistException e) {
        return ResponseEntity.ok(new LRAResponseVo("LRA-0005", e.getMessage(), 5l));
    }

}
