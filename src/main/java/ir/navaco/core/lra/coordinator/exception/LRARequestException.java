package ir.navaco.core.lra.coordinator.exception;

public class LRARequestException {

    // when the input map size is not whats is expected
    public static class BadSizeMap extends BaseException {
        private Integer expectedSize;
        private Integer actualSize;

        public BadSizeMap(Integer expectedSize, Integer actualSize) {
            super("The input map is expected to be " + expectedSize + " in size but it is " + actualSize);
            this.expectedSize = expectedSize;
            this.actualSize = actualSize;
        }

        public Integer getExpectedSize() {
            return expectedSize;
        }

        public Integer getActualSize() {
            return actualSize;
        }
    }

    // when an expected field is not exists in the input map
    public static class FieldNotExist extends BaseException {
        private String fieldName;

        public FieldNotExist(String fieldName) {
            super("The field with name \"" + fieldName + "\" does not exists in the input map");
            this.fieldName = fieldName;
        }

        public String getFieldName() {
            return fieldName;
        }
    }
}
