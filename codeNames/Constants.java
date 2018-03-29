package codeNames;

/**
 * Class for all constants relating GUI sizes.
 * @author Austin Cheng
 */
public class Constants {
    /** Width of the word cards. */
    public static final int CARD_WIDTH = 200;
    /** Height of the word cards. */
    public static final int CARD_HEIGHT = 100;
    /** Space between word cards. */
    public static final int IN_BETWEEN = 50;
    /** Font size of words. */
    public static final int FONT_SIZE = 20;

    /** Width of buttons of game phase. */
    public static final int BUTTON_WIDTH = 50;
    /** Height of buttons of game phase. */
    public static final int BUTTON_HEIGHT = 25;

    /** Space between timer and switch turn button. */
    public static final int TIMER_PADDING = 50;

    /** Width of entire screen. */
    public static final int WIDTH = CARD_WIDTH * 5 + IN_BETWEEN * 6;
    /** Height of entire screen. */
    public static final int HEIGHT = CARD_HEIGHT * 5 + BUTTON_HEIGHT + IN_BETWEEN * 7;

    /** File name of default word set. */
    public static final String DEFAULT_SET = "standardWords.txt";
}
