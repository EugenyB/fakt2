package faktury.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Singleton object that performs and stores connection to DB
 * When created, reads connection properties from config file faktury.props
 */
public class DBConnection {
    private static DBConnection instance;
    private Connection connection;

    /**
     * private constructor for singleton object
     * creates connection to DB
     */
    private DBConnection() {
        Properties props = new Properties();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("faktury.props"))) {
            props.load(reader);
            connection = DriverManager.getConnection(props.getProperty("url"), props);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static DBConnection getInstance() {
        if (instance == null) instance = new DBConnection();
        return instance;
    }

    /**
     * use of connection
     * @return stored connection to DB
     */
    public Connection getConnection() {
        return connection;
    }
}
