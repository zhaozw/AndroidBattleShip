package cmm.android.bataillenavale.view.graphics;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import cmm.android.bataillenavale.modele.Bateau;
import cmm.android.bataillenavale.modele.Coord2D;
import cmm.android.bataillenavale.modele.Mer;
import cmm.android.bataillenavale.utils.CmmScreenAdapter;

public class GraphicMer extends Sprite {
	private static Texture merText, touchedText, emptyText;
	private static TextureRegion shipTextReg;
	private static CmmScreenAdapter screen;
	private ArrayList<Sprite> shipSprites;
	private Mer mer;

	public GraphicMer(Mer mer) {
		this.mer = mer;
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
			int y= (int)(((getY() + getHeight()) - screenY) / (getHeight() / Mer.ARRAY_SIZE));

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

		for(int i = 0; i < Mer.ARRAY_SIZE; i++) {
			y = debY + getHeight() - (i+1) * merHeight;

			for(int j = 0; j < Mer.ARRAY_SIZE; j++) {
				x = debX + (j * merWidth);
				spriteBatch.draw(merText, x, y, merWidth, merHeight);
			}
		}

		for(Sprite s: shipSprites) {
			s.draw(spriteBatch);
		}
	}

	public static void initialize(CmmScreenAdapter screen, TextureRegion shipTextReg) {
		GraphicMer.screen = screen;

		/* ***** Création des textures pour le bateau et pour la mer ***** */
		GraphicMer.shipTextReg = shipTextReg;
		merText = new Texture("data/img/mer.png");
		screen.addTexture(merText);

		/* ***** on place tous les bateaux ***** */
		/*
		float sizeCasePlateau = 0.5f / Mer.ARRAY_SIZE;
		Sprite bateau;
		for(int i = 0; i < Bateau.NB_BOATS; i++) {
			bateau = new Sprite(bateauTextReg);
			bateau.setSize(sizeCasePlateau * (i+1), sizeCasePlateau);
			bateau.setPosition(-0.5f, 0.5f - (i+1) * bateau.getHeight());
			screen.addSprite(bateau);
		}
		 */
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
				getY() + getHeight() - b.getDebY() * heightCase
				);
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
