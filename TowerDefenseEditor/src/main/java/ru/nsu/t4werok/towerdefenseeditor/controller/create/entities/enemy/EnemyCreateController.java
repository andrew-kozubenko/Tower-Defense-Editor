package ru.nsu.t4werok.towerdefenseeditor.controller.create.entities.enemy;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.nsu.t4werok.towerdefenseeditor.config.entities.enemy.EnemiesConfig;

import java.io.File;
import java.io.IOException;

public class EnemyCreateController {
    private EnemiesConfig enemiesConfig;

    public EnemyCreateController() {
        this.enemiesConfig = new EnemiesConfig();
    }

    /**
     * Возвращает текущую конфигурацию врагов.
     *
     * @return объект EnemiesConfig
     */
    public EnemiesConfig getEnemiesConfig() {
        return enemiesConfig;
    }

    /**
     * Очищает текущую конфигурацию врагов.
     */
    public void cleanConfig() {
        this.enemiesConfig = new EnemiesConfig();
    }

    /**
     * Сохраняет текущую конфигурацию врагов в файл.
     *
     * @param file файл, в который нужно сохранить конфигурацию
     * @throws IOException если произошла ошибка записи в файл
     */
    public void saveEnemiesConfig(File file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, enemiesConfig);
    }

    /**
     * Загружает конфигурацию врагов из файла.
     *
     * @param file файл, из которого нужно загрузить конфигурацию
     * @throws IOException если произошла ошибка чтения файла
     */
    public void loadEnemiesConfig(File file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        EnemiesConfig loadedConfig = objectMapper.readValue(file, EnemiesConfig.class);
        enemiesConfig.setEnemiesConfigs(loadedConfig.getEnemiesConfigs());
    }
}
