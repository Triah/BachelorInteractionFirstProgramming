package bachelor.project.nije214.thhym14.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.LinkedList;

import static bachelor.project.nije214.thhym14.StaticGlobalVariables.HEIGHT;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.WIDTH;

/**
 * Created by Nicolai on 12-04-2017.
 */

public abstract class AssembleObject extends State {

    protected Stage stage;
    protected Skin skin;
    protected Table table;
    protected Sprite sprite;
    protected ScrollPane scrollPane;
    protected LinkedList<TextButton> textButtons;
    protected ScrollPane chosenScrollPane;
    protected Table chosenTable;
    protected Preferences enemyPrefs;
    protected Preferences bulletPrefs;
    protected Preferences towerPrefs;
    protected TextButton finishButton;

    public AssembleObject(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, WIDTH, HEIGHT);
        camera.update();
        enemyPrefs = Gdx.app.getPreferences("enemyPrefs");
        towerPrefs = Gdx.app.getPreferences("towerPrefs");
        bulletPrefs = Gdx.app.getPreferences("bulletPrefs");
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        stage = new Stage(new StretchViewport(WIDTH, HEIGHT));
        table = new Table(skin);
        setUpStage();
    }

    protected void setUpStage(){
        scrollPane = new ScrollPane(table,skin);
        this.textButtons = new LinkedList<TextButton>();
        Label avaliableLabel = new Label("Avaliable Attributes", skin);
        avaliableLabel.setFontScale(2.5f);
        avaliableLabel.setPosition(WIDTH*0.65f,HEIGHT*0.965f);
        stage.addActor(avaliableLabel);
        scrollPane.setWidth(WIDTH*0.45f);
        scrollPane.setHeight(HEIGHT*0.80f);
        scrollPane.setPosition(WIDTH*0.6f,HEIGHT*0.15f);
        scrollPane.setFadeScrollBars(false);
        table.align(Align.top);
        createSprite("badlogic.jpg");
        table.setWidth(scrollPane.getWidth());
        for(TextButton textButton : textButtons){
            table.add(textButton).width(scrollPane.getWidth()- 25).height(textButton.getHeight());
            table.row();
        }
        stage.addActor(scrollPane);
        chosenTable = new Table(skin);
        chosenScrollPane = new ScrollPane(chosenTable,skin);
        chosenScrollPane.setWidth(WIDTH*0.45f);
        chosenScrollPane.setHeight(HEIGHT*0.6f);
        chosenTable.align(Align.top);
        chosenTable.setWidth(chosenScrollPane.getWidth());
        chosenScrollPane.setPosition(WIDTH*0.1f,HEIGHT*0f);
        stage.addActor(chosenScrollPane);
        Label chosenLabel = new Label("Chosen Attributes", skin);
        chosenLabel.setFontScale(2.5f);
        chosenLabel.setPosition(WIDTH*0.175f,HEIGHT*0.625f);
        stage.addActor(chosenLabel);
        finishButton();
    }

    public void finishButton(){
        finishButton = new TextButton("Finish",skin);
        finishButton.setHeight(HEIGHT*0.1f);
        finishButton.setWidth(WIDTH*0.40f);
        finishButton.setPosition(WIDTH*0.60f,0);
        finishButton.getLabel().setFontScale(2.5f);
        stage.addActor(finishButton);
    }

    protected void setButtons(String text){
        TextButton textButton = new TextButton(text,skin);
        textButton.setHeight(HEIGHT*0.1f);
        textButton.getLabel().setFontScale(2.5f);
        textButtons.add(textButton);
    }

    protected void createSprite(String textForPicture){
        this.sprite = new Sprite(new Texture(textForPicture));
        sprite.setSize(WIDTH*0.45f,WIDTH*0.45f);
        sprite.setPosition(WIDTH*0.1f,HEIGHT-(getSprite().getHeight()+100));
    }

    protected Sprite getSprite(){
        return sprite;
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
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        if(sprite!=null) {
            sb.begin();
            sb.draw(getSprite(), getSprite().getX(), getSprite().getY(), getSprite().getWidth(), getSprite().getHeight());
            sb.end();
        }

    }

    @Override
    public void dispose() {

    }
}
