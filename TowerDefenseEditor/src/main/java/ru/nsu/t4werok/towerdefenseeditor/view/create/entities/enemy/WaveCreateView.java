package ru.nsu.t4werok.towerdefenseeditor.view.create.entities.enemy;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import ru.nsu.t4werok.towerdefenseeditor.controller.create.entities.enemy.WaveCreateController;
import ru.nsu.t4werok.towerdefenseeditor.config.entities.enemy.WaveConfig;
import ru.nsu.t4werok.towerdefenseeditor.config.entities.enemy.WavesConfig;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class WaveCreateView {
    private final Scene scene;
    private final WaveCreateController controller;

    public WaveCreateView(WaveCreateController controller) {
        this.controller = controller;

        // Основная панель
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // Панель управления
        VBox controlPanel = new VBox(10);
        controlPanel.setPadding(new Insets(10));
        controlPanel.setPrefWidth(300);

        // Поле для ввода интервала между волнами
        TextField intervalField = new TextField();
        intervalField.setPromptText("Interval (ms)");

        // Поле для ввода врагов
        TextField enemiesField = new TextField();
        enemiesField.setPromptText("Enemies (comma-separated)");

        // Кнопка для добавления новой волны
        Button addWaveButton = new Button("Add Wave");

        ListView<String> wavesListView = new ListView<>();

        addWaveButton.setOnAction(e -> {
            try {
                int interval = Integer.parseInt(intervalField.getText());
                String[] enemiesString = enemiesField.getText().split(",");
                int[] enemies = Arrays.stream(enemiesString)
                        .mapToInt(Integer::parseInt)
                        .toArray();

                WaveConfig waveConfig = new WaveConfig();
                waveConfig.setInterval(interval);
                waveConfig.setEnemies(enemies);

                // Теперь мы добавляем в список, так как getWaves() возвращает List
                controller.getWavesConfig().getWaves().add(waveConfig);
                renderWavesList(wavesListView);  // Обновляем отображение списка
            } catch (NumberFormatException ex) {
                ex.printStackTrace(); // Обработка ошибок ввода
            }
        });

        // Сохранение конфигурации волн
        Button saveButton = new Button("Save Waves Config");
        saveButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Waves Config");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                try {
                    controller.saveWavesConfig(file);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });



        // Загрузка конфигурации волн
        Button loadButton = new Button("Load Waves Config");
        loadButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Load Waves Config");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {
                try {
                    controller.loadWavesConfig(file);
                    renderWavesList(wavesListView);  // Обновляем список волн
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Панель для отображения списка волн
        wavesListView.setPrefHeight(200);
        renderWavesList(wavesListView);  // Изначально отображаем все волны

        controlPanel.getChildren().addAll(
                new Label("Wave Settings:"),
                new Label("Interval (ticks):"),
                intervalField,
                new Label("Enemies (comma-separated):"),
                enemiesField,
                addWaveButton,
                new Label("Waves:"),
                wavesListView,
                saveButton,
                loadButton
        );

        root.setLeft(controlPanel);

        this.scene = new Scene(root, 600, 400);
    }

    public Scene getScene() {
        return scene;
    }

    private void renderWavesList(ListView<String> wavesListView) {
        // Инициализация ListView для отображения волн
        WavesConfig wavesConfig = controller.getWavesConfig();
        if (wavesConfig == null || wavesConfig.getWaves() == null) {
            return;
        }

        // Очищаем старые элементы списка
        wavesListView.getItems().clear();

        // Добавляем новые элементы на основе текущих данных о волнах
        for (WaveConfig waveConfig : wavesConfig.getWaves()) {
            String enemies = Arrays.toString(waveConfig.getEnemies());
            String waveText = "Enemies: " + enemies + " | Interval: " + waveConfig.getInterval() + " ticks";
            wavesListView.getItems().add(waveText);
        }
    }

}
