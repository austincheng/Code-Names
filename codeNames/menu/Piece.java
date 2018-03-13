package codeNames.menu;

import java.awt.Color;

public enum Piece {
    RED, BLUE, NEUTRAL, ASSASSIN, NONE;

    public Color getColor() {
        if (this == RED) {
            return new Color(232, 60, 64);
        } else if (this == BLUE) {
            return new Color(35, 142, 177);
        } else if (this == NEUTRAL) {
            return new Color(216, 204, 152);
        } else if (this == ASSASSIN){
            return new Color(71, 69, 64);
        } else {
            return Color.WHITE;
        }
    }

    public String toString() {
        if (this == RED) {
            return "R";
        } else if (this == BLUE) {
            return "B";
        } else if (this == NEUTRAL) {
            return "O";
        } else {
            return "X";
        }
    }

    public String fullString() {
        if (this == RED) {
            return "Red";
        } else if (this == BLUE) {
            return "Blue";
        } else {
            throw new IllegalArgumentException();
        }
    }

    public Piece opposite() {
        if (this == RED) {
            return BLUE;
        } else if (this == BLUE) {
            return RED;
        } else {
            throw new IllegalArgumentException();
        }
    }
}
