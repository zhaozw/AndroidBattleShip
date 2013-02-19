package cmm.android.bataillenavale.utils.menus;

import cmm.android.bataillenavale.utils.CmmScreenAdapter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;

public class CmmMenuGroupListener extends InputAdapter {
	private CmmMenuGroup menuGroup;
	private Vector3 touchPos;
	
	public CmmMenuGroupListener(CmmMenuGroup menuGroup) {
		this.menuGroup = menuGroup;
		touchPos = new Vector3();
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		CmmScreenAdapter.getCamera().unproject(touchPos);
		
		if(menuGroup.getBoundingRectangle().contains(touchPos.x, touchPos.y)) {
			float menuTopCornerY = (menuGroup.getY() + menuGroup.getHeight());
			int indexMenu = (int)((menuTopCornerY - touchPos.y) / menuGroup.getMenuHeight());
			CmmMenu touchedMenu = menuGroup.getMenuAt(indexMenu);
			
			return touchedMenu.onTouched();
		}
		return false;
	}
	
	

}
