package bachelor.project.nije214.thhym14.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

/**
 * Authors:
 * Nicolai Hedegaard Jensen <nije214@student.sdu.dk>
 * Thor Skou Hymøller <thhym14@student.sdu.dk>
 */

public class GameTypeMenuState extends State {

    private Stage stage;
    private Skin skin;
    private LinkedList<TextButton> textButtons;
    private Label label;
    private Texture background;
    private InputProcessor inputProcessor;


    public GameTypeMenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("airadventurelevel2.png");
        textButtons = new LinkedList<TextButton>();
        createInitialUIElements();
        handleBackAction();
        registerInputProcessors();
    }

    private void createInitialUIElements(){
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
        buttonActions();
    }

    private void addActorToStage(Actor actor){
        stage.addActor(actor);
    }

    private void buttonActions(){
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

    private void setButtonAttributes(String buttonText){
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
        for(Actor stageActor : stage.getActors()){
            stage.getActors().removeValue(stageActor,true);
        }
    }

    private void handleBackAction() {
        inputProcessor = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if(keycode == Input.Keys.BACK) {
                    Gdx.app.exit();
                    Gdx.input.setCatchBackKey(true);
                    dispose();
                }
                return false;
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
