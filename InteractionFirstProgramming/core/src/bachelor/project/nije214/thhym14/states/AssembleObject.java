package bachelor.project.nije214.thhym14.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
 * Authors:
 * Nicolai Hedegaard Jensen <nije214@student.sdu.dk>
 * Thor Skou Hymøller <thhym14@student.sdu.dk>
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
    protected Texture background;

    public AssembleObject(GameStateManager gsm) {
        super(gsm);
        background = new Texture("airadventurelevel2.png");
        enemyPrefs = Gdx.app.getPreferences("enemyPrefs");
        towerPrefs = Gdx.app.getPreferences("towerPrefs");
        bulletPrefs = Gdx.app.getPreferences("bulletPrefs");
        setUpStage();
    }

    protected void setUpStage(){
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        stage = new Stage(new StretchViewport(WIDTH, HEIGHT));
        table = new Table(skin);
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

    private void finishButton(){
        finishButton = new TextButton("Finish",skin);
        finishButton.setHeight(HEIGHT*0.1f);
        finishButton.setWidth(WIDTH*0.40f);
        finishButton.setPosition(WIDTH*0.60f,0);
        finishButton.getLabel().setFontScale(2.5f);
        stage.addActor(finishButton);
    }

    protected void createSprite(Texture texture){
        this.sprite = new Sprite(texture);
        sprite.setSize(WIDTH*0.45f,WIDTH*0.45f);
        sprite.setPosition(WIDTH*0.1f,HEIGHT-(getSprite().getHeight()+100));
    }

    protected void labelOptions(Label label, String text){
        label.setText(text);
        if(!chosenTable.getChildren().contains(label,true)){
            chosenTable.add(label);
            chosenTable.row();
        }
    }


    protected Sprite getSprite(){
        return sprite;
    }

    @Override
    public void handleInput() {
        //needed for override
    }

    @Override
    public void update(float deltaTime) {
        handleInput();
        stage.act();
    }

    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.begin();
        sb.draw(background,0,0,background.getWidth(),HEIGHT);
        if(sprite!=null) {
            sb.draw(getSprite(), getSprite().getX(), getSprite().getY(), getSprite().getWidth(), getSprite().getHeight());
        }
        sb.end();
        stage.draw();
    }

    @Override
    public void dispose() {
        //needed for override
    }
}
