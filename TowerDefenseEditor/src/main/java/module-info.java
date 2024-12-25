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
    exports ru.nsu.t4werok.towerdefenseeditor.config.entities.enemy;
    exports ru.nsu.t4werok.towerdefenseeditor.config.entities.tower;

    opens ru.nsu.t4werok.towerdefenseeditor.app to javafx.fxml;
    opens ru.nsu.t4werok.towerdefenseeditor.model.entities.map to com.fasterxml.jackson.databind;
    opens ru.nsu.t4werok.towerdefenseeditor.config.entities.enemy to com.fasterxml.jackson.databind;
    opens ru.nsu.t4werok.towerdefenseeditor.config.entities.tower to com.fasterxml.jackson.databind;
}