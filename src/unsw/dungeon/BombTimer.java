package unsw.dungeon;

import java.util.ArrayList;

public class BombTimer implements Timer{

	@Override
	public void update(Dungeon d) {
		for(LitBomb bomb : getBombs(d)) {
			if(bomb.burnDown().get() == 0) {
				bombExplode(d, bomb);
			}
		}
	}
	
	public ArrayList<LitBomb> getBombs(Dungeon d){
		ArrayList<LitBomb> bombs = new ArrayList<LitBomb>();
		for(Entity e : d.getEntities()) {
			//!!!!!!!!!!!!!!!!!!!! null enetities sometimes
    		if (e == null) continue;
    		if(e.getClass().equals(LitBomb.class)) {
    			bombs.add((LitBomb) e);
    		}
    	}
		return bombs;
	}
	
    private void bombExplode(Dungeon d, LitBomb bomb) {
    	d.removeEntity(bomb);
    	int up = d.newLocationY(bomb, Direction.UP);
    	int down = d.newLocationY(bomb, Direction.DOWN);
    	int left = d.newLocationX(bomb, Direction.LEFT);
    	int right = d.newLocationX(bomb, Direction.RIGHT);
    	ArrayList<Entity> es = new ArrayList<Entity>();
    	es.addAll(d.getEntityByLocation(bomb.getX(), up));
    	es.addAll(d.getEntityByLocation(bomb.getX(), down));
    	es.addAll(d.getEntityByLocation(left, bomb.getY()));
    	es.addAll(d.getEntityByLocation(right, bomb.getY()));
    	es.addAll(d.getEntityByLocation(bomb.getX(), bomb.getY()));
    	for(Entity e : es) {
    		if(e.getClass().equals(Boulder.class) || e.getClass().equals(Enemy.class)) {
    			d.removeEntity(e);
    			
    		}
    		else if(e.getClass().equals(Player.class)){
    			Player p = (Player) e;
    			p.makeDead();
    		}
    	}
	}

}
