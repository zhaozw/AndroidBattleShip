package cmm.android.bataillenavale.controlers;

import cmm.android.bataillenavale.view.screens.net.SearchEnnemy;

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

}
