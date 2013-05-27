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
			else if(message.equals("wait")) {
				screen.setPlayerTurn(false);
			}
			else if(message.equals("true")) {
				//TODO
			}
			else if(message.equals("false")) {
				//TODO
			}
			else {
				String[] parsedMessage = message.split(":");
				int x = Integer.parseInt(parsedMessage[0]);
				int y = Integer.parseInt(parsedMessage[1]);
				boolean touched = screen.tirer(new Coord2D(x, y)); //FIXME: risque e planter
				String result;
				if(touched)
					result = "true";
				else
					result = "false";
				screen.getApp().getClient().sendTCP(result);
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
