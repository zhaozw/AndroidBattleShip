package cmm.android.bataillenavale.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import cmm.android.bataillenavale.BatailleNavale;
import cmm.android.bataillenavale.utils.CmmScreenAdapter;

/**
 * Screen permettant d'afficher uen petite image avant de terminer l'application.
 * @author Jonathan GEOFFROY, Samy CHAYEM
 * @version 1.0
 */
public class CmmFinalScreen extends CmmScreenAdapter {
	private static final int CMM_SPRITE = 0;
	public CmmFinalScreen(BatailleNavale app) {
		super(app);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(float delta) {
		Sprite cmmSprite = sprites.get(CMM_SPRITE);
		
		/* ***** Gestion du mouvement de l'image par accélérométrie ***** */
		float accelerometerY = Gdx.input.getAccelerometerY();		
		float y = cmmSprite.getY() - accelerometerY/100.f;
		// Gestion de la sortie de l'écran
		if(y < -0.5f) {
			y = -0.5f;
		} else if(y > 0.5f - cmmSprite.getY()) {
			y = 0.5f - cmmSprite.getY();
		}
		
		float accelerometerX = Gdx.input.getAccelerometerX();
		float x = cmmSprite.getX() - accelerometerX/100.f;
		//Gestion de la sortie de l'écran
		if(x < -0.5f) {
			x = -0.5f;
		} else if(x > .5f - cmmSprite.getX()) {
			x = .5f - cmmSprite.getX();
		}
		cmmSprite.setPosition(x, y);
		
		
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
		TextureRegion cmmTextReg = new TextureRegion(cmmText, 278, 330);
		
		Sprite cmmSprite = new Sprite(cmmTextReg);
		cmmSprite.setSize(0.2f * getScreenProportion(), 0.2f); //On cherche à avoir l'image carrée malgré l'écran rectangulaire
		cmmSprite.setPosition(-cmmSprite.getWidth()/2, -cmmSprite.getHeight()/2);
		sprites.add(cmmSprite);
	}
}
