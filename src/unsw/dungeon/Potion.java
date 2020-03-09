package unsw.dungeon;

public class Potion extends Entity {

	public Potion(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean Collectable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean CanInteract() {
		// TODO Auto-generated method stub
		return true;
	}
}
