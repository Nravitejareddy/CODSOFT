import java.util.Scanner;

class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public boolean withdraw(double amount) {
        if (amount <= 0) return false;
        if (amount > balance) return false;
        balance -= amount;
        return true;
    }

    public boolean deposit(double amount) {
        if (amount <= 0) return false;
        balance += amount;
        return true;
    }

    public double checkBalance() {
        return balance;
    }
}

public class ATMInterface {

    private static final int BOX_WIDTH = 60;
    private static int TERMINAL_WIDTH;

    // ANSI Colors
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

        BankAccount account = new BankAccount(0); // STARTING BALANCE IS 0
        boolean exit = false;

        while (!exit) {
            clearScreen();
            drawHeader("ATM INTERFACE");

            drawBox("ATM MENU", new String[]{
                    "[1] Check Balance",
                    "[2] Deposit Money",
                    "[3] Withdraw Money",
                    "[4] Exit"
            }, CYAN);

            System.out.print(centerLine("Enter your choice (1-4): "));
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    drawBox("BALANCE", new String[]{
                            "Your Balance: Rs " + String.format("%.2f", account.checkBalance())
                    }, GREEN);
                    pause(sc);
                    break;
                case 2:
                    System.out.print(centerLine("Enter amount to deposit: "));
                    double depositAmount = sc.nextDouble();
                    if (account.deposit(depositAmount)) {
                        drawBox("SUCCESS", new String[]{
                                "Rs " + String.format("%.2f", depositAmount) + " deposited successfully!"
                        }, GREEN);
                    } else {
                        drawBox("ERROR", new String[]{"Invalid deposit amount!"}, RED);
                    }
                    pause(sc);
                    break;
                case 3:
                    System.out.print(centerLine("Enter amount to withdraw: "));
                    double withdrawAmount = sc.nextDouble();
                    if (account.withdraw(withdrawAmount)) {
                        drawBox("SUCCESS", new String[]{
                                "Rs " + String.format("%.2f", withdrawAmount) + " withdrawn successfully!"
                        }, GREEN);
                    } else {
                        drawBox("ERROR", new String[]{"Insufficient balance or invalid amount!"}, RED);
                    }
                    pause(sc);
                    break;
                case 4:
                    drawBox("EXIT", new String[]{"Thank you for using ATM!"}, BLUE);
                    exit = true;
                    break;
                default:
                    drawBox("ERROR", new String[]{"Invalid choice! Please select 1-4."}, RED);
                    pause(sc);
                    break;
            }
        }

        sc.close();
    }

    // ----------------- UTILITY FUNCTIONS -----------------

    private static void pause(Scanner sc) {
        System.out.print(centerLine("Press Enter to continue..."));
        try { System.in.read(); } catch(Exception ignored) {}
    }

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

    private static String centerLine(String text) {
        int padding = Math.max(0, (TERMINAL_WIDTH - text.length()) / 2);
        return " ".repeat(padding) + text;
    }

    private static String centerInside(String text) {
        String cleanText = text.replaceAll("\u001B\\[[;\\d]*m", ""); // remove ANSI codes
        int padding = Math.max(0, (BOX_WIDTH - 2 - cleanText.length()) / 2);
        int rightPadding = BOX_WIDTH - 2 - padding - cleanText.length();
        return " ".repeat(padding) + text + " ".repeat(rightPadding);
    }
}
