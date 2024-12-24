package ru.nsu.t4werok.towerdefenseeditor.config.entities.map;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class MapSelectionConfig {
    private MapConfig mapConfig;

    public MapSelectionConfig(MapConfig mapConfig) {
        this.mapConfig = mapConfig;
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
