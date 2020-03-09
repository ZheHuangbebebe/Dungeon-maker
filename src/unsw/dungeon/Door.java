package unsw.dungeon;

import javafx.beans.property.IntegerProperty;

public class Door extends Entity{

	public int number;
	public boolean state;
	public Door(int x, int y, int n) {
		super(x, y);
		this.number = n;
		this.state = false;
		// TODO Auto-generated constructor stub
	}
	
	public boolean getState() {
		return state;
	}
	
	public int getDoor() {
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
		return false;
	}

	@Override
	public boolean CanInteract() {
		// TODO Auto-generated method stub
		if(state)
			return true;
		return false;
	}
	
	public void openDoor() {
		this.state = true;
		switchBool();
	}
}
