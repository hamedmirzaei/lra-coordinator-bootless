package ir.navaco.core.lra.coordinator.utils;

import ir.navaco.core.lra.coordinator.exception.SystemException;
import ir.navaco.core.lra.coordinator.utils.properties.EurekaProperties;
import ir.navaco.core.lra.coordinator.utils.properties.LRAProperties;

public class Constants {
    public static LRAProperties lraProperties;
    public static EurekaProperties eurekaProperties;

    static {
        try {
            lraProperties = new LRAProperties("lra.properties");
            eurekaProperties = new EurekaProperties("eureka.properties");
        } catch (SystemException.PropertyFileException e) {
            e.printStackTrace();
        }
    }
}
