package ir.navaco.core.lra.coordinator.utils.properties;

import ir.navaco.core.lra.coordinator.exception.SystemException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EurekaProperties {

    private Properties properties;
    private String edgeServerURL;

    public EurekaProperties(String classPathFileName) throws SystemException.PropertyFileException {
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
        this.edgeServerURL = null;
    }

    public String getEdgeServerURL() {
        if (this.edgeServerURL != null)
            return this.edgeServerURL;
        String temp = properties.getProperty("edgeserver.url");
        this.edgeServerURL = temp.endsWith("/") ? temp : temp + "/";
        return this.edgeServerURL;
    }


}
