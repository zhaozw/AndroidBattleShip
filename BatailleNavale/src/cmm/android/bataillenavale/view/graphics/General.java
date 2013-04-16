package cmm.android.bataillenavale.view.graphics;

import cmm.android.bataillenavale.utils.CmmScreenAdapter;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class General extends Sprite {
	public final static int PLAYER = 0, ADVERSAIRE = 1;
	public final static int CLASSIC = 0, HAPPY = 1, UNHAPPY = 2;
	private TextureRegion generalTextReg, happyTextReg, unhappyTextReg;
	private int status;
	
	public General(CmmScreenAdapter screen, int type) {
		Texture text;
		String textName = "";
		switch (type) {
		case PLAYER:
			textName = "PlayerGeneral.png";
			break;
		case ADVERSAIRE:
			textName = "AdversaireGeneral.png";
			break;
			default:
				assert false: "the general type doesn't exist!";
		}
		/* ***** général dans son état normal ****** */
		text = new Texture("data/img/classic" + textName);
		screen.getApp().putTransversalTexture("classic" + textName, text);
		generalTextReg = new TextureRegion(text);
		
		/* ***** général heureux ***** */
		text = new Texture("data/img/happy" + textName);
		screen.getApp().putTransversalTexture("happy" + textName, text);
		happyTextReg = new TextureRegion(text);
		
		/* ***** général déçu ***** */
		text = new Texture("data/img/unhappy" + textName);
		screen.getApp().putTransversalTexture("unhappy" + textName, text);
		unhappyTextReg = new TextureRegion(text);
		
		/* ***** On place la texture classique ****** */
		setRegion(generalTextReg);
		status = CLASSIC;
	
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
		switch(status) {
		case CLASSIC:
			setRegion(generalTextReg);
			break;
		case HAPPY:
			setRegion(happyTextReg);
			break;
		case UNHAPPY:
			setRegion(unhappyTextReg);
			break;
		}
	}
}
