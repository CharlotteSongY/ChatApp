
// use cd .\out\production\test1 to test
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    public static void print(String str, Object... o) {
        System.out.printf(str, o);
    }

    public static void main(String[] args) throws IOException {
        Socket socket = null;
        String name = null;
        try {
            socket = new Socket("127.0.0.1", 12345);
            print("Connecting to %s:%d\n", "127.0.0.1", 12345);
            Scanner scanner = new Scanner(System.in);
            print("Input your account and press ENTER\n");
            name = scanner.nextLine();
        } catch (IOException e) {
            System.err.println("Connect failure!!!");
            e.printStackTrace();
        }

        ExecutorService pool = Executors.newFixedThreadPool(2);
            pool.submit(new ClientSend(socket, name));
            pool.submit(new ClientReceive(socket));
    }
}
