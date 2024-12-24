package ru.nsu.t4werok.towerdefenseeditor.view.create.entities.enemy;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import ru.nsu.t4werok.towerdefenseeditor.controller.create.entities.enemy.EnemyCreateController;
import ru.nsu.t4werok.towerdefenseeditor.config.entities.enemy.EnemyConfig;
import ru.nsu.t4werok.towerdefenseeditor.config.entities.enemy.EnemiesConfig;

import java.io.File;
import java.io.IOException;

public class EnemyCreateView {
    private final Scene scene;
    private final EnemyCreateController controller;

    public EnemyCreateView(EnemyCreateController controller) {
        this.controller = controller;

        // Основная панель
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // Панель управления
        VBox controlPanel = new VBox(10);
        controlPanel.setPadding(new Insets(10));
        controlPanel.setPrefWidth(300);

        // Поля для ввода характеристик врага
        TextField lifePointsField = new TextField();
        lifePointsField.setPromptText("Life Points");

        TextField speedField = new TextField();
        speedField.setPromptText("Speed");

        TextField damageToBaseField = new TextField();
        damageToBaseField.setPromptText("Damage to Base");

        TextField lootField = new TextField();
        lootField.setPromptText("Loot");

        // Кнопка для добавления нового врага
        Button addEnemyButton = new Button("Add Enemy");

        ListView<String> enemiesListView = new ListView<>();

        addEnemyButton.setOnAction(e -> {
            try {
                int lifePoints = Integer.parseInt(lifePointsField.getText());
                int speed = Integer.parseInt(speedField.getText());
                int damageToBase = Integer.parseInt(damageToBaseField.getText());
                int loot = Integer.parseInt(lootField.getText());

                EnemyConfig enemyConfig = new EnemyConfig();
                enemyConfig.setLifePoints(lifePoints);
                enemyConfig.setSpeed(speed);
                enemyConfig.setDamageToBase(damageToBase);
                enemyConfig.setLoot(loot);

                // Добавляем в список конфигураций врагов
                controller.getEnemiesConfig().getEnemiesConfigs().add(enemyConfig);
                renderEnemiesList(enemiesListView);  // Обновляем отображение списка
            } catch (NumberFormatException ex) {
                ex.printStackTrace(); // Обработка ошибок ввода
            }
        });

        // Сохранение конфигурации врагов
        Button saveButton = new Button("Save Enemies Config");
        saveButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Enemies Config");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                try {
                    controller.saveEnemiesConfig(file);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Загрузка конфигурации врагов
        Button loadButton = new Button("Load Enemies Config");
        loadButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Load Enemies Config");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {
                try {
                    controller.loadEnemiesConfig(file);
                    renderEnemiesList(enemiesListView);  // Обновляем список врагов
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Панель для отображения списка врагов
        enemiesListView.setPrefHeight(200);
        renderEnemiesList(enemiesListView);  // Изначально отображаем все врагов

        controlPanel.getChildren().addAll(
                new Label("Enemy Settings:"),
                new Label("Life Points:"),
                lifePointsField,
                new Label("Speed:"),
                speedField,
                new Label("Damage to Base:"),
                damageToBaseField,
                new Label("Loot:"),
                lootField,
                addEnemyButton,
                new Label("Enemies:"),
                enemiesListView,
                saveButton,
                loadButton
        );

        root.setLeft(controlPanel);

        this.scene = new Scene(root, 600, 400);
    }

    public Scene getScene() {
        return scene;
    }

    private void renderEnemiesList(ListView<String> enemiesListView) {
        // Инициализация ListView для отображения врагов
        EnemiesConfig enemiesConfig = controller.getEnemiesConfig();
        if (enemiesConfig == null || enemiesConfig.getEnemiesConfigs() == null) {
            return;
        }

        // Очищаем старые элементы списка
        enemiesListView.getItems().clear();

        // Добавляем новые элементы на основе текущих данных о врагах
        int id = 1; // Инициализируем счетчик ID

        for (EnemyConfig enemyConfig : enemiesConfig.getEnemiesConfigs()) {
            String enemyText = "ID: " + id++ + // Добавляем ID и увеличиваем счетчик
                    " | Life Points: " + enemyConfig.getLifePoints() +
                    " | Speed: " + enemyConfig.getSpeed() +
                    " | Damage: " + enemyConfig.getDamageToBase() +
                    " | Loot: " + enemyConfig.getLoot();
            enemiesListView.getItems().add(enemyText);
        }

    }
}
