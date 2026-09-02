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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

    private boolean validarCamposLogin() {
        String usuario = txtUsuario.getText() != null ? txtUsuario.getText().trim() : "";
        String contrasena = txtContrasena.getText() != null ? txtContrasena.getText() : "";

        if (usuario.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campo Requerido", "El campo de usuario no puede quedar vacío.");
            txtUsuario.requestFocus();
            return false;
        }

        if (usuario.length() < 5) {
            mostrarAlerta(Alert.AlertType.WARNING, "Longitud Inválida", "El usuario debe tener al menos 5 caracteres.");
            txtUsuario.requestFocus();
            return false;
        }

        if (contrasena.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campo Requerido", "El campo de contraseña no puede quedar vacío.");
            txtContrasena.requestFocus();
            return false;
        }

        if (contrasena.length() < 8) {
            mostrarAlerta(Alert.AlertType.WARNING, "Longitud Inválida", "La contraseña debe tener al menos 8 caracteres.");
            txtContrasena.requestFocus();
            return false;
        }

        return true;
    }

    @FXML
    private void onIngresarClick() throws IOException {
        if (!validarCamposLogin()) {
            return;
        }

        String usuario = txtUsuario.getText().trim();
        String contrasena = txtContrasena.getText();
        if (!usuarioService.esAdmin(usuario, contrasena)) {
            mostrarErrorLogin();
            txtContrasena.clear();
            txtContrasena.requestFocus();
            return;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ni/edu/uam/casosestudio/registro-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 720, 680);
        Stage stage = (Stage) txtUsuario.getScene().getWindow();
        stage.setTitle("Registro de estudiantes");
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    private void onCancelarClick() {
        txtUsuario.clear();
        txtContrasena.clear();
        txtUsuario.requestFocus();
    }

    @FXML
    private void onKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                onIngresarClick();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (event.getCode() == KeyCode.ESCAPE) {
            onCancelarClick();
        }
    }

    private void mostrarErrorLogin() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Acceso denegado");
        alert.setHeaderText("Usuario no autorizado");
        alert.setContentText("Debe ingresar con credenciales de administrador.");
        alert.showAndWait();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
