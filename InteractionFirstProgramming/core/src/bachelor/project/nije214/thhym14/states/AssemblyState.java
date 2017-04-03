package bachelor.project.nije214.thhym14.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

import static bachelor.project.nije214.thhym14.StaticGlobalVariables.HEIGHT;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.WIDTH;


import java.util.LinkedList;


/**
 * Created by Nicolai on 21-03-2017.
 */

public class AssemblyState extends State {

    private LinkedList<Texture> textures;
    private LinkedList<Sprite> sprites;

    public AssemblyState(GameStateManager gsm) {
        super(gsm);
        textures = new LinkedList<Texture>();
        sprites = new LinkedList<Sprite>();
        create();
    }

    public void addTexturesToList(String source){
        textures.add(new Texture(source));
    }

    public void create(){
        addTexturesToList("enemyimage.PNG");
        addTexturesToList("mapimage.PNG");
        addTexturesToList("towerimage.PNG");
        addTexturesToList("enemyimage.PNG");

        //must always come after creation of all textures
        createSprites();

        //set position
        for(int i = 0; i<sprites.size();i++){
            if(i==0){
                sprites.get(i).setPosition(100, HEIGHT - 500);
                System.out.println(sprites.get(i).getX() + "   " + sprites.get(i).getY());
            } else{
                sprites.get(i).setPosition(100, HEIGHT - i*200 - 500);
                System.out.println(sprites.get(i).getX() + "   " + sprites.get(i).getY());
            }
        }
    }

    public void createSprites(){
        for(Texture texture : textures){
            sprites.add(new Sprite(texture));
        }
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        for(int i = 0; i < sprites.size(); i++){
            sb.draw(sprites.get(i),sprites.get(i).getX(),sprites.get(i).getY(),250,100);
        }
        sb.end();

    }

    @Override
    public void dispose() {

    }
}
