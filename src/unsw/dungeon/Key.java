package unsw.dungeon;

import javafx.beans.property.IntegerProperty;

public class Key extends Entity{
	private int number;
	public Key(int x, int y, int n) {
		super(x, y);
		this.number = n;
		// TODO Auto-generated constructor stub
	}
	public int getKey() {
		return number;
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
