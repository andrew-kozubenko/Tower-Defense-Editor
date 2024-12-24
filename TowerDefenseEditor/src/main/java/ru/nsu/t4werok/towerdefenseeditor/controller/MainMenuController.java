package ru.nsu.t4werok.towerdefenseeditor.controller;

public class MainMenuController {
    private final SceneController sceneController;

    public MainMenuController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    public void onCreateButtonPressed() {
        sceneController.switchTo("Create");
    }

    public void onEditButtonPressed() {
        sceneController.switchTo("Edit");
    }

    public void onExitButtonPressed() {
        System.exit(0);
    }
}
