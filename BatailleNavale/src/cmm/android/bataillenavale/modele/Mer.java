package cmm.android.bataillenavale.modele;

import java.util.ArrayList;

public class Mer {
	public static final int EMPTY = 0, BOAT_HANDLE_GOOD = 1, BOAT_HANDLE_KILLED = 2, MISSED = 3;
	public static final int ARRAY_SIZE = 8;
	public static final int NB_BOAT_HANDLES = 1 + 2 + 3 + 4 + 5;
	private int[][] mer;
	private ArrayList<Bateau> bateaux;
	private int nbHandlesTouched;

	public Mer() {
		mer = new int[ARRAY_SIZE][];
		for(int i = 0; i < ARRAY_SIZE; i++) {
			mer[i] = new int[ARRAY_SIZE]; 
		}

		setBateaux(new ArrayList<Bateau>(5));
		nbHandlesTouched = 0;
	}


	public boolean tirer(int x, int y) {
		if(mer[y][x] == BOAT_HANDLE_GOOD) {
			mer[y][x] = BOAT_HANDLE_KILLED;
			nbHandlesTouched++;
			return true;
		}
		
		//else
		mer[y][x] = MISSED;
		return false;
	}

	public boolean aPerdu() {
		return nbHandlesTouched == NB_BOAT_HANDLES;
	}


	public ArrayList<Bateau> getBateaux() {
		return bateaux;
	}


	public void setBateaux(ArrayList<Bateau> bateaux) {
		this.bateaux = bateaux;
	}

	public int getNbBoats() {
		return bateaux.size();
	}

	public int caseAt(int x, int y) {
		return mer[y][x];
	}


	public boolean addBateauAt(Bateau b, int x, int y, boolean horizontal) {
		int merX, merY;

		/* 
		 * On vérifie tous les cas où le bateau ne pourrait pas être placé ici
		 * Plus précisément, on va tester tous les cas qui font que le bateau ne peut être placé ici.
		 * Pour chacun d'eux, si la condition est vérifié, on retourne FALSE, et donc on n'affecte pas le bateau
		 * Sinon, on donne les bonnes coordonnées au bateau, et on le place.
		 */

		//Si n'est pas dans l'eau
		if(x < 0 || y < 0) {
			return false;
		}

		if(horizontal) {
			//Si au moins une case dépasse de l'eau
			if(x + b.getTaille() >= ARRAY_SIZE || y >= ARRAY_SIZE) {
				return false;
			}
			//Si au moins une case est déjà prise par un autre bateau:
			for(int i = x, max = x + b.getTaille(); i <= max; i++) {
				if(mer[y][i] != EMPTY) {
					return false;
				}
			}
		}
		else {
			//Si au moins une case dépasse de l'eau
			if(x >= ARRAY_SIZE || y >= ARRAY_SIZE - b.getTaille()){
				return false;
			}
			//Si au moins une case est déjà prise par un autre bateau:
			for(int i = y, max = y + b.getTaille(); i <= max; i++) {
				if(mer[i][x] != EMPTY) {
					return false;
				}
			}
		}


		/* OUF! Arrivé ici, on est sûr que le bateau peut rentrer! */
		//On affecte les bonnes valeurs aux attributs du bateau:
		b.setDebX(x);
		b.setDebY(y);
		b.setHorizontal(horizontal);

		//On modifie la mer afin d'avoir les bonnes informations
		merX = x;
		merY = y;
		for(int i = 0, taille = b.getTaille(); i < taille; i++) {
			if(horizontal) {
				merX = x + i;
			} else {
				merY = y + i;
			}

			mer[merY][merX] = BOAT_HANDLE_GOOD;
		}
		bateaux.add(b);
		return true;
	}


	public Bateau getBateau(int i) {
		return bateaux.get(i);
	}
}
