package ru.nsu.t4werok.towerdefenseeditor.controller.create.entities.map;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.stage.FileChooser;
import ru.nsu.t4werok.towerdefenseeditor.config.entities.map.MapConfig;

import java.io.File;
import java.io.IOException;

public class MapCreateController {
    private MapConfig mapConfig;

    public MapCreateController() {
        this.mapConfig = new MapConfig();
    }

    public MapConfig getMapConfig() {
        return mapConfig;
    }

    public void cleanConfig() {
        this.mapConfig = new MapConfig();
    }

    public void selectImage(String imageType) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select " + imageType + " Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                switch (imageType) {
                    case "Background":
                        mapConfig.setBackgroundImagePath(file.getAbsolutePath());
                        break;
                    case "Tower":
                        mapConfig.setTowerImagePath(file.getAbsolutePath());
                        break;
                    case "Base":
                        mapConfig.setBaseImagePath(file.getAbsolutePath());
                        break;
                    case "SpawnPoint":
                        mapConfig.setSpawnPointImagePath(file.getAbsolutePath());
                        break;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void saveMapConfig() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Map Config");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, mapConfig);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void loadMapConfig() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Map Config");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                MapConfig loadedConfig = objectMapper.readValue(file, MapConfig.class);
                mapConfig.copyFrom(loadedConfig);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
