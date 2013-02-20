package cmm.android.bataillenavale.modele;

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
}
