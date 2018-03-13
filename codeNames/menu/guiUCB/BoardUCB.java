package codeNames.menu.guiUCB;

import codeNames.menu.Piece;

import java.util.Observable;

public class BoardUCB extends Observable {
    private String[][] words;
    private Piece[][] answer;
    private Piece[][] covered;
    public Piece starter;

    public BoardUCB(String[][] words, Piece[][] answer, Piece starter) {
        this.words = words;
        this.answer = answer;
        this.starter = starter;
        covered = new Piece[5][5];
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                covered[r][c] = Piece.NONE;
            }
        }
    }

    public String getWord(int r, int c) {
        return words[r][c];
    }

    public Piece getAnswer(int r, int c) {
        return answer[r][c];
    }

    public Piece getCover(int r, int c) {
        return covered[r][c];
    }

    public void setCover(int r, int c, Piece cover) {
        covered[r][c] = cover;
    }
}
