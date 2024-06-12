package pk1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class GestionComptesBancaires extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private int userId;

    public GestionComptesBancaires() {
        super("MARCELIN MONDIAL BANK");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/logo.jpg"));
        setIconImage(logoIcon.getImage());

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        AccueilPanel accueilPanel = new AccueilPanel(new ContinuerListener());
        MenuPanel menuPanel = new MenuPanel(new ConnecterListener(), new CreerUtilisateurListener(), new QuitterListener());
        CreerUtilisateurPanel creerUtilisateurPanel = new CreerUtilisateurPanel(cardLayout, cardPanel);
        ConnexionPanel connexionPanel = new ConnexionPanel(cardLayout, cardPanel);
        CreerComptePanel creerComptePanel = new CreerComptePanel(cardLayout, cardPanel);
        AidePanel aidePanel = new AidePanel(cardLayout, cardPanel);
        ResumeCreationPanel resumePanel = new ResumeCreationPanel(cardLayout, cardPanel);
        OptionsPanel optionsPanel = new OptionsPanel(cardLayout, cardPanel);
        AfficherComptePanel afficherComptePanel = new AfficherComptePanel(cardLayout, cardPanel);
        CreerLigneComptablePanel creerLigneComptablePanel = new CreerLigneComptablePanel(cardLayout, cardPanel);
        FaireVirementPanel faireVirementPanel = new FaireVirementPanel(cardLayout, cardPanel);
        FaireDepotPanel faireDepotPanel = new FaireDepotPanel(cardLayout, cardPanel);

        cardPanel.add(accueilPanel, "ACCUEIL");
        cardPanel.add(menuPanel, "MENU");
        cardPanel.add(creerUtilisateurPanel, "CREER_UTILISATEUR");
        cardPanel.add(connexionPanel, "CONNEXION");
        cardPanel.add(creerComptePanel, "CREER_COMPTE");
        cardPanel.add(aidePanel, "AIDE");
        cardPanel.add(resumePanel, "RESUME_CREATION");
        cardPanel.add(optionsPanel, "OPTIONS_PAGE");
        cardPanel.add(afficherComptePanel, "AFFICHER_COMPTE");
        cardPanel.add(creerLigneComptablePanel, "CREER_LIGNE_COMPTABLE");
        cardPanel.add(faireVirementPanel, "FAIRE_VIREMENT");
        cardPanel.add(faireDepotPanel, "FAIRE_DEPOT");

        add(cardPanel);
    }

    public void setUserId(int userId) {
        this.userId = userId;
        System.out.println("Set User ID: " + userId);
    }

    private class ContinuerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(cardPanel, "MENU");
        }
    }

    private class ConnecterListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(cardPanel, "CONNEXION");
        }
    }

    private class CreerUtilisateurListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(cardPanel, "CREER_UTILISATEUR");
        }
    }

    private class QuitterListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GestionComptesBancaires frame = new GestionComptesBancaires();
                frame.setVisible(true);
            }
        });
    }
}
