package ir.navaco.core.lra.coordinator.utils.properties;

import ir.navaco.core.lra.coordinator.exception.SystemException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LRAProperties {

    private Properties properties;
    private Integer corePoolSize;
    private Integer maximumPoolSize;
    private Integer cancelHandlerWaitTimeRate;
    private Integer cancelHandlerWaitTimeMax;

    public LRAProperties(String classPathFileName) throws SystemException.PropertyFileException {
        initializeAllFields();
        try (InputStream input = this.getClass().getClassLoader().getResourceAsStream(classPathFileName)) {
            properties = new Properties();
            if (input == null) {
                throw new SystemException.PropertyFileException("There is no property file with name " + classPathFileName + " in the class path");
            }
            properties.load(input);
        } catch (IOException ex) {
            throw new SystemException.PropertyFileException("Some problem occurred during opening property file with name " + classPathFileName + " in the class path");
        }
    }

    private void initializeAllFields() {
        corePoolSize = null;
        maximumPoolSize = null;
        cancelHandlerWaitTimeRate = null;
        cancelHandlerWaitTimeMax = null;
    }

    public Integer getCorePoolSize() {
        if (corePoolSize != null)
            return corePoolSize;
        corePoolSize = Integer.parseInt(properties.getProperty("core.pool.size"));
        return corePoolSize;
    }

    public Integer getMaximumPoolSize() {
        if (maximumPoolSize != null)
            return maximumPoolSize;
        maximumPoolSize = Integer.parseInt(properties.getProperty("maximum.pool.size"));
        return maximumPoolSize;
    }

    public Integer getCancelHandlerWaitTimeRate() {
        if (cancelHandlerWaitTimeRate != null)
            return cancelHandlerWaitTimeRate;
        cancelHandlerWaitTimeRate = Integer.parseInt(properties.getProperty("cancel.handler.wait.time.rate"));
        return cancelHandlerWaitTimeRate;
    }

    public Integer getCancelHandlerWaitTimeMax() {
        if (cancelHandlerWaitTimeMax != null)
            return cancelHandlerWaitTimeMax;
        cancelHandlerWaitTimeMax = Integer.parseInt(properties.getProperty("cancel.handler.wait.time.max"));
        return cancelHandlerWaitTimeMax;
    }
}
