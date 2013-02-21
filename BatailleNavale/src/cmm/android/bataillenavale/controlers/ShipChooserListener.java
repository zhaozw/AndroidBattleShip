package cmm.android.bataillenavale.controlers;

import com.badlogic.gdx.InputAdapter;

import cmm.android.bataillenavale.modele.Coord2F;
import cmm.android.bataillenavale.utils.CmmScreenAdapter;
import cmm.android.bataillenavale.view.graphics.ShipChooser;

public class ShipChooserListener extends InputAdapter {
	private ShipChooser shipChooser;
	
	public ShipChooserListener(ShipChooser shipChooser) {
		this.shipChooser = shipChooser;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		Coord2F coord = CmmScreenAdapter.intToFloatCoord(screenX, screenY);
		return	shipChooser.setSelectedBoat(coord.x, coord.y);
	}	
}
