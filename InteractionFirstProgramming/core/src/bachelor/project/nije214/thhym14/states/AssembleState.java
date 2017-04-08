package bachelor.project.nije214.thhym14.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.LinkedList;

import bachelor.project.nije214.thhym14.Enemy;

import static bachelor.project.nije214.thhym14.StaticGlobalVariables.HEIGHT;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.WIDTH;

/**
 * Created by Nicolai on 06-04-2017.
 */

public class AssembleState extends State {

    private Stage stage;
    private Skin skin;
    private TextButton button;
    private Table table;
    private TextButton button2;
    private LinkedList<TextButton> textButtons;
    private ScrollPane scrollPane;
    private Enemy enemy;

    public AssembleState(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, WIDTH, HEIGHT);
        camera.update();
        createUIElements();
        textButtons = new LinkedList<TextButton>();
        for(int i = 0; i <= 10; i++){
            setButtonAttributes("Speed", 400, 200, 2.5f);
        }
        enemy = new Enemy();
        addButtonToTable();
        Gdx.input.setInputProcessor(stage);
    }

    public void createUIElements(){
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        stage = new Stage(new StretchViewport(WIDTH, HEIGHT));
        table = new Table();
        scrollPane = new ScrollPane(table,skin);
        scrollPane.setSize(800,HEIGHT/2);
        scrollPane.setPosition(WIDTH/2-scrollPane.getWidth()/2,HEIGHT/2-scrollPane.getHeight()/2);
        table.setWidth(stage.getWidth());
        table.align(Align.left|Align.top);
        stage.addActor(scrollPane);
    }

    public void chooseEnemySpeed(){
        for(TextButton textButton : textButtons){
            if(textButton.getLabel().getText().toString().matches("Speed")){
                textButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        enemy.setSpeed(150);
                    }
                });
            }
        }
    }

    public void setButtonAttributes(String buttonText, float width, float height, float fontScale){
        TextButton textButton = new TextButton(buttonText,skin);
        textButton.setWidth(width);
        textButton.setHeight(height);
        textButton.getLabel().setFontScale(fontScale);
        textButtons.add(textButton);
    }

    public void addButtonToTable(){
        for(int i = 0; i<textButtons.size();i++) {
            table.add(textButtons.get(i)).width(textButtons.get(i).getWidth()).height(textButtons.get(i).getHeight());
            if(i%2==0 && i!=0 && i!=textButtons.size()){
                table.row();
            }
        }
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float deltaTime) {
        stage.act();
        chooseEnemySpeed();
    }

    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        System.out.println(enemy.getSpeed());
    }

    @Override
    public void dispose() {

    }
}
