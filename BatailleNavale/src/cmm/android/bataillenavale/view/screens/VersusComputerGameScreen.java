package cmm.android.bataillenavale.view.screens;

import com.badlogic.gdx.Gdx;

import cmm.android.bataillenavale.BatailleNavale;
import cmm.android.bataillenavale.controlers.GameListener;
import cmm.android.bataillenavale.modele.Bateau;
import cmm.android.bataillenavale.modele.Coord2D;
import cmm.android.bataillenavale.modele.Mer;
import cmm.android.bataillenavale.modele.ia.IntelligenceArtificielle;

/**
 * Screen permettant de jouer contre l'ordinateur
 * @author Jonathan GEOFFROY, Samy CHAYEM
 * @version 1.0
 */
public class VersusComputerGameScreen extends GameScreen {
	private float adversaireTimer;
	private IntelligenceArtificielle ia;
	
	public VersusComputerGameScreen(BatailleNavale app) {
		super(app, true);
		adversaireTimer = 0;
	}

	@Override
	public void initialize() {
		super.initialize();
		/* ***** place les bateaux au hasard sur la mer de l'adversaire (i.e. l'ordinateur) ***** */
		boolean horizontal;
		int x, y;
		int i = 0;

		Mer computerMer = graphicAdversaire.getMer();
		while(computerMer.getNbBoats() < Bateau.NB_BOATS) {
			horizontal = Math.random() >= 0.5;
			if(horizontal) {
				x = (int)(Math.random() * (Mer.ARRAY_SIZE - i));
				y = (int)(Math.random() * Mer.ARRAY_SIZE);
			}
			else {
				x = (int)(Math.random() * Mer.ARRAY_SIZE);
				y = (int)(Math.random() * (Mer.ARRAY_SIZE - i));
			}
			if(graphicAdversaire.addBateauAt(new Bateau(Mer.TAILLE_BATEAUX[i]), x, y, horizontal)) {
				i++;
			}
		}
		
		super.initialize();
		ia.setJoueur(graphicJoueur.getMer());
		
		/* ***** gestion du controleur ***** */
		Gdx.input.setInputProcessor(new GameListener(this));
	}

	public void adversairePlay() {
		Coord2D coord = ia.adversairePlay();
		tirer(coord);
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		if(!playerTurn) {
			adversaireTimer += delta;
			if(adversaireTimer > 1) {
				adversairePlay();
				playerTurn = true; //C'est au joueur de jouer.
				adversaireTimer = 0;
			}
		}
	}

	public IntelligenceArtificielle getIa() {
		return ia;
	}

	public void setIa(IntelligenceArtificielle ia) {
		this.ia = ia;
	}
}
