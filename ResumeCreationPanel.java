package pk1;

import javax.swing.*;
import java.awt.*;

public class ResumeCreationPanel extends JPanel {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JTextArea summaryText;

    public ResumeCreationPanel(CardLayout cardLayout, JPanel cardPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;

        setLayout(new BorderLayout());

        summaryText = new JTextArea("Summary of account creation goes here...");
        summaryText.setEditable(false);
        add(new JScrollPane(summaryText), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton validerButton = new JButton("Valider");
        JButton annulerButton = new JButton("Annuler");

        validerButton.addActionListener(e -> {
            // Logic to validate and finalize the account creation
            System.out.println("Account creation validated");
            cardLayout.show(cardPanel, "OPTIONS_PAGE"); // Redirect to options page after validation
        });

        annulerButton.addActionListener(e -> {
            System.out.println("Account creation canceled");
            cardLayout.show(cardPanel, "MENU"); // Redirect to menu if canceled
        });

        buttonPanel.add(validerButton);
        buttonPanel.add(annulerButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setAccountDetails(String firstname, String lastname, String accountNumber, String accountType, double balance, double interestRate) {
        String summary = String.format(
                "First Name: %s\nLast Name: %s\nAccount Number: %s\nAccount Type: %s\nBalance: %.2f\nInterest Rate: %.2f%%",
                firstname, lastname, accountNumber, accountType, balance, interestRate);
        summaryText.setText(summary);
    }
}
