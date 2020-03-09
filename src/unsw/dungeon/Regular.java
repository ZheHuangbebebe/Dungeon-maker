package unsw.dungeon;

public class Regular extends PlayerState {

	@Override
	public PlayerState makeDead() {
		// TODO Auto-generated method stub
		return new Dead();
	}

	@Override
	public PlayerState makeRegular() {
		// TODO Auto-generated method stub
		return new Regular();
	}

	@Override
	public PlayerState makeInvincible() {
		// TODO Auto-generated method stub
		return new Invincible();
	}
	
}
