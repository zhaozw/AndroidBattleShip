package cmm.android.bataillenavale.modele.ia;

import cmm.android.bataillenavale.modele.Coord2D;
import cmm.android.bataillenavale.modele.Mer;

/**
 * Interface de l'intelligence artificielle
 * @author Jonathan GEOFFROY, Samy CHAYEM
 * @version 1.0
 */
public abstract class IntelligenceArtificielle {
	protected Mer joueur;
	
	public IntelligenceArtificielle() {
		joueur = null;
	}
	
	public IntelligenceArtificielle(Mer joueur) {
		this.joueur = joueur;
	}
	
    public abstract Coord2D adversairePlay();

	public Mer getJoueur() {
		return joueur;
	}

	public void setJoueur(Mer joueur) {
		this.joueur = joueur;
	}
}
