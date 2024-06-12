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
public class FaireVirementPanel extends JPanel {
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public FaireVirementPanel(CardLayout cardLayout, JPanel cardPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;

        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Faire un virement", SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(4, 2));
        JLabel fromAccountLabel = new JLabel("De (numéro de compte):");
        JTextField fromAccountField = new JTextField();
        JLabel toAccountLabel = new JLabel("À (numéro de compte):");
        JTextField toAccountField = new JTextField();
        JLabel amountLabel = new JLabel("Montant:");
        JTextField amountField = new JTextField();
        formPanel.add(fromAccountLabel);
        formPanel.add(fromAccountField);
        formPanel.add(toAccountLabel);
        formPanel.add(toAccountField);
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
                String fromAccount = fromAccountField.getText();
                String toAccount = toAccountField.getText();
                String amount = amountField.getText();

                // Connexion à la base de données
                String url = "jdbc:mysql://localhost:3306/bank";
                String user = "root";
                String password = "M@rcel2004";

                try (Connection conn = DriverManager.getConnection(url, user, password)) {
                    // Requête SQL pour effectuer le virement
                    String sql = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, amount);
                        stmt.setString(2, fromAccount);
                        int updatedRows = stmt.executeUpdate();
                        if (updatedRows == 0) {
                            JOptionPane.showMessageDialog(null, "Compte source invalide ou solde insuffisant", "Erreur", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                    sql = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, amount);
                        stmt.setString(2, toAccount);
                        int updatedRows = stmt.executeUpdate();
                        if (updatedRows == 0) {
                            JOptionPane.showMessageDialog(null, "Compte de destination invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                    // Affichage d'un message de succès
                    JOptionPane.showMessageDialog(null, "Virement effectué avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erreur lors de l'exécution du virement", "Erreur", JOptionPane.ERROR_MESSAGE);
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
