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

    }

    public void onWaveButtonPressed() {

    }

    public void onMapButtonPressed() {
        sceneController.switchTo("CreateMap");
    }

    public void onBackButtonPressed() {
        sceneController.switchTo("MainMenu");
    }
}
