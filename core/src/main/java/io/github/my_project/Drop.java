package io.github.my_project;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Drop {
    private float speed;
    private int points;
    private Sprite sprite;

    public Drop(float speed, int points, Sprite sprite) {
        this.speed = speed;
        this.points = points;
        this.sprite = sprite;
    }

    public Sprite getSprite() {
        return this.sprite;
    }

    public float getSpeed() {
        return this.speed;
    }

    public int getPoints() {
        return this.points;
    }
}
