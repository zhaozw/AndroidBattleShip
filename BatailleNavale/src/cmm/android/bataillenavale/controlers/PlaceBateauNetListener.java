package cmm.android.bataillenavale.controlers;

import com.badlogic.gdx.Gdx;
import cmm.android.bataillenavale.view.screens.net.PlaceBateauNetScreen;

/**
 * Controleur permettant de placer les bateaux d'un ShipChooser lors d'une partie en réseau.
 * Ce contrôleur utilise le ShipChooser du screen PlaceBateauScreen passé en paramètre du constructeur.
 * La principale différence avec le PlaceBateauListener est qu'il ne lance pas directement la partie lorsque tous les bateaux sont placés:
 * il attends que l'autre joueur est également placé ces propres bateaux.
 * @author Jonathan GEOFFROY, Samy CHAYEM
 * @version 2.0
 */
public class PlaceBateauNetListener extends PlaceBateauListener {

	public PlaceBateauNetListener(PlaceBateauNetScreen placeBateau) {
		super(placeBateau);
	}

	@Override
	protected void onAllShipPlaced() {
		placeBateau.getApp().getClient().sendTCP("WFP");//WFP: Wait For Playing
		PlaceBateauNetScreen pbn = (PlaceBateauNetScreen)placeBateau;
		pbn.setAllShipPlaced();
		pbn.merPlacee(); //On dit qu'on a placé une mer
		Gdx.input.setInputProcessor(null); //On supprime les Listeners pour pas avoir de conflits
	}
}
