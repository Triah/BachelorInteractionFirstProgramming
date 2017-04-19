package bachelor.project.nije214.thhym14.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import bachelor.project.nije214.thhym14.Bullet;

import java.util.ArrayList;
import java.util.LinkedList;
import bachelor.project.nije214.thhym14.Enemy;
import bachelor.project.nije214.thhym14.InteractionFirstProgramming;
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
    private Preferences mapPrefs;
    private Preferences enemyPrefs;
    private Preferences bulletPrefs;
    private Preferences towerPrefs;
    private ArrayList<Tower> towers;
    private float time;
    private Bullet bullet;
    private Music music;


    public PlayTowerDefenseState(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, WIDTH, HEIGHT);
        camera.update();
        music = Gdx.audio.newMusic(Gdx.files.internal("music/defensemusic.ogg"));
        music.setVolume(0.5f);
        music.setLooping(true);
        music.play();
        mapPrefs = Gdx.app.getPreferences("mapPrefs");
        enemyPrefs = Gdx.app.getPreferences("enemyPrefs");
        bulletPrefs = Gdx.app.getPreferences("bulletPrefs");
        towerPrefs = Gdx.app.getPreferences("towerPrefs");
        time = 0;
        this.enemy = new Enemy();
        towers= new ArrayList<Tower>();
        bullet = new Bullet();
        wp = new Waypoint();
        createWaypoint();
        createEnemy();
        createTower();
        createBullet();
        handleBackAction();
    }

    public void createTower(){
        for(int i = 0; i<mapPrefs.getFloat("towerSize");i++){
            Tower tower = new Tower();
            tower.createTower();
            tower.setHP(towerPrefs.getFloat("towerHealth"));
            tower.setfireRate(towerPrefs.getFloat("towerFireRate"));
            tower.setRange(towerPrefs.getFloat("towerRange"));
            tower.createSprite(new Sprite(new Texture("towermode.PNG")));
            tower.setCenter(mapPrefs.getFloat("towerX"+i),mapPrefs.getFloat("towerY"+i));
            towers.add(tower);
        }
    }

    public void createBullet(){
        bullet.createBulletArray();
        cloneAndAddToListBullet();
    }

    public void createWaypoint(){
        wp.createPath(new Array<Vector2>());
        wp.addPathNode(new Vector2(mapPrefs.getFloat("firstWpX"),mapPrefs.getFloat("firstWpY")));
        for(int i = 0; i < mapPrefs.getFloat("wpSize");i++){
            wp.addPathNode(new Vector2(mapPrefs.getFloat("wpX"+i), mapPrefs.getFloat("wpY"+i)));
        }
    }

    public void createEnemy(){
        enemy.createEnemy();
        enemy.createSprite(new Sprite(new Texture("badlogic.jpg")));
        enemy.setCenter(mapPrefs.getFloat("firstWpX"),mapPrefs.getFloat("firstWpY"));
        enemy.setSpeed(enemyPrefs.getFloat("enemySpeed"));
        enemy.setPath(wp.getPath());
        enemy.setHealth(enemyPrefs.getFloat("enemyHealth"));
        wp.createEnemyArray();
        wp.createSprite(enemy.getSprite());
        wp.createShapeRenderer();
        wp.addEnemyToPath(this.enemy);
    }

    public void processEnemy(){
        //et eller andet er helt forkert her
        for (Bullet b : bullet.getBulletArray()) {
                b.setBulletPosition(b.getX(), b.getVelocity().x, b.getY(), b.getVelocity().y);
        }
        for (Enemy enemy : wp.getEnemyArray()) {
            enemy.setVelocity(enemy.getAngle(),enemy.getSpeed());
            enemy.setSpritePosition(enemy.getX(),enemy.getVelocity().x,enemy.getY(),enemy.getVelocity().y);
            enemy.setSpriteRotation(enemy.getAngle());

            if (enemy.isWaypointReached() && enemy.getWaypoint() == wp.getPath().size - 1) {
                wp.getEnemyArray().removeValue(enemy,false);
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
        for(Bullet b : bullet.getBulletArray()){
            b.getSprite().draw(batch);
        }
        for(Tower t : towers){
            t.getSprite().draw(batch);
        }
    }

    @Override
    public void handleInput() {
    }


    @Override
    public void update(float deltaTime) {
            time += Gdx.graphics.getDeltaTime();
            if (time > 2) {
                cloneAndAddToList();
                cloneAndAddToListBullet();
                time = 0;
            }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        processEnemy();
        disposeEntities();
        draw(sb);
        sb.end();
        wp.drawRoute();
        wp.drawWayPoints();
        wp.drawRouteFromEnemy();

    }

    public void cloneAndAddToList(){
        Enemy enemy = new Enemy();
        enemy.createEnemy();
        enemy.setCenter(mapPrefs.getFloat("firstWpX"),mapPrefs.getFloat("firstWpY"));
        enemy.setSpeed(this.enemy.getSpeed());
        enemy.setPath(this.wp.getPath());
        enemy.setVelocity(enemy.getAngle(),enemy.getSpeed());
        wp.getEnemyArray().add(enemy);
    }


    public void cloneAndAddToListBullet(){
        for(Tower t: towers) {
            Bullet b = new Bullet();
            b.createBullet();
            b.createSprite(new Sprite(new Texture("playButton.jpg")));
            b.setCenter(t.getX(), t.getY());
            b.setSpeed(bulletPrefs.getFloat("bulletSpeed"));
            b.setVelocity(b.getTowerToEnemyAngle(enemy, t), b.getSpeed());
            bullet.getBulletArray().add(b);
        }
    }

    public void disposeEntities() {
        if(enemy.getWaypoint() == wp.getPath().size){
            enemy.dispose();
        }
        for(Bullet b : bullet.getBulletArray()){
            if(b.getSprite().getBoundingRectangle().x > WIDTH ||
                    b.getSprite().getBoundingRectangle().x+b.getSprite().getWidth() < 0 ||
                    b.getSprite().getBoundingRectangle().y > HEIGHT ||
                    b.getSprite().getBoundingRectangle().y+b.getSprite().getHeight() < 0){
                b.getSprite().getTexture().dispose();
                bullet.getBulletArray().removeValue(b,true);
            }
        }
    }

    @Override
    public void dispose(){
        music.dispose();
    }

    public void handleBackAction() {
        InputProcessor adapter = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if(keycode == Input.Keys.BACK) {
                    gsm.set(new AssembleState(gsm));
                    Gdx.input.setCatchBackKey(true);
                    dispose();
                }
                return false;
            }
        };
        Gdx.input.setInputProcessor(adapter);
    }
}
