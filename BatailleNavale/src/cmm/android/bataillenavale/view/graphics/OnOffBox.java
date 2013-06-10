package cmm.android.bataillenavale.view.graphics;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Permet d'afficher un bouton de type check, a placer dans une Scene2D
 * Il prend la valeur "on" lorsqu'il est actif, "off" lorsqu'il est inactif.
 * @author Jonathan GEOFFROY, Samy CHAYEM
 *
 */
public class OnOffBox extends CheckBox {
	public OnOffBox(boolean isChecked, Skin skin) {
		super("On", skin);
		setChecked(isChecked);// Appelle le listener qui se chargera de mettre tout à jour
		
		addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				boolean checked = isChecked();
				if(checked) {
					setText("On");
				}
				else {
					setText("Off");
				}
			}
		});
	}

}
