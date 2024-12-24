package ru.nsu.t4werok.towerdefenseeditor.config.entities.enemy;

import java.util.ArrayList;
import java.util.List;

public class WavesConfig {
    private List<WaveConfig> waves = new ArrayList<>(); // Список вместо массива

    public List<WaveConfig> getWaves() {
        return waves;
    }

    public void setWaves(List<WaveConfig> waves) {
        this.waves = waves;
    }
}
