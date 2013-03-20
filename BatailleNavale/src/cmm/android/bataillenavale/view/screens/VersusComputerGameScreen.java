package cmm.android.bataillenavale.view.screens;

import com.badlogic.gdx.Gdx;

import cmm.android.bataillenavale.BatailleNavale;
import cmm.android.bataillenavale.modele.Bateau;
import cmm.android.bataillenavale.modele.Mer;
import cmm.android.bataillenavale.view.graphics.General;
import cmm.android.bataillenavale.view.graphics.GraphicMer;

/**
 * Screen permettant de jouer contre l'ordinateur
 * @author Jonathan GEOFFROY, Samy CHAYEM
 * @version 1.0
 */
public class VersusComputerGameScreen extends GameScreen {
	public VersusComputerGameScreen(BatailleNavale app) {
		super(app);
	}

	@Override
	public void initialize() {
		/* ***** place les bateaux au hasard sur la mer de l'adversaire (i.e. l'ordinateur) ***** */
		boolean horizontal;
		int x, y;
		int i = 0;
		Mer computerMer = new Mer();
		graphicAdversaire = new GraphicMer(this, computerMer, false);

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
			if(computerMer.addBateauAt(new Bateau(Mer.TAILLE_BATEAUX[i]), x, y, horizontal))
				i++;
		}
		
		super.initialize();
	}

	@Override
	public boolean adversairePlay() {
		int x = (int)(Math.random() * Mer.ARRAY_SIZE);
		int y = (int)(Math.random() * Mer.ARRAY_SIZE);

		Gdx.app.log("game", "computer joue: " + x + "-" + y);
		
		boolean touched = graphicJoueur.getMer().tirer(x, y);		
		if(touched) {
			Gdx.app.log("game", "touché!");
			getGraphicAdversaire().getGeneral().setStatus(General.HAPPY);
			getGraphicJoueur().getGeneral().setStatus(General.UNHAPPY);
		}
		else {
			Gdx.app.log("game", "dans l'eau!");
			getGraphicAdversaire().getGeneral().setStatus(General.CLASSIC);
			getGraphicJoueur().getGeneral().setStatus(General.CLASSIC);
		}
		return touched;
	}
}
