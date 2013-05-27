package cmm.android.bataillenavale.view.screens.net;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;

import cmm.android.bataillenavale.BatailleNavale;
import cmm.android.bataillenavale.controlers.PlaceBateauNetListener;
import cmm.android.bataillenavale.controlers.ShipChooserListener;
import cmm.android.bataillenavale.controlers.WaitForSeaListener;
import cmm.android.bataillenavale.modele.Mer;
import cmm.android.bataillenavale.view.screens.PlaceBateauScreen;
import cmm.android.bataillenavale.view.screens.VersusHumainGameScreen;

public class PlaceBateauNetScreen extends PlaceBateauScreen {
	private Mer adversaireMer;
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
		app.setKryonetListener(new WaitForSeaListener(this));
	}

	public void merPlacee() {
		nbMersPlacees++;
		/* ***** si toutes les mers ont été placées ***** */
		if(nbMersPlacees == 2) {
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					VersusHumainGameScreen screen = (VersusHumainGameScreen) app.getScreen(BatailleNavale.VERSUS_HUMAIN_GAME);
					screen.setAdversaire(adversaireMer);
					screen.setJoueur(graphicMer.getMer());
					app.setScreen(screen);	
				}
			});
		}
	}

	public Mer getAdversaireMer() {
		return adversaireMer;
	}

	public void setAdversaireMer(Mer adversaireMer) {
		this.adversaireMer = adversaireMer;
		merPlacee();
	}
}
