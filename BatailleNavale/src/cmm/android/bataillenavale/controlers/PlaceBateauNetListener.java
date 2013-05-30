package cmm.android.bataillenavale.controlers;

import com.badlogic.gdx.Gdx;
import cmm.android.bataillenavale.view.screens.net.PlaceBateauNetScreen;

public class PlaceBateauNetListener extends PlaceBateauListener {

	public PlaceBateauNetListener(PlaceBateauNetScreen placeBateau) {
		super(placeBateau);
	}

	@Override
	protected void onAllShipPlaced() {
		placeBateau.getApp().getClient().sendTCP("WFP");//WFP: Wait For Playing
		PlaceBateauNetScreen pbn = (PlaceBateauNetScreen)placeBateau;
		pbn.setAllShipPlaced();
		pbn.merPlacee(); //On dit qu'on a placé une mer
		Gdx.input.setInputProcessor(null); //On supprime les Listeners pour pas avoir de conflits
	}
}
