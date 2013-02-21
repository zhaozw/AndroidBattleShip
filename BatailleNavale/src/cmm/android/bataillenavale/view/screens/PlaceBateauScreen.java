package cmm.android.bataillenavale.view.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import cmm.android.bataillenavale.controlers.PlaceBateauListener;
import cmm.android.bataillenavale.controlers.ShipChooserListener;
import cmm.android.bataillenavale.modele.Bateau;
import cmm.android.bataillenavale.modele.Mer;
import cmm.android.bataillenavale.utils.CmmScreenAdapter;
import cmm.android.bataillenavale.view.graphics.GraphicMer;
import cmm.android.bataillenavale.view.graphics.ShipChooser;

public class PlaceBateauScreen extends CmmScreenAdapter {
	private GraphicMer graphicMer;
	private ShipChooser shipChooser;

	public PlaceBateauScreen(Game app) {
		super(app);
	}

	@Override
	public void initialize() {
		super.initialize();

		/* ***** Création du wallpaper ***** */
		Texture wallText = new Texture("data/img/gameWallpaper.jpg");
		setWallpaper(new TextureRegion(wallText));
		textures.add(wallText);

		/* ***** Création de la texture pour le bateau ***** */
		Texture bateauText = new Texture("data/img/boat.png");
		TextureRegion bateauTextReg = new TextureRegion(bateauText);
		textures.add(bateauText);

		float sizeCasePlateau = 0.5f / Mer.ARRAY_SIZE;
		Sprite bateau;
		for(int i = 0; i < Bateau.NB_BOATS; i++) {
			bateau = new Sprite(bateauTextReg);
			bateau.setSize(sizeCasePlateau * (i+1), sizeCasePlateau);
			bateau.setPosition(-0.5f, 0.5f - (i+1) * bateau.getHeight());
			sprites.add(bateau);
		}

		//TODO cycle de vie!
		graphicMer = new GraphicMer(new Mer());
		shipChooser = new ShipChooser(bateauTextReg);
		
		/* ***** Gestion du listener ***** */
		ShipChooserListener scl = new ShipChooserListener(shipChooser);
		PlaceBateauListener sbl = new PlaceBateauListener(this);
		InputMultiplexer multiplexer = new InputMultiplexer(scl, sbl);
		Gdx.input.setInputProcessor(multiplexer);
	}

	@Override
	public boolean tmpSave() {
		// TODO Auto-generated method stub
		return super.tmpSave();
	}

	@Override
	public boolean tmpLoad() {
		// TODO Auto-generated method stub
		return super.tmpLoad();
	}

	public GraphicMer getGraphicMer() {
		return graphicMer;
	}

	public ShipChooser getShipChooser() {
		return shipChooser;
	}	
}
