package cmm.android.bataillenavale.controlers;

import cmm.android.bataillenavale.BatailleNavale;
import cmm.android.bataillenavale.modele.Coord2D;
import cmm.android.bataillenavale.modele.Coord2F;
import cmm.android.bataillenavale.utils.CmmScreenAdapter;
import cmm.android.bataillenavale.view.graphics.GraphicMer;
import cmm.android.bataillenavale.view.screens.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;

/**
 * Controleur permettant de gérer les clic sur la mer adverse afin de simuler un tir.
 * @author Jonathan GEOFFROY, Samy CHAYEM
 * @version 1.0
 */
public class GameListener extends InputAdapter {
	private GameScreen jeu;

	public GameListener(GameScreen jeu) {
		this.jeu = jeu;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		GraphicMer adv = jeu.getGraphicAdversaire();
		Coord2F click = CmmScreenAdapter.intToFloatCoord(screenX, screenY);
		if(adv.getBoundingRectangle().contains(click.x, click.y)) {
			
			Coord2D touchedCase = adv.getCaseAt(click.x, click.y);
			if(adv.getMer().tirer(touchedCase.x, touchedCase.y)) {
				Gdx.app.log("tirer", "touché!");
				if(adv.getMer().aPerdu()) {
					Gdx.app.log("jeu", "Vous avez gagné!");
					jeu.getApp().setScreen(BatailleNavale.MAIN_MENU);
				}
			}
			else {
				Gdx.app.log("tirer", "dans l'eau!");
			}

			jeu.adversairePlay(); //puisqu'on a joué, c'est à l'adversaire!
			return true;
		}
		return false;
	}
}
