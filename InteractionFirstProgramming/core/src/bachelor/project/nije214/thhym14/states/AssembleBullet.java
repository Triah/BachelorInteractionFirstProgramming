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

import bachelor.project.nije214.thhym14.Bullet;
import bachelor.project.nije214.thhym14.Enemy;
import bachelor.project.nije214.thhym14.GestureController;

import static bachelor.project.nije214.thhym14.StaticGlobalVariables.HEIGHT;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.WIDTH;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.bulletSpeedPref;

/**
 * Created by Nicolai on 12-04-2017.
 */

public class AssembleBullet extends AssembleObject {

    private Bullet bullet;
    private Label speedLabel;
    private Label healthLabel;
    private GestureController gc;
    private Array<Texture> avaliableTextures;
    private Vector3 touchPoint;
    private int i;

    public AssembleBullet(GameStateManager gsm) {
        super(gsm);
        bullet = new Bullet();
        speedLabel = new Label("",skin);
        speedLabel.setFontScale(2.5f);
        healthLabel = new Label("", skin);
        healthLabel.setFontScale(2.5f);
        createButtons();
        i = 0;
        avaliableTextures = new Array<Texture>();
        touchPoint = new Vector3();
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
        addTextureToList("Cannon_Ball.png");
        addTextureToList("projectile1.png");
        addTextureToList("projectile2.png");
        addTextureToList("frostbolt.png");
    }

    public void addTextureToList(String textureText){
        Texture texture = new Texture(textureText);
        avaliableTextures.add(texture);
    }

    public void saveTexture(){
        bulletPrefs.putString("bulletSprite",((FileTextureData)getCurrentTexture().getTextureData()).getFileHandle().path().toString());
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
                bulletPrefs.flush();
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

    @Override
    public void handleInput(){
        if(Gdx.input.justTouched()){
            camera.unproject(touchPoint.set(Gdx.input.getX(),Gdx.input.getY(),0));
        }
    }


    public void createButtons(){
        setBulletSpeedButtons("Low Speed",250);
        setBulletSpeedButtons("Medium Speed",500);
        setBulletSpeedButtons("High Speed",1000);
    }

    public void setBulletSpeedButtons(String text, float value) {
        TextButton textButton = new TextButton(text, skin);
        textButton.setHeight(HEIGHT * 0.1f);
        textButton.getLabel().setFontScale(2.5f);
        final float tempValue = value;
        final String tempString = text;
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                bullet.setSpeed(tempValue);
                labelOptions(speedLabel, tempString);
                towerPrefs.putFloat(bulletSpeedPref, bullet.getSpeed());
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



