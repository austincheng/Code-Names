package codeNames.commandLine;

import codeNames.Board;
import codeNames.Piece;
import codeNames.commandLine.gui.*;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

import static codeNames.Constants.DEFAULT_SET;

/**
 * Runnable class to play game that takes command line arguments as parameters.
 * @author Austin Cheng
 */
public class Main {
    public static final Path commaSeparated = Paths.get("wordSets/commaSeparated");
    public static final Path lineSeparated = Paths.get("wordSets/lineSeparated");
    public static final ArrayList<String> ALL = new ArrayList<String>();
    public static int startTime;
    public static int turnTime;

    /** Runs the game. */
    public static void main(String[] args) {
        ArrayList<ArrayList<String>> commands = splitCommands(args);
        startTime = -1;
        turnTime = -1;
        parseCommands(commands);

        String[][] words = getWords();

        Object[] answers = getAnswer();
        Piece[][] answer = (Piece[][]) answers[0];
        Piece first = (Piece) answers[1];

        Board board = new Board(words, answer, first);
        if (startTime == -1) {
            new Game("Code Names", board);
        } else {
            new Game("Code Names", board, startTime, turnTime);
        }
    }

    public static void printErr(String message) {
        System.out.println(message);
        System.exit(0);
    }

    /** Returns random board of word strings. */
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

    /** Returns the random board answers as the first item in the array and which color
     *  goes first as the second item. */
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

    /** Adds all the words in the specified word set with comma separated text file. */
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

    /** Adds all the words in the specified word set with line separated text file. */
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

    /** Splits the arguments into ArrayLists of commands and their arguments.
     *  Ex. args = {--timing, 120, 100, --pg}
     *  Returns <<--timing, 120, 100>, <--pg>>
     */
    public static ArrayList<ArrayList<String>> splitCommands(String[] args) {
        ArrayList<ArrayList<String>> commands = new ArrayList<>();

        for (int i = 0; i < args.length; i++) {
            ArrayList<String> command = new ArrayList<>();
            String arg = args[i];
            command.add(arg);
            if (i < args.length - 1) {
                String next = args[i + 1];
                while (next.length() < 2 || !next.substring(0, 2).equals("--")) {
                    command.add(next);
                    i++;
                    if (i < args.length - 1) {
                        next = args[i + 1];
                    } else {
                        break;
                    }
                }
            }
            commands.add(command);
        }

        return commands;
    }

    /** Uses the ArrayList returned from splitCommands to do every command. */
    public static void parseCommands (ArrayList<ArrayList<String>> commands) {
        ArrayList<String> used = new ArrayList<>();
        if (commands.isEmpty()) {
            addAllWordsLined(DEFAULT_SET);
        }
        for (int i = 0; i < commands.size(); i++) {
            ArrayList<String> command = commands.get(i);
            switch (command.get(0)) {
                case "--words":
                    if (command.size() == 1) {
                        printErr("Error: Too few arguments for --words command");
                    } else {
                        for (int j = 1; j < command.size(); j++) {
                            String fileName = command.get(j);
                            File comma = new File(commaSeparated + "/" + fileName);
                            File lined = new File(lineSeparated + "/" + fileName);
                            if (comma.exists()) {
                                addAllWordsComma(fileName);
                            } else if (lined.exists()) {
                                addAllWordsLined(fileName);
                            } else {
                                printErr("Error: Could not find file " + fileName);
                            }
                        }
                    }
                    break;
                case "--pg":
                    if (command.size() == 1) {
                        addAllWordsLined("PGwords.txt");
                    } else {
                        printErr("Error: Too many arguments for --pg command");
                    }
                    break;
                case "--list":
                    if (!used.isEmpty()) {
                        printErr("Error: Can't use --list in conjunction with other commands");
                    }
                    if (command.size() == 1) {
                        System.out.println("List of Word Sets:");
                        File commaSets = commaSeparated.toFile();
                        File[] listOfFiles = commaSets.listFiles();
                        System.out.println("  Comma Separated: ");
                        for (File f : listOfFiles) {
                            System.out.println("    " + f.getName());
                        }
                        File lineSets = lineSeparated.toFile();
                        listOfFiles = lineSets.listFiles();
                        System.out.println("  Line Separated: ");
                        for (File f : listOfFiles) {
                            System.out.println("    " + f.getName());
                        }
                    } else {
                        printErr("Error: Too many arguments for --list command");
                    }
                    System.exit(0);
                case "--timing":
                    if (used.contains("--old")) {
                        printErr("Error: Old version does not support timing feature");
                    }
                    if (command.size() == 1) {
                        startTime = 150;
                        turnTime = 120;
                    } else if (command.size() == 2) {
                        printErr("Error: Inappropriate number of arguments for --timing command");
                    } else if (command.size() == 3) {
                        int start = 0;
                        int turn = 0;
                        try {
                            start = Integer.parseInt(command.get(1));
                            turn = Integer.parseInt(command.get(2));
                        } catch (NumberFormatException e) {
                            printErr("Error: Inappropriate format for number in --timing command");
                        }
                        if (start <= 0 || turn <= 0) {
                            printErr("Error: Numbers must be positive in --timing command");
                        }
                        startTime = start;
                        turnTime = turn;
                    } else {
                        printErr("Error: Too many arguments for --timing command");
                    }
                    break;
                case "--help":
                    if (!used.isEmpty()) {
                        printErr("Error: Can't use --help in conjunction with other commands");
                    }
                    if (command.size() == 1) {
                        try {
                            FileReader fileReader = new FileReader("help.txt");
                            BufferedReader bf = new BufferedReader(fileReader);

                            String line = bf.readLine();
                            while (line != null) {
                                System.out.println(line);
                                line = bf.readLine();
                            }
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        System.exit(0);
                    } else {
                        printErr("Error: Too many arguments for --help command");
                    }
                default:
                    printErr("Error: Unrecognizable command " + command.get(0));
            }
            used.add(command.get(0));
        }
        if (ALL.isEmpty()) {
            addAllWordsLined(DEFAULT_SET);
        }
    }
}
