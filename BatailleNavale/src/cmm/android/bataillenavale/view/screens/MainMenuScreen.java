package cmm.android.bataillenavale.view.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import cmm.android.bataillenavale.BatailleNavale;
import cmm.android.bataillenavale.utils.CmmScreenAdapter;
import cmm.android.bataillenavale.utils.menus.CmmMenu;
import cmm.android.bataillenavale.utils.menus.CmmMenuGroup;
import cmm.android.bataillenavale.utils.menus.CmmMenuGroupListener;

/**
 * Screen permettant d'afficher un menu afin de choisir ce que l'on veut faire parmi:
 * 		Mode 1 joueur,
 * 		Mode réseau,
 * 		Options,
 * 		Quitter.
 * @author Jonathan GEOFFROY, Samy CHAYEM
 * @version 1.0
 */
public class MainMenuScreen extends CmmScreenAdapter {
	private CmmMenuGroup menu;
	
	public MainMenuScreen(BatailleNavale app) {
		super(app, false);
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
		menus.add(new CmmMenu("Jouer contre l'ordinateur") {
			@Override
			public boolean onTouched() {
				app.setScreen(BatailleNavale.PLACE_BATEAU);
				return true;
			}
		});
		menus.add(new CmmMenu("Jouer contre un humain") {
			@Override
			public boolean onTouched() {
				app.setScreen(BatailleNavale.SEARCH_ENNEMY);
				return true;
			}
		});
		menus.add(new CmmMenu("Options") {
			@Override
			public boolean onTouched() {
				Gdx.app.log("TMP_batailleNavale", "on change les options!");
				return true;
			}
		});
		menus.add(new CmmMenu("Quitter") {
			@Override
			public boolean onTouched() {
				app.setScreen(BatailleNavale.CMM_FINAL);
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
