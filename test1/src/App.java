
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader Loader = new FXMLLoader(getClass().getResource("login.fxml"));
//        Loader.setController(this);
        Parent root = Loader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Login");
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(400);
        //do the creating UI
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
