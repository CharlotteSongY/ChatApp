import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ReceiverClient {
    public ReceiverClient(String serverIP, int port) throws IOException {
        Socket socket = new Socket(serverIP, port);
        DataInputStream in = new DataInputStream(socket.getInputStream());
        byte[] buffer = new byte[1024];

        while (true) {
            String message = "";
            int size = in.readInt();
            while ((size > 0)) {
                int len = in.read(buffer, 0, Math.min(size, buffer.length));
                message += new String(buffer, 0, len);
                size -= len;
            }
            System.out.printf(message + "\n");
        }
    }

    public static void main(String[] args) throws IOException{
        String serverIP = "192.168.68.82";
        int port = 12345;
        new SenderClient(serverIP, port);
    }
}
