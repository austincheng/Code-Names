package codeNames.commandLine;

public class Board {
    private String[][] words;
    private Piece[][] answer;
    private Piece[][] covered;
    public boolean gameOver;
    public int redCovers;
    public int blueCovers;
    public int totalRed;
    public int totalBlue;
    public Piece starter;
    public Piece turn;


    public Board(String[][] words, Piece[][] answer, Piece starter) {
        this.words = words;
        this.answer = answer;
        this.starter = starter;
        this.turn = starter;
        gameOver = false;
        redCovers = 0;
        blueCovers = 0;
        if (starter == Piece.RED) {
            totalRed = 9;
            totalBlue = 8;
        } else {
            totalRed = 8;
            totalBlue = 9;
        }
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

    public void switchTurn() {
        turn = turn.opposite();
    }
}
