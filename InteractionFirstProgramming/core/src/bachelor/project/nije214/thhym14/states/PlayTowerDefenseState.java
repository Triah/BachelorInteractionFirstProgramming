package bachelor.project.nije214.thhym14.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import bachelor.project.nije214.thhym14.Bullet;

import java.util.ArrayList;
import java.util.LinkedList;
import bachelor.project.nije214.thhym14.Enemy;
import bachelor.project.nije214.thhym14.InteractionFirstProgramming;
import bachelor.project.nije214.thhym14.Raycast;
import bachelor.project.nije214.thhym14.Tower;
import bachelor.project.nije214.thhym14.Waypoint;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.HEIGHT;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.WIDTH;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.towerTypePref;

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
    private float timeSpawn;
    private Bullet bullet;
    private float timeShoot;
    private Music music;
    private Sound normalShot;
    private Texture background;
    private Array<Bullet> bullets;

    private Raycast ray;
    private ShapeRenderer sr;

    private int i = 0;


    public PlayTowerDefenseState(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, WIDTH, HEIGHT);
        camera.update();
        music = Gdx.audio.newMusic(Gdx.files.internal("music/defensemusic.ogg"));
        music.setVolume(0.5f);
        music.setLooping(true);
        music.play();
        normalShot = Gdx.audio.newSound(Gdx.files.internal("soundeffects/pistol.wav"));
        timeShoot = 0;
        mapPrefs = Gdx.app.getPreferences("mapPrefs");
        enemyPrefs = Gdx.app.getPreferences("enemyPrefs");
        bulletPrefs = Gdx.app.getPreferences("bulletPrefs");
        towerPrefs = Gdx.app.getPreferences("towerPrefs");
        timeSpawn = 0;
        bullets = new Array<Bullet>();
        this.enemy = new Enemy();
        towers = new ArrayList<Tower>();
        wp = new Waypoint();
        //background = new Texture("grass_template2.jpg");
        createWaypoint();
        createEnemy();
        createTower();
        createBullet();
        handleBackAction();

        ray = new Raycast();
        //circleshape(tower.getRange());
        sr = new ShapeRenderer();
        //circleshape(250);

    }

    public void createTower(){
        for(int i = 0; i<mapPrefs.getFloat("towerSize");i++){
            Tower tower = new Tower();
            tower.createTower(towerPrefs.getString("towerSprite"));
            tower.setHP(towerPrefs.getFloat("towerHealth"));
            tower.setfireRate(towerPrefs.getFloat("towerFireRate"));
            tower.setRange(towerPrefs.getFloat("towerRange"));
            tower.getSprite().setSize(200,200);
            tower.setCenter(mapPrefs.getFloat("towerX"+i),mapPrefs.getFloat("towerY"+i));
            tower.setTimer(0);
            if(towerPrefs.getString(towerTypePref) == "FROST"){
                tower.setType(Tower.Type.FROST);
            } else if (towerPrefs.getString(towerTypePref) == "BASIC"){
                tower.setType(Tower.Type.BASIC);
            } else if (towerPrefs.getString(towerTypePref) == "LASER"){
                tower.setType(Tower.Type.LASER);
            }

            towers.add(tower);
        }
    }

    public void createBullet(){
        bullet = new Bullet();
        bullet.createBullet(bulletPrefs.getString("bulletSprite"));
        cloneAndAddToListBullet(enemy, towers.get(0));
    }

    public void createWaypoint(){
        wp.createPath(new Array<Vector2>());
        wp.addPathNode(new Vector2(mapPrefs.getFloat("firstWpX"),mapPrefs.getFloat("firstWpY")));
        for(int i = 0; i < mapPrefs.getFloat("wpSize");i++){
            wp.addPathNode(new Vector2(mapPrefs.getFloat("wpX"+i), mapPrefs.getFloat("wpY"+i)));
        }
    }

    public void createEnemy(){
        enemy.createEnemy(enemyPrefs.getString("enemySprite"));
        enemy.getSprite().setCenter(mapPrefs.getFloat("firstWpX"),mapPrefs.getFloat("firstWpY"));
        enemy.setSpeed(enemyPrefs.getFloat("enemySpeed"));
        enemy.setPath(wp.getPath());
        enemy.setHealth(enemyPrefs.getFloat("enemyHealth"));
        wp.createEnemyArray();
        wp.createSprite(enemy.getSprite());
        wp.createShapeRenderer();
        wp.addEnemyToPath(this.enemy);
        enemy.getSprite().setSize(200,200);
        enemy.getSprite().setOriginCenter();

    }

    public void processEnemy(){
        for (Bullet b : bullets) {
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
        for(Bullet b : bullets){
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
        for(Tower t : towers){
            float timer = t.getTimer();
            timer += Gdx.graphics.getDeltaTime();
            t.setTimer(timer);
            System.out.println(timer);
        }

        timeSpawn += Gdx.graphics.getDeltaTime();
            if (timeSpawn > 5) {
                cloneAndAddToList();
                timeSpawn = 0;
            }
        timeShoot += Gdx.graphics.getDeltaTime();
        if(!towers.isEmpty()) {
            for(Tower t: towers) {
                for(Enemy e : wp.getEnemyArray()){
                if(ray.isPlayerInSight(e, t, 500)){
                    if(t.getTimer()>5/towerPrefs.getFloat("towerFireRate")){
                        System.out.println("meh");
                        bullet.towerToEnemyLaser(e,t);
                        cloneAndAddToListBullet(e, t);
                        t.setTimer(0);
                        long id = normalShot.play();
                        normalShot.setVolume(id, 0.25f);
                    }

/*
                if (timeShoot > 5 / towerPrefs.getFloat("towerFireRate")) {
                    cloneAndAddToListBullet(e, t);
                    timeShoot = 0;
                    long id = normalShot.play();
                    normalShot.setVolume(id, 0.25f);
                }
                */


            }}
        }}}


    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.setProjectionMatrix(camera.combined);
        //sb.draw(background,0,0,WIDTH,HEIGHT);
        processEnemy();
        disposeEntities();
        draw(sb);
        sb.end();
        wp.drawRoute();
        wp.drawWayPoints();
    }

    public void cloneAndAddToList(){
        Enemy enemy = new Enemy();
        enemy.createEnemy(enemyPrefs.getString("enemySprite"));
        enemy.setCenter(mapPrefs.getFloat("firstWpX"),mapPrefs.getFloat("firstWpY"));
        enemy.setSpeed(this.enemy.getSpeed());
        enemy.setPath(this.wp.getPath());
        enemy.setVelocity(enemy.getAngle(),enemy.getSpeed());
        enemy.getSprite().setSize(this.enemy.getSprite().getWidth(),this.enemy.getSprite().getHeight());
        wp.getEnemyArray().add(enemy);
        enemy.getSprite().setOriginCenter();
    }


    public void cloneAndAddToListBullet(Enemy ex, Tower tx){
       // for(Tower t: towers) {
            Bullet b = new Bullet();
            b.createBullet(bulletPrefs.getString("bulletSprite"));
            b.setCenter(tx.getX() + tx.getSprite().getWidth()/2, tx.getY() + tx.getSprite().getHeight()/2);
            b.setSpeed(bulletPrefs.getFloat("bulletSpeed"));
            b.setVelocity(b.getTowerToEnemyAngle(ex, tx), b.getSpeed());
            b.getSprite().rotate((180/(float)Math.PI * (float)Math.atan2(enemy.getY() - tx.getY(), enemy.getX() - tx.getX()))+90);
            b.getSprite().setOriginCenter();
            bullets.add(b);
     //   }
    }

    public void disposeEntities() {
        if(enemy.getWaypoint() == wp.getPath().size){
            enemy.dispose();
        }
        for(Bullet b : bullets){
            if(b.getSprite().getBoundingRectangle().x > WIDTH ||
                    b.getSprite().getBoundingRectangle().x+b.getSprite().getWidth() < 0 ||
                    b.getSprite().getBoundingRectangle().y > HEIGHT ||
                    b.getSprite().getBoundingRectangle().y+b.getSprite().getHeight() < 0){
                b.getSprite().getTexture().dispose();
                bullets.removeValue(b,true);
            }
        }
    }


/*
    public void circleshape(int radius) {
        //int radius = tower.getRange();
        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
        sr.setColor(Color.CYAN);
        sr.begin();
        for(Tower t : towers()) {
            sr.begin();
            sr.circle(t.getX() + t.getSprite().getWidth()/2, t.getY() +t.getSprite().getHeight()/2, radius);
            Gdx.gl.glLineWidth((20));
       }
        sr.end();
    }
*/

    public void rayThread(){
        System.out.println("loadrays");
        Runnable task1 = new Runnable(){

            @Override
            public void run(){
                Raycast ray = new Raycast();
            }
        };


        Thread thread1 = new Thread(task1);
        thread1.start();
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
