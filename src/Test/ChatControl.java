package Test;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatControl implements Initializable {

    static ObservableList<Node> children;
    static int msgIndex = 0;

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TextField txtInput;
    @FXML
    private Button btnSend;
    @FXML
    private VBox messagePane;
    @FXML
    private Label UsernameLabel;

//    public DataOutputStream out;
//    public DataInputStream in;
//    public boolean connection;
    private Client client;

    public  static TransModel model = new TransModel();

    @FXML
    public void initialize(URL location,ResourceBundle resources) {
        try{
            client = new Client(new Socket("127.0.0.1", 12345));
            print("Connecting to %s:%d\n", "127.0.0.1", 12345);
        }catch (IOException e) {
            System.err.println("Connect failure!!!");
            e.printStackTrace();
        }

        model.textProperty().addListener((obs, oldText, newText) -> UsernameLabel.setText(newText));
        model.textProperty().addListener((obs, oldText, newText) -> {
//            try {
//                client.login(newText);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            client.login(newText);
        });

        children = messagePane.getChildren();

//        messagePane.heightProperty().addListener(event -> {
//            scrollPane.setVvalue(1);
//        });

        messagePane.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                scrollPane.setVvalue((Double) newValue);
            }
        });

        client.receiveMessageFromServer(messagePane);

        btnSend.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String messageToSend = txtInput.getText();
                if (messageToSend != null && !"".equals(messageToSend)) {
                    children.add(messageNode(messageToSend,  true));
                    client.sendMessageToServer(messageToSend);
                    txtInput.clear();
                }

            }
        });
    }


//    public static void connect(String username) throws IOException {
//        Socket socket = null;
//        String name = null;
//        try {
//            socket = new Socket("127.0.0.1", 12345);
//            print("Connecting to %s:%d\n", "127.0.0.1", 12345);
//        } catch (IOException e) {
//            System.err.println("Connect failure!!!");
//            e.printStackTrace();
//        }
//
//        ExecutorService pool = Executors.newFixedThreadPool(2);
//        pool.submit(new ClientSend(socket, username));
//        pool.submit(new ClientReceive(socket));
//    }

    private static Node messageNode(String text, boolean alignToRight) {
        HBox box = new HBox();
        box.paddingProperty().setValue(new Insets(10, 10, 10, 10));

        if (alignToRight)
            box.setAlignment(Pos.BASELINE_RIGHT);
        javafx.scene.control.Label label = new Label(text);
        label.setWrapText(true);
        box.getChildren().add(label);
        return box;
    }

//    private void displaySendMessage() {
//        Platform.runLater(() -> {
//            String message = "";
//            try {
//                message = txtInput.getText();
//                if (message != null && !"".equals(message)) {
//                    int size = message.length();
//                    out.writeInt(size);
//                    out.write(message.getBytes(), 0, size);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//                connection = false;
//            }
////            String text = txtInput.getText();
////            txtInput.clear();
//            children.add(messageNode(message, msgIndex == 0));
//            msgIndex = (msgIndex + 1) % 2;
//        });
//    }

//    private void displaySendMessage() {
//        Platform.runLater(() -> {
//            String text = txtInput.getText();
//            txtInput.clear();
//            children.add(messageNode(text, msgIndex == 0));
//            msgIndex = (msgIndex + 1) % 2;
//        });
//    }


//    public void sendMessage(){
//        String text = txtInput.getText();
//        return  text;
//    }

    public static void displayReceiveMessage(String receiveText) {
        Platform.runLater(() -> {
            children.add(messageNode(receiveText, false));
        });
    }

    public static void print(String str, Object... o) {
        System.out.printf(str, o);
    }

}
