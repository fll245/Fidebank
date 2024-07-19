package fidebank;

public class Cliente {
    private String nombre;
    private String cedula;
    private String pin;
    private Cuenta cuenta;

    public Cliente(String nombre, String cedula, String pin, Cuenta cuenta) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.pin = pin;
        this.cuenta = cuenta;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public String getPin() {
        return pin;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public boolean autenticar(String pin) {
        return this.pin.equals(pin);
    }
}
