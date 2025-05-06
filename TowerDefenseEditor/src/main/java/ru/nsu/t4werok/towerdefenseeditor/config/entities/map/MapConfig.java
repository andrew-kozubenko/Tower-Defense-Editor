package ru.nsu.t4werok.towerdefenseeditor.config.entities.map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.scene.image.Image;
import ru.nsu.t4werok.towerdefenseeditor.model.entities.map.Base;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MapConfig {
    private String mapName;
    private Integer width; // Ширина карты
    private Integer height; // Высота карты
    private List<List<Integer[]>> enemyPaths; // Пути врагов: список списков координат
    private List<Integer[]> towerPositions; // Доступные позиции для башен
    private Integer[] spawnPoint; // Точка появления врагов
    private Base base; // База
    private String backgroundImagePath;
    private String baseImagePath;
    private String towerImagePath;
    private String spawnPointImagePath;

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public List<List<Integer[]>> getEnemyPaths() {
        return enemyPaths;
    }

    public void setEnemyPaths(List<List<Integer[]>> enemyPaths) {
        this.enemyPaths = enemyPaths;
    }

    public List<Integer[]> getTowerPositions() {
        return towerPositions;
    }

    public void setTowerPositions(List<Integer[]> towerPositions) {
        this.towerPositions = towerPositions;
    }

    public Integer[] getSpawnPoint() {
        return spawnPoint;
    }

    public void setSpawnPoint(Integer[] spawnPoint) {
        this.spawnPoint = spawnPoint;
    }

    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
    }

    public String getBackgroundImagePath() {
        return backgroundImagePath;
    }

    public void setBackgroundImagePath (String backgroundImage) {
        this.backgroundImagePath = backgroundImage;
    }

    public String getBaseImagePath() {
        return baseImagePath;
    }

    public void setBaseImagePath(String baseImage) {
        this.baseImagePath = baseImage;
    }

    public String getTowerImagePath() {
        return towerImagePath;
    }

    public void setTowerImagePath(String towerImage) {
        this.towerImagePath = towerImage;
    }

    public String getSpawnPointImagePath() {
        return spawnPointImagePath;
    }

    public void setSpawnPointImagePath(String spawnPointImagePath) {
        this.spawnPointImagePath = spawnPointImagePath;
    }

    @JsonIgnore
    public Image getBackgroundImage() {
        return getImage(backgroundImagePath);
    }

    @JsonIgnore
    public Image getBaseImage() {
        return getImage(baseImagePath);
    }

    @JsonIgnore
    public Image getTowerImage() {
        return getImage(towerImagePath);
    }

    @JsonIgnore
    public Image getSpawnPointImage() {
        return getImage(spawnPointImagePath);
    }

    public void setBasePosition(int x, int y) {
        this.base = new Base(x, y, 100);
    }

    public void addEnemyPath(List<Integer[]> path) {
        if (enemyPaths == null) {
            enemyPaths = new ArrayList<>(); // Инициализация, если null
        }
        enemyPaths.add(path);
    }

    public void addTowerPosition(Integer[] position) {
        if (towerPositions == null) {
            towerPositions = new ArrayList<>(); // Инициализация, если null
        }
        towerPositions.add(position);
    }

    private Image getImage(String path) {
        try {
            // Преобразуем путь в URL
            File file = new File(path);
            if (file.exists()) {
                return new Image(file.toURI().toString());
            } else {
                System.err.println("Background image file not found: " + path);
                return null; // Если файл отсутствует, устанавливаем null
            }
        } catch (Exception e) {
//            System.err.println("Error loading background image: " + e.getMessage());
            return null;
        }
    }

    public void copyFrom(MapConfig other) {
        this.width = other.width;
        this.height = other.height;
        this.base = other.base;
        this.enemyPaths = new ArrayList<>(other.enemyPaths);
        this.towerPositions = new ArrayList<>(other.towerPositions);
    }
}
