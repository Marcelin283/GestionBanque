package pk1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

@SuppressWarnings({ "serial", "unused" })
public class AccueilPanel extends JPanel {
    public AccueilPanel(ActionListener continuerListener) {
        setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Bienvenue à Marcelin Mondial Bank", SwingConstants.CENTER);
        add(welcomeLabel, BorderLayout.CENTER);

        JButton continuerButton = new JButton("Continuer");
        continuerButton.setBackground(new Color(78, 129, 66)); // Nuance de vert #4e8142
        continuerButton.addActionListener(continuerListener);
        add(continuerButton, BorderLayout.SOUTH);
    }
}