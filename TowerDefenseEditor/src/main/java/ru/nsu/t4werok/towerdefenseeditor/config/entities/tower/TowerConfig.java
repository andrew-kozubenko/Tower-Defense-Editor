package ru.nsu.t4werok.towerdefenseeditor.config.entities.tower;

import ru.nsu.t4werok.towerdefenseeditor.config.entities.map.MapConfig;

import java.util.ArrayList;

public class TowerConfig {
    private String name;
    private int price;
    private int damage;
    private String damageType;
    private double fireRate;
    private String visualEffect;
    private int attackRadius;
    private String image;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public String getDamageType() {
        return damageType;
    }

    public void setDamageType(String damageType) {
        this.damageType = damageType;
    }

    public double getFireRate() {
        return fireRate;
    }

    public void setFireRate(double fireRate) {
        this.fireRate = fireRate;
    }

    public String getVisualEffect() {
        return visualEffect;
    }

    public void setVisualEffect(String visualEffect) {
        this.visualEffect = visualEffect;
    }

    public int getAttackRadius() {
        return attackRadius;
    }

    public void setAttackRadius(int attackRadius) {
        this.attackRadius = attackRadius;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void copyFrom(TowerConfig other) {
        this.name = other.getName();
        this.price = other.getPrice();
        this.damage = other.getDamage();
        this.damageType = other.getDamageType();
        this.fireRate = other.getFireRate();
        this.visualEffect = other.getVisualEffect();
        this.attackRadius = other.getAttackRadius();
        this.image = other.getImage();
    }
}
