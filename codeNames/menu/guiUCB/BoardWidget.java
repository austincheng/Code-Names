package codeNames.menu.guiUCB;

import ucb.gui2.Pad;

import java.awt.*;
import java.util.Observer;
import java.util.Observable;

import java.awt.event.MouseEvent;

public class BoardWidget extends Pad implements Observer {
    static final int CARD_WIDTH = 200;
    static final int CARD_HEIGHT = 100;
    static final int IN_BETWEEN = 50;
    static final int FONT_SIZE = 20;
    static final int WIDTH = CARD_WIDTH * 5 + IN_BETWEEN * 6;
    static final int HEIGHT = CARD_HEIGHT * 5 + IN_BETWEEN * 6;


    private static BoardUCB _model;

    BoardWidget(BoardUCB model) {
        _model = model;
        setMouseHandler("click", this::readTap);
        setPreferredSize(WIDTH, HEIGHT);
    }

    @Override
    public synchronized void paintComponent(Graphics2D g) {
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                g.drawRect(IN_BETWEEN + (CARD_WIDTH + IN_BETWEEN) * c, IN_BETWEEN + (CARD_HEIGHT + IN_BETWEEN) * r, CARD_WIDTH, CARD_HEIGHT);
                if (_model.getCover(r, c) != null) {
                    Color color = _model.getCover(r, c).getColor();
                    g.setColor(color);
                    g.fillRect(IN_BETWEEN + (CARD_WIDTH + IN_BETWEEN) * c, IN_BETWEEN + (CARD_HEIGHT + IN_BETWEEN) * r, CARD_WIDTH, CARD_HEIGHT);
                }
                g.setColor(Color.BLACK);
                g.setFont(new Font(g.getFont().getName(), Font.BOLD, FONT_SIZE));
                String word = _model.getWord(r, c);
                g.drawString(word, IN_BETWEEN + CARD_WIDTH / 2 + (CARD_WIDTH + IN_BETWEEN) * c - g.getFontMetrics().stringWidth(word) / 2, IN_BETWEEN + CARD_HEIGHT / 2 + g.getFontMetrics().getAscent() / 2 + (CARD_HEIGHT + IN_BETWEEN) * r);
            }
        }
    }

    private void readTap(String unused, MouseEvent where) {
        int x = where.getX(), y = where.getY();
        if (where.getButton() == MouseEvent.BUTTON1) {
            int row = y / (IN_BETWEEN + CARD_HEIGHT);
            int col = x / (IN_BETWEEN + CARD_WIDTH);

            if ((IN_BETWEEN + CARD_HEIGHT) * row + IN_BETWEEN <= y) {
                if ((IN_BETWEEN + CARD_WIDTH) * col + IN_BETWEEN <= x) {
                    setChanged();
                    notifyObservers(row + "" + col);
                }
            }
        }
    }

    @Override
    public synchronized void update(Observable model, Object arg) {
        repaint();
    }
}
