package cmm.android.bataillenavale.modele;

/**
 * Modèle encapsulant le joueur et l'adversaire
 * @author Jonathan GEOFFROY, Samy CHAYEM
 * @version 1.0
 */
public class Plateau {
	private Mer joueur;
	private Mer adversaire;
	private boolean joueurTour;
	
	public Plateau() {
		setJoueur(new Mer());
		setAdversaire(new Mer());
	}

	public Mer getJoueur() {
		return joueur;
	}

	public void setJoueur(Mer joueur) {
		this.joueur = joueur;
	}

	public Mer getAdversaire() {
		return adversaire;
	}

	public void setAdversaire(Mer adversaire) {
		this.adversaire = adversaire;
	}

	public boolean isJoueurTour() {
		return joueurTour;
	}

	public void setJoueurTour(boolean joueurTour) {
		this.joueurTour = joueurTour;
	}
	
	/**
	 * Permet de changer la personne qui joue
	 * Si c'était au joueur de jouer, ce n'est plus à lui après cette méthode
	 * Si ce n'était pas au joueur de jouer, c'est désormais à lui après cette méthode.
	 */
	public void switchTour() {
		joueurTour = !joueurTour;
	}
}
