package bachelor.project.nije214.thhym14.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import bachelor.project.nije214.thhym14.Enemy;
import bachelor.project.nije214.thhym14.Waypoint;

import static bachelor.project.nije214.thhym14.StaticGlobalVariables.HEIGHT;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.WIDTH;

/**
 * Created by Nicolai on 21-03-2017.
 */

public class GameTypeMenuState extends State {

    private Texture backgroundTexture;
    private Sprite backgroundSprite;
    private Waypoint wp;
    private Enemy enemy;

    public GameTypeMenuState(GameStateManager gsm) {
        super(gsm);
        wp = new Waypoint();
        wp.show();
        enemy = new Enemy();
        camera.setToOrtho(false, WIDTH, HEIGHT);
        camera.update();
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        wp.render();
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
