module ni.edu.uam.casosestudio {
    requires javafx.controls;
    requires javafx.fxml;


    opens ni.edu.uam.casosestudio to javafx.fxml;
    exports ni.edu.uam.casosestudio;
    exports ni.edu.uam.casosestudio.controller;
    opens ni.edu.uam.casosestudio.controller to javafx.fxml;
    exports ni.edu.uam.casosestudio.model;
    opens ni.edu.uam.casosestudio.model to javafx.fxml, javafx.base;
}