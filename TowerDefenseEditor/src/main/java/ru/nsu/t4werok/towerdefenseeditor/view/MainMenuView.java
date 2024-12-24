package ru.nsu.t4werok.towerdefenseeditor.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ru.nsu.t4werok.towerdefenseeditor.controller.MainMenuController;

public class MainMenuView {
    private Scene scene;
    private final VBox layout; // Сохраняем ссылку на layout для обновлений

    public MainMenuView(MainMenuController controller) {
        layout = new VBox(15); // Контейнер для кнопок
        layout.setAlignment(Pos.CENTER); // Устанавливаем начальное выравнивание

        // Создание кнопок
        Button createButton = new Button("Create");
        createButton.setOnAction(e -> controller.onCreateButtonPressed());

        Button editButton = new Button("Edit");
        editButton.setOnAction(e -> controller.onEditButtonPressed());

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> controller.onExitButtonPressed());

        // Добавляем кнопки в layout
        layout.getChildren().addAll(createButton, editButton, exitButton);

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
