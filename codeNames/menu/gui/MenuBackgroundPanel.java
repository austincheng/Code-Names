package codeNames.menu.gui;

import javax.swing.*;
import java.awt.*;

public class MenuBackgroundPanel extends JPanel {
    private static final int NUM_CIRCLES = 10;
    private static final int INITIAL_RADIUS = Menu.SIDE / 2;
    private static final int FINAL_RADIUS = 70;
    private static final int RADIUS_CHANGE = (INITIAL_RADIUS - FINAL_RADIUS) / NUM_CIRCLES;
    private static final double POWER_CHANGE = 1.0 / NUM_CIRCLES;
    private static final int FONT_SIZE = 80;
    private static final String TITLE_WORD = "CODENAMES";
    private static final int y = Menu.SIDE / 4;

    @Override
    protected void paintComponent(Graphics g) {
        double power = 0;
        int radius = INITIAL_RADIUS;
        g.setColor(redToOrange(0));
        g.fillRect(0, 0, Menu.SIDE, Menu.SIDE);
        for (int i = 0; i < NUM_CIRCLES; i++) {
            drawCircle(g, radius, redToOrange(power));
            power += POWER_CHANGE;
            radius -= RADIUS_CHANGE;
        }
        g.setColor(Color.BLACK);
        g.setFont(new Font(g.getFont().getName(), Font.BOLD, FONT_SIZE));
        int length = g.getFontMetrics().stringWidth(TITLE_WORD);
        int x = Menu.SIDE / 2 - length / 2;
        g.setColor(Color.WHITE);
        g.drawString(TITLE_WORD, x, y);
    }

    /* Returns a color from red to orange, with power from 0 to 1. */
    private static Color redToOrange(double power) {
        double H = power * 0.13;
        double S = 0.9;
        double B = 0.9;

        return Color.getHSBColor((float) H, (float) S, (float) B);
    }

    private static void drawCircle(Graphics g, int r, Color c) {
        int x = Menu.SIDE / 2 - r;
        int y = Menu.SIDE / 2 - r;
        g.setColor(c);
        g.fillOval(x, y, r * 2, r * 2);
    }
}
