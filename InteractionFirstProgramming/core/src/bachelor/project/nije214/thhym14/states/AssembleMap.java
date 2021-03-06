package bachelor.project.nije214.thhym14.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

import bachelor.project.nije214.thhym14.Waypoint;

import static bachelor.project.nije214.thhym14.StaticGlobalVariables.HEIGHT;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.WIDTH;

/**
 * Authors:
 * Nicolai Hedegaard Jensen <nije214@student.sdu.dk>
 * Thor Skou Hymøller <thhym14@student.sdu.dk>
 */

public class AssembleMap extends State {

    private Waypoint wp;
    private Vector3 touchPoint;
    private Texture finishTexture;
    private Sprite finishSprite;
    private Texture roadModeTexture;
    private Texture towerModeTexture;
    private Sprite modeSprite;
    private ArrayList<Float> xPathNodes;
    private ArrayList<Float> yPathNodes;
    private boolean towerMode;
    private Preferences mapPrefs;
    private Preferences towerPrefs;
    private ArrayList<Float> xTowerPos;
    private ArrayList<Float> yTowerPos;
    private ArrayList<Sprite> towerSprites;
    private Texture background;
    private InputProcessor inputProcessor;

    public AssembleMap(GameStateManager gsm) {
        super(gsm);
        towerPrefs = Gdx.app.getPreferences("towerPrefs");
        background = new Texture("grass_template2.jpg");
        towerMode = false;
        roadModeTexture = new Texture("roadmode.PNG");
        towerModeTexture = new Texture(towerPrefs.getString("towerSprite"));
        modeSprite = new Sprite(roadModeTexture);
        finishTexture = new Texture("home.png");
        finishSprite = new Sprite(finishTexture);
        touchPoint = new Vector3();
        wp = new Waypoint();
        xPathNodes = new ArrayList<Float>();
        yPathNodes = new ArrayList<Float>();
        xTowerPos = new ArrayList<Float>();
        yTowerPos = new ArrayList<Float>();
        mapPrefs = Gdx.app.getPreferences("mapPrefs");
        towerSprites = new ArrayList<Sprite>();
        create();
        handleBackAction();
        registerInputProcessors();
    }

    private void create(){
        wp.createPath(new Array<Vector2>());
        wp.createShapeRenderer();
        wp.addPathNode(new Vector2(250,0));
        mapPrefs.putFloat("firstWpX",wp.getPath().first().x);
        mapPrefs.putFloat("firstWpY",wp.getPath().first().y);
        finishSprite.setSize(100,100);
        finishSprite.setPosition(WIDTH-finishSprite.getWidth(),0);
        modeSprite.setSize(100,100);
        modeSprite.setPosition(WIDTH*0.95f-modeSprite.getWidth(),HEIGHT*0.98f-modeSprite.getHeight());
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            camera.unproject(touchPoint.set(Gdx.input.getX(),Gdx.input.getY(),0));
            if(finishSprite.getBoundingRectangle().contains(touchPoint.x,touchPoint.y)){
                if(xPathNodes.size() > 0){
                    mapPrefs.putFloat("wpSize", xPathNodes.size());
                    for(int i = 0; i < xPathNodes.size(); i++){
                        mapPrefs.putFloat("wpX" + i, xPathNodes.get(i));
                        mapPrefs.putFloat("wpY" + i, yPathNodes.get(i));
                    }
                    mapPrefs.putFloat("towerSize", xTowerPos.size());
                    for(int i = 0; i < xTowerPos.size(); i++){
                        mapPrefs.putFloat("towerX" + i, xTowerPos.get(i));
                        mapPrefs.putFloat("towerY" + i, yTowerPos.get(i));
                    }
                }
                mapPrefs.flush();
                dispose();
                gsm.set(new AssembleState(gsm));

            } else if(modeSprite.getBoundingRectangle().contains(touchPoint.x,touchPoint.y)){
                if(!towerMode){
                    towerMode = true;
                    modeSprite.setTexture(towerModeTexture);
                } else{
                    towerMode = false;
                    modeSprite.setTexture(roadModeTexture);
                }

            } else if(!towerMode){
                wp.addPathNode(new Vector2(touchPoint.x, touchPoint.y));
                xPathNodes.add(touchPoint.x);
                yPathNodes.add(touchPoint.y);

            } else {
                xTowerPos.add(touchPoint.x);
                yTowerPos.add(touchPoint.y);
                createTowerSpriteAtPosition(touchPoint.x,touchPoint.y);
            }
        }
    }

    private void createTowerSpriteAtPosition(float x, float y){
        Sprite towerSprite = new Sprite(new Texture(towerPrefs.getString("towerSprite")));
        towerSprite.setSize(100,100);
        towerSprite.setPosition(x - towerSprite.getWidth()/2,y - towerSprite.getHeight()/2);
        towerSprites.add(towerSprite);
    }

    @Override
    public void update(float deltaTime) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background,0,0,WIDTH,HEIGHT);
        sb.draw(finishSprite,
                finishSprite.getX(),
                finishSprite.getY(),
                finishSprite.getWidth(),
                finishSprite.getHeight());
        sb.draw(modeSprite,
                modeSprite.getX(),
                modeSprite.getY(),
                modeSprite.getWidth(),
                modeSprite.getHeight());
        for(Sprite sprite : towerSprites){
            sb.draw(sprite,
                    sprite.getX(),
                    sprite.getY(),
                    sprite.getWidth(),
                    sprite.getHeight());
        }
        sb.end();
        wp.drawRoute();
        wp.drawWayPoints();
    }

    @Override
    public void dispose() {
        for(Sprite sprite : towerSprites){
            sprite.getTexture().dispose();
        }
        modeSprite.getTexture().dispose();
        finishSprite.getTexture().dispose();
    }

    private void handleBackAction(){
        inputProcessor = new InputAdapter(){
            @Override
            public boolean keyDown(int keycode){
                if(keycode == Input.Keys.BACK) {
                    gsm.set(new AssembleState(gsm));
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
        Gdx.input.setInputProcessor(multiplexer);
    }
}
