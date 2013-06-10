package cmm.android.bataillenavale.modele.ia;

import java.util.Stack;

import cmm.android.bataillenavale.modele.Coord2D;
import cmm.android.bataillenavale.modele.Mer;

/**
 * Intelligence artificielle niveau "Difficile"
 * @author Jonathan GEOFFROY, Samy CHAYEM
 * @version 2.0
 */
public class IntelligenceArtificielleDifficile extends IntelligenceArtificielle{
	private Stack<Coord2D> caseATester;
	
	public IntelligenceArtificielleDifficile() {
		super();
		caseATester = new Stack<Coord2D>();
	}
	
	public IntelligenceArtificielleDifficile(Mer joueur) {
		super(joueur);
		caseATester = new Stack<Coord2D>();
	}

	@Override
	public Coord2D adversairePlay() {
		int x;
		int y;
		Coord2D caseTir = null;
		int caseTest;
		if(caseATester.isEmpty()) {
			do {
				/* On ne tire que sur une case sur 2 */
				x = (int)(Math.random() * (Mer.ARRAY_SIZE));
				y = (int)(Math.random() * (Mer.ARRAY_SIZE)) - 1;
				
				if(x % 2 != 0) {
					y += 1;
				}
				/* On gère ce petit cas qui pourrait planter l'appli pour pas grand chose */
				else if (y == -1) { 
					y = 1;
				}
				
				caseTir = new Coord2D(x, y);
				caseTest = joueur.caseAt(x, y);
				if(caseTest == Mer.BOAT_HANDLE_GOOD) {
					addAtester(caseTir);
				}
			} while(caseTest == Mer.BOAT_HANDLE_KILLED || caseTest == Mer.MISSED); //Tant qu'on est pas sur une case qu'on a déjà visé
		}
		else {
			caseTir = caseATester.pop();
			caseTest = joueur.caseAt(caseTir.x, caseTir.y);
			if(caseTest == Mer.BOAT_HANDLE_GOOD) {
				addAtester(caseTir);
			}
		}
		
		return caseTir;
	}

	private void addAtester(Coord2D coord) {
		Coord2D[] tests = {	
				new Coord2D(coord.x - 1	, coord.y),
				new Coord2D(coord.x + 1	, coord.y),
				new Coord2D(coord.x		, coord.y - 1),
				new Coord2D(coord.x		, coord.y + 1)
		};
		int caseTest;
		Coord2D t;
		
		for(int i = 0; i < tests.length; i++) {
			t = tests[i];
			if(t.x >= 0 && t.x < Mer.ARRAY_SIZE &&
				t.y >= 0 && t.y < Mer.ARRAY_SIZE) {
				caseTest = joueur.caseAt(t.x, t.y);
				if(caseTest != Mer.BOAT_HANDLE_KILLED && caseTest != Mer.MISSED) {
					caseATester.push(t);
				}
			}
		}
	}
}
