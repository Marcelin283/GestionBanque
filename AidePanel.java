package pk1;

import javax.swing.*;
import java.awt.*;

public class AidePanel extends JPanel {
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public AidePanel(CardLayout cardLayout, JPanel cardPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;

        setLayout(new BorderLayout());

        // Texte complet de l'aide
        String aideText = "Création de compte : Permet d'ajouter un nouveau compte bancaire.\n" +
                "Affichage d'un compte : Affiche les informations du numéro de compte saisi.\n" +
                "Créer une ligne comptable : Permet d'ajouter une nouvelle ligne comptable au compte correspondant.\n" +
                "Faire un virement : Permet de transférer des fonds d'un compte à l'autre.\n" +
                "Faire un dépôt : Permet de déposer des fonds sur un compte.\n" +
                "Sortir : Quitte l'application.\n" +
                "Aide : Affiche ce message d'aide";

        // Création d'un JTextArea pour afficher le texte complet
        JTextArea aideTextArea = new JTextArea(aideText);
        aideTextArea.setEditable(false);
        
        // Ajout du JTextArea à un JScrollPane pour permettre le défilement si nécessaire
        JScrollPane scrollPane = new JScrollPane(aideTextArea);

        // Ajout du JScrollPane au panneau
        add(scrollPane, BorderLayout.CENTER);

        JButton retourButton = new JButton("Retour");
        retourButton.addActionListener(e -> cardLayout.show(cardPanel, "MENU"));
        add(retourButton, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        CardLayout layout = new CardLayout();
        panel.setLayout(layout);

        JPanel aidePanel = new AidePanel(layout, panel);
        panel.add(aidePanel, "AIDE");

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setVisible(true);
    }
}
