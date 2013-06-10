package cmm.android.bataillenavale.modele.ia;


import cmm.android.bataillenavale.modele.Coord2D;
import cmm.android.bataillenavale.modele.Mer;
import java.util.Stack;

/**
 * Intelligence artificielle niveau "Moyen"
 *
 * @author Jonathan GEOFFROY, Samy CHAYEM
 * @version 2.0
 */
public class IntelligenceArtificielleMoyen extends IntelligenceArtificielle {

	private Stack<Coord2D> caseATester;

	public IntelligenceArtificielleMoyen() {
		super();
		caseATester = new Stack<Coord2D>();
	}

	public IntelligenceArtificielleMoyen(Mer joueur) {
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
			x = (int) (Math.random() * Mer.ARRAY_SIZE);
	        y = (int) (Math.random() * Mer.ARRAY_SIZE);
	        caseTir = new Coord2D(x, y);
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
