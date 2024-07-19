package fidebank;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cuenta implements Serializable {
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

    public List<Transaccion> getTransacciones() {
        return transacciones;
    }

    public void retirar(double monto) throws SaldoInsuficienteException {
        if (saldo < monto) {
            throw new SaldoInsuficienteException("Saldo insuficiente para retirar " + monto);
        }
        saldo -= monto;
        transacciones.add(new Transaccion("Retiro", monto));
    }

    public void depositar(double monto) {
        saldo += monto;
        transacciones.add(new Transaccion("Depósito", monto));
    }

    public void transferir(Cuenta destino, double monto) throws SaldoInsuficienteException {
        retirar(monto);
        destino.depositar(monto);
        transacciones.add(new Transaccion("Transferencia a " + destino.getNumeroCuenta(), monto));
    }
}
