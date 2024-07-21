/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fidebank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccountCreationPanel extends JPanel {
    private JTextField nombreField;
    private JTextField cedulaField;
    private JPasswordField pinField;
    private JTextField saldoInicialField;
    private ATMFrame parent;

    public AccountCreationPanel(ATMFrame parent) {
        this.parent = parent;
        setLayout(new GridLayout(6, 2));

        add(new JLabel("Nombre:"));
        nombreField = new JTextField();
        add(nombreField);

        add(new JLabel("Cédula:"));
        cedulaField = new JTextField();
        add(cedulaField);

        add(new JLabel("PIN:"));
        pinField = new JPasswordField();
        add(pinField);

        add(new JLabel("Saldo Inicial:"));
        saldoInicialField = new JTextField();
        add(saldoInicialField);

        JButton createButton = new JButton("Crear Cuenta");
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = nombreField.getText();
                String cedula = cedulaField.getText();
                String pin = new String(pinField.getPassword());
                double saldoInicial = Double.parseDouble(saldoInicialField.getText());

                Cuenta nuevaCuenta = new Cuenta(cedula, saldoInicial); // Asumimos que el número de cuenta es la cédula
                Cliente nuevoCliente = new Cliente(nombre, cedula, pin, nuevaCuenta);
                parent.getCajeroAutomatico().registrarCliente(nuevoCliente);

                JOptionPane.showMessageDialog(AccountCreationPanel.this, "Cuenta creada exitosamente!");
                parent.showPanel("Login");
            }
        });
        add(createButton);

        JButton backButton = new JButton("Volver");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.showPanel("Login");
            }
        });
        add(backButton);
    }
}
