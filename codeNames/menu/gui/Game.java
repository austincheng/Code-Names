package codeNames.menu.gui;

import codeNames.Board;
import codeNames.Constants;
import codeNames.sharedgui.AnswerPanel;
import codeNames.sharedgui.BoardPanel;
import codeNames.sharedgui.TimerLabel;

import static codeNames.Constants.*;

import javax.swing.*;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * JFrame representing everything including the board and the timer and bottom buttons.
 * @author Austin Cheng
 */
public class Game extends JFrame {
    private Board _model;
    private AnswerPanel ap;

    /* Game without Timer. */
    public Game(String title, Board model, Menu menu) {
        _model = model;

        setSize(Constants.WIDTH, Constants.HEIGHT);
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

        /* Return to Menu button and Answer button inside bottom pane. */
        JButton returnMenu = new JButton("Return to Menu");
        returnMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                if (ap != null) {
                    ap.dispose();
                }
                menu.setVisible(true);
            }
        });
        returnMenu.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        bottom.add(returnMenu);

        bottom.add(Box.createRigidArea(new Dimension(Constants.WIDTH / 2, BUTTON_HEIGHT)));

        JButton answer = new JButton("Show Answer");
        answer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!_model.gameOver) {
                    ap = new AnswerPanel(_model);
                }
            }
        });
        answer.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        bottom.add(answer);

        cp.add(bottom);

        setVisible(true);
    }

    /* Game with Timer. */
    public Game(String title, Board model, int startTime, int turnTime, Menu menu) {
        _model = model;

        setSize(Constants.WIDTH, Constants.HEIGHT);
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

        /* Return to Menu button and Answer button inside bottom pane. */
        JButton returnMenu = new JButton("Return to Menu");
        returnMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                if (ap != null) {
                    ap.dispose();
                }
                menu.setVisible(true);
            }
        });
        returnMenu.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        bottom.add(returnMenu);

        bottom.add(Box.createRigidArea(new Dimension(WIDTH / 2, BUTTON_HEIGHT)));

        /* Answer button inside bottom pane. */
        JButton answer = new JButton("Show Answer");
        answer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!_model.gameOver) {
                    ap = new AnswerPanel(_model);
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
