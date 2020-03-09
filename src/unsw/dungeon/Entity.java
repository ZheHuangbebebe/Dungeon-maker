package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * An entity in the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public abstract class Entity {

    // IntegerProperty is used so that changes to the entities position can be
    // externally observed.
	private BooleanProperty b;
    private IntegerProperty x, y;

    /**
     * Create an entity positioned in square (x,y)
     * @param x
     * @param y
     */
    public Entity(int x, int y) {
    	this.b = new SimpleBooleanProperty(true);
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
    }

    public IntegerProperty x() {
        return x;
    }

    public IntegerProperty y() {
        return y;
    }
    
    public BooleanProperty b() {
    	return b;
    }
    
    public int getY() {
        return y().get();
    }

    public int getX() {
        return x().get();
    }
    
    public boolean getB() {
    	return b().get();
    }
    
    public void switchBool() {
    	b().set(!b.get());
    }
    
    public abstract boolean Collectable();
    
    public abstract boolean CanInteract();
}
    
    
