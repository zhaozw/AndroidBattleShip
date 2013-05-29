package cmm.android.bataillenavale.view.screens;

import com.badlogic.gdx.Gdx;

import cmm.android.bataillenavale.BatailleNavale;
import cmm.android.bataillenavale.controlers.GameNetListener;

public class VersusHumainGameScreen extends GameScreen {
	
	public VersusHumainGameScreen(BatailleNavale app) {
		super(app);
	}

	@Override
	public void initialize() {
		super.initialize();
		GameNetListener listener = new GameNetListener(this);
		Gdx.input.setInputProcessor(listener);
	}
}
