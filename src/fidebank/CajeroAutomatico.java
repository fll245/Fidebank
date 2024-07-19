package fidebank;

import java.util.HashMap;
import java.util.Map;

public class CajeroAutomatico {
    private Map<String, Cliente> clientes;

    public CajeroAutomatico() {
        this.clientes = new HashMap<>();
    }

    public void registrarCliente(Cliente cliente) {
        clientes.put(cliente.getCedula(), cliente);
    }

    public Cliente autenticarCliente(String cedula, String pin) throws Exception {
        Cliente cliente = clientes.get(cedula);
        if (cliente == null || !cliente.autenticar(pin)) {
            throw new Exception("Autenticación fallida.");
        }
        return cliente;
    }

    public void imprimirComprobante(Cliente cliente) {
        System.out.println("Comprobante de transacciones para la cuenta: " + cliente.getCuenta().getNumeroCuenta());
        for (String transaccion : cliente.getCuenta().getTransacciones()) {
            System.out.println(transaccion);
        }
        System.out.println("Saldo actual: " + cliente.getCuenta().getSaldo());
    }
}
