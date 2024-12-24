package ru.nsu.t4werok.towerdefenseeditor.config.entities.enemy;

import java.util.ArrayList;
import java.util.List;

public class EnemiesConfig {
    private List<EnemyConfig> enemiesConfigs = new ArrayList<>();

    public List<EnemyConfig> getEnemiesConfigs() {
        return enemiesConfigs;
    }

    public void setEnemiesConfigs(List<EnemyConfig> enemiesConfigs) {
        this.enemiesConfigs = enemiesConfigs;
    }
}
