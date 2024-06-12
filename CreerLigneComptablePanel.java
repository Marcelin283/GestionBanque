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
public class CreerLigneComptablePanel extends JPanel {
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public CreerLigneComptablePanel(CardLayout cardLayout, JPanel cardPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;

        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Création de ligne comptable", SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(4, 2));
        JLabel accountNumberLabel = new JLabel("Numéro de compte:");
        JTextField accountNumberField = new JTextField();
        JLabel amountLabel = new JLabel("Montant:");
        JTextField amountField = new JTextField();
        JLabel descriptionLabel = new JLabel("Description:");
        JTextField descriptionField = new JTextField();
        formPanel.add(accountNumberLabel);
        formPanel.add(accountNumberField);
        formPanel.add(amountLabel);
        formPanel.add(amountField);
        formPanel.add(descriptionLabel);
        formPanel.add(descriptionField);
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
                String description = descriptionField.getText();
                insertLigneComptable(accountNumber, amount, description);
            }
        });

        retourButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "OPTIONS_PAGE");
            }
        });
    }

    private void insertLigneComptable(String accountNumber, String amount, String description) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/Bank";
            String dbUsername = "root";
            String dbPassword = "M@rcel2004";

            Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);

            String insertQuery = "INSERT INTO ligne_comptable (account_number, amount, description) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(insertQuery);
            statement.setString(1, accountNumber);
            statement.setString(2, amount);
            statement.setString(3, description);

            statement.executeUpdate();

            statement.close();
            connection.close();

            JOptionPane.showMessageDialog(null, "Ligne comptable créée avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de la création de la ligne comptable", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
