package cmm.android.bataillenavale.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import cmm.android.bataillenavale.BatailleNavale;
import cmm.android.bataillenavale.utils.CmmScreenAdapter;

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
	private Stage stage;
	private Skin skin;
	
	public MainMenuScreen(BatailleNavale app) {
		super(app, false);
	}

	@Override
	public void initialize() {
		super.initialize();
		
		/* *****  Création du fond ***** */
		Texture wallText = new Texture("data/img/mainMenuWallpaper.jpg");
		TextureRegion wallTextReg = new TextureRegion(wallText, 412, 600);
		Sprite wallpaper = new Sprite(wallTextReg);
		setWallpaper(wallpaper);
		textures.add(wallText);
		
		/* ***** On crée un Stage, qui va contenir les widgets du menu ***** */
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		/* **** on crée un table pour afficher les widgets comme on souhaite ***** */
		Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        /* ***** On crée un Style pour notre menu ***** */
        skin = new Skin();
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		
		/* ***** on crée les textures pour notre style de menu ***** */
		Texture menuText = new Texture("data/img/mainMenu.png");
		TextureRegion menuTextReg = new TextureRegion(menuText, 256, 140);
		addTexture(menuText);
		skin.add("white", new Texture(pixmap));
		skin.add("up", menuTextReg);
		skin.add("down", menuTextReg);
		skin.add("default", new BitmapFont());

		/* ***** On ajoute à notre style, un style de bouton ***** */
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("up");
		textButtonStyle.down = skin.getDrawable("down");
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);
		
		/* ***** Création des boutons ***** */
		final TextButton unJoueur = new TextButton("1 joueur", skin);
		final TextButton deuxJoueurs = new TextButton("2 joueurs", skin);
		final TextButton options = new TextButton("Options", skin);
		final TextButton quitter = new TextButton("Quitter", skin);

		/* ***** On place les boutons dans la table ***** */
		float width = Gdx.graphics.getWidth() * 80 / 100;
		float height = Gdx.graphics.getHeight() * 20 / 100;
		table.add(unJoueur).width(width).height(height).expandY();
		table.row();
		table.add(deuxJoueurs).width(width).height(height).expandY();
		table.row();
		table.add(options).width(width).height(height).expandY();
		table.row();
		table.add(quitter).width(width).height(height).expandY();
		
		/* ***** Création des listener pour changer de Screen au clic ***** */
		unJoueur.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				app.setScreen(BatailleNavale.IA_MENU);				
			}
		});
		deuxJoueurs.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				app.setScreen(BatailleNavale.SEARCH_ENNEMY);				
			}
		});
		options.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				app.setScreen(BatailleNavale.OPTIONS);				
			}
		});
		quitter.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				app.setScreen(BatailleNavale.CMM_FINAL);				
			}
		});
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
	}
	
	
	
}
