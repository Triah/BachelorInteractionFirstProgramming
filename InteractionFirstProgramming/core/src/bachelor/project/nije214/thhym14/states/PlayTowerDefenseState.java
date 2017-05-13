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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import bachelor.project.nije214.thhym14.Bullet;

import java.util.ArrayList;
import java.util.LinkedList;

import bachelor.project.nije214.thhym14.Collision;
import bachelor.project.nije214.thhym14.Enemy;
import bachelor.project.nije214.thhym14.InteractionFirstProgramming;
import bachelor.project.nije214.thhym14.Raycast;
import bachelor.project.nije214.thhym14.Tower;
import bachelor.project.nije214.thhym14.Waypoint;

import static bachelor.project.nije214.thhym14.StaticGlobalVariables.HEIGHT;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.WIDTH;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.bulletDamagePref;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.bulletSpeedPref;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.bulletTypePref;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.enemyHealthPref;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.enemySpeedPref;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.enemyTypePref;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.towerFireRatePref;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.towerRangePref;
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
    private Music music;
    private Sound normalShot;
    private Texture background;
    private Array<Bullet> bullets;
    private Raycast ray;
    private ShapeRenderer sr;
    private int i = 0;
    private Vector3 touchPoint;
    private ArrayList<Tower> inactiveTowers;
    private Collision cl;
    private int lives;
    private Label livesLabel;
    private Stage stage;
    private Label scoreLabel;
    private Skin skin;
    private int score;
    private Preferences scorePrefs;
    private float waveTimer;
    private Label waveLabel;
    private int currentWave;

    public PlayTowerDefenseState(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, WIDTH, HEIGHT);
        camera.update();
        waveTimer = 0;
        currentWave = 1;
        setUpStage();
        touchPoint = new Vector3();
        music = Gdx.audio.newMusic(Gdx.files.internal("music/defensemusic.ogg"));
        music.setVolume(0.5f);
        music.setLooping(true);
        music.play();
        normalShot = Gdx.audio.newSound(Gdx.files.internal("soundeffects/pistol.wav"));
        mapPrefs = Gdx.app.getPreferences("mapPrefs");
        enemyPrefs = Gdx.app.getPreferences("enemyPrefs");
        bulletPrefs = Gdx.app.getPreferences("bulletPrefs");
        towerPrefs = Gdx.app.getPreferences("towerPrefs");
        scorePrefs = Gdx.app.getPreferences("scorePrefs");
        timeSpawn = 0;
        bullets = new Array<Bullet>();
        this.enemy = new Enemy();
        inactiveTowers = new ArrayList<Tower>();
        towers = new ArrayList<Tower>();
        wp = new Waypoint();
        background = new Texture("grass_template2.jpg");
        createWaypoint();
        createEnemy();
        createTower();
        createBullet();
        handleBackAction();
        ray = new Raycast();
        sr = new ShapeRenderer();
        cl = new Collision();
    }

    public void setUpStage() {
        lives = 10;
        score = 0;
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        livesLabel = new Label("Lives remaining: " + lives, skin);
        livesLabel.setFontScale(2.5f);
        livesLabel.setPosition(WIDTH * 0.65f, HEIGHT * 0.95f, Align.center);
        scoreLabel = new Label("Score: " + score, skin);
        scoreLabel.setFontScale(2.5f);
        scoreLabel.setPosition(WIDTH * 0.15f, HEIGHT * 0.95f, Align.center);
        waveLabel = new Label("Wave: " + currentWave, skin);
        waveLabel.setFontScale(2.5f);
        waveLabel.setPosition(WIDTH*0.35f, HEIGHT * 0.95f, Align.center);
        stage.addActor(waveLabel);
        stage.addActor(livesLabel);
        stage.addActor(scoreLabel);
    }

    public void createTower() {
        for (int i = 0; i < mapPrefs.getFloat("towerSize"); i++) {
            Tower tower = new Tower();
            tower.setActive(false);
            tower.createTower("stone.png");
            tower.setHP(towerPrefs.getFloat("towerHealth"));
            tower.setfireRate(towerPrefs.getFloat("towerFireRate"));
            tower.setRange(towerPrefs.getFloat("towerRange"));
            tower.getSprite().setSize(100, 100);
            tower.setCenter(mapPrefs.getFloat("towerX" + i), mapPrefs.getFloat("towerY" + i));
            tower.setTimer(0);
            if (towerPrefs.getString(towerTypePref).equals("FROST")) {
                tower.setType(Tower.Type.FROST);
            } else if (towerPrefs.getString(towerTypePref).equals("BASIC")) {
                tower.setType(Tower.Type.BASIC);
            } else if (towerPrefs.getString(towerTypePref).equals("PENETRATION")) {
                tower.setType(Tower.Type.PENETRATION);
            }
            inactiveTowers.add(tower);
        }

    }


    public void activateTower() {
        if (Gdx.input.justTouched()) {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            for (Tower t : inactiveTowers) {
                if (t.getSprite().getBoundingRectangle().contains(touchPoint.x, touchPoint.y)) {
                    if (towers.contains(t) && t.getActive()) {
                        towers.remove(t);
                        t.getSprite().setTexture(new Texture("stone.png"));
                        t.setActive(false);
                    } else {
                        if (!towers.contains(t) && towers.size() <= 2) {
                            towers.add(t);
                            t.getSprite().setTexture(new Texture(towerPrefs.getString("towerSprite")));
                            t.setActive(true);
                        }
                    }
                }
            }
        }
    }

    public void createBullet() {
        bullet = new Bullet();
        bullet.createBullet(bulletPrefs.getString("bulletSprite"));
    }

    public void createWaypoint() {
        wp.createPath(new Array<Vector2>());
        wp.addPathNode(new Vector2(mapPrefs.getFloat("firstWpX"), mapPrefs.getFloat("firstWpY")));
        for (int i = 0; i < mapPrefs.getFloat("wpSize"); i++) {
            wp.addPathNode(new Vector2(mapPrefs.getFloat("wpX" + i), mapPrefs.getFloat("wpY" + i)));
        }
    }

    public void createEnemy() {
        enemy.createEnemy(enemyPrefs.getString("enemySprite"));
        enemy.getSprite().setCenter(mapPrefs.getFloat("firstWpX"), mapPrefs.getFloat("firstWpY"));
        enemy.setSpeed(enemyPrefs.getFloat("enemySpeed"));
        enemy.setPath(wp.getPath());
        enemy.setHealth(enemyPrefs.getFloat("enemyHealth"));
        if(enemyPrefs.getString(enemyTypePref).equals("BASIC")){
            enemy.setType(Enemy.Type.BASIC);
        }
        if(enemyPrefs.getString(enemyTypePref).equals("SILLY")){
            enemy.setType(Enemy.Type.SILLY);
        }
        wp.createEnemyArray();
        wp.createSprite(enemy.getSprite());
        wp.createShapeRenderer();
        enemy.getSprite().setSize(100, 100);
        enemy.getSprite().setOriginCenter();
    }

    public void processEnemy() {
        for (Bullet b : bullets) {
            b.setBulletPosition(b.getX(), b.getVelocity().x, b.getY(), b.getVelocity().y);
        }
        for (Enemy enemy : wp.getEnemyArray()) {
            if(enemy.getType() == Enemy.Type.BASIC) {
                enemy.setVelocity(enemy.getAngle(), enemy.getSpeed());
                enemy.setSpritePosition(enemy.getX(), enemy.getVelocity().x, enemy.getY(), enemy.getVelocity().y);
                enemy.setSpriteRotation(enemy.getAngle());
            }
            if(enemy.getType() == Enemy.Type.SILLY){
                enemy.setVelocitySilly(enemy.getAngle(), enemy.getSpeed());
                enemy.setSpritePosition(enemy.getX(), enemy.getVelocity().x, enemy.getY(), enemy.getVelocity().y);
                enemy.setSpriteRotation(enemy.getAngle());
            }

            if (enemy.isWaypointReached() && enemy.getWaypoint() == wp.getPath().size - 1) {
                wp.getEnemyArray().removeValue(enemy, false);
                lives--;
                livesLabel.setText("Lives remaining: " + lives);
                if (lives == 0) {
                    scorePrefs.putInteger("finalScore",calculateScore());
                    scorePrefs.flush();
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Gdx.app.postRunnable(new Runnable() {
                                @Override
                                public void run() {
                                    gsm.set(new GameOverState(gsm));
                                }
                            });
                        }
                    });
                    t.start();
                    music.stop();
                }

            }
            if (enemy.isWaypointReached() && !(enemy.getWaypoint() == wp.getPath().size - 1)) {
                enemy.incrementWaypoint();
            }
        }
    }

    public void draw(SpriteBatch batch) {
        for (Bullet b : bullets) {
            b.getSprite().draw(batch);
        }
        for (Enemy enemy : wp.getEnemyArray()) {
            enemy.getSprite().draw(batch);
        }
        for (Tower t : towers) {
            t.getSprite().draw(batch);
        }
        for (Tower t : inactiveTowers) {
            t.getSprite().draw(batch);
        }

    }

    @Override
    public void handleInput() {
        activateTower();
    }


    @Override
    public void update(float deltaTime) {
        for (Tower t : towers) {
            float timer = t.getTimer();
            timer += Gdx.graphics.getDeltaTime();
            t.setTimer(timer);
        }

        for (Enemy e : wp.getEnemyArray()) {
            if (e.isHit()) {
                float eTimer = e.getTimer();
                eTimer += Gdx.graphics.getDeltaTime();
                e.setTimer(eTimer);
            }
        }

        waveTimer += Gdx.graphics.getDeltaTime();
        if(waveTimer > 15){
            this.enemy.setHealth(enemy.getHealth() +1);
            currentWave++;
            waveLabel.setText("Wave: " + currentWave);
            waveTimer = 0;
        }

        timeSpawn += Gdx.graphics.getDeltaTime();
        if (timeSpawn > 2) {
            cloneAndAddToList();
            timeSpawn = 0;
        }
        if (!towers.isEmpty()) {
            for (Tower t : towers) {
                for (Enemy e : wp.getEnemyArray()) {
                    if (ray.simpleRangeCheck(e, t, (int) towerPrefs.getFloat("towerRange"))) {
                        if (t.getTimer() > 5 / towerPrefs.getFloat("towerFireRate")) {
                            cloneAndAddToListBullet(e, t);
                            t.setTimer(0);
                            long id = normalShot.play();
                            normalShot.setVolume(id, 0.25f);
                        }
                    }
                }
            }
        }


        for (Bullet b : bullets) {
            for (Enemy e : wp.getEnemyArray()) {
                    if (e.isHit()) {
                        e.pushBackEnemy(e.getX(), b.getVelocity().x, e.getY(), b.getVelocity().y);
                        if (e.getTimer() > 0.5) {
                            e.setTimer(0);
                            e.setHit(false);
                            bullets.removeValue(b, true);
                        }

                    }
                if (cl.isColliding(b.getSprite().getBoundingRectangle(), e.getSprite().getBoundingRectangle())) {
                    if ((e.getHealth() - b.getDamage()) > 0) {
                        //subtract bullet damage from enemy health
                        if(!e.isHit() && !b.isHit()) {
                            e.setHealth(e.getHealth() - b.getDamage());
                            b.setHit(true);
                        }
                        if(b.isHit()){
                            b.setDamage(0);
                        }
                        if (b.getType() == Bullet.BulletType.PUSHBACK && !e.isHit()) {
                            e.setHit(true);
                        }
                        if (towers.get(0).getType() == Tower.Type.FROST && !e.isHit()) {
                            e.setSpeed(e.getSpeed() * 0.8f);
                        }

                        b.getSprite().setSize(0,0);

                    } else {
                        /**
                         * remove from array
                         * set null for garbagecollection
                         */
                        wp.getEnemyArray().removeValue(e, true);
                        bullets.removeValue(b, true);
                        score = calculateScore();
                        scoreLabel.setText("Score: " + score);
                    }
                }
                e = null;
            }
            b = null;
        }
        handleInput();
    }

    public int calculateScore() {
        if (enemyPrefs.getFloat(enemyHealthPref) >= 3 && enemyPrefs.getFloat(enemyHealthPref) <= 7) {
            score += 3;
        } else if (enemyPrefs.getFloat(enemyHealthPref) <= 2) {
            score += 1;
        } else if (enemyPrefs.getFloat(enemyHealthPref) > 8) {
            score += 5;
        }
        if (enemyPrefs.getFloat(enemySpeedPref) <= 150) {
            score += 1;
        } else if (enemyPrefs.getFloat(enemySpeedPref) > 150 && enemyPrefs.getFloat(enemySpeedPref) <= 300) {
            score += 3;
        } else if (enemyPrefs.getFloat(enemySpeedPref) > 300) {
            score += 5;
        }
        if (towerPrefs.getFloat(towerRangePref) >= 400) {
            score += 1;
        } else if (towerPrefs.getFloat(towerRangePref) >= 250 && towerPrefs.getFloat(towerRangePref) < 400) {
            score += 3;
        } else if (towerPrefs.getFloat(towerRangePref) < 250) {
            score += 5;
        }
        if (towerPrefs.getFloat(towerFireRatePref) == 1) {
            score += 5;
        } else if (towerPrefs.getFloat(towerFireRatePref) > 1 && towerPrefs.getFloat(towerFireRatePref) < 5) {
            score += 3;
        } else if (towerPrefs.getFloat(towerFireRatePref) >= 5) {
            score += 1;
        }
        if (bulletPrefs.getFloat(bulletDamagePref) == 1) {
            score += 5;
        } else if (bulletPrefs.getFloat(bulletDamagePref) > 1 && bulletPrefs.getFloat(bulletDamagePref) <= 5) {
            score += 3;
        } else if (bulletPrefs.getFloat(bulletDamagePref) > 5) {
            score += 1;
        }
        if (bulletPrefs.getFloat(bulletSpeedPref) < 300) {
            score += 5;
        } else if (bulletPrefs.getFloat(bulletSpeedPref) >= 300 && bulletPrefs.getFloat(bulletSpeedPref) <= 800) {
            score += 3;
        } else if (bulletPrefs.getFloat(bulletSpeedPref) > 800) {
            score += 1;
        }

        return score;
    }


    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.setProjectionMatrix(camera.combined);
        sb.draw(background, 0, 0, WIDTH, HEIGHT);
        processEnemy();
        disposeEntities();
        draw(sb);
        sb.end();
        wp.drawRoute();
        wp.drawWayPoints();
        circleshape();
        stage.draw();
    }

    public void cloneAndAddToList() {
        Enemy enemy = new Enemy();
        enemy.createEnemy(enemyPrefs.getString("enemySprite"));
        enemy.setCenter(mapPrefs.getFloat("firstWpX"), mapPrefs.getFloat("firstWpY"));
        enemy.setSpeed(enemyPrefs.getFloat("enemySpeed"));
        enemy.setPath(this.wp.getPath());
        enemy.setHealth(this.enemy.getHealth());
        enemy.setType(this.enemy.getType());
        enemy.setVelocity(enemy.getAngle(), enemy.getSpeed());
        enemy.getSprite().setSize(this.enemy.getSprite().getWidth(), this.enemy.getSprite().getHeight());
        wp.getEnemyArray().add(enemy);
        enemy.getSprite().setOriginCenter();
    }


    public void cloneAndAddToListBullet(Enemy ex, Tower tx) {
        Bullet b = new Bullet();
        b.createBullet(bulletPrefs.getString("bulletSprite"));
        b.setDamage(bulletPrefs.getFloat("bulletDamage"));
        b.getSprite().setSize(100, 100);
        b.setCenter(tx.getX() + tx.getSprite().getWidth() / 2, tx.getY() + tx.getSprite().getHeight() / 2);
        b.setSpeed(bulletPrefs.getFloat("bulletSpeed"));
        b.setVelocity(b.getTowerToEnemyAngle(ex, tx), b.getSpeed());
        b.getSprite().rotate((180 / (float) Math.PI * (float) Math.atan2(ex.getY() - tx.getY(), ex.getX() - tx.getX())) + 90);
        b.getSprite().setOriginCenter();
        b.setHit(false);
        if(bulletPrefs.getString(bulletTypePref).equals("BASIC")){
            b.setType(Bullet.BulletType.BASIC);
        }
        if(bulletPrefs.getString(bulletTypePref).equals("PUSHBACK")){
            b.setType(Bullet.BulletType.PUSHBACK);
        }
        bullets.add(b);
    }

    public void disposeEntities() {
        if (enemy.getWaypoint() == wp.getPath().size) {
            enemy.dispose();
        }
        for (Bullet b : bullets) {
            if (b.getSprite().getBoundingRectangle().x > WIDTH ||
                    b.getSprite().getBoundingRectangle().x + b.getSprite().getWidth() < 0 ||
                    b.getSprite().getBoundingRectangle().y > HEIGHT ||
                    b.getSprite().getBoundingRectangle().y + b.getSprite().getHeight() < 0) {
                b.getSprite().getTexture().dispose();
                bullets.removeValue(b, true);
            }
        }
    }


    public void circleshape() {
        sr.setAutoShapeType(true);
        sr.setColor(Color.CYAN);
        sr.begin();

        for (Tower t : towers) {
            sr.circle(t.getX() + t.getSprite().getWidth() / 2, t.getY() + t.getSprite().getHeight() / 2, towerPrefs.getFloat("towerRange"));
        }
        sr.end();
    }


    @Override
    public void dispose() {
        music.dispose();
    }

    public void handleBackAction() {
        InputProcessor adapter = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.BACK) {
                    gsm.set(new AssembleState(gsm));
                    Gdx.input.setCatchBackKey(true);
                    dispose();
                }
                return false;
            }
        };
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(adapter);
        multiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(multiplexer);
    }
}