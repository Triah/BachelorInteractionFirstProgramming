package bachelor.project.nije214.thhym14.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;

import bachelor.project.nije214.thhym14.GestureController;
import bachelor.project.nije214.thhym14.Tower;

import static bachelor.project.nije214.thhym14.StaticGlobalVariables.HEIGHT;
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
    private InputProcessor inputProcessor;

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
        registerInputProcessors();
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
        setTowerRangeButtons("Short Range",200);
        setTowerRangeButtons("Medium Range",300);
        setTowerRangeButtons("Long Range",450);
        setTowerTypeButtons("Frost Type","FROST");
        setTowerTypeButtons("Basic Type", "BASIC");
    }
    public void setTowerRangeButtons(final String text, final float value) {
        TextButton textButton = new TextButton(text, skin);
        textButton.setHeight(HEIGHT * 0.1f);
        textButton.getLabel().setFontScale(2.5f);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                tower.setRange(value);
                labelOptions(rangeLabel, text);
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

    public void setTowerFireRateButtons(final String text, final float value) {
        TextButton textButton = new TextButton(text, skin);
        textButton.setHeight(HEIGHT * 0.1f);
        textButton.getLabel().setFontScale(2.5f);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                tower.setfireRate(value);
                labelOptions(fireRateLabel, text);
                towerPrefs.putFloat(towerFireRatePref, tower.getFireRate());
            }
        });
        textButtons.add(textButton);
    }

    public void setTowerTypeButtons(final String text, final String value) {
        TextButton textButton = new TextButton(text, skin);
        textButton.setHeight(HEIGHT * 0.1f);
        textButton.getLabel().setFontScale(2.5f);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //will be assigned a type in play mode based on the preference
                labelOptions(typeLabel, text);
                towerPrefs.putString(towerTypePref, value);
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

    public void registerInputProcessors(){
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(inputProcessor);
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(new GestureDetector(gc));
        Gdx.input.setInputProcessor(multiplexer);
    }
}



