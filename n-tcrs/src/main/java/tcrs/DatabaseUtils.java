package tcrs;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseUtils {

    private static final String PROPERTIES_FILENAME = "database.properties";

    public static Connection getConnection() throws Exception {
        // Load the properties from the properties file
        Properties props = new Properties();
        InputStream inputStream = DatabaseUtils.class.getClassLoader().getResourceAsStream(PROPERTIES_FILENAME);
        props.load(inputStream);

        // Get the properties
        String driver = props.getProperty("db.driver");
        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        // Load the MySQL JDBC driver
        Class.forName(driver);

        // Create a connection to the database
        Connection conn = DriverManager.getConnection(url, user, password);

        return conn;
    }
}