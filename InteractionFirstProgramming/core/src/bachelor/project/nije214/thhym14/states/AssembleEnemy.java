package bachelor.project.nije214.thhym14.states;

import com.badlogic.gdx.Gdx;
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

import bachelor.project.nije214.thhym14.Enemy;

import static bachelor.project.nije214.thhym14.StaticGlobalVariables.HEIGHT;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.WIDTH;

/**
 * Created by Nicolai on 12-04-2017.
 */

public class AssembleEnemy extends AssembleObject {

    private Enemy enemy;
    private Label speedLabel;
    private Label healthLabel;

    public AssembleEnemy(GameStateManager gsm) {
        super(gsm);
        enemy = new Enemy();
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
    }

    public void createButtons(){
        setButtons("Low Speed");
        setButtons("Medium Speed");
        setButtons("High Speed");
        setButtons("Low Health");
        setButtons("Medium Health");
        setButtons("High Health");
        setButtonActions();
    }

    public void setButtonActions(){
        for(TextButton textButton : textButtons){
            if(textButton.getLabel().getText().toString().contains("Speed")) {
                if (textButton.getLabel().getText().toString().contains("Low")) {
                    textButton.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                            enemy.setSpeed(100);
                            labelOptions(speedLabel,"Low Speed");
                            prefs.putFloat("enemySpeed", enemy.getSpeed());
                        }
                    });
                }
                if (textButton.getLabel().getText().toString().contains("Medium")) {
                    textButton.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                            enemy.setSpeed(250);
                            labelOptions(speedLabel,"Medium Speed");
                            prefs.putFloat("enemySpeed", enemy.getSpeed());
                        }
                    });
                }
                if (textButton.getLabel().getText().toString().contains("High")) {
                    textButton.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                            enemy.setSpeed(500);
                            labelOptions(speedLabel,"High Speed");
                            prefs.putFloat("enemySpeed", enemy.getSpeed());
                        }
                    });
                }
            }
            if(textButton.getLabel().getText().toString().contains("Health")) {
                if (textButton.getLabel().getText().toString().contains("Low")) {
                    textButton.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                            enemy.setHealth(1);
                            labelOptions(healthLabel,"Low Health");
                            prefs.putFloat("enemyHealth", enemy.getHealth());
                        }
                    });
                }
                if (textButton.getLabel().getText().toString().contains("Medium")) {
                    textButton.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                            enemy.setHealth(5);
                            labelOptions(healthLabel,"Medium Health");
                            prefs.putFloat("enemyHealth", enemy.getHealth());
                        }
                    });
                }
                if (textButton.getLabel().getText().toString().contains("High")) {
                    textButton.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                            enemy.setHealth(10);
                            labelOptions(healthLabel,"High Health");
                            prefs.putFloat("enemyHealth", enemy.getHealth());
                        }
                    });
                }
            }
        }
        finishButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                prefs.flush();
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
}


