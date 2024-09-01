package fidelix.mavenproject2;

import java.util.logging.Logger;

public class CajeroAutomatico {
    private static final Logger LOGGER = Logger.getLogger(CajeroAutomatico.class.getName());

    private Cliente clienteActual;
    private final DataManager dataManager;

    public CajeroAutomatico(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public Cliente getClienteActual() {
        return clienteActual;
    }

    public void setClienteActual(Cliente cliente) {
        this.clienteActual = cliente;
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public Cliente autenticarCliente(String cedula, String pin) throws ClienteNoEncontradoException {
        Cliente cliente = dataManager.buscarCliente(cedula);
        if (cliente != null && cliente.autenticar(pin)) {
            setClienteActual(cliente);
            return cliente;
        }
        throw new ClienteNoEncontradoException("Cliente no encontrado o PIN incorrecto");
    }

    public boolean retirarFondos(double monto) throws SaldoInsuficienteException {
        if (clienteActual == null) {
            throw new IllegalStateException("No hay un cliente autenticado");
        }
        return dataManager.retirarFondos(clienteActual.getCuenta().getNumeroCuenta(), monto);
    }

    public boolean depositarFondos(double monto) {
        if (clienteActual == null) {
            throw new IllegalStateException("No hay un cliente autenticado");
        }
        return dataManager.depositarFondos(clienteActual.getCuenta().getNumeroCuenta(), monto);
    }

    public boolean transferirFondos(String cuentaDestino, double monto) throws SaldoInsuficienteException {
        if (clienteActual == null) {
            throw new IllegalStateException("No hay un cliente autenticado");
        }
        return dataManager.transferirFondos(clienteActual.getCuenta().getNumeroCuenta(), cuentaDestino, monto);
    }

    public double consultarSaldo() {
        if (clienteActual == null) {
            throw new IllegalStateException("No hay un cliente autenticado");
        }
        return dataManager.obtenerSaldo(clienteActual.getCuenta().getNumeroCuenta());
    }

    public void cerrarSesion() {
        clienteActual = null;
    }
}
