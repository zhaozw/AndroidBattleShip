package cmm.android.bataillenavale.utils.menus;

/**
 * Permet de créer un menu sur lequel on peut cliquer, c'est-à-dire un "item" de CmmMenuGroup
 * Chaque menu doit réimplémenter la callback onTouched(), qui, comme son nom l'indique, permet de lancer les bonnes instructions lorsque l'on clique sur ce menu. 
 * @author Jonathan GEOFFROY, Samy CHAYEM
 * @version 1.0
 */
public abstract class CmmMenu {
	private String name;
	
	public CmmMenu(String name) {
		this.name = name;
	}

	public CmmMenu() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public abstract boolean onTouched();
}
