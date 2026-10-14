import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import static java.lang.System.out;

public class Server {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        new Server().run();
    }

    private void run() {
        readerThread().start();

        try {
            ServerSocket serverSocket = new ServerSocket(8081);

            while (true) {
                Socket socket = serverSocket.accept();

                out.println("Accepted connection from " + socket.getInetAddress().getHostName());

                new Thread(new ServerRunnable(socket)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Thread readerThread() {
        return new Thread(() -> {
            while (sc.hasNextLine()) {
                if (sc.nextLine().equalsIgnoreCase("exit")) {
                    sc.close();
                    System.exit(0);
                }
            }
        });
    }
}
