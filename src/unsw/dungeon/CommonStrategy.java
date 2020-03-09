package unsw.dungeon;

public class CommonStrategy implements EnemyStrategy {

	@Override
	public Direction move(int playerX, int playerY, int EnemyX, int EnemyY) {
		int curr = distance(playerX, playerY, EnemyX, EnemyY);
		int up = distance(playerX, playerY, EnemyX, EnemyY-1);
		int down = distance(playerX, playerY, EnemyX, EnemyY+1);
		int left = distance(playerX, playerY, EnemyX-1, EnemyY);
		int right = distance(playerX, playerY, EnemyX+1, EnemyY);
		if(up < curr)
			return Direction.UP;
		else if (down < curr)
			return Direction.DOWN;
		else if (left < curr)
			return Direction.LEFT;
		else if (right < curr)
			return Direction.RIGHT;
		return null;
			
		
	}

	public int distance(int x1, int y1, int x2, int y2) {
		return Math.abs(x1 - x2) + Math.abs(y1 - y2);
	}
}
