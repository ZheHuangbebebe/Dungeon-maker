package unsw.dungeon;

import java.util.ArrayList;

public interface GoalComponent {

	public void add(GoalComponent g);
	public ArrayList<GoalEnum> getEnum();
	public ArrayList<GoalComponent> getGoals();
}
