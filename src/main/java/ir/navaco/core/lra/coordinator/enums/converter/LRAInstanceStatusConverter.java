package ir.navaco.core.lra.coordinator.enums.converter;

import ir.navaco.core.lra.coordinator.enums.LRAInstanceStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class LRAInstanceStatusConverter implements AttributeConverter<LRAInstanceStatus, String> {

    @Override
    public String convertToDatabaseColumn(LRAInstanceStatus lraInstanceStatus) {
        if (lraInstanceStatus == null) {
            return null;
        }
        return lraInstanceStatus.getStatusName();
    }

    @Override
    public LRAInstanceStatus convertToEntityAttribute(String statusName) {
        if (statusName == null) {
            return null;
        }

        return Stream.of(LRAInstanceStatus.values())
                .filter(s -> s.getStatusName().equals(statusName))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
