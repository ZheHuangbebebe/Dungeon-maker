package unsw.dungeon;

import javafx.beans.property.IntegerProperty;

public class Enemy extends Entity{
	
	private EnemyStrategy strategy;

	public Enemy(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}
	
	public void commonStrategy() {
		strategy = new CommonStrategy();
	}
	
	public void runAwayStrategy() {
		strategy = new RunAwayStrategy();
	}
	
	public Direction moveDirection(int playerX, int playerY) {
		return strategy.move(playerX, playerY, getX(), getY());
	}
	
    public void moveEnemy(Direction d, int width, int height) {
    	if(d == null) return;
    	switch(d) {
    		case UP:
    			if (getY() > 0)
    	            y().set(getY() - 1);
    			break;
		case DOWN:
			if (getY() < width-1)
	            y().set(getY() + 1);
			break;
		case LEFT:
			if (getX() > 0)
	            x().set(getX() - 1);
			break;
		case RIGHT:
			if (getX() < height - 1)
	            x().set(getX() + 1);
			break;
		default:
			break;
    	}
    	
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
