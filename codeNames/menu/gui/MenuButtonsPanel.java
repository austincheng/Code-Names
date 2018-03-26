package codeNames.menu.gui;

import codeNames.menu.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * JPanel within the Menu JFrame that shows the menu buttons.
 * @author Austin Cheng
 */
public class MenuButtonsPanel extends JPanel {
    private static final int FONT_SIZE = 25;
    private static OptionsPanel optionsPanel;

    public MenuButtonsPanel(JFrame menu) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JButton newGame = new JButton("NEW GAME");
        newGame.setFont(newGame.getFont().deriveFont(Font.BOLD, FONT_SIZE));
        add(newGame, gbc);

        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu.setVisible(false);
                Main.newGame();
            }
        });

        JButton options = new JButton("OPTIONS");
        options.setFont(options.getFont().deriveFont(Font.BOLD, FONT_SIZE));
        add(options, gbc);

        options.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (optionsPanel == null) {
                    optionsPanel = new OptionsPanel();
                } else {
                    optionsPanel.setVisible(true);
                }
            }
        });

        JButton quit = new JButton("QUIT");
        quit.setFont(quit.getFont().deriveFont(Font.BOLD, FONT_SIZE));
        add(quit, gbc);

        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        setVisible(true);
    }
}
