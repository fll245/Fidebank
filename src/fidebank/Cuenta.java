package fidebank;

import java.util.ArrayList;
import java.util.List;

public class Cuenta {
    private String numeroCuenta;
    private double saldo;
    private List<String> transacciones;

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

    public List<String> getTransacciones() {
        return transacciones;
    }

    public void retirar(double monto) throws Exception {
        if (monto > saldo) {
            throw new Exception("Fondos insuficientes.");
        }
        saldo -= monto;
        transacciones.add("Retiro: " + monto);
    }

    public void depositar(double monto) {
        saldo += monto;
        transacciones.add("Depósito: " + monto);
    }

    public void transferir(Cuenta destino, double monto) throws Exception {
        if (monto > saldo) {
            throw new Exception("Fondos insuficientes.");
        }
        saldo -= monto;
        destino.depositar(monto);
        transacciones.add("Transferencia a " + destino.getNumeroCuenta() + ": " + monto);
    }
}
