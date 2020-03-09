package unsw.dungeon;

import java.util.ArrayList;

public class Goal implements GoalComponent {

	private GoalEnum goal;
	
	public Goal(GoalEnum goal) {
		this.goal = goal;
	}
	@Override
	public void add(GoalComponent g) {}
	
	@Override
	public ArrayList<GoalEnum> getEnum() {
		ArrayList<GoalEnum> g = new ArrayList<GoalEnum>();
		g.add(goal);
		return g;
	}
	@Override
	public ArrayList<GoalComponent> getGoals() {
		ArrayList<GoalComponent> g = new ArrayList<GoalComponent>();
		g.add(this);
		return g;
	}
	

}
