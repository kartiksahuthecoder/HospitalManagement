package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBPropertyUtil {
    public static String getPropertyString(String fileName) throws IOException {
        Properties properties = new Properties();
        FileInputStream inputStream = new FileInputStream(fileName);
        properties.load(inputStream);
        return properties.getProperty("connectionString");
    }
}
