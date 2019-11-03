package ir.navaco.core.lra.coordinator.exception;

public class LRAException {

    // LRA instance not found
    public static class InstanceNotFoundException extends BaseException {
        private String uuid;

        public InstanceNotFoundException(String uuid) {
            super("The LRA instance with uuid = \"" + uuid + "\" does not exist");
            this.uuid = uuid;
        }

        public String getUuid() {
            return uuid;
        }
    }


}
