package cmm.android.bataillenavale.view.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import cmm.android.bataillenavale.BatailleNavale;
import cmm.android.bataillenavale.modele.ia.IntelligenceArtificielleDifficile;
import cmm.android.bataillenavale.modele.ia.IntelligenceArtificielleFacile;
import cmm.android.bataillenavale.modele.ia.IntelligenceArtificielleMoyen;
import cmm.android.bataillenavale.utils.CmmScreenAdapter;
import cmm.android.bataillenavale.utils.menus.CmmMenu;
import cmm.android.bataillenavale.utils.menus.CmmMenuGroup;
import cmm.android.bataillenavale.utils.menus.CmmMenuGroupListener;

public class IaMenuScreen extends CmmScreenAdapter {
	private CmmMenuGroup menu;

	public IaMenuScreen(BatailleNavale app) {
		super(app, false);
	}
	public IaMenuScreen(BatailleNavale app, boolean continousRendering) {
		super(app, continousRendering);
	}
	@Override
	public void initialize() {
		super.initialize();

		/* *****  Création du fond ***** */
		Texture wallText = new Texture("data/img/mainMenuWallpaper.jpg");
		TextureRegion wallTextReg = new TextureRegion(wallText);
		Sprite wallpaper = new Sprite(wallTextReg);
		setWallpaper(wallpaper);
		textures.add(wallText);

		/* ***** Création des boutons composants le menu ***** */
		ArrayList<CmmMenu> menus = new ArrayList<CmmMenu>();
		menus.add(new CmmMenu("Facile") {
			@Override
			public boolean onTouched() {
				VersusComputerGameScreen computerGame = (VersusComputerGameScreen) app.getScreen(BatailleNavale.VERSUS_COMPUTER_GAME);
				computerGame.setIa(new IntelligenceArtificielleFacile());

				app.setScreen(BatailleNavale.PLACE_BATEAU);
				return true;
			}
		});
		menus.add(new CmmMenu("Moyen") {
			@Override
			public boolean onTouched() {
				VersusComputerGameScreen computerGame = (VersusComputerGameScreen) app.getScreen(BatailleNavale.VERSUS_COMPUTER_GAME);
				computerGame.setIa(new IntelligenceArtificielleMoyen());

				app.setScreen(BatailleNavale.PLACE_BATEAU);
				return true;
			}
		});
		menus.add(new CmmMenu("Difficile") {
			@Override
			public boolean onTouched() {
				VersusComputerGameScreen computerGame = (VersusComputerGameScreen) app.getScreen(BatailleNavale.VERSUS_COMPUTER_GAME);
				computerGame.setIa(new IntelligenceArtificielleDifficile());

				app.setScreen(BatailleNavale.PLACE_BATEAU);
				return true;
			}
		});
		menus.add(new CmmMenu("Retour") {
			@Override
			public boolean onTouched() {
				app.setScreen(BatailleNavale.MAIN_MENU);
				return true;
			}
		});

		/* ***** Création de la texture du menu ***** */
		Texture menuText = new Texture("data/img/mainMenu.png");
		TextureRegion menuTextReg = new TextureRegion(menuText);
		textures.add(menuText);

		/* ***** Création du menu ***** */
		menu = new CmmMenuGroup(menus, menuTextReg, -0.4f, -0.4f, 0.8f, 0.8f);
		sprites.add(menu);

		/* ***** Création du listener sur le menu ***** */
		CmmMenuGroupListener listener = new CmmMenuGroupListener(menu);
		Gdx.input.setInputProcessor(listener);
	}


}
