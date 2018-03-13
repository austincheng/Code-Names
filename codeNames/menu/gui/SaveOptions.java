package codeNames.menu.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static codeNames.menu.Main.*;

public class SaveOptions implements ActionListener {
    private JDialog options;
    private ArrayList<JCheckBox> commaChecks;
    private ArrayList<JCheckBox> lineChecks;
    private ButtonGroup version;
    private JCheckBox useTimer;
    private JTextField startField;
    private JTextField nextField;

    public SaveOptions(JDialog options, ArrayList<JCheckBox> comma, ArrayList<JCheckBox> line,
                       ButtonGroup version, JCheckBox useTimer, JTextField start, JTextField next) {
        this.options = options;
        commaChecks = comma;
        lineChecks = line;
        this.version = version;
        this.useTimer = useTimer;
        startField = start;
        nextField = next;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ALL.clear();
        for (JCheckBox set : commaChecks) {
            if (set.isSelected()) {
                addAllWordsComma(set.getText());
            }
        }

        for (JCheckBox set : lineChecks) {
            if (set.isSelected()) {
                addAllWordsLined(set.getText());
            }
        }

        if (ALL.size() < 25) {
            JOptionPane.showMessageDialog(options,
                    "Not enough words in files",
                    "Insufficient Words",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        ButtonModel selectedVersion = version.getSelection();
        if (selectedVersion.getActionCommand().equals("Old")) {
            useOld = true;
        } else {
            useOld = false;
        }

        if (useTimer.isSelected()) {
            int start;
            int turn;
            try {
                start = Integer.parseInt(startField.getText());
                turn = Integer.parseInt(nextField.getText());
            } catch (NumberFormatException er) {
                JOptionPane.showMessageDialog(options,
                        "Inappropriate format for number in timing option",
                        "Inappropriate format",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (start <= 0 || turn <= 0) {
                JOptionPane.showMessageDialog(options,
                        "Numbers must be positive in timing options",
                        "Inappropriate number",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            startTime = start;
            turnTime = turn;
        } else {
            startTime = -1;
            turnTime = -1;
        }

        options.setVisible(false);
    }
}
