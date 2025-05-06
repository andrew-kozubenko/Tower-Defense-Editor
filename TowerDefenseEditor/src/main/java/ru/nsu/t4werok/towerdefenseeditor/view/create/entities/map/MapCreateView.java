package ru.nsu.t4werok.towerdefenseeditor.view.create.entities.map;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import ru.nsu.t4werok.towerdefenseeditor.controller.create.entities.map.MapCreateController;
import ru.nsu.t4werok.towerdefenseeditor.config.entities.map.MapConfig;
import ru.nsu.t4werok.towerdefenseeditor.model.entities.map.GameMap;

import java.util.ArrayList;
import java.util.List;

public class MapCreateView {
    private final Scene scene;
    private final MapCreateController controller;
    private final Canvas canvas;
    private final GraphicsContext gc;
    private final MapRenderer mapRenderer;
    private GameMap gameMap = null;
    private String currentAction = null;
    private List<Integer[]> currentPath = new ArrayList<>();

    public MapCreateView(MapCreateController controller) {
        this.controller = controller;
        this.canvas = new Canvas(800, 600);
        this.gc = canvas.getGraphicsContext2D();
        this.mapRenderer = new MapRenderer(this.controller, this.canvas, this.gc);

        initializeMouseHandler();

        // Основная панель
        BorderPane root = buildLayout();

        this.scene = new Scene(root, 1100, 700);
    }

    private void initializeMouseHandler() {
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
                    break;
                case "setSpawn":
                    mapConfig.setSpawnPoint(new Integer[]{column, row});
                    break;
            }

            mapRenderer.renderMap(); // Обновляем отображение карты
        });
    }

    private BorderPane buildLayout() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        // Панель управления
        root.setLeft(buildControlPanel());
        root.setCenter(canvas);
        return root;
    }

    private VBox buildControlPanel() {
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
            mapRenderer.renderMap();
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

            mapRenderer.renderMap();
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
            mapRenderer.renderMap(); // Перерисовать карту с новым путем
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
            controller.saveMapConfig();
        });

        // Загрузка карты
        Button loadButton = new Button("Load Map");
        loadButton.setOnAction(e -> {
            controller.loadMapConfig();
            mapRenderer.renderMap();
        });

        // Добавление кнопки для загрузки изображения
        Button addBackgroundImageButton = new Button("Add Background Image");
        addBackgroundImageButton.setOnAction(e -> {
            controller.selectImage("Background");
            mapRenderer.renderMap(); // Перерисовка карты
        });

        Button addTowerImageButton = new Button("Add Tower Position Image");
        addTowerImageButton.setOnAction(e -> {
            controller.selectImage("Tower");
            mapRenderer.renderMap(); // Перерисовка карты
        });

        Button addBaseImageButton = new Button("Add Base Image");
        addBaseImageButton.setOnAction(e -> {
            controller.selectImage("Base");
            mapRenderer.renderMap(); // Перерисовка карты
        });

        Button addSpawnPointImageButton = new Button("Add Spawn Point Image");
        addSpawnPointImageButton.setOnAction(e -> {
            controller.selectImage("SpawnPoint");
            mapRenderer.renderMap(); // Перерисовка карты
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
                addBackgroundImageButton,
                addTowerImageButton,
                addBaseImageButton,
                addSpawnPointImageButton,
                saveButton,
                loadButton
        );

        return controlPanel;
    }

    public Scene getScene() {
        return scene;
    }
}
