package fidebank;

import java.util.HashMap;
import java.util.Map;

public class CajeroAutomatico {
    private Map<String, Cliente> clientes;

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
        return cliente;
    }

    void imprimirComprobante(Cliente clienteAutenticado) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

