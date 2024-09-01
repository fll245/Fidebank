package fidelix.mavenproject2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestConexion {

    public static void main(String[] args) {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connection URL for MySQL database using XAMPP
            String connectionUrl = "jdbc:mysql://localhost:3306/fidebank";  // Change 'fidebank' if your database has a different name
            String user = "root";  // Default XAMPP MySQL username
            String password = "";  // Leave blank if you don't have a password

            ResultSet resultSet = null;

            try (Connection connection = DriverManager.getConnection(connectionUrl, user, password);
                 Statement statement = connection.createStatement()) {

                // Insert example data into the "clientes" table
                String insertInto = "INSERT INTO clientes(id, nombre, cedula, pin, cuenta, saldo) " +
                                    "VALUES(3, 'Jose Joaquin', '11223344C', '5678', '123456780', 1000.00)";
                int rowsAffected = statement.executeUpdate(insertInto);
                System.out.println("Rows affected: " + rowsAffected);

                // Select all records from the "clientes" table
                String selectSql = "SELECT * FROM clientes";
                resultSet = statement.executeQuery(selectSql);

                // Print the results
                while (resultSet.next()) {
                    System.out.println("ID: " + resultSet.getInt("id"));
                    System.out.println("Nombre: " + resultSet.getString("nombre"));
                    System.out.println("Cédula: " + resultSet.getString("cedula"));
                    System.out.println("PIN: " + resultSet.getString("pin"));
                    System.out.println("Cuenta: " + resultSet.getString("cuenta"));
                    System.out.println("Saldo: " + resultSet.getDouble("saldo"));
                    System.out.println("----------------------");
                }

            } catch (SQLException e) {
            }

        } catch (ClassNotFoundException e) {
        }
    }
}
