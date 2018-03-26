package codeNames.sharedgui;

import codeNames.Board;

import javax.swing.JDialog;
import javax.swing.WindowConstants;
import java.awt.Graphics;
import java.awt.Font;

/**
 * JDialog that pops up upon clicking the "Show Answer" button.
 * @author Austin Cheng
 */
public class AnswerPanel extends JDialog {
    private static final int WEIRD_OFFSET_X = 10;
    private static final int WEIRD_OFFSET_Y = 31;
    private static final int WIDTH = 300 + WEIRD_OFFSET_X;
    private static final int HEIGHT = 300 + WEIRD_OFFSET_Y;
    private Board _model;

    public AnswerPanel(Board model) {
        _model = model;
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                g.setColor(_model.getAnswer(r, c).getColor());
                g.fillRect(WEIRD_OFFSET_X + 45 * c, WEIRD_OFFSET_Y + 45 * r, 40, 40);
            }
        }

        g.setFont(new Font(g.getFont().getName(), Font.BOLD, 20));
        g.setColor(_model.starter.getColor());
        g.drawString(_model.starter.fullString() + " to Start", WEIRD_OFFSET_X + 60, WEIRD_OFFSET_Y + 250);
    }
}
