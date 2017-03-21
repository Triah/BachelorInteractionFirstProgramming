package bachelor.project.nije214.thhym14.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Nicolai on 21-03-2017.
 */

public class GameTypeMenuState extends State {

    private Texture backgroundTexture;
    private Sprite backgroundSprite;

    public GameTypeMenuState(GameStateManager gsm) {
        super(gsm);
    }

    private void loadTextures(){
        backgroundTexture = new Texture("badlogic.jpg");
        backgroundSprite = new Sprite(backgroundTexture);
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void render(SpriteBatch sb) {
        loadTextures();
        sb.begin();
        backgroundSprite.draw(sb);
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
