import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.JSONObject;

public class CurrencyConverterCLI {

    // ===== COLORS =====
    private static final String RESET = "\u001B[0m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";
    private static final String BLUE = "\u001B[34m";
    private static final String YELLOW = "\u001B[33m";
    private static final String GREEN = "\u001B[32m";
    private static final String MAGENTA = "\u001B[95m";
    private static final String RED = "\u001B[31m";

    private static final int BOX_WIDTH = 60;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<String> history = new ArrayList<>();
        boolean again = true;

        printHeader("CURRENCY CONVERTER");

        while (again) {

            // ===== BASE CURRENCY =====
            String base;
            while (true) {
                System.out.print(centerText(
                        "Enter Base Currency (Examples: USD, INR, EUR) : "));
                base = sc.next().toUpperCase();
                if (isValidCurrency(base)) break;
                drawBox("ERROR", "Currency must be 3 alphabetic letters", RED);
            }
            drawBox("BASE CURRENCY", base, CYAN);

            // ===== TARGET CURRENCY =====
            String target;
            while (true) {
                System.out.print(centerText(
                        "Enter Target Currency (Examples: INR, USD, GBP) : "));
                target = sc.next().toUpperCase();
                if (isValidCurrency(target)) break;
                drawBox("ERROR", "Currency must be 3 alphabetic letters", RED);
            }
            drawBox("TARGET CURRENCY", target, BLUE);

            // ===== AMOUNT =====
            double amount;
            while (true) {
                System.out.print(centerText("Enter Amount to Convert : "));
                if (sc.hasNextDouble()) {
                    amount = sc.nextDouble();
                    if (amount > 0) break;
                } else {
                    sc.next();
                }
                drawBox("ERROR", "Amount must be a positive number", RED);
            }
            drawBox("AMOUNT", String.format("%.2f", amount), YELLOW);

            // ===== CONVERSION =====
            try {
                double rate = fetchRate(base, target);
                double converted = amount * rate;

                String record = String.format("%.2f %s = %.2f %s",
                        amount, base, converted, target);
                history.add(record);

                drawResult(amount, base, converted, target, rate);
                drawHistory(history);

            } catch (Exception e) {
                drawBox("ERROR", "Failed to fetch exchange rate", RED);
            }

            System.out.print(centerText("Convert again? (Y/N): "));
            again = sc.next().equalsIgnoreCase("Y");
            System.out.println();
        }

        drawBox("EXIT", "Thank you for using Currency Converter", RED);
        sc.close();
    }

    // ================= VALIDATION =================
    private static boolean isValidCurrency(String code) {
        return code.matches("[A-Z]{3}");
    }

    // ================= API =================
    private static double fetchRate(String from, String to) throws Exception {
        URL url = new URL("https://free.ratesdb.com/v1/rates?from=" + from + "&to=" + to);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String json = br.readLine();
        br.close();

        JSONObject obj = new JSONObject(json);
        return obj.getJSONObject("data").getJSONObject("rates").getDouble(to);
    }

    // ================= TERMINAL WIDTH (WINDOWS SAFE) =================
    private static int getTerminalWidth() {
        try {
            Process p = new ProcessBuilder("cmd", "/c", "mode con").start();
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));

            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().startsWith("Columns:")) {
                    return Integer.parseInt(line.replaceAll("\\D+", ""));
                }
            }
        } catch (Exception ignored) {}
        return 100; // fallback
    }

    // ================= UI =================
    private static void printHeader(String title) {
        int width = getTerminalWidth();
        System.out.println(PURPLE);
        System.out.println("=".repeat(width));
        System.out.println(centerLine(title));
        System.out.println("=".repeat(width));
        System.out.println(RESET);
    }

    private static void drawBox(String title, String value, String color) {
        int width = getTerminalWidth();
        String space = " ".repeat(Math.max(0, (width - BOX_WIDTH) / 2));

        System.out.println(color);
        System.out.println(space + "+" + "-".repeat(BOX_WIDTH - 2) + "+");
        System.out.println(space + "|" + centerInBox(title) + "|");
        System.out.println(space + "+" + "-".repeat(BOX_WIDTH - 2) + "+");
        System.out.println(space + "|" + centerInBox(value) + "|");
        System.out.println(space + "+" + "-".repeat(BOX_WIDTH - 2) + "+");
        System.out.println(RESET);
    }

    private static void drawResult(double amt, String from, double conv, String to, double rate) {
        int width = getTerminalWidth();
        String space = " ".repeat(Math.max(0, (width - BOX_WIDTH) / 2));

        System.out.println(GREEN);
        System.out.println(space + "+" + "-".repeat(BOX_WIDTH - 2) + "+");
        System.out.println(space + "|" + centerInBox("RESULT") + "|");
        System.out.println(space + "+" + "-".repeat(BOX_WIDTH - 2) + "+");
        System.out.println(space + "|" + centerInBox(
                String.format("%.2f %s = %.2f %s", amt, from, conv, to)) + "|");
        System.out.println(space + "|" + centerInBox(
                String.format("Rate: 1 %s = %.5f %s", from, rate, to)) + "|");
        System.out.println(space + "+" + "-".repeat(BOX_WIDTH - 2) + "+");
        System.out.println(RESET);
    }

    private static void drawHistory(List<String> history) {
        int width = getTerminalWidth();
        String space = " ".repeat(Math.max(0, (width - BOX_WIDTH) / 2));

        System.out.println(MAGENTA);
        System.out.println(space + "+" + "-".repeat(BOX_WIDTH - 2) + "+");
        System.out.println(space + "|" + centerInBox("CONVERSION HISTORY") + "|");
        System.out.println(space + "+" + "-".repeat(BOX_WIDTH - 2) + "+");

        for (String h : history) {
            System.out.println(space + "|" + centerInBox(h) + "|");
        }

        System.out.println(space + "+" + "-".repeat(BOX_WIDTH - 2) + "+");
        System.out.println(RESET);
    }

    // ================= HELPERS =================
    private static String centerInBox(String text) {
        int pad = Math.max(0, (BOX_WIDTH - 2 - text.length()) / 2);
        return " ".repeat(pad) + text + " ".repeat(BOX_WIDTH - 2 - pad - text.length());
    }

    private static String centerLine(String text) {
        int width = getTerminalWidth();
        int pad = Math.max(0, (width - text.length()) / 2);
        return " ".repeat(pad) + text;
    }

    private static String centerText(String text) {
        int width = getTerminalWidth();
        int pad = Math.max(0, (width - text.length()) / 2);
        return " ".repeat(pad) + text;
    }
}

