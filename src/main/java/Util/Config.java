package Util;

import static java.lang.String.format;
import static java.lang.System.getProperties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;


public class Config {

    private static final Logger LOG = Logger.getLogger(Config.class.getName());
    public static final String URI = "uri";
    private static final Properties ENV_PROPERTIES = getProperties();
    private static final String PATH_TO_ENV_PROPERTIES = format("/config/%s.properties", "slack");
    private static Properties properties = new Properties();

    static {
        try (InputStream loadPropertiesStream = Config.class.getResourceAsStream(PATH_TO_ENV_PROPERTIES)) {
            properties.load(loadPropertiesStream);
            properties.putAll(ENV_PROPERTIES);

            LOG.info(format("TID [%d] Was load properties file [%s]", Thread.currentThread().getId(), PATH_TO_ENV_PROPERTIES));

        } catch (IOException e) {
            throw new RuntimeException(format("Can't load config resources [%s]", PATH_TO_ENV_PROPERTIES), e);

        } catch (NullPointerException e) {
            throw new RuntimeException("Config resources is [null]", e);
        }
    }

    public static synchronized String getProperty(final String propKey) {
        if (properties.containsKey(propKey)) {
            LOG.debug("TID [" + Thread.currentThread().getId() + "] " + "Was loaded property (key) [" + propKey + "]");
        } else {
            LOG.debug("Property (key) [" + propKey + "] was NOT found.");
        }

        return properties.getProperty(propKey);
    }

    /* Blocking of 'extends' for this Class */
    private Config() {
        throw new UnsupportedOperationException("Illegal access to private constructor");
    }
}
