package properties;

import java.util.Properties;

public class DomainProperties {
    static final String DOMAIN_PROP_PATH = "domain.properties";

    static final Properties DOMAIN_PROPERTIES = new Properties();

    static {
        try {
            DOMAIN_PROPERTIES.load(DomainProperties.class.getClassLoader().getResourceAsStream(DOMAIN_PROP_PATH));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    public static String getDomain(){
        return DOMAIN_PROPERTIES.getProperty("domain");
    }
}