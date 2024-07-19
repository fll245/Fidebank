package fidebank;

public class Fidebank {
    public static void main(String[] args) {
        // Creación de cuentas y clientes
        Cuenta cuenta1 = new Cuenta("123456789", 1000.0);
        Cliente cliente1 = new Cliente("Juan Perez", "12345678A", "1234", cuenta1);

        Cuenta cuenta2 = new Cuenta("987654321", 500.0);
        Cliente cliente2 = new Cliente("Maria Gomez", "87654321B", "5678", cuenta2);

        // Registro de clientes en el cajero automático
        CajeroAutomatico cajero = new CajeroAutomatico();
        cajero.registrarCliente(cliente1);
        cajero.registrarCliente(cliente2);

        try {
            // Autenticación del cliente
            Cliente clienteAutenticado = cajero.autenticarCliente("12345678A", "1234");

            // Realización de transacciones
            clienteAutenticado.getCuenta().depositar(200.0);
            clienteAutenticado.getCuenta().retirar(150.0);
            clienteAutenticado.getCuenta().transferir(cuenta2, 300.0);

            // Impresión de comprobante
            cajero.imprimirComprobante(clienteAutenticado);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
