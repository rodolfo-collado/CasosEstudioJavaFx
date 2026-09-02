package ni.edu.uam.casosestudio.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import ni.edu.uam.casosestudio.model.Estudiante;
import ni.edu.uam.casosestudio.service.EstudianteService;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

public class RegistroController {
    @FXML
    private ScrollPane rootScrollPane;

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
    private Button btnGuardar;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private Button btnEliminar;

    @FXML
    private TableView<Estudiante> tblEstudiantes;

    @FXML
    private TableColumn<Estudiante, String> colNombre;

    @FXML
    private TableColumn<Estudiante, String> colFechaNacimiento;

    @FXML
    private TableColumn<Estudiante, String> colDepartamento;

    @FXML
    private TableColumn<Estudiante, String> colCurso;

    @FXML
    private TableColumn<Estudiante, String> colModalidad;

    private final EstudianteService estudianteService = new EstudianteService();
    private final ObservableList<Estudiante> listaEstudiantes = FXCollections.observableArrayList();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private Estudiante estudianteSeleccionado;

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
                "Excel",
                "Redes",
                "Diseño gráfico"
        ));

        tblEstudiantes.setItems(listaEstudiantes);

        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombreCompleto()));
        colFechaNacimiento.setCellValueFactory(cellData -> {
            Date date = cellData.getValue().getFechaDeNacimiento();
            return new SimpleStringProperty(date != null ? dateFormat.format(date) : "");
        });
        colDepartamento.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDepartamento() != null ? cellData.getValue().getDepartamento() : "")
        );
        colCurso.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCurso() != null ? cellData.getValue().getCurso() : "")
        );
        colModalidad.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getModalidad() != null ? cellData.getValue().getModalidad() : "")
        );
    }

    private boolean validarFormulario() {
        String nombreCompleto = txtNombreCompleto.getText() != null ? txtNombreCompleto.getText().trim() : "";
        if (nombreCompleto.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campo Requerido", "El nombre completo no puede quedar vacío.");
            txtNombreCompleto.requestFocus();
            return false;
        }

        if (dpFechaNacimiento.getValue() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campo Requerido", "Debe seleccionar una fecha de nacimiento.");
            dpFechaNacimiento.requestFocus();
            return false;
        }

        String departamento = cbDepartamento.getValue();
        if (departamento == null || departamento.trim().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campo Requerido", "Debe seleccionar un departamento.");
            cbDepartamento.requestFocus();
            return false;
        }

        String curso = lvCursos.getSelectionModel().getSelectedItem();
        if (curso == null || curso.trim().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campo Requerido", "Debe elegir un curso de la lista.");
            lvCursos.requestFocus();
            return false;
        }

        if (!rbPresencial.isSelected() && !rbVirtual.isSelected()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campo Requerido", "Debe seleccionar una modalidad.");
            return false;
        }

        if (!chkHorarioManana.isSelected() && !chkHorarioTarde.isSelected() && !chkHorarioSabado.isSelected()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campo Requerido", "Debe elegir al menos un horario (Mañana, Tarde o Sábado).");
            return false;
        }

        if (!chkAceptaNormas.isSelected()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campo Requerido", "Debe aceptar las normas de matrícula.");
            chkAceptaNormas.requestFocus();
            return false;
        }

        return true;
    }

    private Estudiante crearEstudianteDesdeFormulario() {
        String nombreCompleto = txtNombreCompleto.getText() != null ? txtNombreCompleto.getText().trim() : "";
        String nombre = nombreCompleto;
        String apellido = "";
        int pos = nombreCompleto.indexOf(" ");
        if (pos != -1) {
            nombre = nombreCompleto.substring(0, pos);
            apellido = nombreCompleto.substring(pos + 1);
        }

        Date fechaNacimiento = null;
        if (dpFechaNacimiento.getValue() != null) {
            fechaNacimiento = Date.from(dpFechaNacimiento.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        }

        String departamento = cbDepartamento.getValue();
        String curso = lvCursos.getSelectionModel().getSelectedItem();
        String modalidad = rbPresencial.isSelected() ? rbPresencial.getText() : (rbVirtual.isSelected() ? rbVirtual.getText() : "");

        return new Estudiante(nombre, apellido, departamento, fechaNacimiento, curso, modalidad);
    }

    @FXML
    private void onGuardarClick(ActionEvent event) {
        if (!validarFormulario()) {
            return;
        }

        Estudiante estudiante = crearEstudianteDesdeFormulario();
        estudianteService.addEstudiante(estudiante);
        listaEstudiantes.add(estudiante);
        limpiarCampos();
        mostrarAlerta(Alert.AlertType.INFORMATION, "Registro Exitoso", "El estudiante fue registrado correctamente.");
    }

    @FXML
    private void onAgregarEstudianteClick() {
        onGuardarClick(null);
    }

    @FXML
    private void onActualizarClick(ActionEvent event) {
        if (estudianteSeleccionado == null) {
            estudianteSeleccionado = tblEstudiantes.getSelectionModel().getSelectedItem();
        }

        if (estudianteSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Selección Requerida", "Debe hacer doble clic en una fila del TableView para cargar los datos y actualizar.");
            return;
        }

        if (!validarFormulario()) {
            return;
        }

        Estudiante actualizado = crearEstudianteDesdeFormulario();
        estudianteSeleccionado.setNombre(actualizado.getNombre());
        estudianteSeleccionado.setApellido(actualizado.getApellido());
        estudianteSeleccionado.setFechaDeNacimiento(actualizado.getFechaDeNacimiento());
        estudianteSeleccionado.setDepartamento(actualizado.getDepartamento());
        estudianteSeleccionado.setCurso(actualizado.getCurso());
        estudianteSeleccionado.setModalidad(actualizado.getModalidad());

        tblEstudiantes.refresh();
        limpiarCampos();
        mostrarAlerta(Alert.AlertType.INFORMATION, "Actualización Exitosa", "Los datos del estudiante fueron actualizados correctamente.");
    }

    @FXML
    private void onLimpiarClick(ActionEvent event) {
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtNombreCompleto.clear();
        dpFechaNacimiento.setValue(null);
        cbDepartamento.getSelectionModel().clearSelection();
        lvCursos.getSelectionModel().clearSelection();
        rbPresencial.setSelected(true);
        chkHorarioManana.setSelected(false);
        chkHorarioTarde.setSelected(false);
        chkHorarioSabado.setSelected(false);
        chkAceptaNormas.setSelected(false);
        tblEstudiantes.getSelectionModel().clearSelection();
        estudianteSeleccionado = null;
    }

    @FXML
    private void onEliminarClick(ActionEvent event) {
        Estudiante seleccionado = tblEstudiantes.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            seleccionado = estudianteSeleccionado;
        }

        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Selección Requerida", "Debe seleccionar un estudiante de la tabla para eliminarlo.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmar Eliminación");
        confirm.setHeaderText(null);
        confirm.setContentText("¿Está seguro de eliminar al estudiante " + seleccionado.getNombreCompleto() + "?");
        Optional<ButtonType> respuesta = confirm.showAndWait();
        if (respuesta.isPresent() && respuesta.get() == ButtonType.OK) {
            estudianteService.removeEstudiante(seleccionado);
            listaEstudiantes.remove(seleccionado);
            limpiarCampos();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Eliminación Exitosa", "El estudiante fue eliminado correctamente.");
        }
    }

    @FXML
    private void onTableMouseClicked(MouseEvent event) {
        if (event == null) return;
        if (event.getClickCount() == 2) {
            Estudiante seleccionado = tblEstudiantes.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                this.estudianteSeleccionado = seleccionado;
                cargarDatosEnFormulario(seleccionado);
                mostrarDetallesEstudiante(seleccionado);
            }
        }
    }

    private void cargarDatosEnFormulario(Estudiante estudiante) {
        txtNombreCompleto.setText(estudiante.getNombreCompleto());

        if (estudiante.getFechaDeNacimiento() != null) {
            dpFechaNacimiento.setValue(estudiante.getFechaDeNacimiento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        } else {
            dpFechaNacimiento.setValue(null);
        }

        cbDepartamento.setValue(estudiante.getDepartamento());
        lvCursos.getSelectionModel().select(estudiante.getCurso());

        if ("Virtual".equalsIgnoreCase(estudiante.getModalidad())) {
            rbVirtual.setSelected(true);
        } else {
            rbPresencial.setSelected(true);
        }
    }

    private void mostrarDetallesEstudiante(Estudiante estudiante) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información del Estudiante");
        alert.setHeaderText("Datos de: " + estudiante.getNombreCompleto());
        alert.setContentText(
                "Nombre: " + estudiante.getNombreCompleto() + "\n" +
                "Fecha de Nacimiento: " + (estudiante.getFechaDeNacimiento() != null ? dateFormat.format(estudiante.getFechaDeNacimiento()) : "N/A") + "\n" +
                "Departamento: " + (estudiante.getDepartamento() != null ? estudiante.getDepartamento() : "N/A") + "\n" +
                "Curso: " + (estudiante.getCurso() != null ? estudiante.getCurso() : "N/A") + "\n" +
                "Modalidad: " + (estudiante.getModalidad() != null ? estudiante.getModalidad() : "N/A")
        );
        alert.showAndWait();
    }

    @FXML
    private void onKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            onGuardarClick(null);
        } else if (event.getCode() == KeyCode.ESCAPE) {
            onLimpiarClick(null);
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
