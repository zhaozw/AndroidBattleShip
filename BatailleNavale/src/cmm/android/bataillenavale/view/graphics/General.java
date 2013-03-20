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
			textName = "playerGeneral.png";
			break;
		case ADVERSAIRE:
			textName = "adversaireGeneral.png";
			break;
			default:
				assert false: "the general type doesn't exist!";
		}
		setStatus(CLASSIC);
		/* ***** général dans son état normal ****** */
		text = new Texture("./data/img/classic_" + textName);
		screen.getApp().putTransversalTexture("classic_" + textName, text);
		generalTextReg = new TextureRegion(text);
		
//		/* ***** général heureux ***** */
		text = new Texture("./data/img/happy_" + textName);
		screen.getApp().putTransversalTexture("happy_" + textName, text);
		generalTextReg = new TextureRegion(text);
		
		/* ***** général déçu ***** */
		text = new Texture("./data/img/unhappy_" + textName);
		screen.getApp().putTransversalTexture("unhappy_" + textName, text);
		generalTextReg = new TextureRegion(text);
		
		/* ***** On place la texture classique ****** */
		setRegion(generalTextReg);
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
