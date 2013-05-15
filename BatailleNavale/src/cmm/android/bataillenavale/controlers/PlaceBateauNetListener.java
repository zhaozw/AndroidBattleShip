package cmm.android.bataillenavale.controlers;

import com.badlogic.gdx.Gdx;

import cmm.android.bataillenavale.modele.Mer;
import cmm.android.bataillenavale.view.screens.net.PlaceBateauNetScreen;

public class PlaceBateauNetListener extends PlaceBateauListener {

	public PlaceBateauNetListener(PlaceBateauNetScreen placeBateau) {
		super(placeBateau);
	}

	@Override
	protected void onAllShipPlaced() {
		Mer mer = placeBateau.getGraphicMer().getMer();
		placeBateau.getApp().getClient().sendTCP(mer);
		PlaceBateauNetScreen pbn = (PlaceBateauNetScreen)placeBateau;
		pbn.merPlacee(); //On dit qu'on a placé une mer
		Gdx.input.setInputProcessor(null); //On supprime les Listeners pour pas avoir de conflits
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		placeBateau.getApp().getClient().sendTCP("bonjour le test");
		return super.touchUp(screenX, screenY, pointer, button);
	}
}
