package Test;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;


public class Client {
//    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private boolean connection;
//    private BufferedReader bufferedReader;
//    private BufferedWriter bufferedWriter;

    public Client(Socket socket){
        try{
//            this.socket = socket;
//            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            connection = true;
        }catch (IOException e){
            System.out.println("error client");
            e.printStackTrace();
//            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }

    public void sendMessageToServer(String messageToSend){
//        try{
//            bufferedWriter.write(messageToSend);
//            bufferedWriter.newLine();
//            bufferedWriter.flush();
//        }catch (IOException e){
//            e.printStackTrace();
//            System.out.println("error in client sending");
//            closeEverything(socket,bufferedReader,bufferedWriter);
//        }
        try {
            if (messageToSend != null && !"".equals(messageToSend)) {
                int size = messageToSend.length();
                out.writeInt(size);
                out.write(messageToSend.getBytes(), 0, size);
            }
        } catch (IOException e) {
            e.printStackTrace();
            connection = false;
        }
    }

    public void receiveMessageFromServer(VBox messagePane){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(connection){
//                    try{
//
//                        String messageFromClient = bufferedReader.readLine();
//                        ChatControl.displayReceiveMessage(messageFromClient);
//                    }catch (IOException e){
//                        e.printStackTrace();
//                        System.out.println("error in client receiving");
//                        closeEverything(socket,bufferedReader,bufferedWriter);
//                        break;
//                    }
                    String messageFromClient = "";

                    try {
                        byte[] buffer = new byte[1024];

                        int size = in.readInt();
                        while(size > 0) {
                            int len = in.read(buffer, 0, Math.min(size, buffer.length));
                            messageFromClient += new String(buffer, 0, len);
                            size -= len;
                        }
                        ChatControl.displayReceiveMessage(messageFromClient);
                    } catch (IOException e) {
                        e.printStackTrace();
                        connection = false;
                    }
                }
            }
        }).start();
    }

    public void login(String userName){
        try{out.writeInt(userName.length());
            out.write(userName.getBytes(),0,userName.length());
        } catch(IOException e){
            System.err.println("login failure");
        }
    }

//    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
//        try{
//            if(bufferedReader!=null){
//                bufferedReader.close();
//            }
//            if(bufferedWriter!=null){
//                bufferedWriter.close();
//            }
//            if(socket!=null){
//                socket.close();
//            }
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//    }


}
