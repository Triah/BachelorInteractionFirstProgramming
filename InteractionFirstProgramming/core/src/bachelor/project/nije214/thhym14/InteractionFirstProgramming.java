package bachelor.project.nije214.thhym14;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import bachelor.project.nije214.thhym14.states.GameStateManager;
import bachelor.project.nije214.thhym14.states.GameTypeMenuState;
import bachelor.project.nije214.thhym14.states.PlayTowerDefenseState;

import static bachelor.project.nije214.thhym14.StaticGlobalVariables.HEIGHT;
import static bachelor.project.nije214.thhym14.StaticGlobalVariables.WIDTH;

public class InteractionFirstProgramming extends Game {
	private SpriteBatch batch;
	private GameStateManager gsm;
	public Music music;
	private Texture background;

	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		gsm.push(new GameTypeMenuState(gsm));
		music = Gdx.audio.newMusic(Gdx.files.internal("music/Harp.mp3"));
		music.setLooping(true);
		music.setVolume(0.5f);
		music.play();

	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
		if(gsm.peek() instanceof PlayTowerDefenseState){
			music.stop();
		} else {
			music.play();
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		music.dispose();
		gsm.pop();
	}
}
