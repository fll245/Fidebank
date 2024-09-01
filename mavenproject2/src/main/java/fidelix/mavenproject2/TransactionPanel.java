package fidelix.mavenproject2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TransactionPanel extends JPanel {
    private ATMFrame parentFrame;
    private CajeroAutomatico cajeroAutomatico;

    public TransactionPanel(ATMFrame parentFrame, CajeroAutomatico cajeroAutomatico) {
        this.parentFrame = parentFrame;
        this.cajeroAutomatico = cajeroAutomatico;
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridLayout(5, 1, 10, 10));

        JButton retiroButton = new JButton("Retiro de fondos");
        retiroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implementar la funcionalidad de retiro de fondos aquí
                double monto = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el monto a retirar:"));
                try {
                    if (cajeroAutomatico.retirarFondos(monto)) {
                        JOptionPane.showMessageDialog(TransactionPanel.this, "Retiro exitoso.");
                    } else {
                        JOptionPane.showMessageDialog(TransactionPanel.this, "Fondos insuficientes.");
                    }
                } catch (SaldoInsuficienteException ex) {
                    JOptionPane.showMessageDialog(TransactionPanel.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(retiroButton);

        JButton depositoButton = new JButton("Depósito de dinero");
        depositoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implementar la funcionalidad de depósito aquí
                double monto = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el monto a depositar:"));
                if (cajeroAutomatico.depositarFondos(monto)) {
                    JOptionPane.showMessageDialog(TransactionPanel.this, "Depósito exitoso.");
                } else {
                    JOptionPane.showMessageDialog(TransactionPanel.this, "Error en el depósito.");
                }
            }
        });
        add(depositoButton);

        JButton transferenciaButton = new JButton("Transferencia entre cuentas");
        transferenciaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implementar la funcionalidad de transferencia aquí
                String cuentaDestino = JOptionPane.showInputDialog("Ingrese la cuenta de destino:");
                double monto = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el monto a transferir:"));
                try {
                    if (cajeroAutomatico.transferirFondos(cuentaDestino, monto)) {
                        JOptionPane.showMessageDialog(TransactionPanel.this, "Transferencia exitosa.");
                    } else {
                        JOptionPane.showMessageDialog(TransactionPanel.this, "Fondos insuficientes para la transferencia.");
                    }
                } catch (SaldoInsuficienteException ex) {
                    JOptionPane.showMessageDialog(TransactionPanel.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(transferenciaButton);

        JButton saldoButton = new JButton("Información de la cuenta");
        saldoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implementar la funcionalidad de consulta de saldo aquí
                double saldo = cajeroAutomatico.consultarSaldo();
                JOptionPane.showMessageDialog(TransactionPanel.this, "Su saldo es: " + saldo);
            }
        });
        add(saldoButton);

        JButton salirButton = new JButton("Salir");
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cajeroAutomatico.cerrarSesion();
                parentFrame.showPanel("Login");
            }
        });
        add(salirButton);
    }
}
