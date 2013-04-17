package cmm.android.bataillenavale.controlers;
import cmm.android.bataillenavale.modele.Coord2D;
import cmm.android.bataillenavale.view.screens.VersusHumainGameScreen;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
public class HumainTirListener extends Listener {
	VersusHumainGameScreen screen;
	public HumainTirListener(VersusHumainGameScreen screen) {
		this.screen = screen;
	}
	
	@Override
	public void received(Connection connection, Object data) {
		if(data instanceof String) {
			String strData = (String)data;
			String[] splittedData = strData.split(":");
			
			/* ***** on analise le tir de l'adversaire ***** */
			if(!screen.isPlayerTurn() && !screen.isOverGame() && splittedData.length == 2) {
				int x = Integer.parseInt(splittedData[0]);
				int y = Integer.parseInt(splittedData[1]);
				screen.tirer(new Coord2D(x, y));
			}
		}
	}
	
}
