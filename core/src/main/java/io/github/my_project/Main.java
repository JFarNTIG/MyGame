package io.github.my_project;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;

    // Skapa en ShapeRenderer.
    private ShapeRenderer shapeRenderer;

    // Variabler till cirkelns position.
    private float cirkelX;
    private float cirkelY;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");

        // Initiera ShapeRenderer.
        shapeRenderer = new ShapeRenderer();

        // Cirkeln börjar på 200, 400
        cirkelX = 200;
        cirkelY = 400;
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.0f, 0.0f, 0.0f, 1.0f);

        batch.begin();
        batch.draw(image, 140, 210);
        batch.end();

        // Lägg till hastighet + position
        // så att cirkeln åker.
        // (Multiplicera med deltaTime så att det är rätt hastighet, oavsett FPS)
        cirkelX += 1.0f * Gdx.graphics.getDeltaTime();
        cirkelY += 0.0f * Gdx.graphics.getDeltaTime();

        // Rita en blå rektangel med ShapeRenderer.
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 1, 1);
        shapeRenderer.rect(100, 500, 100, 200);
        shapeRenderer.end();

        // Övning: Kan du rita en gul cirkel med ShapeRenderer?
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 0, 1);
        shapeRenderer.circle(cirkelX, cirkelY, 25);
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();

        // Städa upp: se till att köra dispose() på ShapeRenderer
        shapeRenderer.dispose();
    }
}
