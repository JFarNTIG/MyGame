package io.github.my_project;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class DropScreen extends ScreenAdapter {

    Main game;
    Texture backgroundTexture;
    Texture bucketTexture;
    Texture dropTexture;
    Texture drop2Texture;
    Texture coinTexture;
    Sound dropSound;
    Music music;
    Sprite bucketSprite;
    Vector2 touchPos;
    Array<Drop> droplets;
    float dropTimer;
    Rectangle bucketRectangle;
    Rectangle dropRectangle;
    int score;

    public DropScreen(Main game) {
        this.game = game;
        backgroundTexture = new Texture("background.png");
        bucketTexture = new Texture("bucket.png");
        dropTexture = new Texture("drop.png");
        drop2Texture = new Texture("drop2.png");
        coinTexture = new Texture("coin.png");

        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.mp3"));
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));

        bucketSprite = new Sprite(bucketTexture);
        bucketSprite.setSize(1, 1);
        touchPos = new Vector2();
        droplets = new Array<>();
        bucketRectangle = new Rectangle();
        dropRectangle = new Rectangle();

        score = 0;
    }

    @Override
    public void show(){
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        // organize code into three methods
        input();
        logic();
        draw();
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }

    private void input() {
        float speed = 4f;
        float delta = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            bucketSprite.translateX(speed * delta);
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            bucketSprite.translateX(-speed * delta);
        }

        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            game.viewport.unproject(touchPos);
            bucketSprite.setCenterX(touchPos.x);
        }
    }
    
    private void logic() {
        float worldWidth = game.viewport.getWorldWidth();
        float worldHeight = game.viewport.getWorldHeight();
        float bucketWidth = bucketSprite.getWidth();
        float bucketHeight = bucketSprite.getHeight();

        bucketSprite.setX(MathUtils.clamp(bucketSprite.getX(), 0, worldWidth - bucketWidth));

        float delta = Gdx.graphics.getDeltaTime();
        bucketRectangle.set(bucketSprite.getX(), bucketSprite.getY(), bucketWidth, bucketHeight);

        for (int i = droplets.size - 1; i >= 0; i--) {
            Drop drop = droplets.get(i);

            Sprite dropSprite = drop.getSprite();
            float dropWidth = dropSprite.getWidth();
            float dropHeight = dropSprite.getHeight();

            float speed = drop.getSpeed();
            dropSprite.translateY(speed * -2f * delta);

            dropRectangle.set(dropSprite.getX(), dropSprite.getY(), dropWidth, dropHeight);

            if (dropSprite.getY() < -dropHeight) droplets.removeIndex(i);
            else if (bucketRectangle.overlaps(dropRectangle)) {
                droplets.removeIndex(i);
                dropSound.play();
                score += drop.getPoints();
            }
        }

        dropTimer += delta;
        if (dropTimer > 1f) {
            dropTimer = 0;
            createDroplet();
        }
    }
    
    private void draw() {
        ScreenUtils.clear(Color.BLACK);

        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();

        float worldWidth = game.viewport.getWorldWidth();
        float worldHeight = game.viewport.getWorldHeight();

        game.batch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);
        bucketSprite.draw(game.batch);

        for (Drop drop : droplets) {
            Sprite dropSprite = drop.getSprite();
            dropSprite.draw(game.batch);
        }

        game.font.draw(game.batch, "Score " + score, 0.1f, 4.5f);

        game.batch.end();
    }

    private void createDroplet() {
        float dropWidth = 1;
        float dropHeight = 1;
        float worldWidth = game.viewport.getWorldWidth();
        float worldHeight = game.viewport.getWorldHeight();

        Sprite dropSprite = new Sprite(drop2Texture);
        dropSprite.setSize(dropWidth, dropHeight);
        dropSprite.setX(MathUtils.random(0f, worldWidth - dropWidth));
        dropSprite.setY(worldHeight);

        Drop newDrop = new Drop(2.0f, 2, dropSprite);
        droplets.add(newDrop);
    }
}