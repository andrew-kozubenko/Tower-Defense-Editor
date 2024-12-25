package ru.nsu.t4werok.towerdefenseeditor.controller.create.entities.tower;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.nsu.t4werok.towerdefenseeditor.config.entities.tower.TowerConfig;

import java.io.File;
import java.io.IOException;

public class TowerCreateController {
    private TowerConfig towerConfig;

    public TowerCreateController() {
        this.towerConfig = new TowerConfig();
    }

    public TowerConfig getTowerConfig() {
        return towerConfig;
    }

    public void cleanConfig() {
        this.towerConfig = new TowerConfig();
    }

    public void saveTowerConfig(File file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, towerConfig);
    }

    public void loadTowerConfig(File file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        TowerConfig loadedConfig = objectMapper.readValue(file, TowerConfig.class);
        towerConfig.copyFrom(loadedConfig);
    }
}
