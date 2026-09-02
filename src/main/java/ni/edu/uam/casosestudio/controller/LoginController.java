package ni.edu.uam.casosestudio.controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField txtUsuario;

    @FXML
    private Button btnIngresar;

    @FXML
    private Button btnCancelar;

    @FXML
    private PasswordField txtContrasena;

    @FXML
    private void onIngresarClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ni/edu/uam/casosestudio/registro-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 680, 640);
        Stage stage = (Stage) txtUsuario.getScene().getWindow();
        stage.setTitle("Registro de estudiantes");
        stage.setScene(scene);
    }

    @FXML
    private void onCancelarClick() {
        txtUsuario.clear();
        txtContrasena.clear();
        txtUsuario.requestFocus();
    }
}
