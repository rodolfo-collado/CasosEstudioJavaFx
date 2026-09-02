package ni.edu.uam.casosestudio.controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import ni.edu.uam.casosestudio.service.UsuarioService;

import java.io.IOException;

public class LoginController {
    private final UsuarioService usuarioService = new UsuarioService();

    @FXML
    private TextField txtUsuario;

    @FXML
    private Button btnIngresar;

    @FXML
    private Button btnCancelar;

    @FXML
    private PasswordField txtContrasena;

    @FXML
    private ImageView imgLogin;

    @FXML
    private void initialize() {
        Image image = new Image(getClass().getResourceAsStream("/ni/edu/uam/casosestudio/images/marca-comercial.png"));
        imgLogin.setImage(image);
    }

    private boolean verificarLogin(){
        String usuario = txtUsuario.getText().trim();
        String contrasena = txtContrasena.getText();

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            return false;
        }

        return usuarioService.esAdmin(usuario, contrasena);
    }

    @FXML
    private void onIngresarClick() throws IOException {
        if (!verificarLogin()) {
            mostrarErrorLogin();
            txtContrasena.clear();
            txtContrasena.requestFocus();
            return;
        }

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

    private void mostrarErrorLogin() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Acceso denegado");
        alert.setHeaderText("Usuario no autorizado");
        alert.setContentText("Debe ingresar con credenciales de administrador.");
        alert.showAndWait();
    }
}
