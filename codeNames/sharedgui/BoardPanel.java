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
import java.util.ArrayList;

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
                ArrayList<String> subWords = breakUpPhrase(g, word);
                int times = subWords.size();
                while (g.getFontMetrics().getAscent() * times > CARD_HEIGHT) {
                    g.setFont(new Font(g.getFont().getName(), Font.BOLD, g.getFont().getSize() - 1));
                    subWords = breakUpPhrase(g, word);
                    times = subWords.size();
                }
                int count = 0;
                for (double i = -((double) times / 2) + 1; i <= ((double) times) / 2; i++) {
                    int y = IN_BETWEEN + (CARD_HEIGHT + IN_BETWEEN) * r + CARD_HEIGHT / 2
                            + ((int) (i * g.getFontMetrics().getAscent()));
                    g.drawString(subWords.get(count), IN_BETWEEN + (CARD_WIDTH + IN_BETWEEN) * c + CARD_WIDTH / 2
                                    - g.getFontMetrics().stringWidth(subWords.get(count)) / 2, y);
                    count++;
                }
            }
        }
    }

    private ArrayList<String> breakUpPhrase(Graphics g, String word) {
        ArrayList<String> subWords = new ArrayList<>();
        String[] words = word.split(" ");
        if (g.getFontMetrics().stringWidth(word) > CARD_WIDTH || words.length > 1) {
            String subphrase = "";
            for (int i = 0; i < words.length; i++) {
                String subWord = words[i];
                if (g.getFontMetrics().stringWidth(subWord) > CARD_WIDTH) {
                    ArrayList<String> broken = breakUpLongWord(g, subWord);
                    for (String brokenWord: broken) {
                        subWords.add(brokenWord);
                    }
                } else {
                    if (g.getFontMetrics().stringWidth(subphrase + subWord + " ") > CARD_WIDTH) {
                        subWords.add(subphrase);
                        subphrase = "";
                        i--;
                    } else {
                        subphrase += subWord + " ";
                    }
                }
            }
            subWords.add(subphrase);
        } else {
            subWords.add(word);
        }
        return subWords;
    }

    private ArrayList<String> breakUpLongWord(Graphics g, String word) {
        ArrayList<String> subWords = new ArrayList<>();
        int times = (int) Math.ceil(((double) g.getFontMetrics().stringWidth(word)) / CARD_WIDTH);
        int subWordLength = (int) Math.ceil(((double) word.length()) / times);
        for (int i = 1; i <= times; i++) {
            if (i != times) {
                subWords.add(word.substring((i - 1) * subWordLength, i * subWordLength) + "-");
            } else {
                subWords.add(word.substring((i - 1) * subWordLength, word.length()));
            }
        }
        return subWords;
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
