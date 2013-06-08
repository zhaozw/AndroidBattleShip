package cmm.android.bataillenavale.controlers;

import cmm.android.bataillenavale.BatailleNavale;
import cmm.android.bataillenavale.view.screens.net.SearchEnnemy;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class WaitForPlayerListener extends Listener {
	private SearchEnnemy searchEnnemy;
	
	public WaitForPlayerListener(SearchEnnemy se) {
		searchEnnemy = se;
	}
	
	@Override
	public void received(Connection connection, Object data) {
		super.received(connection, data);
		if(data instanceof String) {
			String stringData = (String)data;
			
			if(stringData.equals("wait for sea")) {
				searchEnnemy.setState(SearchEnnemy.WAIT_SEA);
			}
		}
	}

	@Override
	public void disconnected(Connection arg0) {
		super.disconnected(arg0);
		Gdx.app.postRunnable(new Runnable() {
			@Override
			public void run() {
				searchEnnemy.getApp().setScreen(BatailleNavale.MAIN_MENU);
			}
		});
	}
}
