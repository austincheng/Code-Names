package codeNames.sharedgui;

import codeNames.Board;
import codeNames.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * JLabel for the timer label inside the Game JFrame that displays remaining time.
 * @author Austin Cheng
 */
public class TimerLabel extends JLabel {
    private int turnTime;
    private static final int FONT_SIZE = 40;

    private int secondsRem;
    private Piece turn;

    private Board _model;
    private Timer timer;

    public TimerLabel(Board model, int startTime, int turnTime) {
        _model = model;
        secondsRem = startTime;
        this.turnTime = turnTime;
        turn = _model.turn;

        setFont(new Font(getFont().getName(), Font.BOLD, FONT_SIZE));
        setLayout(new BorderLayout());
        setForeground(turn.getColor());
        setText("" + formatTime(secondsRem));
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (_model.gameOver) {
                    Piece winner = _model.turn;
                    setForeground(winner.getColor());
                    setText(winner.fullString() + " Wins!");
                    timer.stop();
                    return;
                }
                if (secondsRem <= 0) {
                    _model.switchTurn();
                    turn = _model.turn;
                    secondsRem = turnTime;
                }
                if (turn != _model.turn) {
                    turn = _model.turn;
                    secondsRem = turnTime;
                }
                setForeground(turn.getColor());
                setText("" + formatTime(secondsRem));
                secondsRem--;
            }
        });
    }

    private static String formatTime(int secondsRem) {
        int minutes = secondsRem / 60;
        int seconds = secondsRem % 60;

        String secondsS = seconds + "";
        if (seconds < 10) {
            secondsS = "0" + seconds;
        }

        return minutes + ":" + secondsS;
    }

    public void start() {
        timer.start();
    }
}
