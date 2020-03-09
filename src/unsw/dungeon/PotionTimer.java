package unsw.dungeon;

public class PotionTimer implements Timer{

	@Override
	public void update(Dungeon d) {
		if(d.drinkPotion() == 0) {
			Player p = d.getPlayer();
			p.makeRegular();
    	}
	}
}
