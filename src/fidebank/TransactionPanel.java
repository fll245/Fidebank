/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fidebank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TransactionPanel extends JPanel {
    private ATMFrame parent;

    public TransactionPanel(ATMFrame parent) {
        this.parent = parent;
        setLayout(new GridLayout(5, 1));

        JButton retiroButton = new JButton("Retiro de fondos");
        retiroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String montoStr = JOptionPane.showInputDialog("Ingrese el monto a retirar:");
                double monto = Double.parseDouble(montoStr);

                try {
                    parent.getCajeroAutomatico().getClienteActual().getCuenta().retirar(monto);
                    JOptionPane.showMessageDialog(TransactionPanel.this, "Retiro exitoso!");
                    parent.getCajeroAutomatico().imprimirComprobante(parent.getCajeroAutomatico().getClienteActual());
                } catch (SaldoInsuficienteException ex) {
                    JOptionPane.showMessageDialog(TransactionPanel.this, ex.getMessage());
                }
            }
        });
        add(retiroButton);

        JButton depositoButton = new JButton("Depósito de dinero");
        depositoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String montoStr = JOptionPane.showInputDialog("Ingrese el monto a depositar:");
                double monto = Double.parseDouble(montoStr);

                parent.getCajeroAutomatico().getClienteActual().getCuenta().depositar(monto);
                JOptionPane.showMessageDialog(TransactionPanel.this, "Depósito exitoso!");
                parent.getCajeroAutomatico().imprimirComprobante(parent.getCajeroAutomatico().getClienteActual());
            }
        });
        add(depositoButton);

        JButton transferenciaButton = new JButton("Transferencia entre cuentas");
        transferenciaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cuentaDestinoStr = JOptionPane.showInputDialog("Ingrese el número de cuenta destino:");
                String montoStr = JOptionPane.showInputDialog("Ingrese el monto a transferir:");
                double monto = Double.parseDouble(montoStr);

                try {
                    Cuenta cuentaDestino = parent.getCajeroAutomatico().buscarCuenta(cuentaDestinoStr);
                    parent.getCajeroAutomatico().getClienteActual().getCuenta().transferir(cuentaDestino, monto);
                    JOptionPane.showMessageDialog(TransactionPanel.this, "Transferencia exitosa!");
                    parent.getCajeroAutomatico().imprimirComprobante(parent.getCajeroAutomatico().getClienteActual());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(TransactionPanel.this, ex.getMessage());
                }
            }
        });
        add(transferenciaButton);

        JButton infoCuentaButton = new JButton("Información de la cuenta");
        infoCuentaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cuenta cuenta = parent.getCajeroAutomatico().getClienteActual().getCuenta();
                String info = "Número de cuenta: " + cuenta.getNumeroCuenta() + "\nSaldo: " + cuenta.getSaldo();
                JOptionPane.showMessageDialog(TransactionPanel.this, info);
            }
        });
        add(infoCuentaButton);

        JButton backButton = new JButton("Salir");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.showPanel("Login");
            }
        });
        add(backButton);
    }
}
