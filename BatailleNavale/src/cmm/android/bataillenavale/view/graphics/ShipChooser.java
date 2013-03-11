package cmm.android.bataillenavale.view.graphics;

import java.util.ArrayList;

import cmm.android.bataillenavale.modele.Bateau;
import cmm.android.bataillenavale.modele.Mer;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ShipChooser extends Sprite {
	private ArrayList<Bateau> bateaux;
	private ArrayList<Sprite> spriteBateaux;
	private int selectedIndex;

	public ShipChooser(TextureRegion bateauTextRegion) {
		Bateau b;
		Sprite s;
		bateaux = new ArrayList<Bateau>(5);
		spriteBateaux = new ArrayList<Sprite>(5);
		
		for(int i = 1; i <= 5; i++) {
			b = new Bateau(i);
			bateaux.add(b);

			s = new Sprite(bateauTextRegion);
			spriteBateaux.add(s);
		}
		selectedIndex = -1;
	}

	public void draw(SpriteBatch batch) {
		for(Sprite s: spriteBateaux) {
			s.draw(batch);
		}
	}

	private void replaceBoats() {
		Sprite s;
		
		/* ***** calcul des diverses coordonnées et tailles afin de placer les bateaux de façon sympa! ***** */
		float x = getX(), y = getY();
		float width = getWidth();
		float height = getHeight();
		float boatHeight = height / 5f;
		float boatWidth;
		float boatX = 0;
//		int taille;
		for(int i = 0, size = spriteBateaux.size(); i < size; i++) {
//			taille = bateaux.get(i).getTaille(); //FIXME je m'en sers pas de ça?
			boatWidth = (i+1) * (width/Mer.NB_BOAT_HANDLES);
			s = spriteBateaux.get(i);
			s.setSize(boatWidth, boatHeight);
			s.setPosition(x + boatX, y + height - boatHeight);
			boatX += boatWidth;
		}
	}

	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		replaceBoats();
	}

	@Override
	public void setX(float x) {
		super.setX(x);
		replaceBoats();
	}

	@Override
	public void setY(float y) {
		super.setY(y);
		replaceBoats();
	}

	public boolean setSelectedBoat(float x, float y) {
		if(getBoundingRectangle().contains(x, y)) {
			selectedIndex = -1; //Réinitialisation si on ne trouve pas.
			Sprite s;
			for(int i = 0, size = spriteBateaux.size(); i < size; i++) {
				s = spriteBateaux.get(i);
				//Si on a trouvé sur quel bateau on a cliqué:
				if(s.getBoundingRectangle().contains(x, y)) {
					selectedIndex = i;
					return true;
				}
			}
		}
		return false;
	}
	
	public Bateau getSelectedBateau() {
		if(selectedIndex >= 0)
			return bateaux.get(selectedIndex);
		return null;
	}
	
	public void rmSelectedBateau() {
		if(selectedIndex > -1) {
			bateaux.remove(selectedIndex);
			spriteBateaux.remove(selectedIndex);
			selectedIndex = -1;
		}
	}
}
