package bachelor.project.nije214.thhym14;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Waypoint implements Screen {

    private ShapeRenderer sr;
    private SpriteBatch batch;
    private Array<Enemy> enemies;

    private Sprite sprite;

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        for(Enemy enemy : enemies)
            enemy.draw(batch);
        batch.end();

        //Tegn rute mellem waypoints som hvid linje
        sr.setColor(Color.WHITE);
        sr.begin(ShapeType.Line);
        for(Enemy enemy : enemies) {
            Vector2 previous = enemy.getPath().first();
            for(Vector2 waypoint : enemy.getPath()) {
                sr.line(previous, waypoint);
                previous = waypoint;
            }
        }
        sr.end();

        //Tegn waypoints
        sr.begin(ShapeType.Filled);
        for(Enemy enemy : enemies)
            for(Vector2 waypoint : enemy.getPath())
                sr.circle(waypoint.x, waypoint.y, 10);
        sr.end();


        // /Tegn rute fra Enemy til nærmeste waypoint
        sr.setColor(Color.CYAN);
        sr.begin(ShapeType.Line);
        for(Enemy enemy : enemies)
            sr.line(new Vector2(enemy.getX() + enemy.getWidth()/2, enemy.getY() + enemy.getHeight()/2), enemy.getPath().get(enemy.getWaypoint()));
        sr.end();

    }


    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        Gdx.gl.glClearColor(0, 0, 0, 1);

        sr = new ShapeRenderer();
        batch = new SpriteBatch();

        sprite = new Sprite(new Texture("badlogic.jpg"));
        sprite.setSize(75, 75);
        sprite.setOrigin(sprite.getHeight()/2, sprite.getWidth()/2);

        enemies = new Array<Enemy>();
        Enemy enemy = new Enemy(sprite, getPath());
        //Enemy enemy2 = new Enemy(sprite, getPath2());


        //enemy.setPosition(250, 0);
        enemy.setCenter(250,0);
        //enemy.setOriginCenter();
        //enemy2.setPosition(800, 1000);


        enemies.add(enemy);
        //enemies.add(enemy2);



    }

    private Array<Vector2> getPath() {
        int height = Gdx.graphics.getHeight();
        int width = Gdx.graphics.getWidth();
        Array<Vector2> path = new Array<Vector2>();
        path.add(new Vector2(250, 500));
        path.add(new Vector2(800, 500));
        path.add(new Vector2(800, 1000));
        path.add(new Vector2(250, 1000));
        path.add(new Vector2(250, 1500));
        path.add(new Vector2(800, 1500));
        path.add(new Vector2(800, height));
        return path;
    }


    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        sr.dispose();
        batch.dispose();
        sprite.getTexture().dispose();
    }

}