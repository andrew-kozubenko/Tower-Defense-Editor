package ru.nsu.t4werok.towerdefenseeditor.controller.create.entities.enemy;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.nsu.t4werok.towerdefenseeditor.config.entities.enemy.WavesConfig;

import java.io.File;
import java.io.IOException;

public class WaveCreateController {
    private WavesConfig wavesConfig;

    public WaveCreateController() {
        this.wavesConfig = new WavesConfig();
    }

    /**
     * Возвращает текущую конфигурацию волн.
     *
     * @return объект WavesConfig
     */
    public WavesConfig getWavesConfig() {
        return wavesConfig;
    }

    /**
     * Очищает текущую конфигурацию волн.
     */
    public void cleanConfig() {
        this.wavesConfig = new WavesConfig();
    }

    /**
     * Сохраняет текущую конфигурацию волн в файл.
     *
     * @param file файл, в который нужно сохранить конфигурацию
     * @throws IOException если произошла ошибка записи в файл
     */
    public void saveWavesConfig(File file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, wavesConfig);
    }

    /**
     * Загружает конфигурацию волн из файла.
     *
     * @param file файл, из которого нужно загрузить конфигурацию
     * @throws IOException если произошла ошибка чтения файла
     */
    public void loadWavesConfig(File file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        WavesConfig loadedConfig = objectMapper.readValue(file, WavesConfig.class);
        wavesConfig.setWaves(loadedConfig.getWaves());
    }
}
