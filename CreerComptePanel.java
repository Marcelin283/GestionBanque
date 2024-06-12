package pk1;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.Random;
//
//@SuppressWarnings("serial")
//public class CreerComptePanel extends JPanel {
//    private CardLayout cardLayout;
//    private JPanel cardPanel;
//    private int userId;
//
//    public CreerComptePanel(CardLayout cardLayout, JPanel cardPanel) {
//        this.cardLayout = cardLayout;
//        this.cardPanel = cardPanel;
//
//        setLayout(new BorderLayout());
//
//        JLabel titleLabel = new JLabel("Création de compte", SwingConstants.CENTER);
//        add(titleLabel, BorderLayout.NORTH);
//
//        JPanel formPanel = new JPanel(new GridLayout(6, 2));
//        JLabel firstnameLabel = new JLabel("Prénom:");
//        JTextField firstnameField = new JTextField();
//        JLabel lastnameLabel = new JLabel("Nom:");
//        JTextField lastnameField = new JTextField();
//        JLabel accountTypeLabel = new JLabel("Type de compte:");
//        JCheckBox courantCheckBox = new JCheckBox("Courant");
//        JCheckBox epargneCheckBox = new JCheckBox("Épargne");
//        JCheckBox jointCheckBox = new JCheckBox("Joint");
//        JLabel balanceLabel = new JLabel("Balance initiale:");
//        JTextField balanceField = new JTextField();
//
//        formPanel.add(firstnameLabel);
//        formPanel.add(firstnameField);
//        formPanel.add(lastnameLabel);
//        formPanel.add(lastnameField);
//        formPanel.add(accountTypeLabel);
//        formPanel.add(courantCheckBox);
//        formPanel.add(new JLabel());
//        formPanel.add(epargneCheckBox);
//        formPanel.add(new JLabel());
//        formPanel.add(jointCheckBox);
//        formPanel.add(balanceLabel);
//        formPanel.add(balanceField);
//        add(formPanel, BorderLayout.CENTER);
//
//        JButton validerButton = new JButton("Valider");
//        JButton retourButton = new JButton("Retour");
//        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
//        buttonPanel.add(validerButton);
//        buttonPanel.add(retourButton);
//        add(buttonPanel, BorderLayout.SOUTH);
//
//        validerButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                String firstname = firstnameField.getText();
//                String lastname = lastnameField.getText();
//                String accountType = "";
//                if (courantCheckBox.isSelected()) accountType = "courant";
//                else if (epargneCheckBox.isSelected()) accountType = "epargne";
//                else if (jointCheckBox.isSelected()) accountType = "joint";
//                else {
//                    JOptionPane.showMessageDialog(null, "Veuillez sélectionner un type de compte", "Erreur", JOptionPane.ERROR_MESSAGE);
//                    return;
//                }
//
//                double balance;
//                try {
//                    balance = Double.parseDouble(balanceField.getText());
//                } catch (NumberFormatException ex) {
//                    JOptionPane.showMessageDialog(null, "Veuillez entrer une balance valide", "Erreur", JOptionPane.ERROR_MESSAGE);
//                    return;
//                }
//
//                double interestRate = 0.0;
//                if (accountType.equals("courant")) interestRate = 1.0;
//                else if (accountType.equals("epargne")) interestRate = 3.0;
//                else if (accountType.equals("joint")) interestRate = 1.5;
//
//                String accountNumber = generateAccountNumber();
//
//                // Vérifier si l'utilisateur existe
//                if (!checkUserExists(userId)) {
//                    JOptionPane.showMessageDialog(null, "Utilisateur non trouvé. Veuillez vérifier l'ID utilisateur.", "Erreur", JOptionPane.ERROR_MESSAGE);
//                    return;
//                }
//
//                // Insertion des données du compte dans la base de données
//                insertAccountData(firstname, lastname, accountNumber, accountType, balance, interestRate);
//
//                // Réglez les détails du compte sur le panneau de résumé
//                ResumeCreationPanel resumePanel = (ResumeCreationPanel) cardPanel.getComponent(6);
//                resumePanel.setAccountDetails(firstname, lastname, accountNumber, accountType, balance, interestRate);
//
//                // Afficher le panneau de résumé
//                cardLayout.show(cardPanel, "RESUME_CREATION");
//            }
//        });
//
//        retourButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                cardLayout.show(cardPanel, "OPTIONS_PAGE");
//            }
//        });
//    }
//
//    private String generateAccountNumber() {
//        Random random = new Random();
//        StringBuilder accountNumber = new StringBuilder();
//        for (int i = 0; i < 10; i++) {
//            accountNumber.append(random.nextInt(10));
//        }
//        return accountNumber.toString();
//    }
//
//    private void insertAccountData(String firstname, String lastname, String accountNumber, String accountType, double balance, double interestRate) {
//        try {
//            // Charger le driver JDBC
//            Class.forName("com.mysql.cj.jdbc.Driver");
//
//            // Établir la connexion à la base de données
//            String url = "jdbc:mysql://localhost:3306/bank";
//            String dbUsername = "root";
//            String dbPassword = "M@rcel2004";
//            Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
//
//            // Préparer la requête SQL
//            String sql = "INSERT INTO accounts (user_id, firstname, lastname, account_number, account_type, balance, interest_rate) VALUES (?, ?, ?, ?, ?, ?, ?)";
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setInt(1, this.userId); // Utilisez l'ID utilisateur défini
//            preparedStatement.setString(2, firstname);
//            preparedStatement.setString(3, lastname);
//            preparedStatement.setString(4, accountNumber);
//            preparedStatement.setString(5, accountType);
//            preparedStatement.setDouble(6, balance);
//            preparedStatement.setDouble(7, interestRate);
//
//            // Exécuter la requête SQL
//            preparedStatement.executeUpdate();
//
//            // Fermer la connexion
//            connection.close();
//
//            JOptionPane.showMessageDialog(null, "Compte créé avec succès!");
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Erreur lors de la création du compte: " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    private boolean checkUserExists(int userId) {
//        try {
//            // Charger le driver JDBC
//            Class.forName("com.mysql.cj.jdbc.Driver");
//
//            // Établir la connexion à la base de données
//            String url = "jdbc:mysql://localhost:3306/bank";
//            String dbUsername = "root";
//            String dbPassword = "M@rcel2004";
//            Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
//
//            // Préparer la requête SQL
//            String sql = "SELECT id FROM users WHERE id = ?";
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setInt(1, userId);
//
//            // Exécuter la requête SQL
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            // Vérifier si l'utilisateur existe
//            boolean exists = resultSet.next();
//
//            // Fermer la connexion
//            connection.close();
//
//            return exists;
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Erreur lors de la vérification de l'utilisateur: " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
//            return false;
//        }
//    }
//
//    public void setUserId(int userId) {
//        this.userId = userId;
//        System.out.println("Set User ID in Create Account Panel: " + userId);
//    }
//}



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

