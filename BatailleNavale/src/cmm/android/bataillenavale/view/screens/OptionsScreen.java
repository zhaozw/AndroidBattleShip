package cmm.android.bataillenavale.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import cmm.android.bataillenavale.BatailleNavale;
import cmm.android.bataillenavale.utils.CmmScreenAdapter;
import cmm.android.bataillenavale.view.graphics.OnOffBox;

public class OptionsScreen extends CmmScreenAdapter {
	private Stage stage;
	private Skin skin;
	
	public OptionsScreen(BatailleNavale app, boolean continousRendering) {
		super(app, continousRendering);
	}

	@Override
	public void initialize() {
		super.initialize();
		
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
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = skin.getFont("default");
		CheckBoxStyle checkboxStyle = new CheckBoxStyle();
		checkboxStyle.up = skin.newDrawable("white", Color.GRAY);
		checkboxStyle.checked = skin.newDrawable("white", Color.BLUE);
		checkboxStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
		checkboxStyle.font = skin.getFont("default");
		skin.add("default", checkboxStyle);
		
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.font = skin.getFont("default");
		textButtonStyle.up = skin.newDrawable("white", Color.LIGHT_GRAY);
		skin.add("default", textButtonStyle);
		
		
		Label sonsLabel = new Label("Activer les sons:", labelStyle);
		final OnOffBox sonsCheckbox = new OnOffBox(true, skin);
		sonsCheckbox.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				app.setPlaySound(sonsCheckbox.isChecked());
			}
		});
		
		Label musiqueLabel = new Label("Activer la musique:", labelStyle);
		final OnOffBox musiqueCheckbox = new OnOffBox(true, skin);
		musiqueCheckbox.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				app.setPlayMusic(musiqueCheckbox.isChecked());
			}
		});
		
		TextButton okButton = new TextButton("Ok", skin);
		okButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				app.setScreen(BatailleNavale.MAIN_MENU);
			}
		});
		
		table.add(sonsLabel).expandX();
		table.add(sonsCheckbox).expandX().width(Gdx.graphics.getWidth() / 4).expandX();;
		table.row().spaceTop(Gdx.graphics.getHeight() / 10);
		table.add(musiqueLabel).expandX();
		table.add(musiqueCheckbox).width(Gdx.graphics.getWidth() / 4).expandX();
		table.row().spaceTop(Gdx.graphics.getHeight() / 10);
		//table.add();
		table.add(okButton)
			.width(Gdx.graphics.getWidth() * 50 / 100)
			.expandX();
			
		
	}

	
	@Override
	public void render(float delta) {
		super.render(delta);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
	}

}
