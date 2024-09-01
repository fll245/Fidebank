package fidelix.mavenproject2;

import java.util.ArrayList;
import java.util.List;

public class Cuenta {
    private String numeroCuenta;
    private double saldo;
    private List<Transaccion> transacciones;

    public Cuenta(String numeroCuenta, double saldoInicial) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldoInicial;
        this.transacciones = new ArrayList<>();
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public List<Transaccion> getTransacciones() {
        return transacciones;
    }

    public void agregarTransaccion(Transaccion transaccion) {
        transacciones.add(transaccion);
    }
}
