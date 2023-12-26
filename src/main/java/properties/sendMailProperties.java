package properties;

import java.io.InputStream;
import java.util.Properties;

public class sendMailProperties {
    public static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = sendMailProperties.class.getClassLoader().getResourceAsStream("sendMail.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find mail.properties");
                return properties;
            }
            properties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties;
    }
}
