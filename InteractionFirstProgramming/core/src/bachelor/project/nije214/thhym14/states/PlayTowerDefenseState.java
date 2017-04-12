package bachelor.project.nije214.thhym14.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

import java.util.LinkedList;

import bachelor.project.nije214.thhym14.Enemy;
import bachelor.project.nije214.thhym14.Waypoint;

import static bachelor.project.nije214.thhym14.StaticGlobalVariables.HEIGHT;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.WIDTH;

/**
 * Created by Nicolai on 26-03-2017.
 */

public class PlayTowerDefenseState extends State {
    private Waypoint wp;
    private Enemy enemy;
    private Vector3 touchPoint;
    private boolean playMode;
    private Texture runButtonTexture;
    private Sprite runButton;
    private AssemblyState as;
    private LinkedList<Enemy> enemies;
    private float time;

    public PlayTowerDefenseState(GameStateManager gsm) {
        super(gsm);
        time = 0;
        this.enemy = new Enemy();
        touchPoint = new Vector3();
        enemies = new LinkedList<Enemy>();
        runButtonTexture = new Texture("playButton.jpg");
        runButton = new Sprite(runButtonTexture);
        camera.setToOrtho(false, WIDTH, HEIGHT);
        camera.update();
        wp = new Waypoint();
        create();
    }

    public void create(){
        enemy.createEnemy();
        enemy.createSprite(new Sprite(new Texture("badlogic.jpg")));
        wp.createPath(new Array<Vector2>());
        wp.addPathNode(new Vector2(250,500));
        wp.createEnemyArray();
        wp.addEnemyToPath(this.enemy);
        wp.createEnemy(enemy);
        wp.createSprite(enemy.getSprite());
        wp.createShapeRenderer();
        enemy.setCenter(250,0);
        enemy.setSpeed(100);
        enemy.setPath(wp.getPath());
        enemy.setVelocity(enemy.getAngle(),enemy.getSpeed());
        runButton.setPosition(WIDTH - 100, HEIGHT - 100);


    }

    public void processEnemy(){
        for (Enemy enemy : wp.getEnemyArray()) {
        enemy.setVelocity(enemy.getAngle(),enemy.getSpeed());
        enemy.setSpritePosition(enemy.getX(),enemy.getVelocity().x,enemy.getY(),enemy.getVelocity().y);
        enemy.setSpriteRotation(enemy.getAngle());
            if (enemy.isWaypointReached() && enemy.getWaypoint() == wp.getPath().size - 1) {
                wp.getEnemyArray().removeValue(enemy,false);
                //TO DO: implement custom dispose
                //enemy.dispose();
            }
            if (enemy.isWaypointReached() && !(enemy.getWaypoint() == wp.getPath().size - 1)) {
                enemy.incrementWaypoint();
            }

        }

    }

    public void draw(SpriteBatch batch){
        for(Enemy enemy : wp.getEnemyArray()){
            enemy.getSprite().draw(batch);
        }
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            camera.unproject(touchPoint.set(Gdx.input.getX(),Gdx.input.getY(),0));
            if(runButton.getBoundingRectangle().contains(touchPoint.x,touchPoint.y)){
                toggleBool();
            } else {
                wp.addPathNode(new Vector2(touchPoint.x,touchPoint.y));
            }
        }
    }

    public void toggleBool(){
        if(!playMode){
            playMode = true;
        } else {
            playMode = false;
        }
    }


    public boolean playMode(){
        return playMode;
    }

    @Override
    public void update(float deltaTime) {
        handleInput();
        playMode();
        time += Gdx.graphics.getDeltaTime();
        if(time>2){
            cloneAndAddToList();
            time = 0;
        }
        for(Enemy enemy : wp.getEnemyArray()) {
            System.out.println(wp.getEnemyArray().size);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        if(playMode()){
            processEnemy();
            draw(sb);
        }
        sb.draw(runButton,runButton.getX(),runButton.getY(),75,75);

        sb.end();

        wp.drawRoute();
        wp.drawWayPoints();
        wp.drawRouteFromEnemy();
    }

    public void cloneAndAddToList(){
        Enemy enemy = new Enemy();
        enemy.createEnemy();
        enemy.setCenter(250,0);
        enemy.setSpeed(this.enemy.getSpeed());
        enemy.setPath(this.wp.getPath());
        enemy.setVelocity(this.enemy.getAngle(),enemy.getSpeed());
        wp.getEnemyArray().add(enemy);
    }

    @Override
    public void dispose() {
        if(enemy.getWaypoint() == wp.getPath().size){
            enemy.dispose();
        }
    }
}
