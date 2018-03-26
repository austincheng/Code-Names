package codeNames.sharedgui;

import codeNames.Board;
import codeNames.Piece;
import static codeNames.Constants.*;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * JPanel for the board panel inside the Game JFrame with words and reads for mouse clicks.
 * @author Austin Cheng
 */
public class BoardPanel extends JPanel implements MouseListener {
    private Board _model;

    public BoardPanel(Board model) {
        _model = model;
        addMouseListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
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
                if (_model.getCover(row, col) == Piece.NONE) {
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
                    update(getGraphics());
                }
            }
        }
    }

    @Override public void mousePressed(MouseEvent evt) { }
    @Override public void mouseReleased(MouseEvent evt) { }
    @Override public void mouseEntered(MouseEvent evt) { }
    @Override public void mouseExited(MouseEvent evt) { }
}
