import java.util.Scanner;

public class StudentGradeCalculator {

    private static final int BOX_WIDTH = 60;
    private static int TERMINAL_WIDTH;

    // ANSI COLORS
    private static final String RESET = "\u001B[0m";
    private static final String CYAN = "\u001B[36m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String PURPLE = "\u001B[35m";
    private static final String BLUE = "\u001B[34m";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TERMINAL_WIDTH = getTerminalWidth();

        clearScreen();
        drawHeader("STUDENT GRADE CALCULATOR");

        System.out.print(centerLine("Enter number of subjects: "));
        int subjects = sc.nextInt();

        if (subjects <= 0) {
            drawBox("ERROR", new String[]{"Number of subjects must be greater than zero"}, RED);
            sc.close();
            return;
        }

        int totalMarks = 0;

        for (int i = 1; i <= subjects; i++) {
            int marks;
            while (true) {
                System.out.print(centerLine("Enter marks for Subject " + i + " (0-100): "));
                marks = sc.nextInt();
                if (marks >= 0 && marks <= 100) break;
                drawMessage("Invalid marks! Enter between 0 and 100.", RED);
            }
            totalMarks += marks;
        }

        double average = (double) totalMarks / subjects;
        String grade = calculateGrade(average);

        // Color-coded display (padding fixed)
        String[] lines = new String[]{
                colorText("Total Marks: " + totalMarks, CYAN),
                colorText("Average Percentage: " + String.format("%.2f", average) + "%", YELLOW),
                grade.equals("F") ? colorText("Grade: " + grade, RED) : colorText("Grade: " + grade, GREEN)
        };

        drawBox("RESULT", lines, PURPLE);
        drawBox("EXIT", new String[]{"Calculation Completed", "Thank you!"}, BLUE);

        sc.close();
    }

    private static String calculateGrade(double avg) {
        if (avg >= 90) return "A+";
        else if (avg >= 80) return "A";
        else if (avg >= 70) return "B";
        else if (avg >= 60) return "C";
        else if (avg >= 50) return "D";
        else return "F";
    }

    // -------- UI UTILITIES --------
    private static int getTerminalWidth() {
        try {
            String cols = System.getenv("COLUMNS");
            if (cols != null) return Integer.parseInt(cols);
        } catch (Exception ignored) {}
        return 100;
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

    // ----- FIX: center text INSIDE box ignoring ANSI codes -----
    private static String centerInside(String text) {
        String cleanText = text.replaceAll("\u001B\\[[;\\d]*m", ""); // remove ANSI codes
        int padding = Math.max(0, (BOX_WIDTH - 2 - cleanText.length()) / 2);
        int rightPadding = BOX_WIDTH - 2 - padding - cleanText.length();
        return " ".repeat(padding) + text + " ".repeat(rightPadding);
    }

    private static String colorText(String text, String color) {
        return color + text + RESET;
    }
}
