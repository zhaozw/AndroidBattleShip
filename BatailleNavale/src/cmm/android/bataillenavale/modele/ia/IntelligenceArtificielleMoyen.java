package cmm.android.bataillenavale.modele.ia;


import cmm.android.bataillenavale.modele.Coord2D;
import cmm.android.bataillenavale.modele.Mer;
import cmm.android.bataillenavale.view.graphics.General;
import cmm.android.bataillenavale.view.graphics.GraphicMer;
import com.badlogic.gdx.Gdx;
import java.util.Random;

/**
 * Intelligence artificielle niveau "Moyen"
 *
 * @author Jonathan GEOFFROY, Samy CHAYEM
 * @version 2.0
 */
public class IntelligenceArtificielleMoyen extends IntelligenceArtificielle {

	public IntelligenceArtificielleMoyen() {
		super();
	}
    public IntelligenceArtificielleMoyen(Mer joueur) {
		super(joueur);
	}

	protected Random r = new Random();
    protected int x1 = r.nextInt(Mer.ARRAY_SIZE);
    protected int y1 = r.nextInt(Mer.ARRAY_SIZE);
    boolean touched = false;

    @Override
    public Coord2D adversairePlay() {
        
        Gdx.app.log("game", "computer joue: " + x1 + "-" + y1);

//        touched = graphicJoueur.getMer().tirer(x1, y1);
//        if (touched) {
//            System.out.println(x1 +";"+y1);
//            Gdx.app.log("game", "touché!");
//            graphicAdversaire.getGeneral().setStatus(General.HAPPY);
//            graphicJoueur.getGeneral().setStatus(General.UNHAPPY);
//            x1 = r.nextInt((x1+1)-(x1-1))+(x1-1);
//            x1 = r.nextInt(3) + (x1 - 1);
//            y1 = r.nextInt((y1+1)-(y1-1))+(y1-1);
//            y1 = r.nextInt(3) + (y1 - 1);
//            System.out.println(x1 +";"+y1);
//
//        } else {
//            System.out.println("pas touche");
//            System.out.println(x1 +";"+y1);
//            Gdx.app.log("game", "dans l'eau!");
//            graphicAdversaire.getGeneral().setStatus(General.CLASSIC);
//            graphicJoueur.getGeneral().setStatus(General.CLASSIC);
//            x1 = r.nextInt(Mer.ARRAY_SIZE);
//            y1 = r.nextInt(Mer.ARRAY_SIZE);
//            System.out.println(x1 +";"+y1);
//        }
        return new Coord2D(0,0);
    }
}
