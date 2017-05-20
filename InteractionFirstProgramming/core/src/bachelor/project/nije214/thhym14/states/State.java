package bachelor.project.nije214.thhym14.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import static bachelor.project.nije214.thhym14.StaticGlobalVariables.HEIGHT;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.WIDTH;

/**
 * Authors:
 * Nicolai Hedegaard Jensen <nije214@student.sdu.dk>
 * Thor Skou Hymøller <thhym14@student.sdu.dk>
 */

public abstract class State {
    protected OrthographicCamera camera;
    protected  GameStateManager gsm;

    public State(GameStateManager gsm){
        this.gsm = gsm;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH, HEIGHT);
        camera.update();
    }

    public abstract void handleInput();
    public abstract void update(float deltaTime);
    public abstract void render(SpriteBatch sb);
    public abstract void dispose();
}


