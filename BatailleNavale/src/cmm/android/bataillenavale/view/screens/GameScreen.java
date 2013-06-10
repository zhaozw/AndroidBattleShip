package cmm.android.bataillenavale.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;

import cmm.android.bataillenavale.BatailleNavale;
import cmm.android.bataillenavale.modele.Coord2D;
import cmm.android.bataillenavale.modele.Mer;
import cmm.android.bataillenavale.utils.ChangeScreenOnTouchListener;
import cmm.android.bataillenavale.utils.CmmScreenAdapter;
import cmm.android.bataillenavale.view.graphics.General;
import cmm.android.bataillenavale.view.graphics.GraphicMer;

/**
 * Screen principale permettant de jouer à la bataille navale.
 * 
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
	private String message;

	public GameScreen(BatailleNavale app) {
		super(app, false);
		isOverGame = false;
	}

	public GameScreen(BatailleNavale app, boolean autoRender) {
		super(app, autoRender);
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

		/* ***** Création de la mer de l'adversaire ***** */
		Mer computerMer = new Mer();
		graphicAdversaire = new GraphicMer(this, computerMer, false);

		/* ***** Création du wallpaper ***** */
		Texture wallText = app.getTransversaleTexture("wallpaper");
		setWallpaper(new TextureRegion(wallText, 460, 400));
		textures.add(wallText);

		/* ***** Création de la texture pour le bateau ***** */
		Texture shipText = new Texture("data/img/ship.png");
		textures.add(shipText);
		TextureRegion shipTextReg = new TextureRegion(shipText, 803, 198);
		GraphicMer.initialize(this, shipTextReg);

		/* ***** place les graphicMer en tant que sprite à afficher ***** */
		graphicJoueur.setSize(1.0f, 0.5f);
		graphicJoueur.setPosition(-0.5f, -0.5f); // On place le joueur à gauche
		sprites.add(graphicJoueur);

		graphicAdversaire.setSize(1.0f, 0.5f);
		graphicAdversaire.setPosition(0.5f - graphicAdversaire.getWidth(),
				0.5f - graphicAdversaire.getHeight()); // On place l'adversaire à droite
		sprites.add(graphicAdversaire);

		/* ***** place les sons en mémoire ***** */
		touchedSound = Gdx.audio.newSound(Gdx.files
				.internal("data/sounds/touched.mp3"));
		missedSound = Gdx.audio.newSound(Gdx.files
				.internal("data/sounds/missed.mp3"));
		sounds.add(touchedSound);
		sounds.add(missedSound);

		/* ***** place la police en mémoire ***** */
		font = new BitmapFont(Gdx.files.internal("data/fonts/mainMenu.fnt"),
				Gdx.files.internal("data/fonts/mainMenu.png"), false);
		font.setUseIntegerPositions(false);
		font.setScale(1.4f / Gdx.graphics.getHeight());
		font.setColor(1, 1, 1, 1);
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		float x, y;
		if (isOverGame) {
			font.setColor((float) Math.random(), (float) Math.random(), (float) Math.random(), 1.0f);
			// On place au mileu
			TextBounds bounds = font.getBounds(message);
			x = - bounds.width / 2;
			y = bounds.height / 2;
		} else {
			if (playerTurn) {
				x = 0.5f - font.getBounds(message).width;
				y = -0.3f;
			} else {
				x = -0.5f;
				y = 0.3f;
			}
		}

		spriteBatch.begin();
		font.drawMultiLine(spriteBatch, message, x, y);
		spriteBatch.end();
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
		if(graphicJoueur.getMer().aPerdu()) {
			message = "Vous avez perdu ...";
		}
		else {
			message = "Vous avez gagné !!!";
		}

		isOverGame = true;
		graphicAdversaire.setBoatsVisible(true);
		Gdx.input.setInputProcessor(new ChangeScreenOnTouchListener(getApp()
				.getScreen(BatailleNavale.MAIN_MENU)) {
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

	public void setPlayerTurn(boolean playerTurn) {
		this.playerTurn = playerTurn;
		if (playerTurn) {
			message = "A vous!";
		} else {
			message = "A lui!";
		}
	}

	public void switchPlayerTurn() {
		setPlayerTurn(!playerTurn);
	}

	public boolean tirer(Coord2D touchedCase) {
		GraphicMer tireur, adv;
		boolean touche = false;

		/* ***** On trouve qui tire, qui va subir ***** */
		if (playerTurn) {
			tireur = graphicJoueur;
			adv = graphicAdversaire;
		} else {
			tireur = graphicAdversaire;
			adv = graphicJoueur;
		}

		/* ***** on simule le tir ***** */
		touche = adv.getMer().tirer(touchedCase.x, touchedCase.y);
		tirer(touche, tireur, adv);

		return touche;
	}

	public void tirer(boolean touched, GraphicMer tireur, GraphicMer adv) {
		if (touched) {
			if(app.isPlaySound())
				touchedSound.play();
			
			tireur.getGeneral().setStatus(General.HAPPY);
			adv.getGeneral().setStatus(General.UNHAPPY);
			if (adv.getMer().aPerdu()) {
				Gdx.app.log("jeu", "gagné!");
				setIsOverGame();
				graphicAdversaire.setBoatsVisible(true);

				Gdx.input.setInputProcessor(new ChangeScreenOnTouchListener(
						this.app.getScreen(BatailleNavale.MAIN_MENU)));
			}
			else {
				switchPlayerTurn();
			}
		} else {
			if(app.isPlaySound())
				missedSound.play();
			tireur.getGeneral().setStatus(General.CLASSIC);
			adv.getGeneral().setStatus(General.CLASSIC);
			switchPlayerTurn();
		}
	}
}
