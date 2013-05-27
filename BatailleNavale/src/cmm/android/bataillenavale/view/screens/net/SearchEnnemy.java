package cmm.android.bataillenavale.view.screens.net;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import cmm.android.bataillenavale.BatailleNavale;
import cmm.android.bataillenavale.controlers.WaitForPlayerListener;
import cmm.android.bataillenavale.utils.ChangeScreenOnTouchListener;
import cmm.android.bataillenavale.utils.CmmScreenAdapter;

public class SearchEnnemy extends CmmScreenAdapter {
	public static final int CONNEXION_ERROR = 0, WAIT_PLAYER = 1, WAIT_SEA = 2;
	private int state;

	public SearchEnnemy(BatailleNavale app) {
		super(app, false);
	}

	@Override
	public void initialize() {
		super.initialize();
		if (app.connect()) {
			state = WAIT_PLAYER;
			app.getClient().addListener(new WaitForPlayerListener(this));
			Gdx.input.setInputProcessor(null);
		} else {
			state = CONNEXION_ERROR;
			/* ***** on place un listener pour revenir au menu principal lorsque l'utilisateur à compris le message d'erreur ***** */
			Gdx.input.setInputProcessor(
					new ChangeScreenOnTouchListener(
							app.getScreen(BatailleNavale.MAIN_MENU)
							)
					);
		}
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		BitmapFont font = app.getFont();
		String message = null;
		switch (state) {
		case CONNEXION_ERROR:
			message = "Connexion Impossible\n, cliquez pour revenir au menu principal";
			break;
		case WAIT_PLAYER:
			message = "En attente qu'un autre joueur se connecte";
			break;
		}
		
		/* ***** affichage du texte ***** */
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		spriteBatch.begin();
		font.setColor(0f, 0f, 0f, 1f);
		font.drawMultiLine(spriteBatch,	message, 0f, 0f);
		spriteBatch.end();
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
		/* ***** Si on peut changer de screen pour placer les bateaux ***** */
		if(state == WAIT_SEA) {
			app.setScreen(BatailleNavale.NET_PLACE_BATEAU);
		}
	}
}
