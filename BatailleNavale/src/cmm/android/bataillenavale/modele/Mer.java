package cmm.android.bataillenavale.modele;

import java.util.ArrayList;

public class Mer {
	public static final int EMPTY = 0, BOAT_HANDLE_GOOD = 1, BOAT_HANDLE_KILLED = 2;
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
}
