package pk1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@SuppressWarnings("serial")
public class AfficherComptePanel extends JPanel {
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public AfficherComptePanel(CardLayout cardLayout, JPanel cardPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;

        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Affichage de compte", SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(2, 2));
        JLabel accountNumberLabel = new JLabel("Numéro de compte:");
        JTextField accountNumberField = new JTextField();
        formPanel.add(accountNumberLabel);
        formPanel.add(accountNumberField);
        add(formPanel, BorderLayout.CENTER);

        JButton afficherButton = new JButton("Afficher");
        JButton retourButton = new JButton("Retour");
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(afficherButton);
        buttonPanel.add(retourButton);
        add(buttonPanel, BorderLayout.SOUTH);

        afficherButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String accountNumber = accountNumberField.getText();
                // Connexion à la base de données et récupération des informations du compte
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "M@rcel2004");
                    String query = "SELECT * FROM accounts WHERE account_number = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, accountNumber);
                    ResultSet resultSet = statement.executeQuery();

                    // Affichage des détails du compte
                    if (resultSet.next()) {
                        JFrame detailsFrame = new JFrame("Détails du compte");
                        detailsFrame.setSize(300, 200);
                        detailsFrame.setLayout(new GridLayout(6, 2));

                        detailsFrame.add(new JLabel("Numéro de compte:"));
                        detailsFrame.add(new JLabel(resultSet.getString("account_number")));
                        detailsFrame.add(new JLabel("Prénom:"));
                        detailsFrame.add(new JLabel(resultSet.getString("firstname")));
                        detailsFrame.add(new JLabel("Nom:"));
                        detailsFrame.add(new JLabel(resultSet.getString("lastname")));
                        detailsFrame.add(new JLabel("Type de compte:"));
                        detailsFrame.add(new JLabel(resultSet.getString("account_type")));
                        detailsFrame.add(new JLabel("Balance:"));
                        detailsFrame.add(new JLabel(resultSet.getDouble("balance") + " €"));
                        detailsFrame.add(new JLabel("Taux d'intérêt:"));
                        detailsFrame.add(new JLabel(resultSet.getDouble("interest_rate") + "%"));

                        detailsFrame.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Aucun compte trouvé avec ce numéro de compte", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }

                    // Fermeture des ressources
                    resultSet.close();
                    statement.close();
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erreur lors de la récupération des informations du compte", "Erreur", JOptionPane.ERROR_MESSAGE);
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
