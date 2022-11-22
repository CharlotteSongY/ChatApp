
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import javafx.scene.control.Button;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader Loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = Loader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Login");
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(400);
        //do the creating UI
        primaryStage.show();

        primaryStage.setOnCloseRequest(event ->{
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

}
