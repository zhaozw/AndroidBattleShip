package cmm.android.bataillenavale.controlers;

import cmm.android.bataillenavale.BatailleNavale;
import cmm.android.bataillenavale.modele.Coord2D;
import cmm.android.bataillenavale.modele.Bateau;
import cmm.android.bataillenavale.modele.Coord2F;
import cmm.android.bataillenavale.view.graphics.GraphicMer;
import cmm.android.bataillenavale.view.graphics.ShipChooser;
import cmm.android.bataillenavale.view.screens.PlaceBateauScreen;
import cmm.android.bataillenavale.view.screens.VersusComputerGameScreen;

import com.badlogic.gdx.InputAdapter;

/**
 * Controleur permettant de placer les bateaux d'un ShipChooser.
 * Ce contrôleur utilise le ShipChooser du screen PlaceBateauScreen passé en paramètre du constructeur.
 * @author Jonathan GEOFFROY, Samy CHAYEM
 * @version 2.0
 */
public class PlaceBateauListener extends InputAdapter {
	protected PlaceBateauScreen placeBateau;

	public PlaceBateauListener(PlaceBateauScreen placeBateau) {
		super();
		this.placeBateau = placeBateau;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		Coord2F coord = PlaceBateauScreen.intToFloatCoord(screenX, screenY);
		GraphicMer graphicMer = placeBateau.getGraphicMer();
		ShipChooser shipChooser = placeBateau.getShipChooser();

		if(graphicMer.touchSea(coord.x, coord.y)) {
			Bateau selected = shipChooser.getSelectedBateau();
			Coord2D clickedCase = graphicMer.getCaseAt(coord.x, coord.y);

			if(selected != null && clickedCase != null) {
				if(graphicMer.addBateauAt(selected, clickedCase.x, clickedCase.y, shipChooser.getHorizontal())) {
					shipChooser.rmSelectedBateau();
				}
			}

			/* ***** Si tous les bateaux sont placés: ****** */
			if(graphicMer.getMer().getNbBoats() == Bateau.NB_BOATS) {
				onAllShipPlaced();
			}
		}
		return false;
	}

	/**
	 * Callback appelée lorsque tous les bateaux sont placées.
	 */
	protected void onAllShipPlaced() {
		VersusComputerGameScreen screen = (VersusComputerGameScreen)placeBateau.getApp().getScreen(BatailleNavale.VERSUS_COMPUTER_GAME);
		screen.setJoueur(placeBateau.getGraphicMer().getMer());
		placeBateau.getApp().setScreen(screen);
	}
}
