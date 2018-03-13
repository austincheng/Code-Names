package codeNames.commandLine.guiUCB;

import ucb.gui2.TopLevel;
import ucb.gui2.LayoutSpec;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class GUIUCB extends TopLevel implements Observer {
    private static final int MIN_WIDTH = BoardWidget.WIDTH;
    private static final int MIN_HEIGHT = BoardWidget.HEIGHT;
    private BoardWidget _widget;
    private BoardUCB _model;

    public GUIUCB(String title, BoardUCB model) {
        super(title, true);
        _model = model;
        _widget = new BoardWidget(model);
        add(_widget,
                new LayoutSpec("height", "1",
                        "width", "REMAINDER",
                        "ileft", 5, "itop", 5, "iright", 5,
                        "ibottom", 5));
        addButton("Show Answer", this::showAnswer,
                new LayoutSpec("height", 1,
                        "width", "REMAINDER"));
        setMinimumSize(MIN_WIDTH, MIN_HEIGHT);
        _widget.addObserver(this);
    }

    private synchronized void showAnswer(String unused) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setVisible(true);
        JPanel pane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                for (int r = 0; r < 5; r++) {
                    for (int c = 0; c < 5; c++) {
                        g.setColor(_model.getAnswer(r, c).getColor());
                        g.fillRect(45 * c, 45 * r, 40, 40);
                    }
                }

                g.setFont(new Font(g.getFont().getName(), Font.BOLD, 20));
                g.setColor(_model.starter.getColor());
                g.drawString(_model.starter.fullString() + " to Start", 60, 250);
            }
        };
        frame.add(pane);
    }

    @Override
    public void update(Observable obs, Object arg) {
        if (obs == _model) {
            _widget.update(_model, arg);
        } else if (obs == _widget) {
            makeTap((String) arg);
        }
    }

    private void makeTap(String sq) {
        int row = Integer.parseInt(sq.substring(0, 1));
        int col = Integer.parseInt(sq.substring(1));

        _model.setCover(row, col, _model.getAnswer(row, col));
        _widget.repaint();
    }
}
