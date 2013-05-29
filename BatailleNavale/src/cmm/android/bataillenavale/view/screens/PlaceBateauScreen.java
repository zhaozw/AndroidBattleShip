package cmm.android.bataillenavale.view.screens;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import cmm.android.bataillenavale.BatailleNavale;
import cmm.android.bataillenavale.controlers.PlaceBateauListener;
import cmm.android.bataillenavale.controlers.ShipChooserListener;
import cmm.android.bataillenavale.modele.Mer;
import cmm.android.bataillenavale.utils.CmmScreenAdapter;
import cmm.android.bataillenavale.view.graphics.GraphicMer;
import cmm.android.bataillenavale.view.graphics.ShipChooser;


/**
 * Screen permettant de placer ses bateaux avant de pouvoir jouer contre un adversaire (humain ou ordinateur).
 * @author Jonathan GEOFFROY, Samy CHAYEM
 * @version 1.0
 */
public class PlaceBateauScreen extends CmmScreenAdapter {
	protected GraphicMer graphicMer;
	protected ShipChooser shipChooser;
	public static final String TMP_SAVE = "./tmp";

	public PlaceBateauScreen(BatailleNavale app) {
		super(app, true);
	}

	@Override
	public void initialize() {
		super.initialize();		

		/* ***** Création du wallpaper ***** */
		Texture wallText = new Texture("data/img/gameWallpaper.jpg");
		setWallpaper(new TextureRegion(wallText, 349, 496));
		app.putTransversalTexture("wallpaper", wallText);

		/* ***** Création des textures ***** */
		Texture shipText = new Texture("data/img/ship.png");
		app.putTransversalTexture("ship", shipText);
		TextureRegion shipTextReg = new TextureRegion(shipText, 803, 198);

		/* ***** initialisation de la mer graphique ***** */
		GraphicMer.initialize(this, shipTextReg);
		graphicMer = new GraphicMer(this, new Mer(), true);
		graphicMer.setSize(1.0f, 0.5f);
		graphicMer.setPosition(-0.5f, -0.5f);
		sprites.add(graphicMer);

		/* ***** Création du ShipChooser ***** */
		shipChooser = new ShipChooser(this, shipTextReg);
		shipChooser.setSize(1.f, 0.3f);
		shipChooser.setPosition(-0.5f, 0.2f);
		sprites.add(shipChooser);

		/* ***** Gestion du listener ***** */
		putListener();
	}

	protected void putListener() {
		ShipChooserListener scl = new ShipChooserListener(shipChooser);
		PlaceBateauListener sbl = new PlaceBateauListener(this);
		InputMultiplexer multiplexer = new InputMultiplexer(sbl, scl);
		Gdx.input.setInputProcessor(multiplexer);		
	}

	@Override
	public boolean tmpSave() {
		try {
			FileHandle handle = Gdx.files.internal(TMP_SAVE);
			ObjectOutputStream save = new ObjectOutputStream(new FileOutputStream(handle.file()));
			save.writeObject(graphicMer.getMer());
			save.close();
		} catch (IOException e) {
			return false;
		}

		return true;
	}

	@Override
	public boolean tmpLoad() {
//		try {
//			FileHandle handle = Gdx.files.internal(TMP_SAVE);
//			ObjectInputStream load = new ObjectInputStream(new FileInputStream(handle.file()));
//			Mer mer = (Mer)load.readObject();
//			graphicMer = new GraphicMer(this, mer, true);
//			load.close();
//		}catch (IOException e) {
//			return false;
//		} catch (ClassNotFoundException e) {
//			return false;
//		}

		return true;
	}

	public GraphicMer getGraphicMer() {
		return graphicMer;
	}

	public ShipChooser getShipChooser() {
		return shipChooser;
	}	
}
