import java.util.Random;
import java.util.Scanner;

public class NumberGame {

    // -------- GAME CONFIG --------
    private static final int MIN = 1;
    private static final int MAX = 100;
    private static final int MAX_ATTEMPTS = 7;

    // -------- UI CONFIG --------
    private static final int BOX_WIDTH = 58;

    // -------- ANSI COLORS --------
    private static final String RESET = "\u001B[0m";
    private static final String CYAN = "\u001B[36m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String PURPLE = "\u001B[35m";
    private static final String BLUE = "\u001B[34m"; // EXIT color

    private static int TERMINAL_WIDTH;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        TERMINAL_WIDTH = getTerminalWidth();

        int roundsWon = 0;
        boolean playAgain = true;

        while (playAgain) {
            clearScreen();
            int secret = random.nextInt(MAX - MIN + 1) + MIN;
            int attempts = 0;
            boolean won = false;

            drawHeader("NUMBER GUESSING GAME");

            drawBox("Game Rules", new String[]{
                    "Guess a number between " + MIN + " and " + MAX,
                    "Maximum Attempts: " + MAX_ATTEMPTS
            }, CYAN);

            while (attempts < MAX_ATTEMPTS) {
                attempts++;

                System.out.print(centerLine(
                        "Attempt " + attempts + "/" + MAX_ATTEMPTS + " -> Enter your guess: "
                ));

                if (!sc.hasNextInt()) {
                    sc.next();
                    drawMessage("Invalid input! Enter numbers only.", RED);
                    attempts--;
                    continue;
                }

                int guess = sc.nextInt();

                if (guess == secret) {
                    won = true;
                    break;
                } else if (guess < secret) {
                    drawMessage("Too LOW!", YELLOW);
                } else {
                    drawMessage("Too HIGH!", YELLOW);
                }
            }

            if (won) {
                roundsWon++;
                int score = calculateScore(attempts);
                drawBox("YOU WON", new String[]{
                        "Correct Guess!",
                        "Attempts Used: " + attempts,
                        "Score: " + score,
                        "Rounds Won: " + roundsWon
                }, GREEN);
            } else {
                drawBox("GAME OVER", new String[]{
                        "You ran out of attempts",
                        "Correct Number was: " + secret
                }, RED);
            }

            System.out.print("\n" + centerLine("Play Again? (Y/N): "));
            char ch = sc.next().toUpperCase().charAt(0);
            playAgain = (ch == 'Y');
        }

        clearScreen();
        drawBox("EXIT", new String[]{
                "Thanks for playing!",
                "Goodbye!"
        }, BLUE);

        sc.close();
    }

    // -------- UI UTILITIES --------

    private static int getTerminalWidth() {
        try {
            String cols = System.getenv("COLUMNS");
            if (cols != null) {
                return Integer.parseInt(cols);
            }
        } catch (Exception ignored) {}
        return 100; // safe fallback
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void drawHeader(String title) {
        String line = "=".repeat(BOX_WIDTH);
        System.out.println(PURPLE + centerLine(line));
        System.out.println(PURPLE + centerLine(title));
        System.out.println(PURPLE + centerLine(line) + RESET + "\n");
    }

    private static void drawBox(String title, String[] lines, String color) {
        int pad = Math.max(0, (TERMINAL_WIDTH - BOX_WIDTH) / 2);
        String space = " ".repeat(pad);

        System.out.println(color + space + "+" + "-".repeat(BOX_WIDTH - 2) + "+");
        System.out.println(space + "|" + centerInside(title) + "|");
        System.out.println(space + "+" + "-".repeat(BOX_WIDTH - 2) + "+");

        for (String line : lines) {
            System.out.println(space + "|" + centerInside(line) + "|");
        }

        System.out.println(space + "+" + "-".repeat(BOX_WIDTH - 2) + "+" + RESET + "\n");
    }

    private static void drawMessage(String msg, String color) {
        System.out.println(color + centerLine(msg) + RESET + "\n");
    }

    private static String centerLine(String text) {
        int padding = Math.max(0, (TERMINAL_WIDTH - text.length()) / 2);
        return " ".repeat(padding) + text;
    }

    private static String centerInside(String text) {
        int padding = Math.max(0, (BOX_WIDTH - 2 - text.length()) / 2);
        return " ".repeat(padding) + text +
                " ".repeat(BOX_WIDTH - 2 - padding - text.length());
    }

    private static int calculateScore(int attempts) {
        return Math.max(0, 100 - (attempts - 1) * 10);
    }
}
