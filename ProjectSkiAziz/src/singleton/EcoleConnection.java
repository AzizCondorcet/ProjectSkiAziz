package singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class EcoleConnection {
    private static EcoleConnection instance;
    private Connection connect;

    private EcoleConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@//193.190.64.10:1522/xepdb1";
            String username = "STUDENT03_08"; 
            String password = "changeme"; 
            this.connect = DriverManager.getConnection(url, username, password);
            if (this.connect != null) 
            {
                System.out.println("Connected to: " + this.connect.getMetaData().getURL());
                System.out.println("Database User: " + this.connect.getMetaData().getUserName());
                System.out.println("Connected to database: " + this.connect.getMetaData().getURL());
            } else {
                System.out.println("Connection is null!");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // Gérer l'exception si le driver n'est pas trouvé
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer les erreurs SQL
        }
    }

    public static EcoleConnection getInstance() {
        if (instance == null) {
            instance = new EcoleConnection();
        }
        return instance;
    }

    public Connection getConnect() {
        return connect;
    }
}
