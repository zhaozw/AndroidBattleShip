package cmm.android.bataillenavale.controlers;

import cmm.android.bataillenavale.BatailleNavale;
import cmm.android.bataillenavale.modele.Coord2D;
import cmm.android.bataillenavale.modele.Mer;
import cmm.android.bataillenavale.view.graphics.GraphicMer;
import cmm.android.bataillenavale.view.screens.VersusHumainGameScreen;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class HumainTirListener extends Listener {
	VersusHumainGameScreen screen;

	public HumainTirListener(VersusHumainGameScreen screen) {
		this.screen = screen;
	}

	@Override
	public void received(Connection connection, Object data) {
		if (data instanceof String) {
			String message = (String)data;

			if(!screen.isPlayerTurn()) {
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
				boolean touche = message.equals("true");
				int status;
				if(touche) {
					status = Mer.BOAT_HANDLE_KILLED;
					screen.getGraphicAdversaire().getMer().incNbHandlesTouched();
				}
				else {
					status = Mer.MISSED;
				}
				GraphicMer adv = screen.getGraphicAdversaire();
				Mer m = adv.getMer();
				Coord2D tir = screen.getTir();
				m.setStatusCase(tir.x, tir.y, status);
				screen.tirer(touche, screen.getGraphicJoueur(), screen.getGraphicAdversaire());
			}
		}
	}

	@Override
	public void disconnected(Connection arg0) {
		super.disconnected(arg0);
		Gdx.app.postRunnable(new Runnable() {
			@Override
			public void run() {
				screen.getApp().setScreen(BatailleNavale.MAIN_MENU);
			}
		});	
	}
}
