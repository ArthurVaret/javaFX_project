module com.example.javafxproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    //requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens com.example.javafxproject to javafx.fxml;
    exports com.example.javafxproject;
    opens controllers to javafx.fxml;
    exports controllers;
    opens models to javafx.fxml;
    exports models;

}