package Test;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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



public class ChatControl {

    static ObservableList<Node> children;
    static int msgIndex = 0;

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TextField txtInput;
    @FXML
    private Button btnFile;
    @FXML
    private VBox messagePane;
    @FXML
    private Label UsernameLabel;

    private DataOutputStream out;
    private DataInputStream in;
    private boolean connection;

    public  static TransModel model = new TransModel();

    @FXML
    protected void initialize() {
        children = messagePane.getChildren();

        messagePane.heightProperty().addListener(event -> {
            scrollPane.setVvalue(1);
        });

        txtInput.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER"))
//                sendMessage();
                displaySendMessage();
        });

        model.textProperty().addListener((obs, oldText, newText) -> UsernameLabel.setText(newText));
        model.textProperty().addListener((obs, oldText, newText) -> {
            try {
                connect(newText);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }


    public static void connect(String username) throws IOException {
        Socket socket = null;
        String name = null;
        try {
            socket = new Socket("127.0.0.1", 12345);
            print("Connecting to %s:%d\n", "127.0.0.1", 12345);
        } catch (IOException e) {
            System.err.println("Connect failure!!!");
            e.printStackTrace();
        }

        ExecutorService pool = Executors.newFixedThreadPool(2);
        pool.submit(new ClientSend(socket, username));
        pool.submit(new ClientReceive(socket));
    }

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

    private void displaySendMessage() {
        Platform.runLater(() -> {
            String text = txtInput.getText();
            txtInput.clear();
            children.add(messageNode(text, msgIndex == 0));
            msgIndex = (msgIndex + 1) % 2;
        });
    }

//    public void sendMessage(){
//        String text = txtInput.getText();
//        return  text;
//    }

    public static void displayReceiveMessage(String receiveText) {
        Platform.runLater(() -> {
            children.add(messageNode(receiveText, msgIndex == 1));
            msgIndex = (msgIndex + 1) % 2;
        });
    }

    public static void print(String str, Object... o) {
        System.out.printf(str, o);
    }

}
