package ir.navaco.core.lra.coordinator.exception;

public class SystemException {

    // internal exceptions like database persist
    public static class InternalException extends BaseException {
        public InternalException(String message) {
            super(message);
        }
    }

    // property file exception
    public static class PropertyFileException extends BaseException {
        public PropertyFileException(String message) {
            super(message);
        }
    }

}
