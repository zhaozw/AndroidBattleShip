package cmm.android.bataillenavale.utils;

import com.badlogic.gdx.InputAdapter;
/**
 * Listener permettant de changer de Screen lorsqu'on touche l'ecran
 * @author Jonathan GEOFFROY
 * @version 1.0
 */
public class ChangeScreenOnTouchListener extends InputAdapter {
	private CmmScreenAdapter screen;
	
	public ChangeScreenOnTouchListener(CmmScreenAdapter screen) {
		this.screen = screen;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		onChangeScreen();
		screen.getApp().setScreen(screen);
		return true;
	}
	
	/**
	 * Méthode appelé lorsqu'on change de screen, c'est-à-dire lorsqu'on clique sur l'ecran
	 * Par défaut, elle ne fait rien, mais on peut hériter cette classe afin de réimplémenter cette méthode.
	 */
	public void onChangeScreen() {}
}
