package bachelor.project.nije214.thhym14.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.LinkedList;

import static bachelor.project.nije214.thhym14.StaticGlobalVariables.HEIGHT;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.WIDTH;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.bulletDamagePref;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.bulletSpeedPref;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.enemyHealthPref;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.enemySpeedPref;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.towerFireRatePref;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.towerRangePref;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.towerTypePref;

/**
 * Created by Nicolai on 06-04-2017.
 */

public class AssembleState extends State {

    private Stage stage;
    private Skin skin;
    private LinkedList<TextButton> textButtons;
    private Texture background;
    private Preferences enemyPrefs, bulletPrefs, towerPrefs, mapPrefs;
    private InputProcessor inputProcessor;


    public AssembleState(GameStateManager gsm) {
        super(gsm);
        enemyPrefs = Gdx.app.getPreferences("enemyPrefs");
        towerPrefs = Gdx.app.getPreferences("towerPrefs");
        bulletPrefs = Gdx.app.getPreferences("bulletPrefs");
        mapPrefs = Gdx.app.getPreferences("mapPrefs");
        background = new Texture("airadventurelevel2.png");
        textButtons = new LinkedList<TextButton>();
        createInitialUIElements();
        handleBackAction();
    }

    private void createInitialUIElements(){
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        stage = new Stage(new StretchViewport(WIDTH, HEIGHT));
        setButtonAttributes("Bullet",WIDTH*0.25f,HEIGHT*0.1f,2.5f,WIDTH*0.25f,HEIGHT*0.57f);
        setButtonAttributes("Tower",WIDTH*0.25f,HEIGHT*0.1f,2.5f,WIDTH*0.25f,HEIGHT*0.43f);
        setButtonAttributes("Enemy",WIDTH*0.25f,HEIGHT*0.1f,2.5f,WIDTH*0.55f,HEIGHT*0.57f);
        setButtonAttributes("Map",WIDTH*0.25f,HEIGHT*0.1f,2.5f,WIDTH*0.55f,HEIGHT*0.43f);
        setButtonAttributes("Play Game", WIDTH*0.55f,HEIGHT*0.1f,2.5f,WIDTH*0.25f,HEIGHT*0.25f);
        Label label = new Label("Tower Defense Assembly Hub", skin);
        label.setPosition(0, HEIGHT- label.getHeight()-300);
        label.setSize(WIDTH,200);
        label.setFontScale(2.5f);
        label.setAlignment(Align.center);
        addActorToStage(label);
        for(TextButton textButton : textButtons) {
            addActorToStage(textButton);
        }
        buttonActions();
        handleBackAction();
        registerInputProcessors();
    }

    private void addActorToStage(Actor actor){
        stage.addActor(actor);
    }

    private void buttonActions(){
        for(TextButton textButton : textButtons){
            if(textButton.getLabel().getText().toString().matches("Enemy")){
                textButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        dispose();
                        Thread t = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Gdx.app.postRunnable(new Runnable() {
                                    @Override
                                    public void run() {
                                        gsm.set(new AssembleEnemy(gsm));
                                    }
                                });
                            }
                        });
                        t.start();
                    }
                });
            }
            if(textButton.getLabel().getText().toString().matches("Tower")){
                textButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        dispose();
                        Thread t = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Gdx.app.postRunnable(new Runnable() {
                                    @Override
                                    public void run() {
                                        gsm.set(new AssembleTower(gsm));
                                    }
                                });
                            }
                        });
                        t.start();
                    }
                });

            }
            if(textButton.getLabel().getText().toString().matches("Bullet")){
                textButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        dispose();
                        Thread t = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Gdx.app.postRunnable(new Runnable() {
                                    @Override
                                    public void run() {
                                        gsm.set(new AssembleBullet(gsm));
                                    }
                                });
                            }
                        });
                        t.start();
                    }
                });

            }
            if(textButton.getLabel().getText().toString().matches("Map")){
                textButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        dispose();
                        Thread t = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Gdx.app.postRunnable(new Runnable() {
                                    @Override
                                    public void run() {
                                        gsm.set(new AssembleMap(gsm));
                                    }
                                });
                            }
                        });
                        t.start();
                    }
                });

            }
            if(textButton.getLabel().getText().toString().matches("Play Game")){
                textButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        //avoid nullpointer if it is the first run ever
                        if (enemyPrefs.getFloat(enemyHealthPref) != 0 &&
                                enemyPrefs.getFloat(enemySpeedPref) != 0 &&
                                bulletPrefs.getFloat(bulletDamagePref) != 0 &&
                                bulletPrefs.getFloat(bulletSpeedPref) != 0 &&
                                towerPrefs.getFloat(towerFireRatePref) != 0 &&
                                towerPrefs.getFloat(towerRangePref) != 0 &&
                                towerPrefs.getString(towerTypePref) != null &&
                                mapPrefs.getFloat("firstWpX") != 0) {
                            dispose();
                            Thread t = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Gdx.app.postRunnable(new Runnable() {
                                        @Override
                                        public void run() {
                                            gsm.set(new PlayTowerDefenseState(gsm));
                                        }
                                    });
                                }
                            });
                            t.start();
                        }
                    }
                });
            }
        }
    }

    private void setButtonAttributes(String buttonText, float width, float height, float fontScale, float x, float y){
        TextButton textButton = new TextButton(buttonText,skin);
        textButton.setWidth(width);
        textButton.setHeight(height);
        textButton.getLabel().setFontScale(fontScale);
        textButton.setX(x);
        textButton.setY(y);
        textButtons.add(textButton);
    }

    @Override
    public void handleInput() {
    }

    @Override
    public void update(float deltaTime) {
        stage.act();
    }

    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.begin();
        sb.draw(background,0,0,background.getWidth(),HEIGHT);
        sb.end();
        stage.draw();
    }

    @Override
    public void dispose() {
        skin.dispose();
        stage.dispose();
    }

    private void handleBackAction() {
        inputProcessor = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if(keycode == Input.Keys.BACK) {
                    gsm.set(new GameTypeMenuState(gsm));
                    Gdx.input.setCatchBackKey(true);
                    dispose();
                }
                return true;
            }
        };
    }

    private void registerInputProcessors(){
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(inputProcessor);
        multiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(multiplexer);
    }
}
