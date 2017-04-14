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
import bachelor.project.nije214.thhym14.Tower;

import static bachelor.project.nije214.thhym14.StaticGlobalVariables.HEIGHT;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.WIDTH;

/**
 * Created by Nicolai on 12-04-2017.
 */

public class AssembleTower extends AssembleObject {

    private Tower tower;
    private Label fireRateLabel;
    private Label healthLabel;
    private Label rangeLabel;

    public AssembleTower(GameStateManager gsm) {
        super(gsm);
        tower = new Tower();
        fireRateLabel = new Label("",skin);
        fireRateLabel.setFontScale(2.5f);
        rangeLabel = new Label("",skin);
        rangeLabel.setFontScale(2.5f);
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
        setButtons("Slow Fire Rate");
        setButtons("Medium Fire Rate");
        setButtons("High Fire Rate");
        setButtons("Low Health");
        setButtons("Medium Health");
        setButtons("High Health");
        setButtons("Short Range");
        setButtons("Medium Range");
        setButtons("Long Range");
        setButtonActions();
    }

    public void setButtonActions(){
        for(TextButton textButton : textButtons){
            if(textButton.getLabel().getText().toString().contains("Fire Rate")) {
                if (textButton.getLabel().getText().toString().contains("Slow")) {
                    textButton.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                            tower.setfireRate(1);
                            labelOptions(fireRateLabel,"Slow Fire Rate");
                            towerPrefs.putFloat("towerFireRate", tower.getFireRate());
                        }
                    });
                }
                if (textButton.getLabel().getText().toString().contains("Medium")) {
                    textButton.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                            tower.setfireRate(4);
                            labelOptions(fireRateLabel,"Medium Fire Rate");
                            towerPrefs.putFloat("towerFireRate", tower.getFireRate());
                        }
                    });
                }
                if (textButton.getLabel().getText().toString().contains("High")) {
                    textButton.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                            tower.setfireRate(8);
                            labelOptions(fireRateLabel,"High Fire Rate");
                            towerPrefs.putFloat("towerFireRate", tower.getFireRate());
                        }
                    });
                }
            }
            if(textButton.getLabel().getText().toString().contains("Health")) {
                if (textButton.getLabel().getText().toString().contains("Low")) {
                    textButton.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                            tower.setHP(1);
                            labelOptions(healthLabel,"Low Health");
                            towerPrefs.putFloat("towerHealth", tower.getHP());
                        }
                    });
                }
                if (textButton.getLabel().getText().toString().contains("Medium")) {
                    textButton.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                            tower.setHP(5);
                            labelOptions(healthLabel,"Medium Health");
                            towerPrefs.putFloat("towerHealth", tower.getHP());
                        }
                    });
                }
                if (textButton.getLabel().getText().toString().contains("High")) {
                    textButton.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                            tower.setHP(10);
                            labelOptions(healthLabel,"High Health");
                            towerPrefs.putFloat("towerHealth", tower.getHP());
                        }
                    });
                }
            }
            if(textButton.getLabel().getText().toString().contains("Range")) {
                if (textButton.getLabel().getText().toString().contains("Short")) {
                    textButton.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                            tower.setRange(300);
                            labelOptions(rangeLabel,"Short Range");
                            towerPrefs.putFloat("towerRange", tower.getRange());
                        }
                    });
                }
                if (textButton.getLabel().getText().toString().contains("Medium")) {
                    textButton.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                            tower.setRange(600);
                            labelOptions(rangeLabel,"Medium Range");
                            towerPrefs.putFloat("towerRange", tower.getRange());
                        }
                    });
                }
                if (textButton.getLabel().getText().toString().contains("Long")) {
                    textButton.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                            tower.setRange(1000);
                            labelOptions(rangeLabel,"Long Range");
                            towerPrefs.putFloat("towerRange", tower.getRange());
                        }
                    });
                }
            }
            //TODO implementer type som en string for at gemme data i prefs
            /*if(textButton.getLabel().getText().toString().contains("Type")) {
                if (textButton.getLabel().getText().toString().contains("Basic")) {
                    textButton.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                            tower.setType(Tower.Type.BASIC);
                            labelOptions(typeLabel,"Basic Type");
                            prefs.putString("towerType", tower.getType());
                        }
                    });
                }
                if (textButton.getLabel().getText().toString().contains("Frost")) {
                    textButton.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                            tower.setType(Tower.Type.FROST);
                            labelOptions(typeLabel,"Frost Type");
                            prefs.putString("towerType", tower.getType());
                        }
                    });
                }
                if (textButton.getLabel().getText().toString().contains("Laser")) {
                    textButton.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                            tower.setType(Tower.Type.LASER);
                            labelOptions(typeLabel,"Laser Type");
                            prefs.putString("towerType", tower.getType());
                        }
                    });
                }
            }*/ // fremtidsfix yolo
        }
        finishButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
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
}



