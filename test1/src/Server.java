import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
//import java.util.ArrayList;

public class Server{
    private HashMap<Integer, UserSocket> userMap;
    private int count = 0;
    public Server() {
        userMap = new HashMap<>();
    }

    public void print(String str, Object... o) {
        System.out.printf(str, o);
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);
        System.out.println("Server start");

        while (true) {
            Socket socket = serverSocket.accept();
            count += 1;
            UserSocket userSocket = new UserSocket(count, socket);
            userMap.put(count, userSocket);

            print("Established a connection to host %s:%d\n",
                    socket.getInetAddress(), socket.getPort(), socket.getInputStream());
            new Thread(userSocket).start();
        }
    }

    class UserSocket implements Runnable {

        private DataInputStream in;
        private DataOutputStream out;
        private int userId;
        private String userName;
        private boolean connection;

        public UserSocket(int userId, Socket socket) {
            this.userId = userId;

            //make sure connection is ok
            try {
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
                connection = true;
            } catch (IOException e) {
                e.printStackTrace();
                connection = false;
            }

            //add user name
            try {
                if (in != null) {
                    byte[] buffer = new byte[1024];
                    int size = in.readInt();
                    String name = "";
                    while(size > 0) {
                        int len = in.read(buffer, 0, Math.min(size, buffer.length));
                        name += new String(buffer, 0, len);
                        size -= len;
                    }
                    this.userName = name;
                }
                sendOther("ID:" + this.userId + " " + this.userName + " is come to the chat", true);
                send("welcome to chat room");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public String receive() {
            String message = "";
            byte[] buffer = new byte[1024];
            try {
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
            return message;
        }

        public void send(String message) {
            try {
                out.writeInt(message.length());
                out.write(message.getBytes(), 0, message.length());
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
                connection = false;
            }
        }

        public void sendOther(String msg, boolean sys) {
            if (msg.startsWith("@") && msg.contains(":")) {
                Integer id = Integer.parseInt(msg.substring(1, msg.indexOf(":")));
                String newMsg = msg.substring(msg.indexOf(":"));

                UserSocket userSocket = userMap.get(id);
                if (userSocket != null) {
                    userSocket.send("ID:" + this.userId + " " + this.userName + " "+ "direct send you" + newMsg);
                }
            } else {
            Collection<UserSocket> values = userMap.values();
            for (UserSocket userSocket : values) {
                if (userSocket != this) {
                    if (sys) {
                        userSocket.send("System message:" + msg);
                    } else {
                        userSocket.send("ID:" + this.userId + " " + this.userName + msg);
                    }
                }
            }
        }
        }

        @Override
        public void run() {
            while (connection) {
                sendOther(receive(),false);
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();

        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}