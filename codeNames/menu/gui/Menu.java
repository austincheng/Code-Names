package codeNames.menu.gui;

import javax.swing.*;
import java.awt.*;

public class Menu extends JFrame {
    public static final int SIDE = 800;

    public Menu(String title) {
        setSize(SIDE, SIDE);
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new MenuBackgroundPanel());

        Container cp = getContentPane();
        cp.setLayout(new GridBagLayout());

        cp.add(new MenuButtonsPanel(this));

        setVisible(true);
    }
}
