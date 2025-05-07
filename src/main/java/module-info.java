module com.aeroguard {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.compiler;
    requires java.desktop;
    requires jdk.jfr;
    requires com.gluonhq.maps;

    opens com.aeroguard to javafx.fxml;
    exports com.aeroguard;
    exports com.aeroguard.geometry;
    opens com.aeroguard.geometry to javafx.fxml;
    exports com.aeroguard.controllers;
    opens com.aeroguard.controllers to javafx.fxml;
}


