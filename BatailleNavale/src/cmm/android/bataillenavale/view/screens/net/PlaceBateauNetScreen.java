package cmm.android.bataillenavale.view.screens.net;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;

import cmm.android.bataillenavale.BatailleNavale;
import cmm.android.bataillenavale.controlers.PlaceBateauNetListener;
import cmm.android.bataillenavale.controlers.ShipChooserListener;
import cmm.android.bataillenavale.controlers.WaitForSeaListener;
import cmm.android.bataillenavale.view.screens.PlaceBateauScreen;

public class PlaceBateauNetScreen extends PlaceBateauScreen {
	private int nbMersPlacees;

	public PlaceBateauNetScreen(BatailleNavale app) {
		super(app);
		nbMersPlacees = 0;
	}

	@Override
	protected void putListener() {
		ShipChooserListener scl = new ShipChooserListener(shipChooser);
		PlaceBateauNetListener sbl = new PlaceBateauNetListener(this);
		InputMultiplexer multiplexer = new InputMultiplexer(sbl, scl);
		Gdx.input.setInputProcessor(multiplexer);
		WaitForSeaListener wfsl = new WaitForSeaListener(this);
		app.getClient().addListener(wfsl);
	}

	public void merPlacee() {
		nbMersPlacees++;
		Gdx.app.log("debug", "nb mers placees: "  + nbMersPlacees);
	}
	
	public void setAllShipPlaced() {
		message = MESSAGE_ATTENTE;
	}
}
