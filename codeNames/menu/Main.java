package codeNames.menu;

import codeNames.menu.gui.*;
import codeNames.menu.guiUCB.*;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static final Path commaSeparated = Paths.get("wordSets/commaSeparated");
    public static final Path lineSeparated = Paths.get("wordSets/lineSeparated");
    public static final ArrayList<String> ALL = new ArrayList<String>();
    public static boolean useOld;
    public static int startTime;
    public static int turnTime;
    private static Menu menu;

    public static void main(String[] args) {
        useOld = false;
        startTime = -1;
        turnTime = -1;

        addAllWordsLined("words.txt");
        menu = new Menu("Code Names");
    }

    public static void newGame() {
        String[][] words = getWords();

        Object[] answers = getAnswer();
        Piece[][] answer = (Piece[][]) answers[0];
        Piece first = (Piece) answers[1];

        if (useOld) {
            BoardUCB board = new BoardUCB(words, answer, first);
            GUIUCB display = new GUIUCB("Code Names", board);
            display.display(true);
        } else {
            Board board = new Board(words, answer, first);
            if (startTime == -1) {
                new Game("Code Names", board, menu);
            } else {
                new Game("Code Names", board, startTime, turnTime, menu);
            }
        }
    }

    public static void printErr(String message) {
        System.out.println(message);
        System.exit(0);
    }

    public static String[][] getWords() {
        if (ALL.size() < 25) {
            printErr("Error: Not enough words in files");
        }

        String[][] words = new String[5][5];
        ArrayList<Integer> used = new ArrayList<>();
        Random ran = new Random();
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                int index = ran.nextInt(ALL.size());
                while (used.contains(index)) {
                    index = ran.nextInt(ALL.size());
                }
                words[r][c] = ALL.get(index);
                used.add(index);
            }
        }
        return words;
    }

    public static Object[] getAnswer() {
        Piece[][] answer = new Piece[5][5];
        Random ran = new Random();
        int assassinRow = ran.nextInt(5);
        int assassinCol = ran.nextInt(5);

        answer[assassinRow][assassinCol] = Piece.ASSASSIN;
        for (int i = 0; i < 23; i++) {
            if (i % 3 == 0) {
                int row = ran.nextInt(5);
                int col = ran.nextInt(5);
                while (answer[row][col] != null) {
                    row = ran.nextInt(5);
                    col = ran.nextInt(5);
                }
                answer[row][col] = Piece.RED;
            } else if (i % 3 == 1) {
                int row = ran.nextInt(5);
                int col = ran.nextInt(5);
                while (answer[row][col] != null) {
                    row = ran.nextInt(5);
                    col = ran.nextInt(5);
                }
                answer[row][col] = Piece.BLUE;
            } else {
                int row = ran.nextInt(5);
                int col = ran.nextInt(5);
                while (answer[row][col] != null) {
                    row = ran.nextInt(5);
                    col = ran.nextInt(5);
                }
                answer[row][col] = Piece.NEUTRAL;
            }
        }

        int lastRow = 0;
        int lastCol = 0;
        boolean found = false;
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                if (answer[r][c] == null) {
                    lastRow = r;
                    lastCol = c;
                    found = true;
                    break;
                }
            }
            if (found) {
                break;
            }
        }

        double coin = ran.nextDouble();
        Piece first;
        if (coin < 0.5) {
            answer[lastRow][lastCol] = Piece.RED;
            first = Piece.RED;
        } else {
            answer[lastRow][lastCol] = Piece.BLUE;
            first = Piece.BLUE;
        }

        Object[] result = new Object[2];
        result[0] = answer;
        result[1] = first;

        return result;
    }

    public static void addAllWordsComma(String fileName) {
        try {
            FileReader fileReader = new FileReader(commaSeparated + "/" + fileName);
            BufferedReader bf = new BufferedReader(fileReader);
            int x = bf.read();
            while (x != -1) {
                String word = "";
                char c = (char) x;
                while (c != ',' && x != -1) {
                    word += c;
                    x = bf.read();
                    c = (char) x;
                }
                while (x == ',' || x== ' ') {
                    x = bf.read();
                }
                ALL.add(word.toUpperCase());
            }
        } catch (FileNotFoundException e) {
            printErr("Error: Could not find file " + fileName);
        } catch (IOException e) {
            printErr("IOException");
        }
    }

    public static void addAllWordsLined(String fileName) {
        try {
            FileReader fileReader = new FileReader(lineSeparated + "/" + fileName);
            BufferedReader bf = new BufferedReader(fileReader);
            String word = bf.readLine();
            while (word != null) {
                ALL.add(word.toUpperCase());
                word = bf.readLine();
            }
        } catch (FileNotFoundException e) {
            printErr("Error: Could not find file " + fileName);
        } catch (IOException e) {
            printErr("IOException");
        }
    }
}
