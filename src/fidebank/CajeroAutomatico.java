package fidebank;

import java.util.HashMap;
import java.util.Map;

public class CajeroAutomatico {
    private Map<String, Cliente> clientes;
    private Cliente clienteActual;

    public CajeroAutomatico() {
        clientes = new HashMap<>();
    }

    public void registrarCliente(Cliente cliente) {
        clientes.put(cliente.getCedula(), cliente);
    }

    public Cliente autenticarCliente(String cedula, String pin) throws ClienteNoEncontradoException {
        Cliente cliente = clientes.get(cedula);
        if (cliente == null || !cliente.autenticar(pin)) {
            throw new ClienteNoEncontradoException("Cliente no encontrado o PIN incorrecto");
        }
        this.clienteActual = cliente;
        return cliente;
    }

    public Cliente getClienteActual() {
        return clienteActual;
    }

    public Cuenta buscarCuenta(String numeroCuenta) throws Exception {
        for (Cliente cliente : clientes.values()) {
            if (cliente.getCuenta().getNumeroCuenta().equals(numeroCuenta)) {
                return cliente.getCuenta();
            }
        }
        throw new Exception("Cuenta no encontrada");
    }

    public void imprimirComprobante(Cliente cliente) {
        System.out.println("Imprimiendo comprobante para " + cliente.getNombre());
        for (Transaccion t : cliente.getCuenta().getTransacciones()) {
            System.out.println(t.getTipo() + ": " + t.getMonto() + " en " + t.getFecha());
        }
    }
}
