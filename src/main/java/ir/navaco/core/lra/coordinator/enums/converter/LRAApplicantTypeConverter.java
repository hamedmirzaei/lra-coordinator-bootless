package ir.navaco.core.lra.coordinator.enums.converter;

import ir.navaco.core.lra.coordinator.enums.LRAApplicantType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class LRAApplicantTypeConverter implements AttributeConverter<LRAApplicantType, String> {

    @Override
    public String convertToDatabaseColumn(LRAApplicantType lraApplicantType) {
        if (lraApplicantType == null) {
            return null;
        }
        return lraApplicantType.getTypeName();
    }

    @Override
    public LRAApplicantType convertToEntityAttribute(String typeName) {
        if (typeName == null) {
            return null;
        }

        return Stream.of(LRAApplicantType.values())
                .filter(s -> s.getTypeName().equals(typeName))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
