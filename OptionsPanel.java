package pk1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class OptionsPanel extends JPanel {
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public OptionsPanel(CardLayout cardLayout, JPanel cardPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;

        setLayout(new GridLayout(7, 1));

        JButton creerCompteButton = new JButton("CREATION DE COMPTE");
        JButton afficherCompteButton = new JButton("AFFICHAGE D'UN COMPTE");
        JButton creerLigneComptableButton = new JButton("CREER UNE LIGNE COMPTABLE");
        JButton faireVirementButton = new JButton("FAIRE UN VIREMENT");
        JButton faireDepotButton = new JButton("FAIRE UN DEPOT");
        JButton sortirButton = new JButton("SORTIR");
        JButton aideButton = new JButton("AIDE");

        add(creerCompteButton);
        add(afficherCompteButton);
        add(creerLigneComptableButton);
        add(faireVirementButton);
        add(faireDepotButton);
        add(sortirButton);
        add(aideButton);

        creerCompteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "CREER_COMPTE");
            }
        });

        afficherCompteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "AFFICHER_COMPTE");
            }
        });

        creerLigneComptableButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "CREER_LIGNE_COMPTABLE");
            }
        });

        faireVirementButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "FAIRE_VIREMENT");
            }
        });

        faireDepotButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "FAIRE_DEPOT");
            }
        });

        sortirButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        aideButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "AIDE");
            }
        });
    }
}
