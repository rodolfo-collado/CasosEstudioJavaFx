package ni.edu.uam.casosestudio;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistroApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RegistroApplication.class.getResource("registro-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 990, 600);
        stage.setTitle("Registro de matrícula");
        stage.setScene(scene);
        stage.show();
    }
}
