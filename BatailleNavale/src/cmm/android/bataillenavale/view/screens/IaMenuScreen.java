package cmm.android.bataillenavale.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
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
import cmm.android.bataillenavale.modele.ia.IntelligenceArtificielleDifficile;
import cmm.android.bataillenavale.modele.ia.IntelligenceArtificielleFacile;
import cmm.android.bataillenavale.modele.ia.IntelligenceArtificielleMoyen;
import cmm.android.bataillenavale.utils.CmmScreenAdapter;

public class IaMenuScreen extends CmmScreenAdapter {
	private Stage stage;
	private Skin skin;

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
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        skin = new Skin();
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		
		Texture menuText = new Texture("data/img/mainMenu.png");
		addTexture(menuText);
		skin.add("white", new Texture(pixmap));
		skin.add("up", menuText);
		skin.add("down", menuText);
		skin.add("default", new BitmapFont());

		// Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("up");
		textButtonStyle.down = skin.getDrawable("down");
		textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
		textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);
		
		/* ***** Création des boutons ***** */
		final TextButton facile = new TextButton("Facile", skin);
		final TextButton moyen = new TextButton("Moyen", skin);
		final TextButton difficile = new TextButton("Difficile", skin);
		final TextButton retour = new TextButton("Retour", skin);

		/* ***** On place les boutons dans la table ***** */
		float width = Gdx.graphics.getWidth() * 80 / 100;
		float height = Gdx.graphics.getHeight() * 20 / 100;
		table.add(facile).width(width).height(height).expandY();
		table.row();
		table.add(moyen).width(width).height(height).expandY();
		table.row();
		table.add(difficile).width(width).height(height).expandY();
		table.row();
		table.add(retour).width(width).height(height).expandY();
		
		/* ***** Création des listener pour changer de Screen au clic ***** */
		facile.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				VersusComputerGameScreen computerGame = (VersusComputerGameScreen) app.getScreen(BatailleNavale.VERSUS_COMPUTER_GAME);
				computerGame.setIa(new IntelligenceArtificielleFacile());
				app.setScreen(BatailleNavale.PLACE_BATEAU);				
			}
		});
		moyen.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				VersusComputerGameScreen computerGame = (VersusComputerGameScreen) app.getScreen(BatailleNavale.VERSUS_COMPUTER_GAME);
				computerGame.setIa(new IntelligenceArtificielleMoyen());
				app.setScreen(BatailleNavale.PLACE_BATEAU);				
			}
		});
		difficile.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				VersusComputerGameScreen computerGame = (VersusComputerGameScreen) app.getScreen(BatailleNavale.VERSUS_COMPUTER_GAME);
				computerGame.setIa(new IntelligenceArtificielleDifficile());
				app.setScreen(BatailleNavale.PLACE_BATEAU);		
			}
		});
		retour.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				app.setScreen(BatailleNavale.MAIN_MENU);				
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