@SuppressWarnings("serial")
public class CreerComptePanel extends JPanel {
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public CreerComptePanel(CardLayout cardLayout, JPanel cardPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;

        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Création de compte", SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(6, 2));
        JLabel firstnameLabel = new JLabel("Prénom:");
        JTextField firstnameField = new JTextField();
        JLabel lastnameLabel = new JLabel("Nom:");
        JTextField lastnameField = new JTextField();
        JLabel accountTypeLabel = new JLabel("Type de compte:");
        JCheckBox courantCheckBox = new JCheckBox("Courant");
        JCheckBox epargneCheckBox = new JCheckBox("Épargne");
        JCheckBox jointCheckBox = new JCheckBox("Joint");
        JLabel balanceLabel = new JLabel("Balance initiale:");
        JTextField balanceField = new JTextField();

        formPanel.add(firstnameLabel);
        formPanel.add(firstnameField);
        formPanel.add(lastnameLabel);
        formPanel.add(lastnameField);
        formPanel.add(accountTypeLabel);
        formPanel.add(courantCheckBox);
        formPanel.add(new JLabel());
        formPanel.add(epargneCheckBox);
        formPanel.add(new JLabel());
        formPanel.add(jointCheckBox);
        formPanel.add(balanceLabel);
        formPanel.add(balanceField);
        add(formPanel, BorderLayout.CENTER);

        JButton validerButton = new JButton("Valider");
        JButton retourButton = new JButton("Retour");
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(validerButton);
        buttonPanel.add(retourButton);
        add(buttonPanel, BorderLayout.SOUTH);

        validerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String firstname = firstnameField.getText();
                String lastname = lastnameField.getText();
                String accountType = "";
                if (courantCheckBox.isSelected()) accountType = "courant";
                else if (epargneCheckBox.isSelected()) accountType = "epargne";
                else if (jointCheckBox.isSelected()) accountType = "joint";
                else {
                    JOptionPane.showMessageDialog(null, "Veuillez sélectionner un type de compte", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double balance;
                try {
                    balance = Double.parseDouble(balanceField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Veuillez entrer une balance valide", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double interestRate = 0.0;
                if (accountType.equals("courant")) interestRate = 1.0;
                else if (accountType.equals("epargne")) interestRate = 3.0;
                else if (accountType.equals("joint")) interestRate = 1.5;

                String accountNumber = generateAccountNumber();

                // Insertion des données du compte dans la base de données
                insertAccountData(firstname, lastname, accountNumber, accountType, balance, interestRate);

                // Réglez les détails du compte sur le panneau de résumé
                ResumeCreationPanel resumePanel = (ResumeCreationPanel) cardPanel.getComponent(6);
                resumePanel.setAccountDetails(firstname, lastname, accountNumber, accountType, balance, interestRate);

                // Afficher le panneau de résumé
                cardLayout.show(cardPanel, "RESUME_CREATION");
            }
        });

        retourButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "OPTIONS_PAGE");
            }
        });
    }

    private String generateAccountNumber() {
        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            accountNumber.append(random.nextInt(10));
        }
        return accountNumber.toString();
    }

    private void insertAccountData(String firstname, String lastname, String accountNumber, String accountType, double balance, double interestRate) {
        try {
            // Charger le driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Établir la connexion à la base de données
            String url = "jdbc:mysql://localhost:3306/bank";
            String dbUsername = "root";
            String dbPassword = "M@rcel2004";
            Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);

            // Préparer la requête SQL
            String sql = "INSERT INTO accounts (firstname, lastname, account_number, account_type, balance, interest_rate) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, firstname);
            preparedStatement.setString(2, lastname);
            preparedStatement.setString(3, accountNumber);
            preparedStatement.setString(4, accountType);
            preparedStatement.setDouble(5, balance);
            preparedStatement.setDouble(6, interestRate);

            // Exécuter la requête SQL
            preparedStatement.executeUpdate();

            // Fermer la connexion
            connection.close();

            JOptionPane.showMessageDialog(null, "Compte créé avec succès!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de la création du compte: " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
