package cmm.android.bataillenavale.controlers;

import cmm.android.bataillenavale.BatailleNavale;
import cmm.android.bataillenavale.view.screens.VersusHumainGameScreen;
import cmm.android.bataillenavale.view.screens.net.PlaceBateauNetScreen;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class WaitForSeaListener extends Listener {
	private PlaceBateauNetScreen screen;

	public WaitForSeaListener(PlaceBateauNetScreen screen) {
		this.screen = screen;
	}

	@Override
	public void received(Connection c, Object o) {		
		if(o instanceof String) {
			String message = (String) o;
			if(message.equals("play") || message.equals("wait")) {
				final boolean play = message.equals("play");

				/* ***** Il faut que ce soit le Thread OpenGL qui fasse ça, et pas le Thread Kryonet ***** */
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						VersusHumainGameScreen versusScreen = (VersusHumainGameScreen) screen.getApp().getScreen(BatailleNavale.VERSUS_HUMAIN_GAME);
						versusScreen.setJoueur(screen.getGraphicMer().getMer());
						versusScreen.setPlayerTurn(play);
						screen.getApp().setScreen(versusScreen);
					}
				});
			}
		}
	}
}
