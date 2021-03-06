package cmm.android.bataillenavale.utils.menus;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;

/**
 * Permet de gérer une série de menus, de les placer sur l'ecran (car il hérite de Sprite).
 * Cette classe utilise la taille du Sprite (width, height) et son origine (x, y) afin de se repeindre correctement.
 * Sa taille est donc recalculé chaque fois que l'on appelle getSize() par exemple.
 * @author Jonathan GEOFFROY, Samy CHAYEM
 * @version 1.0
 */
public class CmmMenuGroup extends Sprite {
	private ArrayList<CmmMenu> menus;
	protected float menuHeight;
	private TextureRegion menuText;
	private BitmapFont font;
	
	public CmmMenuGroup(ArrayList<CmmMenu> menus, TextureRegion menuText, float x, float y, float width, float height) {
		super();
		this.menus = menus;
		this.menuText = menuText;
		setX(x);
		setY(y);
		setSize(width, height);
		
		menuHeight = height / menus.size();

		/* ***** génère une police à partir d'un fichier FNT ***** */
		font = new BitmapFont(Gdx.files.internal("data/fonts/mainMenu.fnt"), Gdx.files.internal("data/fonts/mainMenu.png"), false);
		font.setUseIntegerPositions(false);
		font.setScale(1.4f / Gdx.graphics.getHeight());
		font.setColor(1, 1, 1, 1);
	}
	
	public CmmMenu getMenuAt(int index) {
		return menus.get(index);
	}

	public float getMenuHeight() {
		return menuHeight;
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		float x = getX();
		float y = getY() + getHeight();
		float width = getWidth();
		TextBounds strRect;
		String menuName;
		
		for(CmmMenu m: menus) {
			menuName = m.getName();
			y -= menuHeight;
			batch.draw(menuText, x, y, width, menuHeight);
			strRect = font.getBounds(menuName);
			font.drawMultiLine(batch, menuName, 0.f - (strRect.width/2), y + menuHeight/2 + (strRect.height/2));
		}
	}

	public void setFont(BitmapFont font) {
		this.font = font;
	}
}
