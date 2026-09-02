package ni.edu.uam.casosestudio.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class RegistroController {
    @FXML
    private TextField txtNombreCompleto;

    @FXML
    private ImageView imgRegistro;

    @FXML
    private DatePicker dpFechaNacimiento;

    @FXML
    private ComboBox<String> cbDepartamento;

    @FXML
    private ListView<String> lvCursos;

    @FXML
    private RadioButton rbPresencial;

    @FXML
    private RadioButton rbVirtual;

    @FXML
    private CheckBox chkHorarioManana;

    @FXML
    private CheckBox chkHorarioTarde;

    @FXML
    private CheckBox chkHorarioSabado;

    @FXML
    private CheckBox chkAceptaNormas;

    @FXML
    private void initialize() {
        cbDepartamento.setItems(FXCollections.observableArrayList(
                "Boaco",
                "Carazo",
                "Chinandega",
                "Chontales",
                "Estelí",
                "Granada",
                "Jinotega",
                "León",
                "Madriz",
                "Managua",
                "Masaya",
                "Matagalpa",
                "Nueva Segovia",
                "Rivas",
                "Río San Juan"
        ));

        lvCursos.setItems(FXCollections.observableArrayList(
                "Programación I",
                "Base de Datos",
                "Matemática",
                "Contabilidad",
                "Administración"
        ));
    }

    @FXML
    private void onAgregarEstudianteClick() {
        String modalidad = rbPresencial.isSelected() ? rbPresencial.getText() : rbVirtual.getText();
        System.out.println("Estudiante agregado: " + txtNombreCompleto.getText()
                + ", modalidad: " + modalidad
                + ", acepta normas: " + chkAceptaNormas.isSelected());
    }
}
