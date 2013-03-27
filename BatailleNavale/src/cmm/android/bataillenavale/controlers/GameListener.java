package cmm.android.bataillenavale.controlers;

import cmm.android.bataillenavale.modele.Coord2D;
import cmm.android.bataillenavale.modele.Coord2F;
import cmm.android.bataillenavale.utils.CmmScreenAdapter;
import cmm.android.bataillenavale.view.graphics.GraphicMer;
import cmm.android.bataillenavale.view.screens.GameScreen;

import com.badlogic.gdx.InputAdapter;

/**
 * Controleur permettant de gérer les clic sur la mer adverse afin de simuler un tir.
 * @author Jonathan GEOFFROY, Samy CHAYEM
 * @version 2.0
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
		if(jeu.isPlayerTurn() && adv.touchSea(click.x, click.y)) {
			Coord2D touchedCase = adv.getCaseAt(click.x, click.y);
			jeu.tirer(touchedCase);
			return true;
		}
		return false;
	}
}
