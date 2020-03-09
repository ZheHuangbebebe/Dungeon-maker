package unsw.dungeon;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class LitBomb extends Entity{
	private IntegerProperty timer;
	public LitBomb(int x, int y) {
		super(x, y);
		this.timer = new SimpleIntegerProperty(4);
		// TODO Auto-generated constructor stub
	}

	public IntegerProperty burnDown() {
		timer.set(timer.get() -1);
		return timer;
	}
	
	public int getTimer() {
		return timer.get();
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
		return true;
	}
}
