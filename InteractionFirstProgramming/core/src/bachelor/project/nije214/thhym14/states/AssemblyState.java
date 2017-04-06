package bachelor.project.nije214.thhym14.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.Dictionary;
import java.util.LinkedList;

import bachelor.project.nije214.thhym14.Enemy;

import static bachelor.project.nije214.thhym14.StaticGlobalVariables.HEIGHT;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.WIDTH;


/**
 * Created by Nicolai on 21-03-2017.
 */

public class AssemblyState extends State {

    private Vector3 touchPoint;
    private LinkedList<Texture> textures;
    private LinkedList<Sprite> sprites;
    private Enemy enemy;
    private static float lowSpeed = 50;
    private static float mediumSpeed = 120;
    private static float highSpeed = 300;

    public AssemblyState(GameStateManager gsm) {
        super(gsm);
        textures = new LinkedList<Texture>();
        sprites = new LinkedList<Sprite>();
        touchPoint = new Vector3();
        camera.setToOrtho(false, WIDTH, HEIGHT);
        camera.update();
        enemy = new Enemy();
        create();
    }

    public void addTexturesToList(String source) {
        textures.add(new Texture(source));
    }

    public void create() {
        addTexturesToList("enemyimage.PNG");
        addTexturesToList("mapimage.PNG");
        addTexturesToList("towerimage.PNG");
        addTexturesToList("enemyimage.PNG");

        //must always come after creation of all textures
        createSprites();
        setSpriteSizeAndPosition();
    }

    public void setSpriteSizeAndPosition(){
        for (int i = 0; i < sprites.size(); i++) {
            sprites.get(i).setPosition(100, HEIGHT - (i * 200) - 500);
            sprites.get(i).setSize(250,100);
        }
    }



    public void createSprites() {
        for (Texture texture : textures) {
            sprites.add(new Sprite(texture));
        }
    }

    public void setSpeed(float speed){
        this.enemy.setSpeed(speed);
    }

    public void clearSprites() {
        sprites.clear();
        textures.clear();
    }

    public void setEnemyScreen(){
        clearSprites();
        addTexturesToList("mapimage.PNG");
        addTexturesToList("enemyimage.PNG");
        addTexturesToList("towerimage.PNG");
        createSprites();
        setSpriteSizeAndPosition();
    }


    public void setEnemyAttributes(Enemy enemy){
        this.enemy = enemy;
        enemy.createEnemy();
        for (int i = 0; i<sprites.size(); i++) {
            if(sprites.get(i).getBoundingRectangle().contains(getTouchPoint().x,getTouchPoint().y)){
                if(i==0) {
                    setSpeed(lowSpeed);
                    sprites.get(i).setPosition(sprites.get(i).getX()+500,sprites.get(i).getY());
                } else if(i==1) {
                    setSpeed(mediumSpeed);
                    sprites.get(i).setPosition(sprites.get(i).getX()+500,sprites.get(i).getY());
                } else if(i==2) {
                    setSpeed(highSpeed);
                    sprites.get(i).setPosition(sprites.get(i).getX()+500,sprites.get(i).getY());
                }
            }

        }
    }

    public Enemy getEnemy(){
        return enemy;
    }

    public Vector3 getTouchPoint() {
        return touchPoint;
    }

    public void setTouchPoint() {
        if (Gdx.input.justTouched()) {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
        }
    }

    @Override
    public void handleInput() {
        setTouchPoint();
        if (sprites.get(0).getBoundingRectangle().contains(touchPoint.x, touchPoint.y)) {
                setEnemyScreen();
        }
    }

    @Override
    public void update(float deltaTime) {
        handleInput();
        setEnemyAttributes(enemy);
        System.out.println(enemy.getSpeed());
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        for (int i = 0; i < sprites.size(); i++) {
            sb.draw(sprites.get(i),
                    sprites.get(i).getX(),
                    sprites.get(i).getY(),
                    sprites.get(i).getWidth(),
                    sprites.get(i).getHeight());
        }
        sb.end();
    }

    @Override
    public void dispose() {

    }
}