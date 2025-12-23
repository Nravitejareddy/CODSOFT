import java.io.*;
import java.util.*;

class Student {
    int roll;
    String name;
    String grade;
    int age;

    Student(int roll, String name, String grade, int age) {
        this.roll = roll;
        this.name = name;
        this.grade = grade;
        this.age = age;
    }

    String toFile() {
        return roll + "," + name + "," + grade + "," + age;
    }
}

public class StudentManagementSystem {

    /* COLORS */
    static final String RESET = "\u001B[0m";
    static final String CYAN = "\u001B[36m";
    static final String GREEN = "\u001B[32m";
    static final String RED = "\u001B[31m";
    static final String YELLOW = "\u001B[33m";
    static final String BLUE = "\u001B[34m";
    static final String PURPLE = "\u001B[35m";
    static final String WHITE = "\u001B[37m";

    static final String FILE = "students.txt";
    static List<Student> students = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    static final int BOX_WIDTH = 58;

    static final int COL_ROLL = 12;
    static final int COL_NAME = 20;
    static final int COL_GRADE = 8;
    static final int COL_AGE = 6;

    public static void main(String[] args) {
        load();
        while (true) {
            clear();
            title();
            menu();
            int choice = intInput("Choose option (1-6): ");

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> editStudent();
                case 3 -> removeStudent();
                case 4 -> searchStudent();
                case 5 -> displayAll();
                case 6 -> exitApp();
                default -> message("Invalid option!", RED);
            }
            pause();
        }
    }

    /* ================= CENTERING ================= */

    static int terminalWidth() {
        try {
            return Integer.parseInt(System.getenv("COLUMNS"));
        } catch (Exception e) {
            return 100;
        }
    }

    static String leftPad() {
        int pad = (terminalWidth() - (BOX_WIDTH + 2)) / 2;
        return " ".repeat(Math.max(0, pad));
    }

    static void printLine(String s) {
        System.out.println(leftPad() + s);
    }

    /* ================= UI ================= */

    static void clear() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception ignored) {}
    }

    static void title() {
        printLine("=".repeat(BOX_WIDTH + 2));
        printLine(centerText("STUDENT MANAGEMENT SYSTEM"));
        printLine("=".repeat(BOX_WIDTH + 2));
        System.out.println();
    }

    static void menu() {
        box("MENU", new String[]{
                "[1] Add Student",
                "[2] Edit Student",
                "[3] Remove Student",
                "[4] Search Student",
                "[5] Display All Students",
                "[6] Exit"
        }, CYAN);
    }

    static void message(String msg, String color) {
        box("MESSAGE", new String[]{msg}, color);
    }

    static void box(String title, String[] content, String color) {
        printLine(color + "┌" + "─".repeat(BOX_WIDTH) + "┐" + RESET);
        printLine(color + "│" + padCenter(title, BOX_WIDTH) + "│" + RESET);
        printLine(color + "├" + "─".repeat(BOX_WIDTH) + "┤" + RESET);
        for (String line : content) {
            printLine(color + "│" + padCenter(line, BOX_WIDTH) + "│" + RESET);
        }
        printLine(color + "└" + "─".repeat(BOX_WIDTH) + "┘" + RESET);
    }

    static String centerText(String text) {
        int pad = (BOX_WIDTH + 2 - text.length()) / 2;
        return " ".repeat(Math.max(0, pad)) + text;
    }

    static String padCenter(String text, int width) {
        if (text.length() > width)
            return text.substring(0, width);
        int pad = (width - text.length()) / 2;
        return " ".repeat(pad) + text + " ".repeat(width - text.length() - pad);
    }

    /* ================= FUNCTIONS ================= */

    static void addStudent() {
        int roll = intInput("Roll No: ");
        if (find(roll) != null) {
            message("Roll already exists!", RED);
            return;
        }
        String name = strInput("Name: ");
        String grade = strInput("Grade: ");
        int age = intInput("Age: ");

        students.add(new Student(roll, name, grade, age));
        save();
        message("Student added successfully!", GREEN);
    }

    static void editStudent() {
        int roll = intInput("Enter Roll No: ");
        Student s = find(roll);
        if (s == null) {
            message("Student not found!", RED);
            return;
        }
        s.name = strInput("New Name: ");
        s.grade = strInput("New Grade: ");
        s.age = intInput("New Age: ");
        save();
        message("Student updated!", GREEN);
    }

    static void removeStudent() {
        int roll = intInput("Enter Roll No: ");
        Student s = find(roll);
        if (s == null) {
            message("Student not found!", RED);
            return;
        }
        students.remove(s);
        save();
        message("Student removed!", GREEN);
    }

    static void searchStudent() {
        int roll = intInput("Enter Roll No: ");
        Student s = find(roll);
        if (s == null) {
            message("Student not found!", RED);
            return;
        }
        tableHeader();
        tableRow(s);
        tableFooter();
    }

    static void displayAll() {
        if (students.isEmpty()) {
            message("No students available!", RED);
            return;
        }
        tableHeader();
        for (Student s : students)
            tableRow(s);
        tableFooter();
    }

    static void exitApp() {
        message("Thank you for using the system", YELLOW);
        System.exit(0);
    }

    /* ================= TABLE (COLOR SAFE) ================= */

    static void tableHeader() {
        printLine(BLUE +
                "┌" + "─".repeat(COL_ROLL) +
                "┬" + "─".repeat(COL_NAME) +
                "┬" + "─".repeat(COL_GRADE) +
                "┬" + "─".repeat(COL_AGE) + "┐" + RESET);

        printLine(PURPLE + String.format(
                "│%-" + COL_ROLL + "s│%-" + COL_NAME + "s│%-" + COL_GRADE + "s│%-" + COL_AGE + "s│",
                "Roll", "Name", "Grade", "Age") + RESET);

        printLine(BLUE +
                "├" + "─".repeat(COL_ROLL) +
                "┼" + "─".repeat(COL_NAME) +
                "┼" + "─".repeat(COL_GRADE) +
                "┼" + "─".repeat(COL_AGE) + "┤" + RESET);
    }

    static void tableRow(Student s) {
        printLine(WHITE + String.format(
                "│%-" + COL_ROLL + "d│%-" + COL_NAME + "s│%-" + COL_GRADE + "s│%-" + COL_AGE + "d│",
                s.roll, s.name, s.grade, s.age) + RESET);
    }

    static void tableFooter() {
        printLine(BLUE +
                "└" + "─".repeat(COL_ROLL) +
                "┴" + "─".repeat(COL_NAME) +
                "┴" + "─".repeat(COL_GRADE) +
                "┴" + "─".repeat(COL_AGE) + "┘" + RESET);
    }

    /* ================= STORAGE ================= */

    static void load() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");
                students.add(new Student(
                        Integer.parseInt(d[0]),
                        d[1],
                        d[2],
                        Integer.parseInt(d[3])));
            }
        } catch (Exception ignored) {}
    }

    static void save() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE))) {
            for (Student s : students)
                pw.println(s.toFile());
        } catch (Exception e) {
            System.out.println("File error!");
        }
    }

    /* ================= HELPERS ================= */

    static Student find(int roll) {
        for (Student s : students)
            if (s.roll == roll)
                return s;
        return null;
    }

    static int intInput(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                int v = Integer.parseInt(sc.nextLine());
                if (v <= 0) throw new Exception();
                return v;
            } catch (Exception e) {
                System.out.println("Invalid number!");
            }
        }
    }

    static String strInput(String msg) {
        while (true) {
            System.out.print(msg);
            String s = sc.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("Cannot be empty!");
        }
    }

    static void pause() {
        System.out.print("\nPress Enter to continue...");
        sc.nextLine();
    }
}
