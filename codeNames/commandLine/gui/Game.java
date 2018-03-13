package codeNames.commandLine.gui;

import codeNames.commandLine.Board;

import javax.swing.*;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game extends JFrame {
    public static final int CARD_WIDTH = 200;
    public static final int CARD_HEIGHT = 100;
    public static final int IN_BETWEEN = 50;
    public static final int FONT_SIZE = 20;

    private static final int BUTTON_WIDTH = 50;
    private static final int BUTTON_HEIGHT = 25;

    private static final int TIMER_PADDING = 50;

    private static final int WIDTH = CARD_WIDTH * 5 + IN_BETWEEN * 6;
    private static final int HEIGHT = CARD_HEIGHT * 5 + BUTTON_HEIGHT + IN_BETWEEN * 7;

    private Board _model;

    /* Game without Timer. */
    public Game(String title, Board model) {
        _model = model;

        setSize(WIDTH, HEIGHT);
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /* Main Pane for entire JFrame. */
        Container cp = getContentPane();
        BoxLayout layout = new BoxLayout(cp, BoxLayout.Y_AXIS);
        cp.setLayout(layout);

        /* Top Board pane. */
        BoardPanel pane = new BoardPanel(_model);
        cp.add(pane);

        /* Bottom Answer pane. */
        JPanel bottom = new JPanel();
        BoxLayout layoutBottom = new BoxLayout(bottom, BoxLayout.X_AXIS);
        bottom.setLayout(layoutBottom);

        /* Answer button inside bottom pane. */
        JButton answer = new JButton("Show Answer");
        answer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!_model.gameOver) {
                    new AnswerPanel(_model);
                }
            }
        });
        answer.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        bottom.add(answer);

        cp.add(bottom);

        setVisible(true);
    }

    /* Game with Timer. */
    public Game(String title, Board model, int startTime, int turnTime) {
        _model = model;

        setSize(WIDTH, HEIGHT);
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /* Main Pane for entire JFrame. */
        Container cp = getContentPane();
        BoxLayout layout = new BoxLayout(cp, BoxLayout.Y_AXIS);
        cp.setLayout(layout);

        /* Top Board pane. */
        BoardPanel pane = new BoardPanel(_model);
        cp.add(pane);

        /* Middle Turn and Timer pane. */
        JPanel middle = new JPanel();
        BoxLayout layoutMiddle = new BoxLayout(middle, BoxLayout.X_AXIS);
        middle.setLayout(layoutMiddle);

        /* Timer label inside middle pane. */
        TimerLabel timerLabel = new TimerLabel(_model, startTime, turnTime);
        middle.add(timerLabel);

        /* Add padding between label and button. */
        middle.add(Box.createRigidArea(new Dimension(TIMER_PADDING, BUTTON_HEIGHT)));

        /* Timer button inside middle pane. */
        JButton switchTurn = new JButton("Switch Turn");
        switchTurn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!_model.gameOver) {
                    _model.switchTurn();
                }
            }
        });
        switchTurn.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        middle.add(switchTurn);

        /* Not showing middle pane until later. */
        middle.setVisible(false);
        cp.add(middle);

        /* Bottom Answer pane. */
        JPanel bottom = new JPanel();
        BoxLayout layoutBottom = new BoxLayout(bottom, BoxLayout.X_AXIS);
        bottom.setLayout(layoutBottom);

        /* Answer button inside bottom pane. */
        JButton answer = new JButton("Show Answer");
        answer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!_model.gameOver) {
                    new AnswerPanel(_model);
                    timerLabel.start();
                    middle.setVisible(true);
                }
            }
        });
        answer.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        bottom.add(answer);

        cp.add(bottom);

        setVisible(true);
    }
}
