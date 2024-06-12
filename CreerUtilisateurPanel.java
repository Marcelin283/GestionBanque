package pk1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

@SuppressWarnings("serial")
public class CreerUtilisateurPanel extends JPanel {
    private Connection connection;

    public CreerUtilisateurPanel(CardLayout cardLayout, JPanel cardPanel) {
        setLayout(new BorderLayout());

        JLabel phraseLabel = new JLabel("Veuillez créer un nouveau nom d'utilisateur et un mot de passe");
        phraseLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(phraseLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(2, 2));
        JLabel usernameLabel = new JLabel("Nom d'utilisateur:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Mot de passe:");
        JPasswordField passwordField = new JPasswordField();
        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        add(formPanel, BorderLayout.CENTER);

        JButton enregistrerButton = new JButton("Enregistrer");
        enregistrerButton.setBackground(new Color(78, 129, 66)); // Nuance de vert #4e8142
        JButton retourButton = new JButton("Retour");
        retourButton.setBackground(new Color(196, 43, 28)); // Couleur rouge
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(enregistrerButton);
        buttonPanel.add(retourButton);
        add(buttonPanel, BorderLayout.SOUTH);

        enregistrerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (utilisateurExisteDeja(username)) {
                    JOptionPane.showMessageDialog(null, "Un utilisateur avec ce nom existe déjà", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                enregistrerUtilisateur(username, password);

                usernameField.setText("");
                passwordField.setText("");

                JOptionPane.showMessageDialog(null, "Utilisateur enregistré avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
                cardLayout.show(cardPanel, "MENU");
            }
        });

        retourButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "ACCUEIL");
            }
        });
    }

    private boolean utilisateurExisteDeja(String username) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/Bank";
            String dbUsername = "root";
            String dbPassword = "M@rcel2004"; // Mettez votre mot de passe MySQL ici
            connection = DriverManager.getConnection(url, dbUsername, dbPassword);

            String sql = "SELECT * FROM users WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            boolean existe = resultSet.next();

            resultSet.close();
            statement.close();
            connection.close();

            return existe;
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de la vérification de l'existence de l'utilisateur", "Erreur", JOptionPane.ERROR_MESSAGE);
            return true;
        }
    }

    private void enregistrerUtilisateur(String username, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/Bank";
            String dbUsername = "root";
            String dbPassword = "M@rcel2004"; // Mettez votre mot de passe MySQL ici
            connection = DriverManager.getConnection(url, dbUsername, dbPassword);

            String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.executeUpdate();

            statement.close();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de l'enregistrement de l'utilisateur", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
