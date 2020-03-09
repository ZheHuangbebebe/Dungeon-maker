package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Loads a dungeon from a .json file.
 *
 * By extending this class, a subclass can hook into entity creation. This is
 * useful for creating UI elements with corresponding entities.
 *
 * @author Robert Clifton-Everest
 *
 */
public abstract class DungeonLoader {

    private static int keyNum = 0;
    private static int doorNum = 0;
	private JSONObject json;

    public DungeonLoader(String filename) throws FileNotFoundException {
        json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + filename)));
    }

    /**
     * Parses the JSON to create a dungeon.
     * @return
     */
    public Dungeon load() {
        int width = json.getInt("width");
        int height = json.getInt("height");

        Dungeon dungeon = new Dungeon(width, height);

        JSONArray jsonEntities = json.getJSONArray("entities");

        for (int i = 0; i < jsonEntities.length(); i++) {
            loadEntity(dungeon, jsonEntities.getJSONObject(i));
        }
        dungeon.setGoal(loadGoal(json));
        return dungeon;
    }
    
    private GoalComponent loadGoal(JSONObject json) {
		 GoalComponent gc = new GoalComposite();
		 JSONObject jsonGoals = null;
		 if(json.has("goal-condition"))
			 jsonGoals = json.getJSONObject("goal-condition");
    		 if(jsonGoals == null) {
	        	 String g = json.getString("goal");
	        	 switch (g) {
	        	 case "exit":
	        		 GoalComponent exit = new Goal(GoalEnum.EXIT);
	        		 gc = exit;
	        		 break;
	        	 case "treasure":
	        		 GoalComponent treasure = new Goal(GoalEnum.TREASURE);
	        		 gc = treasure;
	        		 break;
	        	 case "enemies":
	        		 GoalComponent enemies = new Goal(GoalEnum.ENEMY);
	        		 gc = enemies;
	        		 break;
	        	 case "boulders":
	        		 GoalComponent fs = new Goal(GoalEnum.SWITCH);
	        		 gc = fs;
	        		 break;
	        	 }
    		 }
    		 else if(jsonGoals.getString("goal").equals("OR")) {
	         	JSONArray goals  = jsonGoals.getJSONArray("subgoals");
	         	GoalComponent composites = new GoalComposite();
	         	for(int i=0;i<goals.length();i++) {
	         		//System.out.println(goals.getJSONObject(i));
	         		composites.add(loadGoal(goals.getJSONObject(i)));
	         	}
	         	gc.add(composites);
	         	
	         }
    		 else if(jsonGoals.getString("goal").equals("AND")) {
	         	JSONArray goals  = jsonGoals.getJSONArray("subgoals");
	         	for(int i=0;i<goals.length();i++) {
	         		gc.add(loadGoal(goals.getJSONObject(i)));
	         	}
	         }
    		 else if(jsonGoals != null) {
    			 String g = jsonGoals.getString("goal");
	        	 switch (g) {
	        	 case "exit":
	        		 GoalComponent exit = new Goal(GoalEnum.EXIT);
	        		 gc = exit;
	        		 break;
	        	 case "treasure":
	        		 GoalComponent treasure = new Goal(GoalEnum.TREASURE);
	        		 gc = treasure;
	        		 break;
	        	 case "enemies":
	        		 GoalComponent enemies = new Goal(GoalEnum.ENEMY);
	        		 gc = enemies;
	        		 break;
	        	 case "boulders":
	        		 GoalComponent fs = new Goal(GoalEnum.SWITCH);
	        		 gc = fs;
	        		 break;
	        	 }
    		 }
		return gc;
    }
    

    private void loadEntity(Dungeon dungeon, JSONObject json) {
        String type = json.getString("type");
        int x = json.getInt("x");
        int y = json.getInt("y");

        Entity entity = null;
        switch (type) {
        case "player":
            Player player = new Player(dungeon, x, y);
            dungeon.setPlayer(player);
            onLoad(player);
            entity = player;
            break;
        case "wall":
            Wall wall = new Wall(x, y);
            onLoad(wall);
            entity = wall;
            break;
        case "exit":
        	Exit exit = new Exit(x,y);
        	onLoad(exit);
        	entity = exit;
        	break;
        case "boulder":
        	Boulder boulder = new Boulder(x,y);
        	onLoad(boulder);
        	entity = boulder;
        	break;
        case "switch":
        	FloorSwitch floorSwitch = new FloorSwitch(x,y);
        	onLoad(floorSwitch);
        	entity = floorSwitch;
        	break;	
        	
        case "treasure":
        	Treasure treasure = new Treasure(x,y);
        	onLoad(treasure);
        	entity = treasure;
        	break;
        case "sword":
        	Sword sword = new Sword(x,y);
        	onLoad(sword);
        	entity = sword;
        	break;
        case "bomb":
        	UnlitBomb bomb = new UnlitBomb(x,y);
        	onLoad(bomb);
        	entity = bomb;
        	break;
        case "enemy":
        	Enemy enemy = new Enemy(x,y);
        	onLoad(enemy);
        	entity = enemy;
        	break;
        case "invincibility":
        	Potion potion = new Potion (x,y);
        	onLoad(potion);
        	entity = potion;
        	break;
        case "key":
        	Key key = new Key(x,y,keyNum);
        	keyNum++;
        	onLoad(key);
        	entity = key;
        	break;
        case "door":
        	Door door = new Door(x,y,doorNum);
        	doorNum++;
        	onLoad(door);
        	entity = door;
        	break;
        // TODO Handle other possible entities
        }
        dungeon.addEntity(entity);
    }

    public abstract void onLoad(Entity player);

    public abstract void onLoad(Wall wall);
    
    public abstract void onLoad(Exit exit);
    
    public abstract void onLoad(Boulder boulder);
    
    public abstract void onLoad(FloorSwitch floorswitch);
    
    public abstract void onLoad(Treasure treasure);
    
    public abstract void onLoad(Door door);
    
    public abstract void onLoad(Key key);
    
    public abstract void onLoad(UnlitBomb unlitBomb);
    
    public abstract void onLoad(LitBomb litBomb);
    
    public abstract void onLoad(Enemy enemy);
    
    public abstract void onLoad(Sword sword);
    
    public abstract void onLoad(Potion potion);

    // TODO Create additional abstract methods for the other entities

}
