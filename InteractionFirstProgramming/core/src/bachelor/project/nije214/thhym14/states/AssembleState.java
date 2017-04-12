package bachelor.project.nije214.thhym14.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.LinkedList;

import bachelor.project.nije214.thhym14.Enemy;

import static bachelor.project.nije214.thhym14.StaticGlobalVariables.HEIGHT;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.WIDTH;

/**
 * Created by Nicolai on 06-04-2017.
 */

public class AssembleState extends State {

    private Stage stage;
    private Skin skin;
    private LinkedList<TextButton> textButtons;
    private Label label;

    public AssembleState(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, WIDTH, HEIGHT);
        camera.update();
        textButtons = new LinkedList<TextButton>();
        createInitialUIElements();
        Gdx.input.setInputProcessor(stage);
    }

    public void createInitialUIElements(){
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        stage = new Stage(new StretchViewport(WIDTH, HEIGHT));
        setButtonAttributes("Bullet",400,200,2.5f, WIDTH/2 - 400,HEIGHT/2 - 125);
        setButtonAttributes("Tower",400,200,2.5f, WIDTH/2 + 50,HEIGHT/2 + 125);
        setButtonAttributes("Enemy",400,200,2.5f, WIDTH/2 - 400,HEIGHT/2 + 125);
        setButtonAttributes("Map",400,200,2.5f, WIDTH/2 + 50,HEIGHT/2 - 125);
        label = new Label("Tower Defense Assembly Hub",skin);
        label.setPosition(0, HEIGHT-label.getHeight()-300);
        label.setSize(WIDTH,200);
        label.setFontScale(4f);
        label.setAlignment(Align.center);
        addActorToStage(label);
        for(TextButton textButton : textButtons) {
            addActorToStage(textButton);
        }
    }

    public void addActorToStage(Actor actor){
        stage.addActor(actor);
    }

    public void buttonActions(){
        for(TextButton textButton : textButtons){
            if(textButton.getLabel().getText().toString().matches("Enemy")){
                textButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
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
        }
    }

    public void setButtonAttributes(String buttonText, float width, float height, float fontScale, float x, float y){
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
        buttonActions();
    }

    @Override
    public void update(float deltaTime) {
        stage.act();
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void dispose() {

    }
}
