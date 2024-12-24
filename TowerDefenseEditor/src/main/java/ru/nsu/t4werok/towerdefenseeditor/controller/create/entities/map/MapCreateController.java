package ru.nsu.t4werok.towerdefenseeditor.controller.create.entities.map;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    public void saveMapConfig(File file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, mapConfig);
    }

    public void loadMapConfig(File file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        MapConfig loadedConfig = objectMapper.readValue(file, MapConfig.class);
        mapConfig.copyFrom(loadedConfig);
    }
}
