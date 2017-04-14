package bachelor.project.nije214.thhym14.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.LinkedList;

import bachelor.project.nije214.thhym14.Enemy;
import bachelor.project.nije214.thhym14.Waypoint;

import static bachelor.project.nije214.thhym14.StaticGlobalVariables.HEIGHT;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.WIDTH;

/**
 * Created by Nicolai on 21-03-2017.
 */

public class GameTypeMenuState extends State {

    private Stage stage;
    private Skin skin;
    private LinkedList<TextButton> textButtons;
    private Label label;

    public GameTypeMenuState(GameStateManager gsm) {
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
        setButtonAttributes("Tower Defense");
        addActorToStage(textButtons.get(0));
        textButtons.get(0).setPosition(WIDTH/2-textButtons.get(0).getWidth()/2,
                HEIGHT/2 - textButtons.get(0).getWidth()/2);
        this.label = new Label("Choose Game Mode",skin);
        label.setPosition(0, HEIGHT-label.getHeight()-300);
        label.setSize(WIDTH,200);
        label.setFontScale(2.5f);
        label.setAlignment(Align.center);
        addActorToStage(label);
    }


    public void addActorToStage(Actor actor){
        stage.addActor(actor);
    }

    public void buttonActions(){
        for(TextButton textButton : textButtons){
            if(textButton.getLabel().getText().toString().matches("Tower Defense")){
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
                                        gsm.set(new AssembleState(gsm));
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

    public void setButtonAttributes(String buttonText){
        TextButton textButton = new TextButton(buttonText,skin);
        textButton.setWidth(WIDTH*0.40f);
        textButton.setHeight(HEIGHT*0.1f);
        textButton.getLabel().setFontScale(2.5f);
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
        skin.dispose();
        for(Actor stageActor : stage.getActors()){
            stage.getActors().removeValue(stageActor,true);
        }
    }
}
