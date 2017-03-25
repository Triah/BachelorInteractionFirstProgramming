package bachelor.project.nije214.thhym14;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import bachelor.project.nije214.thhym14.states.GameStateManager;
import bachelor.project.nije214.thhym14.states.GameTypeMenuState;

public class InteractionFirstProgramming extends Game {
	private SpriteBatch batch;
	private Texture img;
	private GameStateManager gsm;

	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		gsm.push(new GameTypeMenuState(gsm));
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		//img.dispose();
		gsm.pop();
	}
}
