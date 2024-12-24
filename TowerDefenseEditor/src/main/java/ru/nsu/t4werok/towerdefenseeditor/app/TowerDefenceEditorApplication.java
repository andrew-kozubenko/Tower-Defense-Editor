package ru.nsu.t4werok.towerdefenseeditor.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.nsu.t4werok.towerdefenseeditor.controller.MainMenuController;
import ru.nsu.t4werok.towerdefenseeditor.controller.SceneController;
import ru.nsu.t4werok.towerdefenseeditor.controller.create.CreateController;
import ru.nsu.t4werok.towerdefenseeditor.controller.create.entities.enemy.EnemyCreateController;
import ru.nsu.t4werok.towerdefenseeditor.controller.create.entities.enemy.WaveCreateController;
import ru.nsu.t4werok.towerdefenseeditor.controller.create.entities.map.MapCreateController;
import ru.nsu.t4werok.towerdefenseeditor.view.MainMenuView;
import ru.nsu.t4werok.towerdefenseeditor.view.create.CreateView;
import ru.nsu.t4werok.towerdefenseeditor.view.create.entities.enemy.EnemyCreateView;
import ru.nsu.t4werok.towerdefenseeditor.view.create.entities.enemy.WaveCreateView;
import ru.nsu.t4werok.towerdefenseeditor.view.create.entities.map.MapCreateView;

import java.io.IOException;

public class TowerDefenceEditorApplication extends Application {
    @Override
    public void start(Stage stage) {
//        SettingsManager settingsManager = SettingsManager.getInstance();
//        settingsManager.setMainStage(stage);

        SceneController sceneController = new SceneController(stage);

        // Контроллеры
        MainMenuController mainMenuController = new MainMenuController(sceneController);
        CreateController createController = new CreateController(sceneController);
        MapCreateController mapCreateController = new MapCreateController();
        WaveCreateController waveCreateController = new WaveCreateController();
        EnemyCreateController enemyCreateController = new EnemyCreateController();
//        SettingsController settingsController = new SettingsController(sceneController);

        // Представления
        MainMenuView mainMenuView = new MainMenuView(mainMenuController);
        CreateView createView = new CreateView(createController);
        MapCreateView mapCreateView = new MapCreateView(mapCreateController);
        WaveCreateView waveCreateView = new WaveCreateView(waveCreateController);
        EnemyCreateView enemyCreateView = new EnemyCreateView(enemyCreateController);
//        SettingsView settingsView = new SettingsView(settingsController);

        // Регистрация сцен
        sceneController.addScene("MainMenu", mainMenuView.getScene());
        sceneController.addScene("Create", createView.getScene());
        sceneController.addScene("CreateMap", mapCreateView.getScene());
        sceneController.addScene("CreateWave", waveCreateView.getScene());
        sceneController.addScene("CreateEnemy", enemyCreateView.getScene());

//        sceneController.addScene("Edit", EditView.getScene());
//        sceneController.addScene("Settings", settingsView.getScene());
//        sceneController.addScene("ReplaySelection", replaySelectionView.getScene());
//        sceneController.addScene("MapSelection", mapSelectionView.getScene());

        // Запуск приложения с главного меню
        sceneController.switchTo("MainMenu");

        stage.setTitle("T4werOK Editor");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}