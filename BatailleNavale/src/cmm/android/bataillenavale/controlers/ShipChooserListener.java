package cmm.android.bataillenavale.controlers;

import com.badlogic.gdx.InputAdapter;

import cmm.android.bataillenavale.modele.Coord2F;
import cmm.android.bataillenavale.utils.CmmScreenAdapter;
import cmm.android.bataillenavale.view.graphics.ShipChooser;

/**
 * Controleur permettant de séléctionner un bateau dans le ShipChooser passé en paramètre du constructeur
 * @author Jonathan GEOFFROY, Samy CHAYEM
 * @version 2.0
 */
public class ShipChooserListener extends InputAdapter {
	private ShipChooser shipChooser;

	public ShipChooserListener(ShipChooser shipChooser) {
		this.shipChooser = shipChooser;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		boolean hasTouched = false;
		Coord2F coord = CmmScreenAdapter.intToFloatCoord(screenX, screenY);
		if(shipChooser.getBoundingRectangle().contains(coord.x, coord.y)) {
			//Si on a appuyé sur le bouton pour changer l'orientation:
			if(shipChooser.getChangeOrientationButton().getBoundingRectangle().contains(coord.x, coord.y)) {
				hasTouched = true;
				shipChooser.switchHorizontal();
			}
			else {
				hasTouched = shipChooser.setSelectedBoat(coord.x, coord.y);
			}
		}

		return hasTouched;
	}	
}
