package fidelix.mavenproject2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class ATMFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private CajeroAutomatico cajeroAutomatico;
    private Connection connection;

    public ATMFrame(CajeroAutomatico cajeroAutomatico, Connection connection) {
        this.cajeroAutomatico = cajeroAutomatico;
        this.connection = connection;
        initComponents();
    }

    private void initComponents() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Panels for different screens
        LoginPanel loginPanel = new LoginPanel(this);
        AccountCreationPanel accountCreationPanel = new AccountCreationPanel(this, cajeroAutomatico.getDataManager());
        TransactionPanel transactionPanel = new TransactionPanel(this, cajeroAutomatico);

        // Add panels to the main panel with card layout
        mainPanel.add(loginPanel, "Login");
        mainPanel.add(accountCreationPanel, "AccountCreation");
        mainPanel.add(transactionPanel, "Transaction");

        // Add main panel to the frame
        this.add(mainPanel);
        this.setTitle("FideBank ATM");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);
    }

    public void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }

    public CajeroAutomatico getCajeroAutomatico() {
        return cajeroAutomatico;
    }

    public Connection getDatabaseConnection() {
        return connection;
    }

    // Panel para la creación de cuentas
    private class AccountCreationPanel extends JPanel {
        private JTextField nombreField;
        private JTextField cedulaField;
        private JPasswordField pinField;
        private JTextField saldoInicialField;
        private DataManager dataManager;

        public AccountCreationPanel(JFrame parentFrame, DataManager dataManager) {
            this.dataManager = dataManager;
            setLayout(new GridLayout(5, 2, 10, 10));

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

                    if (nombre.isEmpty() || cedula.isEmpty() || pin.isEmpty() || saldoInicialField.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(AccountCreationPanel.this, "Todos los campos son obligatorios.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    double saldoInicial;
                    try {
                        saldoInicial = Double.parseDouble(saldoInicialField.getText());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(AccountCreationPanel.this, "El saldo inicial debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    Cuenta nuevaCuenta = new Cuenta(cedula, saldoInicial);
                    Cliente nuevoCliente = new Cliente(nombre, cedula, pin, nuevaCuenta);

                    if (!dataManager.existeCuenta(cedula)) {
                        dataManager.registrarCliente(nuevoCliente);
                        JOptionPane.showMessageDialog(AccountCreationPanel.this, "Cuenta creada exitosamente!");
                        ((ATMFrame) parentFrame).showPanel("Login");
                    } else {
                        JOptionPane.showMessageDialog(AccountCreationPanel.this, "La cuenta ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            add(createButton);

            JButton backButton = new JButton("Volver");
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ((ATMFrame) parentFrame).showPanel("Login");
                }
            });
            add(backButton);
        }
    }
}
