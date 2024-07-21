/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fidebank;

import javax.swing.*;
import java.awt.*;

public class ATMFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private CajeroAutomatico cajeroAutomatico;

    public ATMFrame(CajeroAutomatico cajeroAutomatico) {
        this.cajeroAutomatico = cajeroAutomatico;
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Añadir los paneles al mainPanel
        mainPanel.add(new LoginPanel(this), "Login");
        mainPanel.add(new TransactionPanel(this), "Transaction");
        mainPanel.add(new AccountCreationPanel(this), "AccountCreation");

        add(mainPanel);
        setTitle("FideBank ATM");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }

    public CajeroAutomatico getCajeroAutomatico() {
        return cajeroAutomatico;
    }

    public static void main(String[] args) {
        // Inicializa el cajero automático con algunos datos de prueba
        CajeroAutomatico cajero = new CajeroAutomatico();
        ATMFrame frame = new ATMFrame(cajero);
        frame.setVisible(true);
    }
}
