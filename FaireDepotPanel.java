package pk1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@SuppressWarnings("serial")
public class FaireDepotPanel extends JPanel {
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public FaireDepotPanel(CardLayout cardLayout, JPanel cardPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;

        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Faire un dépôt", SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(3, 2));
        JLabel accountNumberLabel = new JLabel("Numéro de compte:");
        JTextField accountNumberField = new JTextField();
        JLabel amountLabel = new JLabel("Montant:");
        JTextField amountField = new JTextField();
        formPanel.add(accountNumberLabel);
        formPanel.add(accountNumberField);
        formPanel.add(amountLabel);
        formPanel.add(amountField);
        add(formPanel, BorderLayout.CENTER);

        JButton validerButton = new JButton("Valider");
        JButton retourButton = new JButton("Retour");
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(validerButton);
        buttonPanel.add(retourButton);
        add(buttonPanel, BorderLayout.SOUTH);

        validerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String accountNumber = accountNumberField.getText();
                String amount = amountField.getText();

                // Connexion à la base de données
                String url = "jdbc:mysql://localhost:3306/bank";
                String user = "root";
                String password = "M@rcel2004";

                try (Connection conn = DriverManager.getConnection(url, user, password)) {
                    // Requête SQL pour créditer le compte
                    String sql = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, amount);
                        stmt.setString(2, accountNumber);
                        int updatedRows = stmt.executeUpdate();
                        if (updatedRows == 0) {
                            JOptionPane.showMessageDialog(null, "Compte invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                    // Affichage d'un message de succès
                    JOptionPane.showMessageDialog(null, "Dépôt effectué avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erreur lors de l'exécution du dépôt", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        retourButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "OPTIONS_PAGE");
            }
        });
    }
}
