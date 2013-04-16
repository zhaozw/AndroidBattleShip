package cmm.android.bataillenavale.controlers;

import cmm.android.bataillenavale.modele.Mer;
import cmm.android.bataillenavale.view.screens.net.PlaceBateauNetScreen;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class MerReceiverListener extends Listener {
	PlaceBateauNetScreen placeBateau;
	
	public MerReceiverListener(PlaceBateauNetScreen placeBateau) {
		this .placeBateau = placeBateau;
	}

	@Override
	public void received(Connection connection, Object data) {
		super.received(connection, data);
		
		if(data instanceof Mer) {
			Mer mer = (Mer)data;
			placeBateau.setAdversaireMer(mer);
		}
	}
	
	
}
