module com.example.ingsoft {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.ingsoft to javafx.fxml;
    exports com.example.ingsoft;
    exports com.example.ingsoft.Controllers;
    opens com.example.ingsoft.Controllers to javafx.fxml;
    exports com.example.ingsoft.Model.Persona;
    opens com.example.ingsoft.Model.Persona to javafx.fxml;
    exports com.example.ingsoft.Model.Lavoro;
    opens com.example.ingsoft.Model.Lavoro to javafx.fxml;
    exports com.example.ingsoft.Model.Lavoratore;
    opens com.example.ingsoft.Model.Lavoratore to javafx.fxml;
}