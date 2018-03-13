package codeNames.menu.gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.ArrayList;

import static codeNames.menu.Main.*;

public class OptionsPanel extends JDialog {
    private static final int TITLE_FONT_INCREASE = 10;

    public OptionsPanel() {
        setModalityType(ModalityType.APPLICATION_MODAL);
        setSize(500, 500);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        ArrayList<JCheckBox> commaChecks = new ArrayList<>();
        ArrayList<JCheckBox> lineChecks = new ArrayList<>();
        ArrayList<String> allFileNames = new ArrayList<>();

        JLabel wordLists = new JLabel("Word Lists:");
        wordLists.setFont(new Font(wordLists.getFont().getName(), Font.BOLD, wordLists.getFont().getSize() + TITLE_FONT_INCREASE));
        add(wordLists, gbc);

        File commaSets = commaSeparated.toFile();
        File[] listOfFiles = commaSets.listFiles();
        for (File f : listOfFiles) {
            String fileName = f.getName();
            allFileNames.add(fileName);
            JCheckBox set = new JCheckBox(fileName);
            if (fileName.equals("words.txt")) {
                set.setSelected(true);
            }
            commaChecks.add(set);
            add(set, gbc);
        }

        File lineSets = lineSeparated.toFile();
        listOfFiles = lineSets.listFiles();
        for (File f : listOfFiles) {
            String fileName = f.getName();
            if (!allFileNames.contains(fileName)) {
                JCheckBox set = new JCheckBox(fileName);
                if (fileName.equals("words.txt")) {
                    set.setSelected(true);
                }
                lineChecks.add(set);
                add(set, gbc);
            }
        }

        JLabel version = new JLabel("Version:");
        version.setFont(new Font(version.getFont().getName(), Font.BOLD, version.getFont().getSize() + TITLE_FONT_INCREASE));
        add(version, gbc);

        JRadioButton oldV = new JRadioButton("Old");
        oldV.setActionCommand("Old");
        add(oldV, gbc);
        JRadioButton newV = new JRadioButton("New");
        newV.setActionCommand("New");
        newV.setSelected(true);
        add(newV, gbc);

        ButtonGroup versionGroup = new ButtonGroup();
        versionGroup.add(oldV);
        versionGroup.add(newV);

        JLabel timing = new JLabel("Timing:");
        timing.setFont(new Font(timing.getFont().getName(), Font.BOLD, timing.getFont().getSize() + TITLE_FONT_INCREASE));
        add(timing, gbc);

        JCheckBox useTimer = new JCheckBox("Use Timer");
        add(useTimer, gbc);
        JPanel timings = new JPanel(new BorderLayout());

        JPanel labelPane = new JPanel(new GridLayout(0, 1));
        JLabel startTime = new JLabel("First Turn Time (seconds):");
        JLabel nextTime = new JLabel("Other Turn Time (seconds):");
        labelPane.add(startTime);
        labelPane.add(nextTime);

        JPanel fieldPane = new JPanel(new GridLayout(0, 1));
        JTextField startField = new JTextField();
        startField.setColumns(4);
        startField.setText("150");
        JTextField nextField = new JTextField();
        nextField.setColumns(4);
        nextField.setText("120");

        startField.setEditable(false);
        nextField.setEditable(false);

        fieldPane.add(startField);
        fieldPane.add(nextField);

        timings.add(labelPane, BorderLayout.CENTER);
        timings.add(fieldPane, BorderLayout.LINE_END);

        add(timings, gbc);

        oldV.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                AbstractButton abstractButton = (AbstractButton) e.getSource();
                ButtonModel buttonModel = abstractButton.getModel();

                if (buttonModel.getActionCommand().equals("Old")) {
                    if (buttonModel.isSelected()) {
                        useTimer.setSelected(false);
                        useTimer.setEnabled(false);
                    } else {
                        useTimer.setEnabled(true);
                    }
                }
            }
        });

        useTimer.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                AbstractButton abstractButton = (AbstractButton) e.getSource();
                ButtonModel buttonModel = abstractButton.getModel();
                if (buttonModel.isSelected()) {
                    startField.setEditable(true);
                    nextField.setEditable(true);
                } else {
                    startField.setEditable(false);
                    nextField.setEditable(false);
                }
            }
        });


        JButton save = new JButton("Save Options");
        save.addActionListener(new SaveOptions(this, commaChecks, lineChecks, versionGroup, useTimer, startField, nextField));
        add(save, gbc);

        setVisible(true);
    }
}
