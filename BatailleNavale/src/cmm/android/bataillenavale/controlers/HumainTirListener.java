package cmm.android.bataillenavale.controlers;
import cmm.android.bataillenavale.modele.Coord2D;
import cmm.android.bataillenavale.modele.Mer;
import cmm.android.bataillenavale.view.screens.VersusHumainGameScreen;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
public class HumainTirListener extends Listener {
	VersusHumainGameScreen screen;
	private Coord2D advTir;

	public HumainTirListener(VersusHumainGameScreen screen) {
		this.screen = screen;
	}

	@Override
	public void received(Connection connection, Object data) {
		if (data instanceof String) {
			String message = (String)data;
			if(message.equals("play")) {
				screen.setPlayerTurn(true);
			}
			else {
				screen.setPlayerTurn(false);
			}
		}
		else if(data instanceof Coord2D) {
			/* ***** on analyse le tir de l'adversaire ***** */
			advTir = (Coord2D)data;
			if(!screen.isPlayerTurn() && !screen.isOverGame()) {
				boolean touche = screen.tirer(advTir);
				screen.getApp().getClient().sendTCP(new Boolean(touche));
			}
		}

		/* ***** on récupère la réponse de l'adversaire sur notre propre tir ***** */
		else if(data instanceof Boolean) {
			Boolean touche = (Boolean)data;
			int status;
			if(touche){
				status = Mer.MISSED;
			}
			else {
				status = Mer.BOAT_HANDLE_KILLED;
			}
			screen.getGraphicAdversaire().getMer().setStatusCase(advTir.x, advTir.y, status);
		}
		/* On change de joueur */
		screen.switchPlayerTurn();
	}
}
