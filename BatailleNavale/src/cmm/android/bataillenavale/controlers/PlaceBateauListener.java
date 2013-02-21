package cmm.android.bataillenavale.controlers;

import cmm.android.bataillenavale.modele.Coord2D;
import cmm.android.bataillenavale.modele.Bateau;
import cmm.android.bataillenavale.modele.Coord2F;
import cmm.android.bataillenavale.modele.Mer;
import cmm.android.bataillenavale.view.graphics.GraphicMer;
import cmm.android.bataillenavale.view.graphics.ShipChooser;
import cmm.android.bataillenavale.view.screens.PlaceBateauScreen;

import com.badlogic.gdx.InputAdapter;

public class PlaceBateauListener extends InputAdapter {
	private PlaceBateauScreen placeBateau;
	
	public PlaceBateauListener(PlaceBateauScreen placeBateau) {
		super();
		this.placeBateau = placeBateau;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		Coord2F coord = PlaceBateauScreen.intToFloatCoord(screenX, screenY);
		GraphicMer graphicMer = placeBateau.getGraphicMer();
		ShipChooser shipChooser = placeBateau.getShipChooser();
		
		if(graphicMer.getBoundingRectangle().contains(coord.x, coord.y)) {
			Bateau selected = shipChooser.getSelectedBateau();
			Coord2D clickedCase = graphicMer.getCaseAt(screenX, screenY);
			if(selected != null && canPutBoatOnCase(selected, clickedCase.x, clickedCase.y)) {
				graphicMer.getMer().addBateauAt(selected, clickedCase.x, clickedCase.y);
				
			}
		}
		return false;
	}

	private boolean canPutBoatOnCase(Bateau bateau, int caseX, int caseY) {
		Mer mer = placeBateau.getGraphicMer().getMer();
		boolean horizontal = bateau.isHorizontal();
		int x, y;
		for(int i = 0, taille = bateau.getTaille(); i < taille; i++) {
			if(horizontal) {
				x = caseX + i;
				y = caseY;
			}else {
				x = caseX;
				y = caseY + i;
			}
			
			if(mer.caseAt(x, y) != Mer.EMPTY) {
				return false;
			}
		}
		
		return true;
	}
}
