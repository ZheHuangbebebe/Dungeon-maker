package unsw.dungeon;

import javafx.beans.property.IntegerProperty;

public class UnlitBomb extends Entity{

	public UnlitBomb(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public IntegerProperty x() {
		// TODO Auto-generated method stub
		return super.x();
	}

	@Override
	public IntegerProperty y() {
		// TODO Auto-generated method stub
		return super.y();
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return super.getY();
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return super.getX();
	}

	@Override
	public boolean Collectable() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean CanInteract() {
		// TODO Auto-generated method stub
		return true;
	}

}
