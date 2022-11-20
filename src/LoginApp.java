import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

public class LoginApp extends Application {

    @FXML
    private TextField Username;
    @FXML
    private TextField Password;

    @Override
    public void start(Stage LoginStage) throws IOException {
        FXMLLoader Login = new FXMLLoader(getClass().getResource("login.fxml"));
        Login.setController(this);
        Parent root = Login.load();
        LoginStage.setScene(new Scene(root));
        LoginStage.setTitle("Login");
        LoginStage.setMinWidth(300);
        LoginStage.setMinHeight(500);
        // 这一行 do the creating UI
        LoginStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
