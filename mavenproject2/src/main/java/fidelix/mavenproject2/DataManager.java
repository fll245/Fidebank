package fidelix.mavenproject2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataManager {
    private static final Logger LOGGER = Logger.getLogger(DataManager.class.getName());
    private final Connection connection;

    public DataManager(Connection connection) {
        this.connection = connection;
    }

    /**
     * Verifica si una cuenta ya existe en la base de datos.
     * 
     * @param numeroCuenta El número de cuenta a verificar.
     * @return true si la cuenta existe, false en caso contrario.
     */
    public boolean existeCuenta(String numeroCuenta) {
        String sql = "SELECT 1 FROM clientes WHERE cuenta = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, numeroCuenta);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al verificar si la cuenta existe: {0}", e.getMessage());
            return false;
        }
    }

    /**
     * Registra un nuevo cliente en la base de datos.
     * 
     * @param cliente El objeto Cliente a registrar.
     */
    public void registrarCliente(Cliente cliente) {
        String sql = "INSERT INTO clientes (nombre, cedula, pin, cuenta, saldo) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, cliente.getNombre());
            pstmt.setString(2, cliente.getCedula());
            pstmt.setString(3, cliente.getPin());
            pstmt.setString(4, cliente.getCuenta().getNumeroCuenta());
            pstmt.setDouble(5, cliente.getCuenta().getSaldo());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al registrar cliente: {0}", e.getMessage());
        }
    }

    /**
     * Busca un cliente en la base de datos por su cédula.
     * 
     * @param cedula La cédula del cliente a buscar.
     * @return El objeto Cliente si se encuentra, null en caso contrario.
     */
    public Cliente buscarCliente(String cedula) {
        String sql = "SELECT * FROM clientes WHERE cedula = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, cedula);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String nombre = rs.getString("nombre");
                String pin = rs.getString("pin");
                String numeroCuenta = rs.getString("cuenta");
                double saldo = rs.getDouble("saldo");
                Cuenta cuenta = new Cuenta(numeroCuenta, saldo);
                return new Cliente(nombre, cedula, pin, cuenta);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al buscar el cliente: {0}", e.getMessage());
        }
        return null;
    }

    /**
     * Retira fondos de la cuenta de un cliente.
     * 
     * @param numeroCuenta El número de cuenta del cliente.
     * @param monto El monto a retirar.
     * @return true si el retiro es exitoso, false si no hay suficientes fondos.
     * @throws SaldoInsuficienteException si el saldo es insuficiente.
     */
    public boolean retirarFondos(String numeroCuenta, double monto) throws SaldoInsuficienteException {
        double saldoActual = obtenerSaldo(numeroCuenta);
        if (saldoActual >= monto) {
            actualizarSaldo(numeroCuenta, saldoActual - monto);
            registrarTransaccion(numeroCuenta, "retiro", monto, null);
            return true;
        } else {
            throw new SaldoInsuficienteException("Fondos insuficientes para realizar el retiro.");
        }
    }

    /**
     * Deposita fondos en la cuenta de un cliente.
     * 
     * @param numeroCuenta El número de cuenta del cliente.
     * @param monto El monto a depositar.
     * @return true si el depósito es exitoso.
     */
    public boolean depositarFondos(String numeroCuenta, double monto) {
        double saldoActual = obtenerSaldo(numeroCuenta);
        actualizarSaldo(numeroCuenta, saldoActual + monto);
        registrarTransaccion(numeroCuenta, "depósito", monto, null);
        return true;
    }

    /**
     * Transfiere fondos de una cuenta a otra.
     * 
     * @param cuentaOrigen El número de cuenta de origen.
     * @param cuentaDestino El número de cuenta de destino.
     * @param monto El monto a transferir.
     * @return true si la transferencia es exitosa.
     * @throws SaldoInsuficienteException si el saldo es insuficiente en la cuenta de origen.
     */
    public boolean transferirFondos(String cuentaOrigen, String cuentaDestino, double monto) throws SaldoInsuficienteException {
        try {
            connection.setAutoCommit(false);

            double saldoOrigen = obtenerSaldo(cuentaOrigen);
            if (saldoOrigen >= monto) {
                double saldoDestino = obtenerSaldo(cuentaDestino);
                actualizarSaldo(cuentaOrigen, saldoOrigen - monto);
                actualizarSaldo(cuentaDestino, saldoDestino + monto);
                registrarTransaccion(cuentaOrigen, "transferencia", monto, cuentaDestino);
                connection.commit();
                return true;
            } else {
                connection.rollback();
                throw new SaldoInsuficienteException("Fondos insuficientes para realizar la transferencia.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al transferir fondos: {0}", e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, "Error al hacer rollback: {0}", ex.getMessage());
            }
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error al restablecer el auto-commit: {0}", e.getMessage());
            }
        }
    }

    /**
     * Obtiene el saldo de la cuenta de un cliente.
     * 
     * @param numeroCuenta El número de cuenta del cliente.
     * @return El saldo actual de la cuenta.
     */
    public double obtenerSaldo(String numeroCuenta) {
        String sql = "SELECT saldo FROM clientes WHERE cuenta = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, numeroCuenta);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("saldo");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener el saldo: {0}", e.getMessage());
        }
        return 0.0;
    }

    /**
     * Actualiza el saldo de la cuenta de un cliente.
     * 
     * @param numeroCuenta El número de cuenta del cliente.
     * @param nuevoSaldo El nuevo saldo a establecer.
     */
    private void actualizarSaldo(String numeroCuenta, double nuevoSaldo) {
        String sql = "UPDATE clientes SET saldo = ? WHERE cuenta = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDouble(1, nuevoSaldo);
            pstmt.setString(2, numeroCuenta);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar el saldo: {0}", e.getMessage());
        }
    }

    /**
     * Registra una transacción en la base de datos.
     * 
     * @param numeroCuenta El número de cuenta del cliente.
     * @param tipoTransaccion El tipo de transacción realizada.
     * @param monto El monto de la transacción.
     * @param cuentaDestino El número de cuenta destino (en caso de transferencias), puede ser null.
     */
    private void registrarTransaccion(String numeroCuenta, String tipoTransaccion, double monto, String cuentaDestino) {
        String sql = "INSERT INTO transacciones (id_cliente, tipo_transaccion, monto, cuenta_destino) VALUES ((SELECT id FROM clientes WHERE cuenta = ?), ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, numeroCuenta);
            pstmt.setString(2, tipoTransaccion);
            pstmt.setDouble(3, monto);
            pstmt.setString(4, cuentaDestino);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al registrar la transacción: {0}", e.getMessage());
        }
    }
}
