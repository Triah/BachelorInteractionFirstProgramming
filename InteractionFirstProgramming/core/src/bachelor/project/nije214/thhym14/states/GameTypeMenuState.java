package bachelor.project.nije214.thhym14.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import bachelor.project.nije214.thhym14.Enemy;
import bachelor.project.nije214.thhym14.Waypoint;

import static bachelor.project.nije214.thhym14.StaticGlobalVariables.HEIGHT;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.WIDTH;

/**
 * Created by Nicolai on 21-03-2017.
 */

public class GameTypeMenuState extends State {

    private Waypoint wp;
    private Enemy enemy;

    public GameTypeMenuState(GameStateManager gsm) {
        super(gsm);
        this.enemy = new Enemy();
        camera.setToOrtho(false, WIDTH, HEIGHT);
        camera.update();
        wp = new Waypoint();
        create();
    }

    public void create() {
        enemy.createEnemy();
        //enemy.createSprite(new Sprite(new Texture("badlogic.jpg")));
        wp.createPath(new Array<Vector2>());
        wp.addPathNode(new Vector2(250, 500));
        wp.addPathNode(new Vector2(800, 500));
        wp.addPathNode(new Vector2(800, 1000));
        wp.addPathNode(new Vector2(250, 1000));
        wp.addPathNode(new Vector2(250, 1500));
        wp.addPathNode(new Vector2(800, 1500));
        wp.addPathNode(new Vector2(800, HEIGHT));
        wp.createEnemyArray();
        wp.addEnemyToPath(this.enemy);
        wp.createEnemy(enemy);
        wp.createSprite(enemy.getSprite());
        wp.createShapeRenderer();
        enemy.setCenter(250, 0);
        enemy.setSpeed(100);
        enemy.setPath(wp.getPath());
        enemy.setVelocity(enemy.getAngle(), enemy.getSpeed());
        System.out.println("hest");
    }

    public void processEnemy() {
        enemy.setVelocity(enemy.getAngle(), enemy.getSpeed());
        enemy.setSpritePosition(enemy.getX(), enemy.getVelocity().x, enemy.getY(), enemy.getVelocity().y);
        enemy.setSpriteRotation(enemy.getAngle());
        for (int i = 0; i < wp.getEnemyArray().size; i++) {
            if (enemy.isWaypointReached() && enemy.getWaypoint() == wp.getPath().size - 1) {
                wp.getEnemyArray().removeIndex(i);
                System.out.println(wp.getEnemyArray());
                //TO DO: implement custom dispose
                //enemy.dispose();
            }
            if (enemy.isWaypointReached() && !(enemy.getWaypoint() == wp.getPath().size - 1)) {
                enemy.incrementWaypoint();
            }

        }

    }

    public void draw(SpriteBatch batch) {
        for (Enemy enemy : wp.getEnemyArray()) {
            enemy.getSprite().draw(batch);
        }
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        processEnemy();
        draw(sb);
        sb.end();
        wp.drawRoute();
        wp.drawWayPoints();
        wp.drawRouteFromEnemy();
    }

    @Override
    public void dispose() {
        enemy.dispose();
    }
}
