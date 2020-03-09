/**
 *
 */
package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

/**
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 *
 * @author Robert Clifton-Everest
 *
 */
public class Dungeon {

    private int width, height;
    private List<Entity> entities;
    private Player player;
    private GoalComponent goals; 
    private boolean complete;
    //private ArrayList<Observer> observers;
    private ArrayList<Timer> timers;

    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<>();
        this.player = null;
        this.timers = new ArrayList<Timer>();
        this.timers.add(new BombTimer());
        this.timers.add(new PotionTimer());
        this .complete = false;
    }

    public int getWidth() {
        return width;
    }
    
    public List<Entity> getEntities(){
    	return entities;
    }
    
    public boolean gameCompleted() {
    	return complete;
    }

    public int getHeight() {
        return height;
    }

    public Player getPlayer() {
        return player;
    }
    
    public GoalComponent getGoals() {
    	return goals;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }
    
    public void removeEntity(Entity entity) {
    	entities.remove(entity);
    	entity.switchBool();
    }
    
    public void setGoal(GoalComponent g) {
    	goals = g;
    }
    
    // player only have three choices each step, move, use sword or bomb 
    // so their is only three methods public 
   
    public void move(Direction d) {
    	if(player.getState().getClass().equals(Dead.class)) return;
    	if(gameCompleted()) return;
    	int newLocationX = newLocationX(player, d);
    	int newLocationY = newLocationY(player, d);
    	//System.out.println(newLocationX + " and " + newLocationY);
    	

    	//check if a boulder in newLocation first
    	moveBoulder(newLocationX, newLocationY, d);
    	
    	//check if a door in newLocation
    	openDoor(newLocationX, newLocationY);

    	//check if a player can move to the new location
    	if(checkCanInteract(newLocationX, newLocationY))
    		movePlayer(d);
    	
    	//check if a player can pick up the entity in this location
    	pickUp();
    	
    	//interact with potion
    	interactPotion();
    	
    	//interat with enemy
    	playerInteractEnemy();
    	
    	//Enemies move
    	moveEnemies();
    	
    	//check it again because enemies moved
    	playerInteractEnemy();
    	
    	//minus 1 second for every bomb and player's invincible time then check the state
    	
    	notifyAllTimer();
    	
    	//check win state and if game over and win or loss
    	//if(player.getState().getClass().equals(Dead.class)) {
    	//	System.out.println("Game Over!");
    	//}
    	if(checkWinState(goals)) {
    	//	System.out.println("You Win!");
    		complete = true;
    	}
    	//System.out.println("game: "+gameCompleted());
    	//System.out.println("player: "+ player.getState());
    }

    
    public void useSword(Direction d) {
    	if(! player.useSword()) return;
    	int attackX = newLocationX(player, d);
    	int attackY = newLocationY(player, d);
    	for(Entity e : getEntityByLocation(attackX, attackY)) {
    		if(e.getClass().equals(Enemy.class)) {
    			removeEntity(e);
    		}
    	}
    	move(null);
    }
    
    public LitBomb useBomb() {
    	if(player.useBomb()) {
    		LitBomb l = new LitBomb(player.getX(), player.getY());
    		entities.add(l);
    		move(null);
    		return l;
    	}
    	return null;
    }
    
    private void notifyAllTimer(){
    	for(Timer t : timers) {
    		t.update(this);
    	}
    }
    
    private void movePlayer(Direction d) {
    	if(d == null) return;
    	switch(d) {
	    	case UP: 
	    		player.moveUp();
	    		break;
	    	case DOWN: 
	    		player.moveDown();
	    		break;
	    	case LEFT:
	    		player.moveLeft();
	    		break;
	    	case RIGHT: 
	    		player.moveRight();
	    		break;
	    	default:
				break;
    	}
    	
    }
    
    private void moveEnemies() {
    	if(player.getState().getClass().equals(Invincible.class))
    		RunAwayStrategyForEnemies();
    	else
    		CommonStrategyForEnemies();
    }
    	
    private void moveEnemy(Enemy enemy) {
    	Direction d = enemy.moveDirection(player.getX(), player.getY());
    	int newX = newLocationX(enemy, d);
    	int newY = newLocationY(enemy, d);
    	if(checkCanInteract(newX, newY))
    		enemy.moveEnemy(d, getWidth(), getHeight());
    }
    
    private void CommonStrategyForEnemies() {
    	for(Entity e : entities) {
    		//!!!!!!!!!!!!!!!!!!!! null enetities sometimes
    		if (e == null) continue;
    		if(e.getClass().equals(Enemy.class)) {
    			Enemy enemy = (Enemy) e;
    			enemy.commonStrategy();
    	    	moveEnemy(enemy);
    		}
    	}
    }
    
    private void RunAwayStrategyForEnemies() {
    	for(Entity e : entities) {
    		//!!!!!!!!!!!!!!!!!!!! null enetities sometimes
    		if (e == null) continue;
    		if(e.getClass().equals(Enemy.class)) {
    			Enemy enemy = (Enemy) e;
    			enemy.runAwayStrategy();
    			moveEnemy(enemy);
    		}
    	}
    }

    private void openDoor(int x, int y) {
    	for(Entity e : getEntityByLocation(x, y)) {
    		if(e.getClass().equals(Door.class)) {
    			Door door = (Door) e;
    			if(player.hasKey() && door.getDoor() == player.getKeyNumber()) {
    				player.useKey();
    				door.openDoor();
    			}
    		}
    	}
    }
    
    private boolean checkWinState(GoalComponent g) {
    	if(g == null) return true;
    	if(g.getGoals().size() == 1) {
    		return checkOrGoals(g.getGoals().get(0));
    	}
    	else {
			return checkAndGoals(g);
    	}
    }
    
    private boolean checkOrGoals(GoalComponent g) {
    	if(g == null) return true;
    	if(g.getGoals().size() == 1) {
    		return checkSubgoal(g.getEnum());
    	}
    	else {
    		return checkSubgoal(g.getEnum());
    	}
    }
    
    private boolean checkAndGoals(GoalComponent g) {
    	if(g == null) return true;
    	for(GoalComponent gs : g.getGoals()) {
    		if(gs.getGoals().size() == 1) {
    			if(!checkSubgoal(gs.getEnum())) return false;
    		}
    		else {
    			if(!checkSubgoal(gs.getEnum())) return false;
    		}
    	}
    	return true;
    }
    
    private boolean checkSubgoal(ArrayList<GoalEnum> gs) {
    	for(GoalEnum ge : gs) {
			switch(ge) {
			case EXIT: 
				if(! playerOnExit())
					continue;
				return true;
			case TREASURE:
				if(findTreasure())
					continue;
				return true;
			case ENEMY:	
				if(findEnemy())
					continue;
				return true;
			case SWITCH: 
				if(! allSwitchTriggered())
					continue;
				return true;
    		}
		}
		return false;
    }
    
    private boolean allSwitchTriggered() {
    	for(Entity e : entities) {
    		//!!!!!!!!!!!!!!!!!!!! null enetities sometimes
    		if (e == null) continue;
    		if(e.getClass().equals(FloorSwitch.class)) {
    			if(! SwitchTriggered((FloorSwitch)e)) {
    				return false;
    			}
    		}
    	}
    	return true;
    }
    
    private boolean SwitchTriggered(FloorSwitch s) {
    	for(Entity e : getEntityByLocation(s.getX(), s.getY())) {
    		if (e.getClass().equals(Boulder.class)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    private boolean playerOnExit() {
    	for (Entity e : getEntityByLocation(player.getX(), player.getY())) {
			// the Exit square only can have Player and Exit itself
			// which means if other Entity on this square, then false
			if(e.getClass().equals(Player.class))
				continue;
			if(! e.getClass().equals(Exit.class))
				return false;
			else
				return true;
		}
    	return false;
    }
    
    private boolean findEnemy() {
    	for(Entity e : entities) {
    		//!!!!!!!!!!!!!!!!!!!! null enetities sometimes
    		if (e == null) continue;
    		if(e.getClass().equals(Enemy.class)) {
    			return true;
    		}
    	}
    	return false;
	}

    private boolean findTreasure() {
    	for(Entity e : entities) {
    		//!!!!!!!!!!!!!!!!!!!! null enetities sometimes
    		if (e == null) continue;
    		if(e.getClass().equals(Treasure.class)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    private boolean checkBoulder(int x, int y, Direction d) {
    	for(Entity boulder : getEntityByLocation(x, y)) {
    		if (boulder.getClass().equals(Boulder.class)) {
    			int newLocationX = newLocationX(boulder, d);
    	    	int newLocationY = newLocationY(boulder, d);
    	    	//System.out.println(newLocationX + " and "+ newLocationY);
    	    	for(Entity e : getEntityByLocation(newLocationX, newLocationY)) {
	    			if(!(e.CanInteract() &&(e.getClass().equals(FloorSwitch.class)||e.getClass().equals(Door.class)))) {
	    				return false;
	    			}
    	    	}
    	    	return true;
    		}
    	}
    	return false;
    }
    
    private void moveBoulder(int x, int y, Direction d) {
    	if (checkBoulder(x, y, d)) {
    		for(Entity e : getEntityByLocation(x, y)) {
    			if(e.getClass().equals(Boulder.class)) {
    				if(d == null) return ;
    				switch(d) {
    		    	case LEFT:
    		    		 if (e.getX() > 0)
    		    			 e.x().set(e.getX() - 1);
    		    		 break;
    		    	case RIGHT: 
    		    		 if (e.getX() < getWidth() - 1)
    		    			 e.x().set(e.getX() + 1);
    		    		 break;
    				case DOWN:
    					if (e.getY() < getHeight() - 1)
    						e.y().set(e.getY() + 1);
    					break;
    				case UP:
    					if (e.getY() > 0)
    						e.y().set(e.getY() - 1);
    					break;
    				default:
    					break;
    		    	}
    			}
    		}
    	}
    }
    
    private void interactPotion() {
    	ArrayList<Entity> es = getEntityByLocation(player.getX(), player.getY());
    	//System.out.println(es);
    	for(Entity e : es) {
    		// interact with potion
    		if (e.getClass().equals(Potion.class)) {
    			player.makeInvincible();
    			removeEntity(e);
    		}
    	}
    }
    
    private void playerInteractEnemy() {
    	// interact with enemy
    	ArrayList<Entity> es = getEntityByLocation(player.getX(), player.getY());
    	for(Entity e : es) {
			if (e.getClass().equals(Enemy.class)) {
				// if palyer invincible, makeDead() return invincible so no if condintion needed
				player.makeDead();
				if(player.getState().getClass().equals(Invincible.class)) {
					removeEntity(e);
				}
			}
    	}
    }
    
    private boolean checkCanInteract(int x, int y) {
    	for(Entity entity : getEntityByLocation(x, y)) {
    		if(entity.getClass().equals(Key.class) && player.hasKey()) {
    			return false;
    		}
    		if(! entity.CanInteract()) {
    			return false;
    		}
    	}
    	return true;
    }
    
    private ArrayList<Entity> checkCollectable(int x, int y){
    	ArrayList<Entity> es = new ArrayList<Entity>();
    	for(Entity entity : getEntityByLocation(x, y)) {
    		if(entity.Collectable())
    			es.add(entity);
    	}
    	return es;
    }
    
    private void pickUp() {
    	for(Entity e : checkCollectable(player.getX(), player.getY())) {
    		player.pickUp(e);
    		removeEntity(e);
    	}
    }
    
    public ArrayList<Entity> getEntityByLocation(int x, int y) {
    	ArrayList<Entity> es = new ArrayList<Entity>();
    	for(Entity entity: entities) {
    		//!!!!!!!!!!!!!!!!!!!! null enetities sometimes
    		if (entity == null) continue;
    		if(entity.getX() == x && entity.getY() == y) {
    			es.add(entity);
    		}
    	}
    	return es;
    }
    
    protected int newLocationX (Entity e, Direction d) {
    	if(d == null) return e.getX();
    	switch(d) {
    	case LEFT:
    		 if (e.getX() > 0)
    			 return (e.getX()-1);
    		 return (e.getX());
    	case RIGHT: 
    		 if (e.getX() < getWidth() - 1)
    	           return (e.getX()+1);
    		 return (e.getX());
		case DOWN:
			break;
		case UP:
			break;
		default:
			break;
    	}
    	return e.getX();
	}
    
    protected int newLocationY (Entity e, Direction d) {
    	if(d == null) return e.getY();
    	switch(d) {
    	case LEFT:
    		 break;
    	case RIGHT: 
    		break;
		case DOWN:
			if (e.getY() < getHeight() - 1)
   			 	return (e.getY()+1);
   		 	return (e.getY());
		case UP:
			if (e.getY() > 0)
	   			 return (e.getY()-1);
	   		 return (e.getY());
		default:
			break;
    	}
    	return e.getY();
	}
    
    protected int drinkPotion() {
    	return player.drinkPotion();
    }
}
