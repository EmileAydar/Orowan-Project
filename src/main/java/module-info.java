module fr.arcelormittal {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires org.slf4j;

    opens fr.arcelormittal to javafx.fxml;
    exports fr.arcelormittal;
    exports fr.arcelormittal.Controllers;
    opens fr.arcelormittal.Controllers to javafx.fxml;
}