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

        // Очищаем canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Рассчитываем размер клетки как минимальное из (ширина_канваса/ширина_карты, высота_канваса/высота_карты)
        int cellSize = calculateCellSize(mapConfig);

        // Рассчитываем отступы для центрирования сетки
        double offsetX = (canvas.getWidth() - cellSize * mapConfig.getWidth()) / 2;
        double offsetY = (canvas.getHeight() - cellSize * mapConfig.getHeight()) / 2;

        // Рисуем фон (если есть)
        Image backgroundImage = mapConfig.getBackgroundImage();
        if (backgroundImage != null) {
            gc.drawImage(backgroundImage, offsetX, offsetY,
                    cellSize * mapConfig.getWidth(),
                    cellSize * mapConfig.getHeight());
        }

        // Отрисовка сетки
        renderGrid(mapConfig, cellSize, offsetX, offsetY);

        // Отрисовка остальных элементов с учетом отступов
        renderTowerPositions(mapConfig, cellSize, offsetX, offsetY);
        renderEnemyPaths(mapConfig, cellSize, offsetX, offsetY);
        renderBase(mapConfig, cellSize, offsetX, offsetY);
        renderSpawnPoint(mapConfig, cellSize, offsetX, offsetY);
    }

    public int calculateCellSize(MapConfig mapConfig) {
        int maxCellWidth = (int) (canvas.getWidth() / mapConfig.getWidth());
        int maxCellHeight = (int) (canvas.getHeight() / mapConfig.getHeight());
        return Math.min(maxCellWidth, maxCellHeight);
    }

    private void renderGrid(MapConfig mapConfig, int cellSize, double offsetX, double offsetY) {
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(0.5);

        // Вертикальные линии
        for (int x = 0; x <= mapConfig.getWidth(); x++) {
            double lineX = offsetX + x * cellSize;
            gc.strokeLine(lineX, offsetY, lineX, offsetY + mapConfig.getHeight() * cellSize);
        }

        // Горизонтальные линии
        for (int y = 0; y <= mapConfig.getHeight(); y++) {
            double lineY = offsetY + y * cellSize;
            gc.strokeLine(offsetX, lineY, offsetX + mapConfig.getWidth() * cellSize, lineY);
        }
    }

    private void renderTowerPositions(MapConfig mapConfig, int cellSize, double offsetX, double offsetY) {
        if (mapConfig.getTowerPositions() != null && mapConfig.getTowerImage() != null) {
            for (Integer[] position : mapConfig.getTowerPositions()) {
                gc.drawImage(mapConfig.getTowerImage(),
                        offsetX + position[0] * cellSize,
                        offsetY + position[1] * cellSize,
                        cellSize, cellSize);
            }
        }
    }

    private void renderEnemyPaths(MapConfig mapConfig, int cellSize, double offsetX, double offsetY) {
        gc.setStroke(Color.RED);
        gc.setLineWidth(3);

        // Рендерим сохраненные пути
        if (mapConfig.getEnemyPaths() != null) {
            for (List<Integer[]> path : mapConfig.getEnemyPaths()) {
                renderPath(path, cellSize, offsetX, offsetY);
            }
        }
    }

    public void renderCurrentPath(List<Integer[]> currentPath, int cellSize, double offsetX, double offsetY) {
        gc.setStroke(Color.RED);
        gc.setLineWidth(3);
        renderPath(currentPath, cellSize, offsetX, offsetY);
    }

    private void renderPath(List<Integer[]> path, int cellSize, double offsetX, double offsetY) {
        if (path.size() < 2) return;

        for (int i = 0; i < path.size() - 1; i++) {
            Integer[] start = path.get(i);
            Integer[] end = path.get(i + 1);
            gc.strokeLine(
                    offsetX + start[0] * cellSize + cellSize / 2,
                    offsetY + start[1] * cellSize + cellSize / 2,
                    offsetX + end[0] * cellSize + cellSize / 2,
                    offsetY + end[1] * cellSize + cellSize / 2
            );
        }
    }

    private void renderBase(MapConfig mapConfig, int cellSize, double offsetX, double offsetY) {
        if (mapConfig.getBase() != null && mapConfig.getBaseImage() != null) {
            int baseX = mapConfig.getBase().getX();
            int baseY = mapConfig.getBase().getY();
            gc.drawImage(mapConfig.getBaseImage(),
                    offsetX + baseX * cellSize,
                    offsetY + baseY * cellSize,
                    cellSize, cellSize);
        }
    }

    private void renderSpawnPoint(MapConfig mapConfig, int cellSize, double offsetX, double offsetY) {
        if (mapConfig.getSpawnPoint() != null && mapConfig.getSpawnPointImage() != null) {
            Integer[] spawn = mapConfig.getSpawnPoint();
            gc.drawImage(mapConfig.getSpawnPointImage(),
                    offsetX + spawn[0] * cellSize,
                    offsetY + spawn[1] * cellSize,
                    cellSize, cellSize);
        }
    }
}
