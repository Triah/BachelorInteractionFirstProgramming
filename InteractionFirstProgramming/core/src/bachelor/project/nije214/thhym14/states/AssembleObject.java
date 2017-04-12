package bachelor.project.nije214.thhym14.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
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

    public AssembleObject(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, WIDTH, HEIGHT);
        camera.update();
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        stage = new Stage(new StretchViewport(WIDTH, HEIGHT));
        table = new Table(skin);
        setUpStage();
        Gdx.input.setInputProcessor(stage);
    }

    protected void setUpStage(){
        scrollPane = new ScrollPane(table,skin);
        this.textButtons = new LinkedList<TextButton>();
        for(int i = 0; i<10; i++){
            setButtons("testbutton number:"+ "\n" + i);
        }
        scrollPane.setWidth(400);
        scrollPane.setHeight(HEIGHT - 200);
        scrollPane.setPosition(WIDTH/2 +100,100);
        scrollPane.setFadeScrollBars(false);
        table.align(Align.top);
        createSprite("badlogic.jpg");
        getSprite().setSize(500,500);
        getSprite().setPosition(100,HEIGHT-(getSprite().getHeight()+100));
        table.setWidth(scrollPane.getWidth());
        for(TextButton textButton : textButtons){
            table.add(textButton).width(scrollPane.getWidth()- 25).height(textButton.getHeight());
            table.row();
        }
        stage.addActor(scrollPane);
    }

    protected void setButtons(String text){
        TextButton textButton = new TextButton(text,skin);
        textButton.setHeight(200);
        textButton.getLabel().setFontScale(2.5f);
        textButtons.add(textButton);
    }

    protected void createSprite(String textForPicture){
        this.sprite = new Sprite(new Texture(textForPicture));
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
