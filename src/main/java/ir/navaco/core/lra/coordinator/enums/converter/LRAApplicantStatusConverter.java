package ir.navaco.core.lra.coordinator.enums.converter;

import ir.navaco.core.lra.coordinator.enums.LRAApplicantStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class LRAApplicantStatusConverter implements AttributeConverter<LRAApplicantStatus, String> {

    @Override
    public String convertToDatabaseColumn(LRAApplicantStatus lraApplicantStatus) {
        if (lraApplicantStatus == null) {
            return null;
        }
        return lraApplicantStatus.getStatusName();
    }

    @Override
    public LRAApplicantStatus convertToEntityAttribute(String statusName) {
        if (statusName == null) {
            return null;
        }

        return Stream.of(LRAApplicantStatus.values())
                .filter(s -> s.getStatusName().equals(statusName))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
