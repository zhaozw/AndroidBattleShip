package cmm.android.bataillenavale.view.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import cmm.android.bataillenavale.utils.CmmScreenAdapter;

public class CmmFinalScreen extends CmmScreenAdapter {
	private static final int CMM_SPRITE = 0;
	public CmmFinalScreen(Game app) {
		super(app);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(float delta) {
		/* ***** Gestion du mouvement de l'image par accélérométrie ***** */
		float accelerometerY = Gdx.input.getAccelerometerY();
		float accelerometerX = Gdx.input.getAccelerometerX();
		
		Sprite cmmSprite = sprites.get(CMM_SPRITE);
		
		float y = cmmSprite.getY() + accelerometerY/10.f;
		// Gestion de la sortie de l'écran
		if(y < -0.5f) {
			y = -0.5f;
		} else if(y > 0.5f - cmmSprite.getY()) {
			y = 1.f - cmmSprite.getY();
		}
		
		float x = cmmSprite.getX() + accelerometerX/10.f;
		//Gestion de la sortie de l'écran
		if(x < -0.5f) {
			x = -0.5f;
		} else if(x > .5f - cmmSprite.getX()) {
			x = 1.f - .5f - cmmSprite.getX();
		}
		
		/* ***** Gestion du clic en polling ***** */
		if(Gdx.input.isTouched()) {
			Gdx.app.exit();// On quitte tout!
		}
		
		super.render(delta);
	}

	@Override
	public void initialize() {
		super.initialize();
		
		Texture cmmText = new Texture("data/img/cmm.png");
		textures.add(CMM_SPRITE, cmmText);
		TextureRegion cmmTextReg = new TextureRegion(cmmText);
		
		Sprite cmmSprite = new Sprite(cmmTextReg);
		cmmSprite.setSize(0.2f, 0.2f);
		cmmSprite.setPosition(-cmmSprite.getWidth()/2, -cmmSprite.getHeight()/2);
		sprites.add(cmmSprite);
	}
}
