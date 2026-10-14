import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.math.BigInteger;
import java.util.Scanner;

public class ServerRunnable implements Runnable {
    private static final String AUTHOR = "Feshchuk Vlad";
    private static final String GROUP = "4144";
    private static final String VARIANT = "6";

    private final Scanner in;
    private final PrintWriter out;

    public ServerRunnable(Socket socket) {
        try {
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        out.println("Author: " + AUTHOR + " | Group: " + GROUP + " | Variant: " + VARIANT
                + " | Date and time: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        out.println("Enter your numbers (separated by spaces):");

        while (in.hasNextLine()) {
            String msgClient = in.nextLine().trim();

            if (msgClient.isEmpty()) {
                out.println("- Please enter at least one number!");
            } else if (!msgClient.matches("[+-]?\\d+(\\s+[+-]?\\d+)*")) {
                out.println("- Please write only decimal integers separated by spaces!");
            } else {
                out.println("Result: " + getResult(msgClient.split("\\s+")));
            }

            out.println("Enter your numbers (separated by spaces):");
        }
    }

    /** Повертає рядок "max min" для послідовності чисел. */
    private static String getResult(String[] tokens) {
        BigInteger max = null;
        BigInteger min = null;

        for (String token : tokens) {
            BigInteger value = new BigInteger(token.startsWith("+") ? token.substring(1) : token);

            if (max == null || value.compareTo(max) > 0) {
                max = value;
            }
            if (min == null || value.compareTo(min) < 0) {
                min = value;
            }
        }
        return max + " " + min;
    }
}
