package bachelor.project.nije214.thhym14.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import bachelor.project.nije214.thhym14.Bullet;
import bachelor.project.nije214.thhym14.Enemy;
import bachelor.project.nije214.thhym14.Tower;
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


    private Bullet bullet;
    private Tower tower;


    public PlayTowerDefenseState(GameStateManager gsm) {
        super(gsm);
        this.enemy = new Enemy();
        this.bullet = new Bullet();
        this.tower = new Tower();
        touchPoint = new Vector3();
        runButtonTexture = new Texture("badlogic.jpg");
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
        //enemy.setVelocity(enemy.getAngle(),enemy.getSpeed());
        runButton.setPosition(WIDTH - 100, HEIGHT - 100);




        tower.createTower();
        tower.createSprite(new Sprite(new Texture("badlogic.jpg")));
        tower.setCenter(500,1500);


        bullet.createBullet();
        bullet.createSprite(new Sprite(new Texture("badlogic.jpg")));
        bullet.setCenter(500,1500);
        bullet.setSpeed(1000);




    }

    public void processEnemy(){
        bullet.setVelocity(bullet.getTowerToEnemyAngle(enemy, tower), bullet.getSpeed());
        bullet.setBulletPosition(bullet.getX(),bullet.getVelocity().x, bullet.getY(), bullet.getVelocity().y);

        enemy.setVelocity(enemy.getAngle(),enemy.getSpeed());
        enemy.setSpritePosition(enemy.getX(),enemy.getVelocity().x,enemy.getY(),enemy.getVelocity().y);
        enemy.setSpriteRotation(enemy.getAngle());
        for (int i = 0; i < wp.getEnemyArray().size; i++) {
            if (enemy.isWaypointReached() && enemy.getWaypoint() == wp.getPath().size - 1) {
                wp.getEnemyArray().removeIndex(i);

                //// TODO: 09-04-2017  implement custom dispose
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
//// TODO: 09-04-2017 bullet loop
        bullet.getSprite().draw(batch);
        tower.getSprite().draw(batch);
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

    @Override
    public void dispose() {
        if(enemy.getWaypoint() == wp.getPath().size){
            enemy.dispose();
        }
    }
}
