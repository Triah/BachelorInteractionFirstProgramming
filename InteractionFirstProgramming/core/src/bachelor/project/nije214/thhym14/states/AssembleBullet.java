package bachelor.project.nije214.thhym14.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import bachelor.project.nije214.thhym14.Bullet;
import bachelor.project.nije214.thhym14.Enemy;

import static bachelor.project.nije214.thhym14.StaticGlobalVariables.HEIGHT;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.WIDTH;

/**
 * Created by Nicolai on 12-04-2017.
 */

public class AssembleBullet extends AssembleObject {

    private Bullet bullet;
    private Label speedLabel;
    private Label healthLabel;

    public AssembleBullet(GameStateManager gsm) {
        super(gsm);
        bullet = new Bullet();
        speedLabel = new Label("",skin);
        speedLabel.setFontScale(2.5f);
        healthLabel = new Label("", skin);
        healthLabel.setFontScale(2.5f);
        createButtons();
        for(TextButton textButton : textButtons){
            table.add(textButton).width(scrollPane.getWidth()-25).height(textButton.getHeight());
            table.row();
        }
        createSprite("enemyimage.PNG");
        handleBackAction();
    }

    public void createButtons(){
        setButtons("Low Speed");
        setButtons("Medium Speed");
        setButtons("High Speed");
        setButtonActions();
    }

    public void setButtonActions(){
        for(TextButton textButton : textButtons){
            if(textButton.getLabel().getText().toString().contains("Speed")) {
                if (textButton.getLabel().getText().toString().contains("Low")) {
                    textButton.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                            bullet.setSpeed(100);
                            labelOptions(speedLabel,"Low Speed");
                            bulletPrefs.putFloat("bulletSpeed", bullet.getSpeed());
                        }
                    });
                }
                if (textButton.getLabel().getText().toString().contains("Medium")) {
                    textButton.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                            bullet.setSpeed(250);
                            labelOptions(speedLabel,"Medium Speed");
                            bulletPrefs.putFloat("bulletSpeed", bullet.getSpeed());
                        }
                    });
                }
                if (textButton.getLabel().getText().toString().contains("High")) {
                    textButton.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                            bullet.setSpeed(500);
                            labelOptions(speedLabel,"High Speed");
                            bulletPrefs.putFloat("bulletSpeed", bullet.getSpeed());
                        }
                    });
                }
            }

        }
        finishButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
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
        Gdx.input.setInputProcessor(multiplexer);
    }
}



