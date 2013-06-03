package cmm.android.bataillenavale.view.graphics;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

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
