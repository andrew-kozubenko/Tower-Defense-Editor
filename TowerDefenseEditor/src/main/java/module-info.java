module ru.nsu.t4werok.towerdefenseeditor.app {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.fasterxml.jackson.databind;

    exports ru.nsu.t4werok.towerdefenseeditor.app;
    exports ru.nsu.t4werok.towerdefenseeditor.config.entities.map;

    opens ru.nsu.t4werok.towerdefenseeditor.app to javafx.fxml;
    opens ru.nsu.t4werok.towerdefenseeditor.model.entities.map to com.fasterxml.jackson.databind;
}