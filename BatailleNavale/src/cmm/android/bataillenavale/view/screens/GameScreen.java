package cmm.android.bataillenavale.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import cmm.android.bataillenavale.BatailleNavale;
import cmm.android.bataillenavale.controlers.GameListener;
import cmm.android.bataillenavale.modele.Mer;
import cmm.android.bataillenavale.utils.ChangeScreenOnTouchListener;
import cmm.android.bataillenavale.utils.CmmScreenAdapter;
import cmm.android.bataillenavale.view.graphics.GraphicMer;

/**
 * Screen principale permettant de jouer à la bataille navale.
 * @author Jonathan GEOFFROY, Samy CHAYEM
 * @version 2.0
 */
public abstract class GameScreen extends CmmScreenAdapter {
	protected GraphicMer graphicJoueur;
	protected GraphicMer graphicAdversaire;
	protected boolean isOverGame;

	public GameScreen(BatailleNavale app) {
		super(app, false);
		isOverGame = false;
	}

	public void setJoueur(Mer joueur) {
		graphicJoueur = new GraphicMer(this, joueur, true);
	}

	public void setAdversaire(Mer adv) {
		graphicAdversaire = new GraphicMer(this, adv, false);
	}

	@Override
	public void initialize() {
		super.initialize();
		isOverGame = false;
		
		/* ***** Création du wallpaper ***** */
		Texture wallText = app.getTransversaleTexture("wallpaper");
		setWallpaper(new TextureRegion(wallText, 349, 496));
		textures.add(wallText);

		/* ***** Création de la texture pour le bateau ***** */
		Texture shipText = new Texture("data/img/ship.png");
		textures.add(shipText);
		TextureRegion shipTextReg = new TextureRegion(shipText, 803, 198);
		GraphicMer.initialize(this, shipTextReg);

		/* ***** place les graphicMer en tant que sprite à afficher ***** */
		graphicJoueur.setSize(1.0f, 0.5f);
		graphicJoueur.setPosition(-0.5f, -0.5f); //On place le joueur à gauche
		sprites.add(graphicJoueur);
		
		graphicAdversaire.setSize(1.0f, 0.5f);
		graphicAdversaire.setPosition(0.5f - graphicAdversaire.getWidth(), 0.5f - graphicAdversaire.getHeight()); //On place l'adversaire à droite
		sprites.add(graphicAdversaire);

		/* ***** gestion du controleur ***** */
		Gdx.input.setInputProcessor(new GameListener(this));
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		if(isOverGame) {
			//TODO afficher "vous avez gagné", ou "vous avez perdu"
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		GraphicMer.dispose();
	}

	public abstract boolean adversairePlay();

	public GraphicMer getGraphicJoueur() {
		return graphicJoueur;
	}

	public GraphicMer getGraphicAdversaire() {
		return graphicAdversaire;
	}

	public void setIsOverGame() {
		isOverGame = true;
		Gdx.input.setInputProcessor(new ChangeScreenOnTouchListener(getApp().getScreen(BatailleNavale.MAIN_MENU)) {
			@Override
			/**
			 * Supprime les Textures transversales
			 * Cela correspond aux Textures initialisées et utilisés dans plusieurs Screen.
			 */
			public void onChangeScreen() {
				super.onChangeScreen();
				GameScreen.this.getApp().disposeTransversalTextures();
			}
		});
	}

	public boolean isOverGame() {
		return isOverGame;
	}
}
