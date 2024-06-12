package pk1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ConnexionPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton retourButton;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public ConnexionPanel(CardLayout cardLayout, JPanel cardPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;

        setLayout(new GridLayout(4, 2)); // Changement de GridLayout pour inclure le bouton retour

        add(new JLabel("Username:"));
        usernameField = new JTextField(15);
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField(15);
        add(passwordField);

        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Authenticating...");

                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (authenticateUser(username, password)) {
                    System.out.println("Authentication successful");
                    ((GestionComptesBancaires) SwingUtilities.getWindowAncestor(ConnexionPanel.this)).setUserId(13);
                    cardLayout.show(cardPanel, "OPTIONS_PAGE"); // Afficher les options en cas de succès
                } else {
                    System.out.println("Authentication failed");
                    JOptionPane.showMessageDialog(ConnexionPanel.this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(loginButton);

        retourButton = new JButton("Retour");
        retourButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "ACCUEIL");
            }
        });
        add(retourButton);
    }

    private boolean authenticateUser(String username, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/Bank";
            String dbUsername = "root";
            String dbPassword = "M@rcel2004"; // Mettez votre mot de passe MySQL ici
            Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);

            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            boolean authenticated = resultSet.next();

            resultSet.close();
            statement.close();
            connection.close();

            return authenticated;
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de l'authentification", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
