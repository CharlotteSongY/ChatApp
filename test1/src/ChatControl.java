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
import java.io.IOException;
import java.net.Socket;

public class ChatControl {

    ObservableList<Node> children;
    int msgIndex = 0;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TextField txtInput;
    @FXML
    private Button btnFile;
    @FXML
    private VBox messagePane;

    private DataInputStream in;
    private boolean connection;

    @FXML
    protected void initialize() {
        children = messagePane.getChildren();

        messagePane.heightProperty().addListener(event -> {
            scrollPane.setVvalue(1);
        });

        txtInput.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER"))
                displayMessage();
        });
    }

    private Node messageNode(String text, boolean alignToRight) {
        HBox box = new HBox();
        box.paddingProperty().setValue(new Insets(10, 10, 10, 10));

        if (alignToRight)
            box.setAlignment(Pos.BASELINE_RIGHT);
        javafx.scene.control.Label label = new Label(text);
        label.setWrapText(true);
        box.getChildren().add(label);
        return box;
    }

    private void displayMessage() {
        Platform.runLater(() -> {
//            String text = txtInput.getText();
            String message = "";

            txtInput.clear();
            children.add(messageNode(message, msgIndex == 0));
//            children.add(messageNode(text, msgIndex == 0));
            msgIndex = (msgIndex + 1) % 2;
        });
    }

    public void ClientReceive(Socket socket) {
        try {
            in = new DataInputStream(socket.getInputStream());
            connection = true;
        } catch (IOException e) {
            e.printStackTrace();
            connection = false;
        }
    }

    public void receive(String message) {
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
//        print(message + "\n");
    }

}
