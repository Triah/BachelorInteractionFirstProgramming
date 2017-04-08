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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
    private Label label;

    public AssembleState(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, WIDTH, HEIGHT);
        camera.update();
        textButtons = new LinkedList<TextButton>();
        createInitialUIElements();
        enemy = new Enemy();
        Gdx.input.setInputProcessor(stage);
    }

    public void createInitialUIElements(){
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        stage = new Stage(new StretchViewport(WIDTH, HEIGHT));
        //table = new Table();
        /*scrollPane = new ScrollPane(table,skin);
        scrollPane.setSize(800,HEIGHT/2);
        scrollPane.setPosition(WIDTH/2-scrollPane.getWidth()/2,HEIGHT/2-scrollPane.getHeight()/2);*/
        //table.setWidth(stage.getWidth());
        // table.align(Align.left|Align.center);
        //addActorToStage(table);
        setButtonAttributes("Tower Defense",400,200,2.5f);
        //addButtonToTable();
        addActorToStage(textButtons.get(0));
        textButtons.get(0).setPosition(WIDTH/2-textButtons.get(0).getWidth()/2,
                HEIGHT/2 - textButtons.get(0).getWidth()/2);
        createLabel("Choose Game Mode");
        label.setPosition(0, HEIGHT-label.getHeight()-300);
        label.setSize(WIDTH,200);
        label.setFontScale(4f);
        label.setAlignment(Align.center);
        addActorToStage(label);
    }

    public void createLabel(String text){
        this.label = new Label(text,skin);
    }

    public void clearAll(){
        if(stage != null){
            stage.clear();
        }
        if(scrollPane != null){
            scrollPane.clear();
        }
        if(table != null){
            table.clear();
        }
        if(!textButtons.isEmpty()){
            textButtons.clear();
        }
    }

    public void addActorToStage(Actor actor){
        stage.addActor(actor);
    }

    public void buttonActions(){
        for(TextButton textButton : textButtons){
            if(textButton.getLabel().getText().toString().matches("Speed")){
                textButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        enemy.setSpeed(150);
                    }
                });
            }
            if(textButton.getLabel().getText().toString().matches("Tower Defense")){
                textButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        clearAll();
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
        buttonActions();
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
