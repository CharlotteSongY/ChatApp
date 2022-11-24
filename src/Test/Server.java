import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public Server(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            Socket clientSocket = serverSocket.accept();
            Thread t = new Thread(() -> {
                try {
                    serve(clientSocket);
                } catch (IOException ex) {
                    System.out.println("Connection drop!");
                }
            });
            t.start();
        }
    }

    private void serve(Socket clientSocket) throws IOException {
        byte[] buffer = new byte[1024];
        DataInputStream in = new DataInputStream(clientSocket.getInputStream());
        DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

        while (true) {
            int size = in.readInt();
            String message = "";
            while (size > 0) {
                int len = in.read(buffer, 0, Math.min(size, buffer.length));
                message += new String(buffer, 0, len);
                size -= len;
            }

            out.writeInt(message.length());
            out.write(message.getBytes(), 0, message.length());
        }
    }

    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(args[0]);
        new Server(port);
    }
}
