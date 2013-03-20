package cmm.android.bataillenavale;

import cmm.android.bataillenavale.utils.CmmGameAdapter;
import cmm.android.bataillenavale.view.screens.CmmFinalScreen;
import cmm.android.bataillenavale.view.screens.MainMenuScreen;
import cmm.android.bataillenavale.view.screens.PlaceBateauScreen;
import cmm.android.bataillenavale.view.screens.SearchEnnemyScreen;
import cmm.android.bataillenavale.view.screens.VersusComputerGameScreen;

public class BatailleNavale extends CmmGameAdapter {
	public static final int MAIN_MENU = 0, CMM_FINAL = 1, SEARCH_ENNEMY = 2,PLACE_BATEAU = 3, VERSUS_COMPUTER_GAME = 4;	
	@Override
	public void create() {
		super.create();
		
		/* ***** on crée toutes les screens en mémoire pour les utiliser dans l'appli ***** */
		screens.add(MAIN_MENU, new MainMenuScreen(this));
		screens.add(CMM_FINAL,new CmmFinalScreen(this));
		screens.add(SEARCH_ENNEMY, new SearchEnnemyScreen(this));
		screens.add(PLACE_BATEAU, new PlaceBateauScreen(this));
		screens.add(VERSUS_COMPUTER_GAME, new VersusComputerGameScreen(this));
		
		/* ***** On place MAIN_MENU comme le premier screen de l'appli ***** */
		setScreen(screens.get(MAIN_MENU));
	}
}
