/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fidebank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {
    private JTextField cedulaField;
    private JPasswordField pinField;
    private ATMFrame parent;

    public LoginPanel(ATMFrame parent) {
        this.parent = parent;
        setLayout(new GridLayout(4, 2));

        add(new JLabel("Digite su cédula:"));
        cedulaField = new JTextField();
        add(cedulaField);

        add(new JLabel("Digite su pin de seguridad:"));
        pinField = new JPasswordField();
        add(pinField);

        JButton loginButton = new JButton("Ingresar");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String cedula = cedulaField.getText();
                    String pin = new String(pinField.getPassword());
                    parent.getCajeroAutomatico().autenticarCliente(cedula, pin);
                    parent.showPanel("Transaction");
                } catch (ClienteNoEncontradoException ex) {
                    JOptionPane.showMessageDialog(LoginPanel.this, "Cliente no encontrado o PIN incorrecto");
                }
            }
        });
        add(loginButton);

        JButton createAccountButton = new JButton("Crear cuenta nueva");
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.showPanel("AccountCreation");
            }
        });
        add(createAccountButton);
    }
}
