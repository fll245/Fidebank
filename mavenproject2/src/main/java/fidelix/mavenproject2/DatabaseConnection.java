package fidelix.mavenproject2;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnection {
    private static final Logger LOGGER = Logger.getLogger(DatabaseConnection.class.getName());

    private static final String URL = "jdbc:mysql://localhost:3306/fidebank";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection = null;

    private DatabaseConnection() {
        // Previene la creación de instancias
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                LOGGER.info("Conexión a la base de datos establecida.");
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error al conectar con la base de datos: {0}", e.getMessage());
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                LOGGER.info("Conexión a la base de datos cerrada.");
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error al cerrar la conexión a la base de datos: {0}", e.getMessage());
            } finally {
                connection = null;
            }
        }
    }
}
