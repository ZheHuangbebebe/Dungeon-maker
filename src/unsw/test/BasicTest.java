package unsw.test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import unsw.dungeon.*;
public class BasicTest {
	Dungeon dungeon33 = new Dungeon(3,3);
	Dungeon dungeon13 = new Dungeon(1,3);
	Player p = new Player(dungeon33, 0,0);
	Goal exit = new Goal(GoalEnum.EXIT);
	Goal enemy = new Goal(GoalEnum.ENEMY);
	Goal treasure = new Goal(GoalEnum.TREASURE);
	Goal floorSwitch = new Goal(GoalEnum.SWITCH);
	Direction right = Direction.RIGHT;
	Direction down = Direction.DOWN;
	Direction left = Direction.LEFT;
	Direction up = Direction.UP;
	
	@Test
	public void playerStoppedByWall() {
		dungeon33.setPlayer(p);
		dungeon33.setGoal(exit);
		Wall w = new Wall(0,1);
		dungeon33.addEntity(w);
		Exit e = new Exit(0, 2);
		dungeon33.addEntity(e);
		dungeon33.move(Direction.DOWN);
		dungeon33.move(Direction.DOWN);
		// if player can be stopped by wall, player should still at 0, 0
		assertEquals(0, p.getX());
		assertEquals(0, p.getY());
	}
	
	@Test
	public void playerEnterExit() {
		dungeon33.setPlayer(p);
		dungeon33.setGoal(exit);
		Wall w = new Wall(0,1);
		dungeon33.addEntity(w);
		Exit e = new Exit(0, 2);
		dungeon33.addEntity(e);
		dungeon33.move(right);
		dungeon33.move(down);
		dungeon33.move(down);
		dungeon33.move(left);
		// player enter Exit, game complete
		assertEquals(0, p.getX());
		assertEquals(2, p.getY());
		assertTrue(dungeon33.gameCompleted());
	}
	
	@Test
	public void playerCollectTreasure() {
		dungeon33.setPlayer(p);
		dungeon33.setGoal(treasure);
		Treasure t = new Treasure(0,1);
		dungeon33.addEntity(t);
		dungeon33.move(down);
		//System.out.println(dungeon33.getEntities());
		assertTrue(dungeon33.gameCompleted());
	}
	
	
	@Test
	public void playerStoppedByClosedDoor() {
		dungeon33.setPlayer(p);
		dungeon33.setGoal(exit);
		Exit e = new Exit (0, 2);
		dungeon33.addEntity(e);
		// the 3rd int number is the no. of the door
		Door d = new Door(0, 1, 1);
		dungeon33.addEntity(d);
		dungeon33.move(down);
		dungeon33.move(down);
		assertEquals(0, p.getX());
		assertEquals(0, p.getY());
	}
	
	@Test
	public void playerCollectKey() {
		dungeon33.setPlayer(p);
		Key k = new Key(0, 1, 1);
		dungeon33.addEntity(k);
		dungeon33.move(down);
		assertTrue(p.hasKey());
	} 
	
	@Test
	public void playerPushBoulderOnSwitch() {
		dungeon33.setPlayer(p);
		Boulder b = new Boulder(0, 1);
		dungeon33.addEntity(b);
		dungeon33.setGoal(floorSwitch);
		FloorSwitch f = new FloorSwitch(0,2);
		dungeon33.addEntity(f);
		dungeon33.move(down);
		assertEquals(0, b.getX());
		assertEquals(2, b.getY());
		assertEquals(0, p.getX());
		assertEquals(1, p.getY());
		assertTrue(dungeon33.gameCompleted());
	}
	
	@Test
	public void playerStandOnSwitch() {
		dungeon33.setPlayer(p);
		dungeon33.setGoal(floorSwitch);
		FloorSwitch f = new FloorSwitch(0,1);
		dungeon33.addEntity(f);
		dungeon33.move(down);
		assertEquals(1, p.getY());
		assertFalse(dungeon33.gameCompleted());
	}
	
	@Test
	public void playerCollectUnLitBomb() {
		dungeon33.setPlayer(p);
		UnlitBomb b = new UnlitBomb(0, 1);
		dungeon33.addEntity(b);
		dungeon33.move(down);
		assertTrue(p.useBomb());
		assertFalse(p.useBomb());
	}
	
	@Test
	public void playerPutLitBomb() {
		dungeon33.setPlayer(p);
		dungeon33.addEntity(p);
		UnlitBomb b = new UnlitBomb(0,1);
		dungeon33.addEntity(b);
		dungeon33.move(down);
		dungeon33.useBomb();
		dungeon33.move(up);
		dungeon33.move(down);
		dungeon33.move(up);
		//System.out.println(p.getState());
		assertTrue(p.getState().getClass().equals(Dead.class));
	}
	
	@Test
	public void playerKilledByEnemy() {
		dungeon33.setPlayer(p);
		Enemy e = new Enemy(0, 1);
		dungeon33.addEntity(e);
		dungeon33.move(down);
		assertTrue(p.getState().getClass().equals(Dead.class));
		assertEquals(0, e.getX());
		assertEquals(1, e.getY());
	}
	
