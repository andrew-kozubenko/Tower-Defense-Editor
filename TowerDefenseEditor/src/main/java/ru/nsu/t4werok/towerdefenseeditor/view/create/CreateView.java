package ru.nsu.t4werok.towerdefenseeditor.view.create;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ru.nsu.t4werok.towerdefenseeditor.controller.create.CreateController;

public class CreateView {
    private Scene scene;
    private final VBox layout;

    public CreateView(CreateController controller) {
        layout = new VBox(15); // Контейнер для кнопок
        layout.setAlignment(Pos.CENTER); // Устанавливаем начальное выравнивание

        // Создание кнопок
        Button towerButton = new Button("Tower");
        towerButton.setOnAction(e -> controller.onTowerButtonPressed());

        Button enemyButton = new Button("Enemy");
        enemyButton.setOnAction(e -> controller.onEnemyButtonPressed());

        Button waveButton = new Button("Wave");
        waveButton.setOnAction(e -> controller.onWaveButtonPressed());

        Button mapButton = new Button("Map");
        mapButton.setOnAction(e -> controller.onMapButtonPressed());

        Button exitButton = new Button("Back");
        exitButton.setOnAction(e -> controller.onBackButtonPressed());

        // Добавляем кнопки в layout
        layout.getChildren().addAll(towerButton, enemyButton, waveButton, mapButton, exitButton);

        // Создаём сцену
        this.scene = new Scene(layout, 400, 300); // Указываем стартовые размеры

        layout.prefWidthProperty().bind(this.scene.widthProperty());
        layout.prefHeightProperty().bind(this.scene.heightProperty());

        layout.widthProperty().addListener((observable, oldValue, newValue) -> {
            layout.setAlignment(Pos.CENTER); // Центрируем элементы при изменении ширины
        });
        layout.heightProperty().addListener((observable, oldValue, newValue) -> {
            layout.setAlignment(Pos.CENTER); // Центрируем элементы при изменении высоты
        });
    }

    public Scene getScene() {
        return scene;
    }
}
