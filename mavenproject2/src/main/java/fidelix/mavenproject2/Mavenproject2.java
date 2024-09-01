package fidelix.mavenproject2;

import java.sql.Connection;

public class Mavenproject2 {
    public static void main(String[] args) {
        // Conectar a la base de datos
        Connection connection = DatabaseConnection.getConnection();

        if (connection != null) {
            System.out.println("Conexión exitosa con la base de datos fidebank.");
            DataManager dataManager = new DataManager(connection);

            // Crear clientes y verificar si las cuentas ya existen antes de registrarlas
            Cuenta cuenta1 = new Cuenta("123456789", 1000.0);
            Cliente cliente1 = new Cliente("Juan Perez", "12345678A", "1234", cuenta1);
            if (!dataManager.existeCuenta(cuenta1.getNumeroCuenta())) {
                dataManager.registrarCliente(cliente1);
            }

            Cuenta cuenta2 = new Cuenta("987654321", 500.0);
            Cliente cliente2 = new Cliente("Maria Gomez", "87654321B", "2345", cuenta2);
            if (!dataManager.existeCuenta(cuenta2.getNumeroCuenta())) {
                dataManager.registrarCliente(cliente2);
            }

            // Registro de clientes en el cajero automático
            CajeroAutomatico cajero = new CajeroAutomatico(dataManager);

            // Iniciar la interfaz gráfica
            javax.swing.SwingUtilities.invokeLater(() -> {
                ATMFrame frame = new ATMFrame(cajero, connection);
                frame.setVisible(true);
            });
        } else {
            System.out.println("No se pudo establecer la conexión con la base de datos.");
        }
    }
}

