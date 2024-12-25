package ru.nsu.t4werok.towerdefenseeditor.view.create.entities.tower;

import ru.nsu.t4werok.towerdefenseeditor.controller.create.entities.tower.TowerCreateController;


import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ru.nsu.t4werok.towerdefenseeditor.controller.create.entities.tower.TowerCreateController;
import ru.nsu.t4werok.towerdefenseeditor.config.entities.tower.TowerConfig;

import java.io.File;
import java.io.IOException;

public class TowerCreateView {
    private final Scene scene;
    private final TowerCreateController controller;
    private final Canvas canvas;
    private final GraphicsContext gc;

    public TowerCreateView(TowerCreateController controller) {
        this.controller = controller;
        this.canvas = new Canvas(800, 600);
        this.gc = canvas.getGraphicsContext2D();

        // Основная панель
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // Панель управления
        VBox controlPanel = new VBox(10);
        controlPanel.setPadding(new Insets(10));
        controlPanel.setPrefWidth(300);

        // Поле для ввода имени башни
        TextField towerNameField = new TextField();
        towerNameField.setPromptText("Enter tower name");
        Button applyTowerNameButton = new Button("Apply Tower Name");

        applyTowerNameButton.setOnAction(e -> {
            String towerName = towerNameField.getText();
            controller.getTowerConfig().setName(towerName);
            renderTower();
        });

        // Поля для ввода параметров башни
        TextField damageField = new TextField("10");
        TextField rangeField = new TextField("5");
        TextField fireRateField = new TextField("1.2");
        Button applyAttributesButton = new Button("Apply Attributes");

        applyAttributesButton.setOnAction(e -> {
            int damage = Integer.parseInt(damageField.getText());
            int range = Integer.parseInt(rangeField.getText());
            double fireRate = Double.parseDouble(rangeField.getText());
            TowerConfig towerConfig = controller.getTowerConfig();
            towerConfig.setDamage(damage);
            towerConfig.setAttackRadius(range);
            towerConfig.setFireRate(fireRate);
            renderTower();
        });

        // Кнопка для сохранения конфигурации
        Button saveButton = new Button("Save Tower");
        saveButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Tower Config");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                try {
                    controller.saveTowerConfig(file);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Кнопка для загрузки конфигурации
        Button loadButton = new Button("Load Tower");
        loadButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Load Tower Config");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {
                try {
                    controller.loadTowerConfig(file);
                    renderTower();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        controlPanel.getChildren().addAll(
                new Label("Tower Name:"),
                towerNameField,
                applyTowerNameButton,
                new Label("Attributes:"),
                new HBox(5, new Label("Damage:"), damageField),
                new HBox(5, new Label("Range:"), rangeField),
                applyAttributesButton,
                saveButton,
                loadButton
        );

        root.setLeft(controlPanel);
        root.setCenter(canvas);


        this.scene = new Scene(root, 1100, 700);
    }

    public Scene getScene() {
        return scene;
    }

    public void renderTower() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        TowerConfig towerConfig = controller.getTowerConfig();
        if (towerConfig != null) {
            gc.strokeText(
                    "Tower: " + towerConfig.getName() +
                            ", Damage: " + towerConfig.getDamage() +
                            ", Range: " + towerConfig.getAttackRadius(),
                    10, 20
            );
        }
    }
}
