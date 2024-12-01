package singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class EcoleConnection {
    private static EcoleConnection instance;
    private Connection connect;

    private EcoleConnection() {
        // Crée une connexion initiale
        createConnection();
    }

    private void createConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@//193.190.64.10:1522/xepdb1";
            String username = "STUDENT03_08";
            String password = "changeme";
            this.connect = DriverManager.getConnection(url, username, password);

            if (this.connect != null) {
                System.out.println("Connected to: " + this.connect.getMetaData().getURL());
                System.out.println("Database User: " + this.connect.getMetaData().getUserName());
            } else {
                System.out.println("Connection is null!");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // Gestion de l'erreur si le driver JDBC n'est pas trouvé
        } catch (SQLException e) {
            e.printStackTrace(); // Gestion des erreurs SQL
        }
    }

    public static EcoleConnection getInstance() {
        if (instance == null) {
            instance = new EcoleConnection();
        }
        return instance;
    }

    public Connection getConnect() {
        try {
            // Vérifiez si la connexion est toujours valide
            if (connect == null || connect.isClosed()) {
                System.out.println("Connection is closed or null. Reopening...");
                createConnection(); // Réouvre la connexion si elle est fermée
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la vérification de la connexion :");
            e.printStackTrace();
            createConnection(); // Réessaye d'ouvrir la connexion en cas d'échec
        }
        return connect;
    }
}
