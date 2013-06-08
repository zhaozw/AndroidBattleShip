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
			if(message.equals("true")) {
				screen.tirer(true, screen.getGraphicJoueur(), screen.getGraphicAdversaire());
			}
			else if(message.equals("false")) {
				screen.tirer(true, screen.getGraphicJoueur(), screen.getGraphicAdversaire());
			}
			else if(!screen.isPlayerTurn()) {
				/* ***** On s'attend à recevoir un message du type x:y, correspondant à la case visée par l'adversaire ****** */
				String[] parsedMessage = message.split(":");
				if(parsedMessage.length != 2)
					return;

				int x = Integer.parseInt(parsedMessage[0]);
				int y = Integer.parseInt(parsedMessage[1]);
				boolean touched = screen.tirer(new Coord2D(x, y));
				String result;

				if(touched)
					result = "true";
				else
					result = "false";
				screen.getApp().getClient().sendTCP(result);
			}
			/* ***** Si on reçoit la réponse sur notre propre tir ***** */
			else if(message.equals("true") || message.equals("false")) {
				Boolean touche = message.equals("true");
				int status;
				if(touche) {
					status = Mer.BOAT_HANDLE_KILLED;
					screen.getGraphicAdversaire().getMer().incNbHandlesTouched();
				}
				else {
					status = Mer.MISSED;
				}
				screen.getGraphicAdversaire().getMer().setStatusCase(advTir.x, advTir.y, status);
				screen.tirer(touche, screen.getGraphicJoueur(), screen.getGraphicAdversaire());
			}
		}

	}
}
