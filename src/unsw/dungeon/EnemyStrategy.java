package unsw.dungeon;

public interface EnemyStrategy {
	public Direction move(int playerX, int playerY, int EnemyX, int EnemyY);
}
