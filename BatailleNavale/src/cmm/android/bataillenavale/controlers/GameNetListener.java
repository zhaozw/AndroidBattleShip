package cmm.android.bataillenavale.controlers;
import cmm.android.bataillenavale.modele.Coord2D;
import cmm.android.bataillenavale.modele.Coord2F;
import cmm.android.bataillenavale.utils.CmmScreenAdapter;
import cmm.android.bataillenavale.view.graphics.GraphicMer;
import cmm.android.bataillenavale.view.screens.GameScreen;

public class GameNetListener extends GameListener  {

	public GameNetListener(GameScreen jeu) {
		super(jeu);
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		GraphicMer adv = jeu.getGraphicAdversaire();

		Coord2F click = CmmScreenAdapter.intToFloatCoord(screenX, screenY);
		if(jeu.isPlayerTurn() && adv.touchSea(click.x, click.y)) {
			Coord2D tirCase = adv.getCaseAt(click.x, click.y);
			jeu.getApp().getClient().sendTCP("" + tirCase.x + ":" + tirCase.y);
			return true;
		}
		return false;
	}


}