	@Test
	public void playerUseSwordKillEnemy() {
		dungeon33.setPlayer(p);
		dungeon33.setGoal(enemy);
		Sword s = new Sword(0, 1);
		dungeon33.addEntity(s);
		Enemy e = new Enemy(2, 2);
		dungeon33.addEntity(e);
		dungeon33.move(down);
		// should kill nothing but enemy should move to right side of player
		dungeon33.useSword(right);
		// should kill enemy
		dungeon33.useSword(right);
		assertEquals(3, s.getCapable());
		assertTrue(dungeon33.gameCompleted());
	}
	
	@Test
	public void playerDrinkPotionKillEnmey() {
		dungeon13.setPlayer(p);
		Enemy e = new Enemy(0, 2);
		dungeon13.addEntity(e);
		Potion po = new Potion(0, 1);
		dungeon13.setGoal(enemy);
		dungeon13.addEntity(po);
		dungeon13.move(down);
		dungeon13.move(down);
		assertTrue(p.getState().getClass().equals(Invincible.class));
		assertTrue(dungeon13.gameCompleted());
	}
	
	@Test
	public void playerOpenDoor() {
		dungeon33.setPlayer(p);
		Door d = new Door(1,1,1);
		Key k = new Key(1,0,1);
		dungeon33.addEntity(d);
		dungeon33.addEntity(k);
		dungeon33.move(right);
		dungeon33.move(down);
		dungeon33.move(down);
		dungeon33.move(up);
		assertTrue(d.getState());
		assertEquals(1, p.getX());
		assertEquals(1, p.getY());
	}
	
	@Test
	public void playerPutLitBombDestoryBoulder() {
		dungeon33.setPlayer(p);
		dungeon33.addEntity(p);
		UnlitBomb b = new UnlitBomb(0,1);
		Boulder bo = new Boulder(1,1);
		dungeon33.addEntity(b);
		dungeon33.addEntity(bo);
		dungeon33.move(down);
		dungeon33.useBomb();
		dungeon33.move(up);
		dungeon33.move(right);
		dungeon33.move(right);
		dungeon33.move(left);
		dungeon33.move(down);
		assertEquals(1,p.getX());
		assertEquals(1,p.getY());
		assertEquals(1, dungeon33.getEntities().size());
	}
	
	@Test
	public void playerPushBoulderCrossOpenedDoor () {
		dungeon33.setPlayer(p);
		Door d = new Door(1,2,1);
		Key k = new Key(1,0,1);
		dungeon33.addEntity(d);
		dungeon33.addEntity(k);
		dungeon33.move(right);
		dungeon33.move(down);
		dungeon33.move(down);
		dungeon33.move(up);
		dungeon33.move(up);
		Boulder b = new Boulder(1,1);
		dungeon33.addEntity(b);
		dungeon33.move(down);
		assertEquals(b.getX(), d.getX());
		assertEquals(b.getY(), d.getY());
	}
	
	@Test
	public void BoluderStoppedByOtherEntities() {
		dungeon33.setPlayer(p);
		dungeon33.addEntity(p);
		Treasure t = new Treasure(3,0);
		Boulder b = new Boulder(2,0);
		dungeon33.addEntity(t);
		dungeon33.addEntity(b);
		dungeon33.move(right);
		assertEquals(3, dungeon33.getEntities().size());
		assertEquals(2, b.getX());
		assertEquals(1, p.getX());
		assertEquals(3, t.getX());
	}
	
	@Test
	public void EnmeyDestoryedByBomb() {
		dungeon33.setPlayer(p);
		dungeon33.addEntity(p);
		Enemy e = new Enemy(2,2);
		LitBomb b = new LitBomb(1,1);
		dungeon33.addEntity(e);
		dungeon33.addEntity(b);
		dungeon33.move(up);
		dungeon33.move(up);
		dungeon33.move(up);
		assertEquals(0, p.getX());
		assertEquals(0, p.getY());
		assertEquals(1, dungeon33.getEntities().size());
	}
	
	@Test
	public void PlayerInvincibleWithLitBomb() {
		dungeon33.setPlayer(p);
		dungeon33.addEntity(p);
		UnlitBomb b = new UnlitBomb(0,1);
		Potion po = new Potion(1,1);
		dungeon33.addEntity(b);
		dungeon33.addEntity(po);
		dungeon33.move(down);
		dungeon33.useBomb();
		dungeon33.move(right);
		dungeon33.move(left);
		dungeon33.move(up);
		assertEquals(1, dungeon33.getEntities().size());
		assertTrue(p.getState().getClass().equals(Invincible.class));
		dungeon33.move(up);
		Potion po2 = new Potion(1,0);
		dungeon33.addEntity(po2);
		dungeon33.move(right);
		assertTrue(p.getState().getClass().equals(Invincible.class));
		dungeon33.move(up);
		dungeon33.move(up);
		dungeon33.move(up);
		dungeon33.move(up);
		dungeon33.move(up);
		assertTrue(p.getState().getClass().equals(Regular.class));
		
	}
	
	
	
	
}
