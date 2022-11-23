

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientReceive implements Runnable {
    private DataInputStream in;
    private boolean connection;
    public ChatControl controller;

    public static void print(String str, Object... o) {
        System.out.printf(str, o);
    }

    public ClientReceive(Socket socket) {
        try {
            in = new DataInputStream(socket.getInputStream());
            connection = true;
        } catch (IOException e) {
            e.printStackTrace();
            connection = false;
        }
    }

    public void receive() {
        String message = "";

        try {
            byte[] buffer = new byte[1024];

            int size = in.readInt();
            while(size > 0) {
                int len = in.read(buffer, 0, Math.min(size, buffer.length));
                message += new String(buffer, 0, len);
                size -= len;
            }
        } catch (IOException e) {
            e.printStackTrace();
            connection = false;
        }
        print(message + "\n");
        ChatControl.displayReceiveMessage(message);

        //return message;
    }

    @Override
    public void run() {
        while (connection) {
            receive();
        }
    }
}

