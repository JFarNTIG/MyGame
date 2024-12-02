package io.github.my_project;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen extends ScreenAdapter {
    // (Behöver inte en egen SpriteBatch)
    // private SpriteBatch batch;
    private Texture image;

    // Skapa en ShapeRenderer.
    // (Vi behöver inte en egen ShapeRenderer, finns en i Game)
    // private ShapeRenderer shapeRenderer;

    // Variabler till cirkelns position.
    private float cirkelX;
    private float cirkelY;

    // RGB, nuvarande bakgrundfärg.
    float r = MathUtils.random();
    float g = MathUtils.random();
    float b = MathUtils.random();

    private ArrayList<Circle> circles = new ArrayList<>(); 

    Main game;

    public GameScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        // batch = new SpriteBatch();
        image = new Texture("libgdx.png");

        // Initiera ShapeRenderer.
        // shapeRenderer = new ShapeRenderer();

        // Cirkeln börjar på 200, 400
        cirkelX = 200;
        cirkelY = 400;

        // Input events: Hantera tangenter, eller musclick.
        // touchDown() körs *endast* när man klickar någonstans.
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyTyped (char key) {
                // r = MathUtils.random();
                // g = MathUtils.random();
                // b = MathUtils.random();
                return true;
            }

            @Override
            public boolean touchDown (int x, int y, int pointer, int button) {
                // r = MathUtils.random();
                // g = MathUtils.random();
                // b = MathUtils.random();

                Circle circ = new Circle();
                circ.x = x;
                circ.y = 800 - y;
                circles.add(circ);

                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        // Polling: Kolla om en tangent är nertryckt (just i det här ögonblicket).
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            cirkelY++;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.S)){
            cirkelY--;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            cirkelX--;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.D)){
            cirkelX++;
        }

        ScreenUtils.clear(r, g, b, 1.0f);

        game.batch.begin();
        game.batch.draw(image, 140, 210);
        game.batch.end();

        // Lägg till hastighet + position
        // så att cirkeln åker.
        // (Multiplicera med deltaTime så att det är rätt hastighet, oavsett FPS)
        // cirkelX += 5.0f * Gdx.graphics.getDeltaTime();
        // cirkelY += 0.0f * Gdx.graphics.getDeltaTime();

        // Rita en blå rektangel med ShapeRenderer.
        game.shapeRenderer.begin(ShapeType.Filled);
        game.shapeRenderer.setColor(0, 0, 1, 1);
        game.shapeRenderer.rect(100, 500, 100, 200);
        game.shapeRenderer.end();

        // Övning: Kan du rita en gul cirkel med ShapeRenderer?
        game.shapeRenderer.begin(ShapeType.Filled);
        game.shapeRenderer.setColor(1, 1, 0, 1);
        game.shapeRenderer.circle(cirkelX, cirkelY, 25);
        for(Circle circle : circles) {
            game.shapeRenderer.circle(circle.x, circle.y, 20);
        }
        game.shapeRenderer.end();
    }

    @Override
    public void hide() {
        // batch.dispose();
        image.dispose();

        // Städa upp: se till att köra dispose() på ShapeRenderer
        // shapeRenderer.dispose();
    }
}
