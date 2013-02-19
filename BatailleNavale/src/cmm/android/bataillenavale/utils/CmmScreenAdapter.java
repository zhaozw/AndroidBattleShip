package cmm.android.bataillenavale.utils;

import java.util.ArrayList;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class CmmScreenAdapter implements Screen {
	private Game app;
	private Sprite wallpaper;
	protected ArrayList<Sprite> sprites;
	protected ArrayList<Texture> textures;
	protected static SpriteBatch spriteBatch;
	protected static OrthographicCamera camera;
	
	public CmmScreenAdapter(Game app) {
		super();
		assert(spriteBatch != null):  "The SpriteBatch isn't initialized !";
		this.app = app;
	}

	@Override
	public void render(float delta) {
		if(wallpaper != null) {
			wallpaper.draw(spriteBatch);
		}
		for(Sprite s: sprites) {
			s.draw(spriteBatch);
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void show() {
		initialize();
		tmpLoad();
	}

	@Override
	public void hide() {
		tmpSave();
		dispose();
	}

	@Override
	public void pause() {
		tmpSave();
		dispose();
	}

	@Override
	public void resume() {
		initialize();
		tmpLoad();
	}

	@Override
	public void dispose() {
		for(Texture t : textures) {
			t.dispose();
		}
		textures.clear();
	}
	
	public void initialize() {
		sprites = new ArrayList<Sprite>();
		textures = new ArrayList<Texture>();
	}
	
	public boolean tmpSave() { return false; }
	public boolean tmpLoad() { return false; }
	
	public static void getSpriteBatch(SpriteBatch sb) {
		spriteBatch = sb;
	}
	
	public void setWallpaper(TextureRegion wallpaperText) {
		if(wallpaper != null) {
			wallpaper.getTexture().dispose();
		}
		wallpaper = new Sprite(wallpaperText);
		wallpaper.setSize(1.f, 1.f * wallpaper.getHeight() / wallpaper.getWidth());
		wallpaper.setOrigin(wallpaper.getWidth()/2, wallpaper.getHeight()/2);
		wallpaper.setPosition(-wallpaper.getWidth()/2, -wallpaper.getHeight()/2);
	}

	public void addGraphicComponent(Sprite sprite) {
		assert(!sprites.contains(sprite));
		sprites.add(sprite);
	}
	
	public Game getApp() {
		return app;
	}

	public void setApp(Game app) {
		this.app = app;
	}

	public static Camera getCamera() {
		return camera;
	}
}
