package ru.nsu.t4werok.towerdefenseeditor.view.create.entities.map;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ru.nsu.t4werok.towerdefenseeditor.controller.create.entities.map.MapCreateController;
import ru.nsu.t4werok.towerdefenseeditor.config.entities.map.MapConfig;
import ru.nsu.t4werok.towerdefenseeditor.model.entities.map.GameMap;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapCreateView {
    private final Scene scene;
    private final MapCreateController controller;
    private final Canvas canvas;
    private final GraphicsContext gc;
    private GameMap gameMap = null;
    private String currentAction = null;
    private List<Integer[]> currentPath = new ArrayList<>();

    public MapCreateView(MapCreateController controller) {
        this.controller = controller;
        this.canvas = new Canvas(800, 600);
        this.gc = canvas.getGraphicsContext2D();

        canvas.setOnMouseClicked(event -> {
            if (currentAction == null) return;

            MapConfig mapConfig = controller.getMapConfig();
            int cellWidth = (int) (canvas.getWidth() / mapConfig.getWidth());
            int cellHeight = (int) (canvas.getHeight() / mapConfig.getHeight());

            // Вычисляем координаты ячейки
            int column = (int) (event.getX() / cellWidth);
            int row = (int) (event.getY() / cellHeight);

            // Обработка действий в зависимости от текущего режима
            switch (currentAction) {
                case "setBase":
                    if (controller.getMapConfig().getBase() != null) {
                        break;
                    }
                    mapConfig.setBasePosition(column, row); // Устанавливаем базу
                    break;
                case "addTower":
                    mapConfig.addTowerPosition(new Integer[]{column, row}); // Добавляем позицию башни
                    break;
                case "addPath":
                    currentPath.add(new Integer[]{column, row});
                    renderMap(); // Обновляем отображение пути
                    break;
                case "setSpawn":
                    mapConfig.setSpawnPoint(new Integer[]{column, row});
                    renderMap();
                    break;
            }

            renderMap(); // Обновляем отображение карты
        });

        // Основная панель
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // Панель управления
        VBox controlPanel = new VBox(10);
        controlPanel.setPadding(new Insets(10));
        controlPanel.setPrefWidth(300);

        // Поле для ввода имени карты
        TextField mapNameField = new TextField();
        mapNameField.setPromptText("Enter map name");
        Button applyMapNameButton = new Button("Apply Map Name");

        applyMapNameButton.setOnAction(e -> {
            String mapName = mapNameField.getText();
            controller.getMapConfig().setMapName(mapName);
            renderMap();
        });

        // Поля для ввода размеров карты
        TextField widthField = new TextField("10");
        TextField heightField = new TextField("8");
        Button applySizeButton = new Button("Apply Size");

        applySizeButton.setOnAction(e -> {
            int width = Integer.parseInt(widthField.getText());
            int height = Integer.parseInt(heightField.getText());

            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

            controller.cleanConfig();

            controller.getMapConfig().setWidth(width);
            controller.getMapConfig().setHeight(height);

            renderMap();
        });

        // Добавление базы
        Button setBaseButton = new Button("Set Base");
        setBaseButton.setOnAction(e -> {
            currentAction = "setBase";
            currentPath.clear();

        });

        // Добавление пути врагов
        Button addPathButton = new Button("Add Enemy Path");
        addPathButton.setOnAction(e -> {
            currentAction = "addPath";
        });

        Button finishPathButton = new Button("Finish Path");
        finishPathButton.setOnAction(e -> {
            MapConfig mapConfig = controller.getMapConfig();
            if (!currentPath.isEmpty()) {
                mapConfig.addEnemyPath(new ArrayList<>(currentPath));
                currentPath.clear(); // Очистить текущий путь
            }
            renderMap(); // Перерисовать карту с новым путем
        });


        // Добавление позиций для башен
        Button addTowerButton = new Button("Add Tower Positions");
        addTowerButton.setOnAction(e -> {
            currentAction = "addTower";
        });

        // Установка точки спавна
        Button setSpawnButton = new Button("Set Spawn Point");
        setSpawnButton.setOnAction(e -> {
            currentAction = "setSpawn";
        });

        // Сохранение карты
        Button saveButton = new Button("Save Map");
        saveButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Map Config");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                try {
                    controller.saveMapConfig(file);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Загрузка карты
        Button loadButton = new Button("Load Map");
        loadButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Load Map Config");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {
                try {
                    controller.loadMapConfig(file);
                    renderMap();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Добавление кнопки для загрузки изображения
        Button addBackgroundButton = new Button("Add Background Image");
        addBackgroundButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Background Image");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
            );
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {
                try {
                    controller.getMapConfig().setBackgroundImage(file.getAbsolutePath());
                    renderMap(); // Перерисовка карты
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        controlPanel.getChildren().addAll(
                new Label("Map Name:"),
                mapNameField,
                applyMapNameButton,
                new Label("Map Dimensions:"),
                new HBox(5, new Label("Width:"), widthField),
                new HBox(5, new Label("Height:"), heightField),
                applySizeButton,
                setBaseButton,
                addPathButton,
                finishPathButton,
                addTowerButton,
                setSpawnButton,
                addBackgroundButton,
                saveButton,
                loadButton
        );

        root.setLeft(controlPanel);
        root.setCenter(canvas);

        this.scene = new Scene(root, 1100, 700);
//        renderMap();
    }

    public Scene getScene() {
        return scene;
    }

    public void renderMap() {
        MapConfig gameMapConfig = controller.getMapConfig();
        if (gameMapConfig == null) {
            return;
        }

        Image backgroundImage = gameMapConfig.getImage();
        if (backgroundImage != null) {
            gc.drawImage(backgroundImage, 0, 0, canvas.getWidth(), canvas.getHeight());
        }

        // Отрисовка сетки
        int cellWidth = (int) (canvas.getWidth() / gameMapConfig.getWidth());
        int cellHeight = (int) (canvas.getHeight() / gameMapConfig.getHeight());
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(0.5); // Тонкие линии для сетки
        for (int x = 0; x <= gameMapConfig.getWidth(); x++) {
            gc.strokeLine(x * cellWidth, 0, x * cellWidth, canvas.getHeight());
        }
        for (int y = 0; y <= gameMapConfig.getHeight(); y++) {
            gc.strokeLine(0, y * cellHeight, canvas.getWidth(), y * cellHeight);
        }

        // Отрисовка доступных позиций для башен
        if (gameMapConfig.getTowerPositions() != null) {
            gc.setFill(Color.GREEN);
            for (Integer[] position : gameMapConfig.getTowerPositions()) {
                gc.fillRect(position[0] * cellWidth, position[1] * cellHeight, cellWidth, cellHeight);
            }
        }

        if (gameMapConfig.getEnemyPaths() != null) {
            // Отрисовка пути врагов
            gc.setStroke(Color.RED);
            gc.setLineWidth(3);
            for (List<Integer[]> path : gameMapConfig.getEnemyPaths()) {
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

        if (gameMapConfig.getBase() != null) {
            // Отрисовка базы
            gc.setFill(Color.BLUE);
            int baseX = gameMapConfig.getBase().getX();
            int baseY = gameMapConfig.getBase().getY();
            gc.fillRect(baseX * cellWidth, baseY * cellHeight, cellWidth, cellHeight); // Рисуем базу
        }

        if (gameMapConfig.getSpawnPoint() != null) {
            // Отрисовка точки спавна
            gc.setFill(Color.YELLOW);
            Integer[] spawn = gameMapConfig.getSpawnPoint();
            gc.fillRect(spawn[0] * cellWidth, spawn[1] * cellHeight, cellWidth, cellHeight); // Рисуем точку спавна
        }
    }
}
