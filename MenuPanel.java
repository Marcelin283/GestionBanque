package pk1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel {
    public MenuPanel(ActionListener connecterListener, ActionListener creerUtilisateurListener, ActionListener quitterListener) {
        setLayout(new GridLayout(3, 1));

        JButton connecterButton = new JButton("Se connecter");
        JButton creerUtilisateurButton = new JButton("Créer un utilisateur");
        JButton quitterButton = new JButton("Quitter");

        add(connecterButton);
        add(creerUtilisateurButton);
        add(quitterButton);

        connecterButton.addActionListener(connecterListener);
        creerUtilisateurButton.addActionListener(creerUtilisateurListener);
        quitterButton.addActionListener(quitterListener);
    }
}
