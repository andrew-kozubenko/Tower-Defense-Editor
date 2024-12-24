package ru.nsu.t4werok.towerdefenseeditor.controller.create;

import ru.nsu.t4werok.towerdefenseeditor.controller.SceneController;

public class CreateController {
    private final SceneController sceneController;

    public CreateController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    public void onTowerButtonPressed() {

    }

    public void onEnemyButtonPressed() {
        sceneController.switchTo("CreateEnemy");
    }

    public void onWaveButtonPressed() {
        sceneController.switchTo("CreateWave");
    }

    public void onMapButtonPressed() {
        sceneController.switchTo("CreateMap");
    }

    public void onBackButtonPressed() {
        sceneController.switchTo("MainMenu");
    }
}
