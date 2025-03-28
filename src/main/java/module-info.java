module sio.demoprojetjava {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;
    requires com.google.gson;
    requires spring.security.crypto;
    requires java.desktop;
    opens sio.demoprojetjava to javafx.fxml;
    exports sio.demoprojetjava;
    exports sio.demoprojetjava.tools;
    opens sio.demoprojetjava.tools to javafx.fxml;
    opens sio.demoprojetjava.model to javafx.fxml, javafx.base;
}