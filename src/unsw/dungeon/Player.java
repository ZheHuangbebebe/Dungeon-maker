package unsw.dungeon;

import java.util.ArrayList;

/**
 * The player entity
 * @author Robert Clifton-Everest
 *
 */
public class Player extends Entity {

    private Dungeon dungeon;
    private PlayerState state;
    private int stateTimer;
    private Sword sword = null;
    private Key key = null;
    private ArrayList<UnlitBomb> bombs = new ArrayList<UnlitBomb> ();
    /**
     * Create a player positioned in square (x,y)
     * @param x
     * @param y
     */
    public Player(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        this.state = new Regular();
        stateTimer = -1;
    }
    
    public boolean useSword() {
    		if(sword != null) {
    			if(sword.use() == 0)
    				sword = null;
    			return true;
    		}
    	return false;
    }
    
    public boolean useBomb() {
    	if(bombs.isEmpty()) {
    		return false;
    	}
    	bombs.remove(0);
    	return true;
    }
    
    public void useKey() {
    	key = null;
    }
    
    public boolean hasKey() {
    	if(key == null)
    		return false;
    	return true;
    }
    
    public int getKeyNumber() {
    	return key.getKey();
    }

    public void moveUp() {
        if (getY() > 0)
            y().set(getY() - 1);
    }

    public void moveDown() {
        if (getY() < dungeon.getHeight() - 1)
            y().set(getY() + 1);
    }

    public void moveLeft() {
        if (getX() > 0)
            x().set(getX() - 1);
    }

    public void moveRight() {
        if (getX() < dungeon.getWidth() - 1)
            x().set(getX() + 1);
    }
    
    public void makeRegular() {
    	state = state.makeRegular();
    	stateTimer = -1;
    }
    
    public void makeDead() {
    	state = state.makeDead();
    }
    
    public void makeInvincible() {
    	state = state.makeInvincible();
    	stateTimer = 5;
    }
    
    public PlayerState getState() {
    	return state;
    }

	public void removeSword() {
		// TODO Auto-generated method stub
	}
	
	public void pickUp(Entity e) {
		if(e.getClass().equals(Sword.class)) {
			sword = (Sword) e;
			return;
		}
		else if(e.getClass().equals(Key.class)) {
			key = (Key) e;
			return;
		}
		else if(e.getClass().equals(UnlitBomb.class)) {
			bombs.add((UnlitBomb) e);
			return;
		}
		else
			return;
	}

	@Override
	public boolean Collectable() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean CanInteract() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public int drinkPotion() {
		if(stateTimer > 0)
			stateTimer --;
		return stateTimer;
	}
}
