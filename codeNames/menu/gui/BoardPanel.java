package codeNames.menu.gui;

import codeNames.menu.Board;
import codeNames.menu.Piece;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BoardPanel extends JPanel implements MouseListener {
    private static final int CARD_WIDTH = Game.CARD_WIDTH;
    private static final int CARD_HEIGHT = Game.CARD_HEIGHT;
    private static final int IN_BETWEEN = Game.IN_BETWEEN;
    private static final int FONT_SIZE = Game.FONT_SIZE;

    private Board _model;

    public BoardPanel(Board model) {
        _model = model;
        addMouseListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                g.drawRect(IN_BETWEEN + (CARD_WIDTH + IN_BETWEEN) * c, IN_BETWEEN + (CARD_HEIGHT + IN_BETWEEN) * r, CARD_WIDTH, CARD_HEIGHT);

                Color color = _model.getCover(r, c).getColor();
                g.setColor(color);
                g.fillRect(IN_BETWEEN + (CARD_WIDTH + IN_BETWEEN) * c, IN_BETWEEN + (CARD_HEIGHT + IN_BETWEEN) * r, CARD_WIDTH, CARD_HEIGHT);
                g.setColor(Color.BLACK);
                g.setFont(new Font(g.getFont().getName(), Font.BOLD, FONT_SIZE));
                String word = _model.getWord(r, c);
                g.drawString(word, IN_BETWEEN + CARD_WIDTH / 2 + (CARD_WIDTH + IN_BETWEEN) * c - g.getFontMetrics().stringWidth(word) / 2, IN_BETWEEN + CARD_HEIGHT / 2 + g.getFontMetrics().getAscent() / 2 + (CARD_HEIGHT + IN_BETWEEN) * r);
            }
        }
    }


    @Override
    public void mouseClicked(MouseEvent where) {
        int x = where.getX(), y = where.getY();
        int row = y / (IN_BETWEEN + CARD_HEIGHT);
        int col = x / (IN_BETWEEN + CARD_WIDTH);

        if ((IN_BETWEEN + CARD_HEIGHT) * row + IN_BETWEEN <= y) {
            if ((IN_BETWEEN + CARD_WIDTH) * col + IN_BETWEEN <= x) {
                Piece cover = _model.getAnswer(row, col);
                _model.setCover(row, col, cover);
                if (cover == Piece.RED) {
                    _model.redCovers++;
                } else if (cover == Piece.BLUE) {
                    _model.blueCovers++;
                }
                if (cover != _model.turn) {
                    if (cover == Piece.ASSASSIN) {
                        _model.gameOver = true;
                    }
                    _model.switchTurn();
                }
                if (_model.redCovers == _model.totalRed) {
                    _model.gameOver = true;
                } else if (_model.blueCovers == _model.totalBlue) {
                    _model.gameOver = true;
                }
                repaint();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent evt) {
    }

    @Override
    public void mouseReleased(MouseEvent evt) {
    }

    @Override
    public void mouseEntered(MouseEvent evt) {
    }

    @Override
    public void mouseExited(MouseEvent evt) {
    }
}
