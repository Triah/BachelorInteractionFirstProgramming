package bachelor.project.nije214.thhym14.states;

import com.badlogic.gdx.Gdx;
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

import static bachelor.project.nije214.thhym14.StaticGlobalVariables.HEIGHT;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.WIDTH;

/**
 * Created by Nicolai on 30-04-2017.
 */

public class GameOverState extends State{

    private Stage stage;
    private Skin skin;
    private TextButton goToAssemblyButton;
    private Texture background;
    private Label label;


    public GameOverState(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, WIDTH, HEIGHT);
        camera.update();
        stage = new Stage(new StretchViewport(WIDTH, HEIGHT));
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        background = new Texture("airadventurelevel2.png");
        label = new Label("Game Over", skin);
        label.setPosition(0, HEIGHT-label.getHeight()-300);
        label.setSize(WIDTH,200);
        label.setFontScale(3.5f);
        label.setAlignment(Align.center);
        goToAssemblyButton = new TextButton("Return",skin);
        goToAssemblyButton.setWidth(WIDTH*0.40f);
        goToAssemblyButton.setHeight(HEIGHT*0.1f);
        goToAssemblyButton.getLabel().setFontScale(2.5f);
        goToAssemblyButton.setPosition(WIDTH/2-goToAssemblyButton.getWidth()/2,
                HEIGHT/2-goToAssemblyButton.getHeight()/2);
        stage.addActor(goToAssemblyButton);
        stage.addActor(label);
        Gdx.input.setInputProcessor(stage);
        addListenerToReturn();
    }

    public void addListenerToReturn(){
        goToAssemblyButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                dispose();
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
    public void handleInput() {

    }

    @Override
    public void update(float deltaTime) {
        stage.act();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background,0,0,WIDTH,HEIGHT);
        sb.end();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
