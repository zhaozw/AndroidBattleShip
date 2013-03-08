package cmm.android.bataillenavale.modele;

public class Bateau {
	public static final int NB_BOATS = 5;
	private int taille;
	private boolean horizontal;
	private int debX, debY;
	
	public Bateau(int taille) {
		super();
		assert(taille >= 1 && taille <= 5);
		this.taille = taille;
		this.horizontal = true;
		this.debX = -1;
		this.debY = -1;
	}
	
	public Bateau(int taille, boolean horizontal, int debX, int debY) {
		super();
		assert(taille >= 1 && taille <= 5);
		this.taille = taille;
		this.horizontal = horizontal;
		this.debX = debX;
		this.debY = debY;
	}
	
	public int getTaille() {
		return taille;
	}

	public void setTaille(int taille) {
		assert(taille >= 1 && taille <= 5);
		this.taille = taille;
	}

	public boolean isHorizontal() {
		return horizontal;
	}

	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}

	public int getDebX() {
		return debX;
	}

	public void setDebX(int debX) {
		assert(debX >= 0 && debX <= Mer.ARRAY_SIZE);
		this.debX = debX;
	}

	public int getDebY() {
		return debY;
	}

	public void setDebY(int debY) {
		assert(debY >= 0 && debY <= Mer.ARRAY_SIZE);
		this.debY = debY;
	}
}
