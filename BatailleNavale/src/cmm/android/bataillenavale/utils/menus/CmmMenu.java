package cmm.android.bataillenavale.utils.menus;

public abstract class CmmMenu {
	private String name;
	
	public CmmMenu(String name) {
		this.name = name;
	}

	public CmmMenu() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public abstract boolean onTouched();
}
