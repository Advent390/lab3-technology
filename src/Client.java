import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 8081);

        Scanner in = new Scanner(socket.getInputStream());
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        readerThread(in).start();
        doRequest(socket, out);
    }

    private static void doRequest(Socket socket, PrintWriter out) throws IOException {
        while (sc.hasNextLine()) {
            String msg = sc.nextLine();

            if (msg.equalsIgnoreCase("exit")) {

                socket.close();
                System.exit(0);
            }
            out.println(msg);
        }
    }

    private static Thread readerThread(Scanner in) {
        return new Thread(() -> {
            while (in.hasNextLine()) {
                String msg = in.nextLine();
                System.out.println(msg);
            }
        });
    }
}
