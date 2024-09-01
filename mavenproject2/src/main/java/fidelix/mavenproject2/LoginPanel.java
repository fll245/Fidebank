package fidelix.mavenproject2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {
    private JTextField cedulaField;
    private JPasswordField pinField;
    private ATMFrame parentFrame;

    public LoginPanel(ATMFrame parentFrame) {
        this.parentFrame = parentFrame;
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("Cédula:"));
        cedulaField = new JTextField();
        add(cedulaField);

        add(new JLabel("PIN:"));
        pinField = new JPasswordField();
        add(pinField);

        JButton loginButton = new JButton("Iniciar sesión");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cedula = cedulaField.getText();
                String pin = new String(pinField.getPassword());

                try {
                    CajeroAutomatico cajeroAutomatico = parentFrame.getCajeroAutomatico();
                    cajeroAutomatico.autenticarCliente(cedula, pin);
                    parentFrame.showPanel("Transaction");
                } catch (ClienteNoEncontradoException ex) {
                    JOptionPane.showMessageDialog(LoginPanel.this, "Cédula o PIN incorrecto.", "Error de autenticación", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(loginButton);

        JButton createAccountButton = new JButton("Crear cuenta");
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.showPanel("AccountCreation");
            }
        });
        add(createAccountButton);
    }
}
