package cmm.android.bataillenavale.utils.menus;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CmmMenuGroup extends Sprite {
	private ArrayList<CmmMenu> menus;
	protected float menuHeight;
	private TextureRegion menuText;
	private BitmapFont font; //TODO initialize
	
	public CmmMenuGroup(ArrayList<CmmMenu> menus, TextureRegion menuText, float x, float y, float width, float height) {
		super();
		this.menus = menus;
		this.menuText = menuText;
		setX(x);
		setY(y);
		setSize(width, height);
		
		menuHeight = height / menus.size();
		
		CmmMenuGroupListener listener = new CmmMenuGroupListener(this);
		Gdx.input.setInputProcessor(listener);
	}
	
	public CmmMenu getMenuAt(int index) {
		return menus.get(index);
	}

	public float getMenuHeight() {
		return menuHeight;
	}
	
	public void draw(SpriteBatch batch) {
		float x = getX();
		String menuName;
		for(CmmMenu m: menus) {
			menuName = m.getName();
			x -= menuHeight;
			batch.draw(menuText, x, this.getY(), this.getWidth(), menuHeight);
			font.draw(batch, menuName, x, this.getY());
		}
	}
}
