package cmm.android.bataillenavale;

import java.io.IOException;
import java.net.InetAddress;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.esotericsoftware.kryonet.Client;

import cmm.android.bataillenavale.utils.CmmGameAdapter;
import cmm.android.bataillenavale.view.screens.CmmFinalScreen;
import cmm.android.bataillenavale.view.screens.IaMenuScreen;
import cmm.android.bataillenavale.view.screens.MainMenuScreen;
import cmm.android.bataillenavale.view.screens.PlaceBateauScreen;
import cmm.android.bataillenavale.view.screens.VersusComputerGameScreen;
import cmm.android.bataillenavale.view.screens.VersusHumainGameScreen;
import cmm.android.bataillenavale.view.screens.net.PlaceBateauNetScreen;
import cmm.android.bataillenavale.view.screens.net.SearchEnnemy;

public class BatailleNavale extends CmmGameAdapter {
	public static final int MAIN_MENU = 0, IA_MENU = 1, CMM_FINAL = 2, SEARCH_ENNEMY = 3,PLACE_BATEAU = 4, NET_PLACE_BATEAU = 5, VERSUS_COMPUTER_GAME = 6, VERSUS_HUMAIN_GAME = 7;
	public final static int TCP_PORT = 54722, UDP_PORT = 54723;
	
	private Client client;
	private BitmapFont font;
	
	@Override
	public void create() {
		super.create();
		
		/* ***** initialise la partie réseau en utilisant IPV4 ***** */
		System.setProperty("java.net.preferIPv4Stack" , "true");
		
		
		/* ***** On met la musique en streaming ***** */
		Music music = Gdx.audio.newMusic(Gdx.files.internal("data/sounds/music.mp3"));
		music.play();
		
		/* ***** on crée toutes les screens en mémoire pour les utiliser dans l'appli ***** */
		screens.add(MAIN_MENU, new MainMenuScreen(this));
		screens.add(IA_MENU, new IaMenuScreen(this));
		screens.add(CMM_FINAL,new CmmFinalScreen(this));
		screens.add(SEARCH_ENNEMY, new SearchEnnemy(this));
		screens.add(PLACE_BATEAU, new PlaceBateauScreen(this));
		screens.add(NET_PLACE_BATEAU, new PlaceBateauNetScreen(this));
		screens.add(VERSUS_COMPUTER_GAME, new VersusComputerGameScreen(this));
		screens.add(VERSUS_HUMAIN_GAME, new VersusHumainGameScreen(this));
		
		/* ***** On place MAIN_MENU comme le premier screen de l'appli ***** */
		setScreen(screens.get(MAIN_MENU));
		
		/* ***** On initialise le font ***** */
		setFont(new BitmapFont(
				Gdx.files.internal("data/fonts/mainMenu.fnt"),
				Gdx.files.internal("data/fonts/mainMenu.png"),
				false
				));
		font.setUseIntegerPositions(false);
		font.setScale(1.4f / Gdx.graphics.getHeight());
	}
	
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	
	public boolean connect() {
		client = new Client();
		client.start();
		/* ***** on essaye de trouver un serveur ***** */
		/*
		InetAddress adress = client.discoverHost(UDP_PORT, 10000); 
		Gdx.app.log("NET", "addr:" + adress);
		*/		
		try {
			client.connect(5000, "192.168.2.67", TCP_PORT, UDP_PORT);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean disconnect() {
		if(client != null) {
			client.close();
			client = null;
		}
		
		return true;
	}

	public BitmapFont getFont() {
		return font;
	}

	public void setFont(BitmapFont font) {
		this.font = font;
	}
}
