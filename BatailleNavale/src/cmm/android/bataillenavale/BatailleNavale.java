package cmm.android.bataillenavale;

import java.util.ArrayList;

import cmm.android.bataillenavale.utils.CmmScreenAdapter;
import cmm.android.bataillenavale.view.screens.CmmFinalScreen;
import cmm.android.bataillenavale.view.screens.MainMenuScreen;
import cmm.android.bataillenavale.view.screens.PlaceBateauScreen;
import cmm.android.bataillenavale.view.screens.SearchEnnemyScreen;
import cmm.android.bataillenavale.view.screens.VersusComputerGameScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BatailleNavale extends Game {
	public static final int MAIN_MENU = 0, CMM_FINAL = 1, SEARCH_ENNEMY = 2,PLACE_BATEAU = 3, VERSUS_COMPUTER_GAME = 4;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private ArrayList<Screen> screens;
	
	@Override
	public void create() {
		camera = new OrthographicCamera(1, 1);
		batch = new SpriteBatch();
		
		CmmScreenAdapter.setSpriteBatch(batch);
		CmmScreenAdapter.setCamera(camera);
		
		screens = new ArrayList<Screen>();
		screens.add(MAIN_MENU, new MainMenuScreen(this));
		screens.add(CMM_FINAL,new CmmFinalScreen(this));
		screens.add(SEARCH_ENNEMY, new SearchEnnemyScreen(this));
		screens.add(PLACE_BATEAU, new PlaceBateauScreen(this));
		screens.add(VERSUS_COMPUTER_GAME, new VersusComputerGameScreen(this));
		setScreen(screens.get(MAIN_MENU));
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause() {}
	@Override
	public void resume() {}
	
	public void setScreen(int screenType) {
		setScreen(screens.get(screenType));
	}
	
	public Screen getScreen(int screenType) {
		return screens.get(screenType);
	}
}
