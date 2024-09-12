package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

    private final String dbName = "postgres";
    private final String user = "GreenPulse";
    private final String password = "";

   private static ConnectionUtil instance;

    private Connection connection;

    private ConnectionUtil() {
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/" + dbName;
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL Driver not found. Include the JDBC library in the classpath.");
        } catch (SQLException e) {
            System.out.println("Error while connecting to the PostgreSQL database.");
        }
    }

    public static ConnectionUtil getInstance() {
        if (instance == null) {
            synchronized (ConnectionUtil.class) {
                if (instance == null) {
                    instance = new ConnectionUtil();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
