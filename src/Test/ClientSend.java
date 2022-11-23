package Test;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

//implements Runnable
public class ClientSend implements Runnable{
    private DataOutputStream out;
    private DataInputStream in;
    private boolean connection;
    private Scanner scanner;

    public ClientSend(Socket socket, String userName) {
        try {
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
            login(out, userName);
            connection = true;
        } catch (IOException e) {
            e.printStackTrace();
            connection = false;
        }

    }

    public void send() {
        try {
            String message= scanner.nextLine();
            if (message != null && !"".equals(message)) {
                int size = message.length();
                out.writeInt(size);
                out.write(message.getBytes(), 0, size);
            }
        } catch (IOException e) {
            e.printStackTrace();
            connection = false;
        }
    }

    public void print(String str, Object... o) {
        System.out.printf(str, o);
    }
    public void login(DataOutputStream out, String userName){
        try{out.writeInt(userName.length());
            out.write(userName.getBytes(),0,userName.length());
        } catch(IOException e){
            System.err.println("login failure");
        }
    }

    @Override
    public void run() {
        while (connection) {
            send();
        }
    }
}


