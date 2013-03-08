package cmm.android.bataillenavale.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import cmm.android.bataillenavale.BatailleNavale;
import cmm.android.bataillenavale.controlers.PlaceBateauListener;
import cmm.android.bataillenavale.controlers.ShipChooserListener;
import cmm.android.bataillenavale.modele.Mer;
import cmm.android.bataillenavale.utils.CmmScreenAdapter;
import cmm.android.bataillenavale.view.graphics.GraphicMer;
import cmm.android.bataillenavale.view.graphics.ShipChooser;
//TODO cycle de vie!

public class PlaceBateauScreen extends CmmScreenAdapter {
	private GraphicMer graphicMer;
	private ShipChooser shipChooser;

	public PlaceBateauScreen(BatailleNavale app) {
		super(app);
	}

	@Override
	public void initialize() {
		super.initialize();		
		
		/* ***** Création du wallpaper ***** */
		Texture wallText = new Texture("data/img/gameWallpaper.jpg");
		setWallpaper(new TextureRegion(wallText, 349, 496));
		textures.add(wallText);
		
		/* ***** Création des textures ***** */
		Texture shipText = new Texture("data/img/ship.png");
		textures.add(shipText);
		TextureRegion shipTextReg = new TextureRegion(shipText);
		
		/* ***** initialisation de la mer graphique ***** */
		GraphicMer.initialize(this, shipTextReg);
		graphicMer = new GraphicMer(new Mer());
		float sp = getScreenProportion();
		graphicMer.setSize(0.5f, 0.5f / sp);
		graphicMer.setPosition(-graphicMer.getWidth()/2, -graphicMer.getHeight()/2);
		sprites.add(graphicMer);
		
		/* ***** Création du ShipChooser ***** */
		shipChooser = new ShipChooser(shipTextReg);
		shipChooser.setSize(1.f, 0.3f);
		shipChooser.setPosition(-0.5f, 0.2f);
		sprites.add(shipChooser);
		
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
