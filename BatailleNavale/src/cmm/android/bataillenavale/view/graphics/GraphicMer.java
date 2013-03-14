package cmm.android.bataillenavale.view.graphics;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import cmm.android.bataillenavale.BatailleNavale;
import cmm.android.bataillenavale.modele.Bateau;
import cmm.android.bataillenavale.modele.Coord2D;
import cmm.android.bataillenavale.modele.Mer;
import cmm.android.bataillenavale.utils.CmmScreenAdapter;

/**
 * Cette classe est un helper graphique.
 * Elle permet d'afficher une Mer sur l'écran.
 * Le fait qu'elle hérite de Sprite permet de lui donner une taille (width, height), et une origine (x, y).
 * L'affichage prend donc en compte les attributs héritées de Sprite.
 * @author Jonathan GEOFFROY, Samy CHAYEM
 * @version 1.0
 */
public class GraphicMer extends Sprite {
	private static Texture merText, touchedText, missedText;
	private static TextureRegion shipTextReg;
	private ArrayList<Sprite> shipSprites;
	private Mer mer;
	private boolean shipsVisible;

	public GraphicMer(Mer mer, boolean shipsVisible) {
		this.mer = mer;
		this.shipsVisible = shipsVisible;
		shipSprites = new ArrayList<Sprite>(5);

		for(Bateau b: mer.getBateaux()) {
			addGraphicboat(b);
		}
	}

	public Mer getMer() {
		return mer;
	}


	public Coord2D getCaseAt(float screenX, float screenY) {
		if(getBoundingRectangle().contains(screenX, screenY)) {
			int x = (int)( (screenX - getX()) / (getWidth() / Mer.ARRAY_SIZE) );
			int y = (int)(((getY() + getHeight()) - screenY) / (getHeight() / Mer.ARRAY_SIZE));

			return new Coord2D(x, y);
		}
		return null;
	}

	@Override
	public void draw(SpriteBatch spriteBatch) {
		float merWidth = getWidth() / Mer.ARRAY_SIZE;
		float merHeight = getHeight() / Mer.ARRAY_SIZE;
		float debX = getX(), debY = getY();
		float x, y;
		Texture t = null;
		for(int i = 0; i < Mer.ARRAY_SIZE; i++) {
			y = debY + getHeight() - (i+1) * merHeight;

			for(int j = 0; j < Mer.ARRAY_SIZE; j++) {
				x = debX + (j * merWidth);
				switch(mer.caseAt(j, i)) {
				case Mer.EMPTY:
				case Mer.BOAT_HANDLE_GOOD:
					t = merText;
					break;
				case Mer.BOAT_HANDLE_KILLED:
					t = touchedText;
					break;
				case Mer.MISSED:
					t = missedText;
					break;

				default:
					assert(false) : "cette texture n'existe pas";
				}
				
				spriteBatch.draw(t, x, y, merWidth, merHeight);
			}
		}

		if(shipsVisible) {
			for(Sprite s: shipSprites) {
				s.draw(spriteBatch);
			}
		}
	}

	public static void initialize(CmmScreenAdapter screen, TextureRegion shipTextReg) {
		/* ***** Création des textures pour le bateau et pour la mer ***** */
		GraphicMer.shipTextReg = shipTextReg;
		merText = new Texture("data/img/mer.png");
		missedText = new Texture("data/img/missed.png");
		touchedText = new Texture("data/img/touched.png");
		
		/* On place les textures dans l'ArrayList de textures transversales, afin de pouvoir les utiliser dans les autres screens */
		BatailleNavale app = screen.getApp();
		app.addTexture(merText);
		app.addTexture(missedText);
		app.addTexture(touchedText);
	}

	public static void dispose() {
		merText.dispose();
		missedText.dispose();
		touchedText.dispose();
	}
	public boolean addBateauAt(Bateau b, int x, int y, boolean horizontal) {
		if(mer.addBateauAt(b, x, y, horizontal)) {
			addGraphicboat(b);
			return true;
		}
		return false;
	}

	private void addGraphicboat(Bateau b) {
		Sprite s = new Sprite(shipTextReg);
		shipSprites.add(s);
		resizeBoat(shipSprites.size() - 1);
	}

	private void resizeBoats() {
		for(int i = 0, size = shipSprites.size(); i < size; i++) {
			shipSprites.get(i).setRegion(shipTextReg);
			resizeBoat(i);
		}
	}

	private void resizeBoat(int index) {
		float width, height;
		float widthCase = getWidth() / Mer.ARRAY_SIZE;
		float heightCase = getHeight() / Mer.ARRAY_SIZE;
		Sprite s;
		Bateau b;
		s = shipSprites.get(index);
		b = mer.getBateau(index);
		if(b.isHorizontal()) {
			width = widthCase * b.getTaille();
			height = heightCase;
		} else {
			width = widthCase;
			height = heightCase * b.getTaille();
		}
		s.setSize(width, height);
		s.setPosition(
				getX() + b.getDebX() * widthCase,
				getY() + getHeight() - (b.getDebY()+1) * heightCase
				);
		
		if(!b.isHorizontal()) {
			s.rotate90(true);
			s.setY(s.getY() - (s.getHeight() - heightCase) );
		}
	}


	@Override
	public void setSize(float width, float height) {
		super.setSize(width, height);
		resizeBoats();
	}

	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		resizeBoats();
	}

	@Override
	public void setX(float x) {
		super.setX(x);
		resizeBoats();
	}

	@Override
	public void setY(float y) {
		super.setY(y);
		resizeBoats();
	}
}
