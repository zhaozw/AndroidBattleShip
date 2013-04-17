package cmm.android.bataillenavale.modele.ia;

import cmm.android.bataillenavale.modele.Coord2D;
import cmm.android.bataillenavale.modele.Mer;
import cmm.android.bataillenavale.view.graphics.GraphicMer;

/**
 * Intelligence artificielle niveau "Facile"
 *
 * @author Jonathan GEOFFROY, Samy CHAYEM
 * @version 2.0
 */
public class IntelligenceArtificielleFacile extends IntelligenceArtificielle {

	public IntelligenceArtificielleFacile() {
		super();
	}
    public IntelligenceArtificielleFacile(Mer joueur) {
		super(joueur);
	}

	@Override
    public Coord2D adversairePlay() {
        int x = (int) (Math.random() * Mer.ARRAY_SIZE);
        int y = (int) (Math.random() * Mer.ARRAY_SIZE);

        return new Coord2D(x, y);
    }
}
