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
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
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

import bachelor.project.nije214.thhym14.Enemy;
import bachelor.project.nije214.thhym14.GestureController;
import bachelor.project.nije214.thhym14.Tower;

import static bachelor.project.nije214.thhym14.StaticGlobalVariables.HEIGHT;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.WIDTH;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.bulletSpeedPref;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.towerFireRatePref;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.towerHealthPref;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.towerRangePref;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.towerTypePref;

/**
 * Created by Nicolai on 12-04-2017.
 */

public class AssembleTower extends AssembleObject {

    private Tower tower;
    private Label fireRateLabel;
    private Label healthLabel;
    private Label rangeLabel;
    private Label typeLabel;
    private Array<Texture> avaliableTextures;
    private int i;
    private GestureController gc;
    private Vector3 touchPoint;

    public AssembleTower(GameStateManager gsm) {
        super(gsm);
        tower = new Tower();
        avaliableTextures = new Array<Texture>();
        touchPoint = new Vector3();
        i = 0;
        fireRateLabel = new Label("",skin);
        fireRateLabel.setFontScale(2.5f);
        rangeLabel = new Label("",skin);
        rangeLabel.setFontScale(2.5f);
        healthLabel = new Label("", skin);
        healthLabel.setFontScale(2.5f);
        typeLabel = new Label("", skin);
        typeLabel.setFontScale(2.5f);
        createButtons();
        for(TextButton textButton : textButtons){
            table.add(textButton).width(scrollPane.getWidth()-25).height(textButton.getHeight());
            table.row();
        }
        createTextures();
        createSprite(avaliableTextures.get(i));
        createGestureControls();
        handleBackAction();
        finish();
    }

    public void createTextures(){
        addTextureToList("tower_grass.png");
        addTextureToList("tower_round.png");
        addTextureToList("tower_square.png");
        addTextureToList("towermedieval.png");
    }

    public void addTextureToList(String textureText){
        Texture texture = new Texture(textureText);
        avaliableTextures.add(texture);
    }

    public void saveTexture(){
        towerPrefs.putString("towerSprite",((FileTextureData)getCurrentTexture().getTextureData()).getFileHandle().path().toString());
    }

    public Texture getCurrentTexture(){
        return getSprite().getTexture();
    }

    public void createGestureControls(){
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

    public void finish(){
        finishButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                saveTexture();
                towerPrefs.flush();
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


    public void createButtons(){
        setTowerFireRateButtons("Slow Fire Rate",1);
        setTowerFireRateButtons("Medium Fire Rate",4);
        setTowerFireRateButtons("High Fire Rate",8);
        setTowerHealthButtons("Low Health",1);
        setTowerHealthButtons("Medium Health",5);
        setTowerHealthButtons("High Health",10);
        setTowerRangeButtons("Short Range",300);
        setTowerRangeButtons("Medium Range",600);
        setTowerRangeButtons("Long Range",900);
        setTowerTypeButtons("Frost Type","FROST");
        setTowerTypeButtons("Basic Type", "BASIC");
        setTowerTypeButtons("Laser Type", "LASER");
    }
    public void setTowerRangeButtons(String text, float value) {
        TextButton textButton = new TextButton(text, skin);
        textButton.setHeight(HEIGHT * 0.1f);
        textButton.getLabel().setFontScale(2.5f);
        final float tempValue = value;
        final String tempString = text;
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                tower.setRange(tempValue);
                labelOptions(rangeLabel, tempString);
                towerPrefs.putFloat(towerRangePref, tower.getRange());
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


    public void setTowerFireRateButtons(String text, float value) {
        TextButton textButton = new TextButton(text, skin);
        textButton.setHeight(HEIGHT * 0.1f);
        textButton.getLabel().setFontScale(2.5f);
        final float tempValue = value;
        final String tempString = text;
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                tower.setfireRate(tempValue);
                labelOptions(fireRateLabel, tempString);
                towerPrefs.putFloat(towerFireRatePref, tower.getFireRate());
            }
        });
        textButtons.add(textButton);
    }

    public void setTowerHealthButtons(String text, float value) {
        TextButton textButton = new TextButton(text, skin);
        textButton.setHeight(HEIGHT * 0.1f);
        textButton.getLabel().setFontScale(2.5f);
        final float tempValue = value;
        final String tempString = text;
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                tower.setHP(tempValue);
                labelOptions(healthLabel, tempString);
                towerPrefs.putFloat(towerHealthPref, tower.getHP());
            }
        });
        textButtons.add(textButton);
    }

    public void setTowerTypeButtons(String text, String value) {
        TextButton textButton = new TextButton(text, skin);
        textButton.setHeight(HEIGHT * 0.1f);
        textButton.getLabel().setFontScale(2.5f);
        final String tempValue = value;
        final String tempString = text;
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //will be assigned a type in play mode based on the preference
                labelOptions(typeLabel, tempString);
                towerPrefs.putString(towerTypePref, tempValue);
            }
        });
        textButtons.add(textButton);
    }

    private void labelOptions(Label label, String text){
        label.setText(text);
        if(!chosenTable.getChildren().contains(label,true)){
            chosenTable.add(label);
            chosenTable.row();
        }
    }

    @Override
    public void dispose(){
        stage.dispose();
        skin.dispose();
    }

    public void handleBackAction() {
        InputMultiplexer multiplexer = new InputMultiplexer();
        InputProcessor adapter = new InputAdapter() {
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
        multiplexer.addProcessor(adapter);
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(new GestureDetector(gc));
        Gdx.input.setInputProcessor(multiplexer);
    }
}



