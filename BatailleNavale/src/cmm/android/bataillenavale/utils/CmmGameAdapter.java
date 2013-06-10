package cmm.android.bataillenavale.utils;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * helper redéfinissant les méthodes de la classe Game
 * Le but est de faciliter l'utilisation du cycle de vie Android, ainsi que d'automatiser la gestion de ressource, en supprimant les textures au changement de Screen.
 * Globalement, les Textures à supprimer au changement de Screen doivent être ajoutées à l'ArrayList screens;
 * ceux à garder aux fils des Screens sont doivent être ajoutées à la HashMap transversalTextures.
 * @author Jonathan GEOFFROY, Samy CHAYEM
 */
public class CmmGameAdapter extends Game {

	protected OrthographicCamera camera;
	protected SpriteBatch batch;
	protected ArrayList<Screen> screens;
	protected HashMap<String, Texture> transversalTextures;
	
	@Override
	public void create() {
		/* ***** Initialisation de la camera et du batch ***** */
		camera = new OrthographicCamera(1, 1);
		batch = new SpriteBatch();
		
		CmmScreenAdapter.setSpriteBatch(batch);
		CmmScreenAdapter.setCamera(camera);
		
		screens = new ArrayList<Screen>();
		transversalTextures = new HashMap<String, Texture>();
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause() {}
	@Override
	public void resume() {}
	
	public void setScreen(int screenType) {
		assert(screens.size() < screenType): "Il n'y a pas de screen n°" + screenType;
		setScreen(screens.get(screenType));
	}
	
	public CmmScreenAdapter getScreen(int screenType) {
		return (CmmScreenAdapter)screens.get(screenType);
	}
	
	public void putTransversalTexture(String name, Texture t) {
		transversalTextures.put(name, t);
	}
	public Texture getTransversaleTexture(String key) {
		return transversalTextures.get(key);
	}
	public void disposeTransversalTextures() {
		for(String key: transversalTextures.keySet()) {
			transversalTextures.get(key).dispose();
		}
		transversalTextures.clear();
	}
}
