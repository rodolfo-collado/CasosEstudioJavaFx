module ni.edu.uam.casosestudio {
    requires javafx.controls;
    requires javafx.fxml;


    opens ni.edu.uam.casosestudio to javafx.fxml;
    exports ni.edu.uam.casosestudio;
}