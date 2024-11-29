module sio.demoprojetjava {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;
    opens sio.demoprojetjava to javafx.fxml;
    exports sio.demoprojetjava;
}