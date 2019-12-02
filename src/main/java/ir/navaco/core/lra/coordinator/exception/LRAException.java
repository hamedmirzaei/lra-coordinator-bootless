package ir.navaco.core.lra.coordinator.exception;

public class LRAException {

    // LRA instance not found
    public static class InstanceNotFoundException extends BaseException {
        private String uuid;

        public InstanceNotFoundException(String uuid) {
            super("The LRA instance with uuid = \"" + uuid + "\" does not exist.");
            this.uuid = uuid;
        }

        public String getUuid() {
            return uuid;
        }
    }

    // LRA instance processed before
    public static class InstanceAlreadyProcessedException extends BaseException {
        private String uuid;

        public InstanceAlreadyProcessedException(String uuid) {
            super("The LRA instance with uuid = \"" + uuid + "\" is already been processed and you cant register any applicant to it.");
            this.uuid = uuid;
        }

        public String getUuid() {
            return uuid;
        }
    }

    // LRA instance is under cancel operation
    public static class InstanceUnderCancelException extends BaseException {
        private String uuid;

        public InstanceUnderCancelException(String uuid) {
            super("The LRA instance with uuid = \"" + uuid + "\" is under canceling.");
            this.uuid = uuid;
        }

        public String getUuid() {
            return uuid;
        }
    }

    // LRA instance is canceled before
    public static class InstanceAlreadyCanceledException extends BaseException {
        private String uuid;

        public InstanceAlreadyCanceledException(String uuid) {
            super("The LRA instance with uuid = \"" + uuid + "\" is already been canceled.");
            this.uuid = uuid;
        }

        public String getUuid() {
            return uuid;
        }
    }

}
