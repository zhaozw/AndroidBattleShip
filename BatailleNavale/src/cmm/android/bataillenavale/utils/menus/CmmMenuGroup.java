package cmm.android.bataillenavale.utils.menus;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

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

		/* ***** génère une police à partir d'un fichier TTF ***** */
		FileHandle fontFile = Gdx.files.local("data/fonts/mainMenu.ttf");
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
		font = generator.generateFont(15);
		generator.dispose();
	}
	
	public CmmMenu getMenuAt(int index) {
		return menus.get(index);
	}

	public float getMenuHeight() {
		return menuHeight;
	}
	
	public void draw(SpriteBatch batch) {
		float x = getX();
		float y = getY() + getHeight();
		float width = getWidth();
		String menuName;
		for(CmmMenu m: menus) {
			menuName = m.getName();
			y -= menuHeight;
			batch.draw(menuText, x, y, width, menuHeight);
			font.draw(batch, menuName, x, y);
		}
	}

	public void setFont(BitmapFont font) {
		this.font = font;
	}
}
