package cmm.android.bataillenavale.controlers;

import cmm.android.bataillenavale.view.screens.net.PlaceBateauNetScreen;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class WaitForSeaListener extends Listener {
	private PlaceBateauNetScreen screen;
	
	public WaitForSeaListener(PlaceBateauNetScreen screen) {
		this.screen = screen;
	}
	
	@Override
	public void received(Connection c, Object o) {
		super.received(c, o);
		if(o instanceof String) {
			System.out.println("on recoit la mer de l'adv");
			screen.merPlacee();
		}
	}

}
