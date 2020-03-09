package unsw.dungeon;

import java.util.ArrayList;

public class GoalComposite implements GoalComponent {

	private ArrayList<GoalComponent> goals;
	
	public GoalComposite() {
		this.goals = new ArrayList<GoalComponent>();
	}
	@Override
	public void add(GoalComponent g) {
		goals.add(g);
	}
	
	@Override
	public ArrayList<GoalEnum> getEnum() {
		ArrayList<GoalEnum> g = new ArrayList<GoalEnum>();
		for(GoalComponent gc : goals) {
			g.addAll(gc.getEnum());
		}
		return g;
	}
	
	public ArrayList<GoalComponent> getGoals(){
		return goals;
	}
	

}
