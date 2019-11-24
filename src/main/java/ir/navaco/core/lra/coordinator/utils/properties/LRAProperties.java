package ir.navaco.core.lra.coordinator.utils.properties;

import ir.navaco.core.lra.coordinator.exception.SystemException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LRAProperties {

    private Properties properties;
    private Integer corePoolSize;
    private Integer maximumPoolSize;

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
        this.corePoolSize = null;
        this.maximumPoolSize = null;
    }

    public Integer getCorePoolSize() {
        if (this.corePoolSize != null)
            return this.corePoolSize;
        this.corePoolSize = Integer.parseInt(properties.getProperty("core.pool.size"));
        return this.corePoolSize;
    }

    public Integer getMaximumPoolSize() {
        if (this.maximumPoolSize != null)
            return this.maximumPoolSize;
        this.maximumPoolSize = Integer.parseInt(properties.getProperty("maximum.pool.size"));
        return this.maximumPoolSize;
    }


}
