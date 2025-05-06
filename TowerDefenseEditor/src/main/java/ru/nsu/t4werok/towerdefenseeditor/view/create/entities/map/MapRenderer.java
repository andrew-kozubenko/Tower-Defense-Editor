package ru.nsu.t4werok.towerdefenseeditor.view.create.entities.map;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import ru.nsu.t4werok.towerdefenseeditor.config.entities.map.MapConfig;
import ru.nsu.t4werok.towerdefenseeditor.controller.create.entities.map.MapCreateController;

import java.util.List;

public class MapRenderer {
    private final MapCreateController controller;
    private final Canvas canvas;
    private final GraphicsContext gc;

    public MapRenderer(MapCreateController controller, Canvas canvas, GraphicsContext gc) {
        this.controller = controller;
        this.canvas = canvas;
        this.gc = gc;
    }

    public void renderMap() {
        MapConfig mapConfig = controller.getMapConfig();
        if (mapConfig == null) {
            return;
        }

        Image backgroundImage = mapConfig.getBackgroundImage();
        if (backgroundImage != null) {
            gc.drawImage(backgroundImage, 0, 0, canvas.getWidth(), canvas.getHeight());
        }

        int cellWidth = (int) (canvas.getWidth() / mapConfig.getWidth());
        int cellHeight = (int) (canvas.getHeight() / mapConfig.getHeight());

        // Отрисовка сетки
        renderGrid(mapConfig, cellWidth, cellHeight);

        // Отрисовка доступных позиций для башен
        renderTowerPositions(mapConfig, cellWidth, cellHeight);

        // Отрисовка путей врагов
        renderEnemyPaths(mapConfig, cellWidth, cellHeight);

        // Отрисовка базы
        renderBase(mapConfig, cellWidth, cellHeight);

        // Отрисовка точки спавна
        renderSpawnPoint(mapConfig, cellWidth, cellHeight);
    }

    private void renderGrid(MapConfig mapConfig, int cellWidth, int cellHeight) {
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(0.5); // Тонкие линии для сетки
        for (int x = 0; x <= mapConfig.getWidth(); x++) {
            gc.strokeLine(x * cellWidth, 0, x * cellWidth, canvas.getHeight());
        }
        for (int y = 0; y <= mapConfig.getHeight(); y++) {
            gc.strokeLine(0, y * cellHeight, canvas.getWidth(), y * cellHeight);
        }
    }

    private void renderTowerPositions(MapConfig mapConfig, int cellWidth, int cellHeight) {
        if (mapConfig.getTowerPositions() != null) {
            Image towerImage = mapConfig.getTowerImage();
            for (Integer[] position : mapConfig.getTowerPositions()) {
                gc.drawImage(towerImage,
                        position[0] * cellWidth, position[1] * cellHeight, cellWidth, cellHeight);

            }
        }
    }

    private void renderEnemyPaths(MapConfig mapConfig, int cellWidth, int cellHeight) {
        if (mapConfig.getEnemyPaths() != null) {
            gc.setStroke(Color.RED);
            gc.setLineWidth(3);
            for (List<Integer[]> path : mapConfig.getEnemyPaths()) {
                for (int i = 0; i < path.size() - 1; i++) {
                    Integer[] start = path.get(i);
                    Integer[] end = path.get(i + 1);
                    gc.strokeLine(
                            start[0] * cellWidth + cellWidth / 2, start[1] * cellHeight + cellHeight / 2,
                            end[0] * cellWidth + cellWidth / 2, end[1] * cellHeight + cellHeight / 2
                    );
                }
            }
        }
    }

    private void renderBase(MapConfig mapConfig, int cellWidth, int cellHeight) {
        if (mapConfig.getBase() != null) {
            int baseX = mapConfig.getBase().getX();
            int baseY = mapConfig.getBase().getY();
            Image baseImage = mapConfig.getBaseImage();
            gc.drawImage(baseImage,
                    baseX * cellWidth, baseY * cellHeight, cellWidth, cellHeight); // Рисуем базу
        }
    }

    private void renderSpawnPoint(MapConfig mapConfig, int cellWidth, int cellHeight) {
        if (mapConfig.getSpawnPoint() != null) {
            Integer[] spawn = mapConfig.getSpawnPoint();
            Image spawnPoinImage = mapConfig.getSpawnPointImage();
            gc.drawImage(spawnPoinImage,
                    spawn[0] * cellWidth, spawn[1] * cellHeight, cellWidth, cellHeight);// Рисуем точку спавна
        }
    }
}
