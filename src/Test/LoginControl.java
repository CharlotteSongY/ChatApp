package Test;

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


    @FXML
    public void Initialize(){

    }

    public void LoginBtnOnAction(ActionEvent event){
        if(UserNameTextField.getText().isEmpty() == false && PasswordTextField.getText().isEmpty() == false){
            validateLogin();
        }else{
            LoginMessageLabel.setText("Please enter your unsername and password");
        }
    }

    public void CancelBtnOnAction(ActionEvent event){
        Stage stage = (Stage) CancelBtn.getScene().getWindow();
        stage.close();
    }

    public void validateLogin(){
        if(UserNameTextField.getText().equals("name") && PasswordTextField.getText().equals("password")){
            Stage stage = (Stage) LoginBtn.getScene().getWindow();
            stage.close();
            turnToChat();
        }else {
            LoginMessageLabel.setText("Invalid, try again");
        }
        //turnToChat();
    }

    public void turnToChat(){
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
