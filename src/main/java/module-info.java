module pharmacy {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires jasperreports;
    requires java.sql;
    requires mysql.connector.j;

    opens com.ttdat.application.controllers to javafx.fxml;
//    opens com.ttdat.application.model to javafx.base;
    exports com.ttdat.application;
}