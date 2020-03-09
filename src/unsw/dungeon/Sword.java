package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;

public class Sword extends Entity{

	private int capable;
	
	public Sword(int x, int y) {
		super(x, y);
		capable = 5;
		// TODO Auto-generated constructor stub
	}
	
	public int use() {
		capable --;
		return capable;
	}
	
	public int getCapable() {
		return capable;
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
    public BooleanProperty b() {
    	return super.b();
    }
	@Override
    public boolean getB() {
    	return super.getB();
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
