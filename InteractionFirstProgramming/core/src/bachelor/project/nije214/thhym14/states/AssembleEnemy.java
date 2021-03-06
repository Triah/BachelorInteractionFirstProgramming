package bachelor.project.nije214.thhym14.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.ArrayList;

import bachelor.project.nije214.thhym14.Enemy;
import bachelor.project.nije214.thhym14.GestureController;

import static bachelor.project.nije214.thhym14.StaticGlobalVariables.HEIGHT;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.WIDTH;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.enemyHealthPref;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.enemySpeedPref;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.enemyTypePref;

/**
 * Authors:
 * Nicolai Hedegaard Jensen <nije214@student.sdu.dk>
 * Thor Skou Hymøller <thhym14@student.sdu.dk>
 */

public class AssembleEnemy extends AssembleObject {

    private Label speedLabel;
    private Label healthLabel;
    private Array<Texture> avaliableTextures;
    private GestureController gc;
    private Vector3 touchPoint;
    private int i;
    private Label typeLabel;
    private InputProcessor inputProcessor;

    public AssembleEnemy(GameStateManager gsm) {
        super(gsm);
        speedLabel = new Label("",skin);
        speedLabel.setFontScale(2.5f);
        typeLabel = new Label("",skin);
        typeLabel.setFontScale(2.5f);
        healthLabel = new Label("", skin);
        healthLabel.setFontScale(2.5f);
        touchPoint = new Vector3();
        createButtons();
        avaliableTextures = new Array<Texture>();
        for(TextButton textButton : textButtons){
            table.add(textButton).width(scrollPane.getWidth()-25).height(textButton.getHeight());
            table.row();
        }
        i=0;
        createTextures();
        createSprite(avaliableTextures.get(i));
        createGestureControls();
        handleBackAction();
        registerInputProcessors();
        finish();
    }

    private void createTextures(){
        addTextureToList("rock_enemy_game_character.png");
        addTextureToList("monsterenemy.png");
        addTextureToList("spider.png");
        addTextureToList("rocketenemy.png");
    }

    private void addTextureToList(String textureText){
        Texture texture = new Texture(textureText);
        avaliableTextures.add(texture);
    }

    private void createButtons(){
        setEnemyValueButtons(enemySpeedPref, speedLabel, "Low Speed",100);
        setEnemyValueButtons(enemySpeedPref, speedLabel,"Medium Speed",250);
        setEnemyValueButtons(enemySpeedPref, speedLabel,"High Speed",500);
        setEnemyValueButtons(enemyHealthPref, healthLabel,"Low Health",1);
        setEnemyValueButtons(enemyHealthPref, healthLabel,"Medium Health",5);
        setEnemyValueButtons(enemyHealthPref, healthLabel,"High Health",10);
        setEnemyTypeButtons(enemyTypePref, typeLabel, "Basic type", "BASIC");
        setEnemyTypeButtons(enemyTypePref, typeLabel, "Silly type", "SILLY");
    }

    private void saveTexture(){
        enemyPrefs.putString("enemySprite",((FileTextureData)getCurrentTexture().getTextureData()).getFileHandle().path().toString());
    }

    private Texture getCurrentTexture(){
        return getSprite().getTexture();
    }

    private void createGestureControls(){
        gc = new GestureController(){
            @Override
            public boolean fling(float velocityX, float velocityY, int button) {
                if (Math.abs(velocityX) > Math.abs(velocityY)) {
                    if (velocityX > 0) {
                        if(touchPoint != null && getSprite().getBoundingRectangle().contains(touchPoint.x,touchPoint.y)){
                            i--;
                            if(i == -1){
                                i=avaliableTextures.size-1;
                                getSprite().setTexture(avaliableTextures.get(i));
                            } else{
                                getSprite().setTexture(avaliableTextures.get(i));
                            }
                        }
                    } else if (velocityX < 0) {
                        if(touchPoint != null && getSprite().getBoundingRectangle().contains(touchPoint.x,touchPoint.y)){
                            i++;
                            if(i > avaliableTextures.size-1){
                                i=0;
                                getSprite().setTexture(avaliableTextures.get(i));
                            } else{
                                getSprite().setTexture(avaliableTextures.get(i));
                            }
                        }
                    } else {
                    }
                }
                return false;
            }
        };
    }

    private void finish(){
        finishButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                saveTexture();
                enemyPrefs.flush();
                dispose();
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                gsm.set(new AssembleState(gsm));
                            }
                        });
                    }
                });
                t.start();
            }
        });
    }

    private void setEnemyValueButtons(final String pref, final Label labelType, final String text, final float value) {
        TextButton textButton = new TextButton(text, skin);
        textButton.setHeight(HEIGHT * 0.1f);
        textButton.getLabel().setFontScale(2.5f);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                labelOptions(labelType, text);
                enemyPrefs.putFloat(pref, value);
            }
        });
        textButtons.add(textButton);
    }

    private void setEnemyTypeButtons(final String pref, final Label labelType, final String text, final String value) {
        TextButton textButton = new TextButton(text, skin);
        textButton.setHeight(HEIGHT * 0.1f);
        textButton.getLabel().setFontScale(2.5f);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //will be assigned a type in play mode based on the preference
                labelOptions(labelType, text);
                enemyPrefs.putString(pref, value);
            }
        });
        textButtons.add(textButton);
    }

    @Override
    public void handleInput(){
        if(Gdx.input.justTouched()){
            camera.unproject(touchPoint.set(Gdx.input.getX(),Gdx.input.getY(),0));
        }
    }

    @Override
    public void dispose(){
        stage.dispose();
        skin.dispose();
    }

    private void handleBackAction() {

        inputProcessor = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if(keycode == Input.Keys.BACK) {
                    gsm.set(new AssembleState(gsm));
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
        multiplexer.addProcessor(new GestureDetector(gc));
        Gdx.input.setInputProcessor(multiplexer);
    }
}



