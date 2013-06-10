package cmm.android.bataillenavale.utils;

import java.util.ArrayList;


import cmm.android.bataillenavale.BatailleNavale;
import cmm.android.bataillenavale.modele.Coord2F;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

/**
 * Classe qui réimplémente certaines méthodes de la classe Screen. Cette classe est un helper gérer correctement l'affichage de tous les sprites qui la composent
 * De plus, elle permet d'appeller dispose() sur toutes les Texture qui la composent.
 * Elle permet enfin de réaliser des calculs (ainsi que de mémoriser certains résultats) puisqu'elle contient des Singletons sur la OrthographicCamera et le SpriteBatch
 * Par exemple, elle mémorise la proportion width/height de l'écran sur lequel on lance l'application, afin de ne pas avoir à la calculer à chaque appelle des fonctions render().
 * 
 *  Globalement, chaque classe héritant de cette classe doit:
 *  	Réimplémenter initialize() afin de placer en mémoire les Textures. 
 *  		Si vous souhaitez qu'une texture soit supprimé automatiquement lors des appels à hide() et dispose(), vous devez l'ajouterdans l'ArrayList textures (fortement conseillé)
 *  		Si vous souhaite qu'un sprite soit affiché automatique lors des appels à render(), il faut l'ajouter dans l'ArrayList sprites (fortement conseillé)
 *  	Réimplémenter render(), en affichant tout ce qui ne fait pas partit des Sprite de l'ArrayList sprites
 *  	Réimplémenter dispose(), en appelant dispose() sur toutes les Texture  qui ne sont pas ajoutés dans l'ArrayList textures.
 *  
 *  	Cette classe permet donc d'appeler les bonnes méthodes en gérant le cycle de vie android. Les méthodes tmpSave et tmpLoad permettent respectivement de sauver et charger la mémoire dans un fichier afin d'être sûr que les données ne sont pas supprimé lorsque votre appication est mis en pause (voir cycle de vie android).
 * @author Jonathan GEOFFROY, Samy CHAYEM
 * @version 1.0
 */
public abstract class CmmScreenAdapter implements Screen {
	protected BatailleNavale app;
	private Sprite wallpaper;
	protected ArrayList<Sprite> sprites;
	protected ArrayList<Texture> textures;
	protected ArrayList<Sound> sounds;
	private boolean continousRendering;
	protected static SpriteBatch spriteBatch;
	protected static OrthographicCamera camera;
	private static float screenProportion;
	private static final Vector3 touchPos = new Vector3();
	private static final Coord2F coord = new Coord2F();
	
	public CmmScreenAdapter(BatailleNavale app, boolean continousRendering) {
		super();
		assert(spriteBatch != null):  "The SpriteBatch isn't initialized !";
		assert(camera != null):  "The OrthographicCamera isn't initialized !";
		this.app = app;
		this.continousRendering = continousRendering; 
		float h = Gdx.graphics.getHeight();
		float w = Gdx.graphics.getWidth();
		screenProportion = h/w;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();

		/* ***** On affiche le fond d'écran ***** */
		if(wallpaper != null) {
			wallpaper.draw(spriteBatch);
		}
		
		/* ***** on affiche tous les sprites ***** */
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
		sprites.clear();
		
		for(Sound s : sounds) {
			s.dispose();
		}
		sounds.clear();
	}
	
	/**
	 * Permet d'initialiser les ArrayList contenant les textures, sprites et sounds
	 */
	public void initialize() {
		sprites = new ArrayList<Sprite>();
		textures = new ArrayList<Texture>();
		sounds = new ArrayList<Sound>();
		Gdx.graphics.setContinuousRendering(continousRendering); 
	}
	
	/**
	 * Permet aux superclasses de redéfinir ce qui est placé en mémoire.
	 * Par défaut, rien n'est placé en mémoire, puisqu'on libére toute la mémoire, avant de la recharger ensuite
	 * @return true si on a utilisé la sauvegarde temporaire
	 */
	public boolean tmpSave() { return false; }
	/**
	 * Permet aux superclasses de recharger ce qui a été placé précédemment en mémoire cache
	 * @return true si le chargement a été effectué convenablement
	 */
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

	/**
	 * Permet d'ajouter un sprite, qui sera ensuite affiché automatiquement
	 * @param sprite le Sprite à ajouter
	 */
	public void addSprite(Sprite sprite) {
		assert(!sprites.contains(sprite));
		sprites.add(sprite);
	}
	/**
	 * permet d'ajouter une Texture, qui sera ensuite géré automatiquement
	 * @param text
	 */
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
	
	/**
	 * Permet de calculer les données en float, a partir du pixel de l'écran
	 * @param x le pixel en abscisse
	 * @param y le pixel en ordonnée
	 * @return les coordonnées passées en paramètre, converti en float en fonction de la caméra.
	 */
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

	public void addSoundAt(int index, Sound sound) {
		sounds.add(index, sound);
	}

	public boolean addSound(Sound sound) {
		return sounds.add(sound);
	}
}
