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
 * @version 2.0
 */
public class GraphicMer extends Sprite {
	private static Texture merText, touchedText, missedText;
	private static TextureRegion shipTextReg;
	private ArrayList<Sprite> shipSprites;
	private Mer mer;
	private General general;
	private boolean isPlayer;
	private float widthCase, heightCase;

	public GraphicMer(CmmScreenAdapter screen, Mer mer, boolean isPlayer) {
		this.mer = mer;
		this.isPlayer = isPlayer;
		shipSprites = new ArrayList<Sprite>(5);

		for(Bateau b: mer.getBateaux()) {
			addGraphicboat(b);
		}

		/* ***** on place le bon général ***** */
		if(isPlayer) {
			general = new General(screen, General.PLAYER);
		}
		else {
			general = new General(screen, General.ADVERSAIRE);
		}
	}

	public Mer getMer() {
		return mer;
	}


	public Coord2D getCaseAt(float screenX, float screenY) {
		if(touchSea(screenX, screenY)) {
			int x, y;
			if(isPlayer) {
				x = (int)( (screenX - getX()) / widthCase );
				y = (int)(((getY() + getHeight()) - screenY) / heightCase);
			}
			else {
				x = (int)( (screenX - getX() - general.getWidth()) / widthCase );
				y = (int)(((getY() + getHeight()) - screenY) / heightCase);
			}
			return new Coord2D(x, y);
		}
		return null;
	}

	@Override
	public void draw(SpriteBatch spriteBatch) {
		float debX = getX(), debY = getY();
		float x, y;

		/* ***** On place la grille de la mer ***** */ 
		for(int i = 0; i < Mer.ARRAY_SIZE; i++) {
			y = debY + getHeight() - (i+1) * heightCase;

			for(int j = 0; j < Mer.ARRAY_SIZE; j++) {
				if(isPlayer)
					x = debX + (j * widthCase);
				else
					x = general.getWidth() + debX + (j * widthCase); // la mer de l'adversaire commence APRES le Sprite du général

				spriteBatch.draw(merText, x, y, widthCase, heightCase);
			}
		}

		/* ***** On place les bateaux s'il doivent être visibles ***** */
		if(isPlayer) {
			for(Sprite s: shipSprites) {
				s.draw(spriteBatch);
			}
		}

		/* ***** On place les croix là où on a tiré ***** */
		int currentCase;
		for(int i = 0; i < Mer.ARRAY_SIZE; i++) {
			y = debY + getHeight() - (i+1) * heightCase;
			for(int j = 0; j < Mer.ARRAY_SIZE; j++) {
				if(isPlayer)
					x = debX + (j * widthCase);
				else 
					x = debX + general.getWidth() + (j * widthCase);

				currentCase = mer.caseAt(j, i);
				switch(currentCase) {
				case Mer.BOAT_HANDLE_KILLED:
					spriteBatch.draw(touchedText, x, y, widthCase, heightCase);
					break;
				case Mer.MISSED:
					spriteBatch.draw(missedText, x, y, widthCase, heightCase);
					break;
				}
			}
		}

		/* ***** on affiche le général ***** */
		general.draw(spriteBatch);
	}

	public static void initialize(CmmScreenAdapter screen, TextureRegion shipTextReg) {
		/* ***** Création des textures pour le bateau et pour la mer ***** */
		GraphicMer.shipTextReg = shipTextReg;
		merText = new Texture("data/img/mer.png");
		missedText = new Texture("data/img/missed.png");
		touchedText = new Texture("data/img/touched.png");

		/* On place les textures dans l'ArrayList de textures transversales, afin de pouvoir les utiliser dans les autres screens */
		BatailleNavale app = screen.getApp();
		app.putTransversalTexture("mer", merText);
		app.putTransversalTexture("missed", missedText);
		app.putTransversalTexture("touched", touchedText);
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

		/* ***** On cherche à peindre une mer carré en prenant height * height ***** */
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
	
	public boolean touchSea(float x, float y) {
		boolean touch;
		if(isPlayer) {
			touch = x > getX() && x < getX() + Mer.ARRAY_SIZE * widthCase &&
					y < getY() + getHeight() &&  y > getY() + getHeight() - Mer.ARRAY_SIZE * heightCase;
		}
		else {
			touch = x > getX() + general.getWidth() && x < getX() + general.getWidth() + Mer.ARRAY_SIZE * widthCase &&
					y < getY() + getHeight() &&  y > getY() + getHeight() - Mer.ARRAY_SIZE * heightCase;
		}
		return touch;
	}
	
	
	@Override
	public void setSize(float width, float height) {
		super.setSize(width, height);
		/* ***** on calcule de manière à avoir une mer carré: le général prend donc le reste de la place ***** */
		float generalSize = width - height * CmmScreenAdapter.getScreenProportion();
		general.setSize(generalSize, generalSize / CmmScreenAdapter.getScreenProportion());

		/* ***** on calcule la taille de la mer ***** */
		widthCase = (getWidth() - general.getWidth()) / Mer.ARRAY_SIZE;
		heightCase = (getHeight() - general.getHeight()) * CmmScreenAdapter.getScreenProportion() / Mer.ARRAY_SIZE;
		resizeBoats();
	}

	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		resizeBoats();

		/* ***** on recalcule la place du général ***** */
		float generalX, generalY;
		if(isPlayer) {
			// On place le général en haut à droites
			generalX = getX() + getWidth() - general.getWidth();
			generalY = getY() + getHeight() - general.getHeight();
		}
		else {
			//On place le génral en bas à gauche
			generalX = getX();
			generalY = getY();
		}
		general.setPosition(generalX, generalY);
	}
	@Override
	public void setX(float x) {
		super.setX(x);
		resizeBoats();
		general.setX(getX() + getWidth() - general.getWidth());
	}

	@Override
	public void setY(float y) {
		super.setY(y);
		resizeBoats();
		general.setY(getY() + getHeight() - general.getHeight());
	}

	public General getGeneral() {
		return general;
	}
}
