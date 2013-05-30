package cmm.android.bataillenavale.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;

import cmm.android.bataillenavale.BatailleNavale;
import cmm.android.bataillenavale.controlers.GameListener;
import cmm.android.bataillenavale.modele.Coord2D;
import cmm.android.bataillenavale.modele.Mer;
import cmm.android.bataillenavale.utils.ChangeScreenOnTouchListener;
import cmm.android.bataillenavale.utils.CmmScreenAdapter;
import cmm.android.bataillenavale.view.graphics.General;
import cmm.android.bataillenavale.view.graphics.GraphicMer;

/**
 * Screen principale permettant de jouer à la bataille navale.
 * @author Jonathan GEOFFROY, Samy CHAYEM
 * @version 2.0
 */
public abstract class GameScreen extends CmmScreenAdapter {
	protected GraphicMer graphicJoueur;
	protected GraphicMer graphicAdversaire;
	private Sound missedSound, touchedSound;
	protected boolean playerTurn;
	protected boolean isOverGame;
	private BitmapFont font;
	private String messageFin;

	public GameScreen(BatailleNavale app) {
		super(app, false);
		isOverGame = false;
		playerTurn = Math.random() > 0.5;
	}
	public GameScreen(BatailleNavale app, boolean autoRender) {
		super(app, autoRender);
		isOverGame = false;
		playerTurn = Math.random() > 0.5;
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

		/* ***** Création de la mer de l'adversaire ***** */
		Mer computerMer = new Mer();
		graphicAdversaire = new GraphicMer(this, computerMer, false);

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


		/* ***** place les sons en mémoire ***** */
		touchedSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/touched.mp3"));
		missedSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/missed.mp3"));
		sounds.add(touchedSound);
		sounds.add(missedSound);

		/* ***** place la police en mémoire ***** */
		font = new BitmapFont(Gdx.files.internal("data/fonts/mainMenu.fnt"), Gdx.files.internal("data/fonts/mainMenu.png"), false);
		font.setUseIntegerPositions(false);
		font.setScale(1.4f / Gdx.graphics.getHeight());
		font.setColor(1, 1, 1, 1);
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		if(isOverGame) {
			font.setColor((float)Math.random(), (float)Math.random(), (float)Math.random(), 1.0f);
			TextBounds bounds = font.getBounds(messageFin);
			spriteBatch.begin();			
			font.drawMultiLine(spriteBatch, messageFin, - bounds.width/2, -bounds.height/2);
			spriteBatch.end();
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		GraphicMer.dispose();
		font.dispose();
	}


	public GraphicMer getGraphicJoueur() {
		return graphicJoueur;
	}

	public GraphicMer getGraphicAdversaire() {
		return graphicAdversaire;
	}

	public void setIsOverGame() {
		isOverGame = true;
		graphicAdversaire.setBoatsVisible(true);
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

	public boolean isPlayerTurn() {
		return playerTurn;
	}

	public void setPlayerTurn(boolean b) {
		playerTurn = b;		
	}

	public void switchPlayerTurn() {
		playerTurn = !playerTurn;
	}

	public boolean tirer(Coord2D touchedCase) {
		GraphicMer tireur, adv;
		boolean touche = false;

		/* ***** On trouve qui tire, qui va subir ***** */
		if(playerTurn) {
			tireur = graphicJoueur;
			adv = graphicAdversaire;
		}
		else {
			tireur = graphicAdversaire;
			adv = graphicJoueur;
		}

		/* ***** on simule le tir ***** */
		touche = adv.getMer().tirer(touchedCase.x, touchedCase.y);
		tirer(touche, tireur, adv);

		return touche;
	}

	public void tirer(boolean touched, GraphicMer tireur, GraphicMer adv) {
		if(touched) {
			touchedSound.play();
			tireur.getGeneral().setStatus(General.HAPPY);
			adv.getGeneral().setStatus(General.UNHAPPY);
			if(adv.getMer().aPerdu()) {
				Gdx.app.log("jeu", "gagné!");
				isOverGame = true;
				graphicAdversaire.setBoatsVisible(true);

				if(playerTurn) {
					messageFin = "Vous avez Gagné!\n Bravo!";
				}
				else {
					messageFin = "Vous avez Perdu!\n Désolé ...";
				}
				Gdx.input.setInputProcessor(new ChangeScreenOnTouchListener(this.app.getScreen(BatailleNavale.MAIN_MENU)));
			}
			playerTurn = !playerTurn;
		}
		else {
			missedSound.play();
			tireur.getGeneral().setStatus(General.CLASSIC);
			adv.getGeneral().setStatus(General.CLASSIC);
			playerTurn = !playerTurn;
		}
	}
}
