import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class LoginControl{
    @FXML
    private TextField UserNameTextField;
    @FXML
    private PasswordField PasswordTextField;
    @FXML
    private Button LoginBtn;
    @FXML
    private Button CancelBtn;
    @FXML
    private Label LoginMessageLabel;

    private DataOutputStream out;
    private DataInputStream in;
    private boolean connection;
    private Scanner scanner;

    public void LoginBtnOnAction(ActionEvent event){
        if(UserNameTextField.getText().isEmpty() == false){
            validateLogin();
        }else{
            LoginMessageLabel.setText("Please enter your unsername");
        }
    }

    public void CancelBtnOnAction(ActionEvent event){
        Stage stage = (Stage) CancelBtn.getScene().getWindow();
        stage.close();
    }

    public void validateLogin(){
        if(UserNameTextField.getText().isEmpty() == false){
            Stage stage = (Stage) LoginBtn.getScene().getWindow();

            Socket socket = null;
            String name = null;
            try {
                socket = new Socket("127.0.0.1", 12345);
                name = UserNameTextField.getText();
            } catch (IOException e) {
                System.err.println("Connect failure!!!");
                e.printStackTrace();
            }
//
//            ExecutorService pool = Executors.newFixedThreadPool(2);
//            pool.submit(new ClientSend(socket, name));
//            pool.submit(new ClientReceive(socket));

            stage.close();
            turnToChat(socket);
        }else {
            LoginMessageLabel.setText("Invalid, try again");
        }
        //turnToChat();
    }

    public void turnToChat(Socket socket){
        try {
            FXMLLoader Loader = new FXMLLoader(getClass().getResource("Chat.fxml"));
            Parent root = Loader.load();
            Stage chatStage = new Stage();
            chatStage.setScene(new Scene(root));
            chatStage.setTitle("Login");
            chatStage.setMinWidth(600);
            chatStage.setMinHeight(400);
            //do the creating UI
            chatStage.show();
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }
}
