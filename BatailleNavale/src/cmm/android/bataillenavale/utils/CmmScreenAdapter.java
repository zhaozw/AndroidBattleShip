package cmm.android.bataillenavale.utils;

import java.util.ArrayList;


import cmm.android.bataillenavale.BatailleNavale;
import cmm.android.bataillenavale.modele.Coord2F;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

public abstract class CmmScreenAdapter implements Screen {
	protected BatailleNavale app;
	private Sprite wallpaper;
	protected ArrayList<Sprite> sprites;
	protected ArrayList<Texture> textures;
	protected static SpriteBatch spriteBatch;
	protected static OrthographicCamera camera;
	private static float screenProportion;
	private static final Vector3 touchPos = new Vector3();
	private static final Coord2F coord = new Coord2F();
	
	public CmmScreenAdapter(BatailleNavale app) {
		super();
		assert(spriteBatch != null):  "The SpriteBatch isn't initialized !";
		assert(camera != null):  "The OrthographicCamera isn't initialized !";
		this.app = app;
		float h = Gdx.graphics.getHeight();
		float w = Gdx.graphics.getWidth();
		screenProportion = h/w;
		Gdx.app.log("h/w", ""+screenProportion);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();

		if(wallpaper != null) {
			wallpaper.draw(spriteBatch);
		}
		for(Sprite s: sprites) {
			s.draw(spriteBatch);
		}
		
		spriteBatch.end();
	}

	@Override
	public void resize(int width, int height) {
		Gdx.app.log("CmmScreenAdapter", getClass() + ": on_resize");
//		screenProportion = Gdx.graphics.getHeight() / Gdx.graphics.getWidth();
	}

	@Override
	public void show() {
	
		Gdx.app.log("CmmScreenAdapter", getClass() + ": on_show");
		initialize();
		tmpLoad();
	}

	@Override
	public void hide() {
		Gdx.app.log("CmmScreenAdapter", getClass() + ": on_hide");
		tmpSave();
		dispose();
	}

	@Override
	public void pause() {
		Gdx.app.log("CmmScreenAdapter", getClass() + ": on_pause");
		tmpSave();
		dispose();
	}

	@Override
	public void resume() {
		Gdx.app.log("CmmScreenAdapter", getClass() + ": on_resume");
		initialize();
		tmpLoad();
	}

	@Override
	public void dispose() {
		Gdx.app.log("CmmScreenAdapter", getClass() + ": on_dispose");
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
	
	public static void setSpriteBatch(SpriteBatch sb) {
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

	public void addSprite(Sprite sprite) {
		assert(!sprites.contains(sprite));
		sprites.add(sprite);
	}
	public void addTexture(Texture text) {
		assert(!textures.contains(text));
		textures.add(text);
	}
	
	public BatailleNavale getApp() {
		return app;
	}

	public void setApp(BatailleNavale app) {
		this.app = app;
	}

	public static Camera getCamera() {
		return camera;
	}
	
	public static void setCamera(OrthographicCamera cam) {
		camera = cam;
	}
	
	public static Coord2F intToFloatCoord(int x, int y) {
		touchPos.set(x, y, 0);
		camera.unproject(touchPos);
		coord.x = touchPos.x;
		coord.y = touchPos.y;
		return coord;
	}
	
	public static float getScreenProportion() {
		return screenProportion;
	}
}
