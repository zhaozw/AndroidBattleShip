package cmm.android.bataillenavale.view.screens;

import com.badlogic.gdx.Gdx;

import cmm.android.bataillenavale.BatailleNavale;
import cmm.android.bataillenavale.controlers.GameNetListener;
import cmm.android.bataillenavale.controlers.HumainTirListener;
import cmm.android.bataillenavale.modele.Coord2D;

public class VersusHumainGameScreen extends GameScreen {
	private Coord2D tir;
	public VersusHumainGameScreen(BatailleNavale app) {
		super(app, true);
	}

	@Override
	public void initialize() {
		super.initialize();
		GameNetListener listener = new GameNetListener(this);
		Gdx.input.setInputProcessor(listener);
		HumainTirListener htl = new HumainTirListener(this);
		app.getClient().addListener(htl);
	}

	public Coord2D getTir() {
		return tir;
	}

	public void setTir(Coord2D tir) {
		this.tir = tir;
	}
}
