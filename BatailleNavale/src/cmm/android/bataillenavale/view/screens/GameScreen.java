package cmm.android.bataillenavale.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import cmm.android.bataillenavale.BatailleNavale;
import cmm.android.bataillenavale.controlers.GameListener;
import cmm.android.bataillenavale.modele.Mer;
import cmm.android.bataillenavale.utils.CmmScreenAdapter;
import cmm.android.bataillenavale.view.graphics.GraphicMer;

public abstract class GameScreen extends CmmScreenAdapter {
	protected GraphicMer graphicJoueur;
	protected GraphicMer graphicAdversaire;
	
	public GameScreen(BatailleNavale app) {
		super(app);
	}

	public void setJoueur(Mer joueur) {
		graphicJoueur = new GraphicMer(joueur, true);
	}
	
	public void setAdversaire(Mer adv) {
		graphicAdversaire = new GraphicMer(adv, false);
	}

	@Override
	public void initialize() {
		super.initialize();
		
		/* ***** Création du wallpaper ***** */
		Texture wallText = new Texture("data/img/gameWallpaper.jpg");
		setWallpaper(new TextureRegion(wallText, 349, 496));
		textures.add(wallText);

		/* ***** Création de la texture pour le bateau ***** */
		Texture shipText = new Texture("data/img/ship.png");
		textures.add(shipText);
		TextureRegion shipTextReg = new TextureRegion(shipText);
		GraphicMer.initialize(this, shipTextReg);

		/* ***** place les graphicMer en tant que sprite à afficher ***** */
		float sp = getScreenProportion();
		graphicJoueur.setSize(0.5f * sp, 0.5f);
		graphicJoueur.setPosition(-graphicJoueur.getWidth()/2, -0.5f);
		graphicAdversaire.setSize(0.5f * sp, 0.5f);
		graphicAdversaire.setPosition(-graphicAdversaire.getWidth()/2, 0.5f - graphicAdversaire.getHeight());
		sprites.add(graphicJoueur);
		sprites.add(graphicAdversaire);

		/* ***** gestion du controleur ***** */
		Gdx.input.setInputProcessor(new GameListener(this));
	}

	public GraphicMer getGraphicJoueur() {
		return graphicJoueur;
	}

	public GraphicMer getGraphicAdversaire() {
		return graphicAdversaire;
	}
	
	public abstract boolean adversairePlay();
}
