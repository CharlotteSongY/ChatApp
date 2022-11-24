import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class SenderClient {
    public SenderClient(String serverIP, int port) throws IOException {
        Socket socket = new Socket(serverIP, port);
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String message = scanner.nextLine();
            int size = message.length();
            out.writeInt(size);
            out.write(message.getBytes(), 0, size);
        }
    }

    public static void main(String[] args) throws IOException {
//        String serverIP = args[0];
//        int port = Integer.parseInt(args[1]);
        String serverIP = "192.168.68.82";
        int port =12345;
        new SenderClient(serverIP, port);
    }
}
