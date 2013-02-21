package cmm.android.bataillenavale.view.graphics;

import com.badlogic.gdx.graphics.g2d.Sprite;

import cmm.android.bataillenavale.modele.Coord2D;
import cmm.android.bataillenavale.modele.Mer;

public class GraphicMer extends Sprite {
	private Mer mer;
	
	public GraphicMer(Mer mer) {
		this.mer = mer;
	}

	public Mer getMer() {
		return mer;
	}

	
	public Coord2D getCaseAt(float screenX, float screenY) {
		if(getBoundingRectangle().contains(screenX, screenY)) {
			int x = (int)( (screenX - getX()) / (getWidth() / Mer.ARRAY_SIZE) );
			int y = (int)( (screenY - (getY() + getHeight())) / (getHeight() / Mer.ARRAY_SIZE) );
			return new Coord2D(x, y);
		}
		return null;
	}
	 
}
