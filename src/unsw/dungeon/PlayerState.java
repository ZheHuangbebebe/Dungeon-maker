package unsw.dungeon;

public abstract class PlayerState {
	public abstract PlayerState makeDead();
	public abstract PlayerState makeRegular();
	public abstract PlayerState makeInvincible();
}
