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
        setLayout(new GridLayout(4, 1));

        JButton retiroButton = new JButton("Retiro de fondos");
        retiroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para retiro de fondos
            }
        });
        add(retiroButton);

        JButton depositoButton = new JButton("Depósito de dinero");
        depositoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para depósito de dinero
            }
        });
        add(depositoButton);

        JButton transferenciaButton = new JButton("Transferencia entre cuentas");
        transferenciaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para transferencia entre cuentas
            }
        });
        add(transferenciaButton);

        JButton infoCuentaButton = new JButton("Información de la cuenta");
        infoCuentaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para mostrar información de la cuenta
            }
        });
        add(infoCuentaButton);
    }
}
