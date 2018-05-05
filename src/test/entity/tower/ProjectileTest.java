package test.entity.tower;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.embed.swing.JFXPanel;
import model.entity.enemy.Enemy;
import model.entity.enemy.TestEnemy;
import model.entity.tower.projectiles.Arrow;
import model.entity.tower.projectiles.Bomb;
import model.entity.tower.projectiles.Bounce;
import model.entity.tower.projectiles.IceWave;
import model.entity.tower.projectiles.ProjectileType;

public class ProjectileTest {

	@Test
    void testHitandMovement() {
		
		JFXPanel makeItWork = new JFXPanel();
		// creates and enemy at 200, 200,  width = 100, height = 100; 
		TestEnemy testEnemy = new TestEnemy(200,200, 100, 100);
		testEnemy.setHP(50);
		
		Arrow tester = new Arrow(testEnemy, 200, 200, 1, false);
		// method for tester sets the speed.
		tester.setSpeed(30);
		
		tester.doAction(null);
		assertFalse(testEnemy.getHP() == 50);
		// arrow should need to be removed so it will not do damage.
		testEnemy.setHP(50);
		tester.doAction(null);
		assertEquals(testEnemy.getHP(),50);
		
		// distance around 141 from target enemy
		// arrows speed is 30, will need 5 calls to hit
		testEnemy.setHP(50);
		tester = new Arrow(testEnemy, 100, 100, 1, false);
		tester.setSpeed(30);
		
		tester.doAction(null);
		tester.doAction(null);
		tester.doAction(null);
		tester.doAction(null);
		assertEquals(testEnemy.getHP(),50);
		tester.doAction(null);
		assertFalse(testEnemy.getHP() == 50);
	}
	
	@Test
    void testBombHit() {
		
		JFXPanel makeItWork = new JFXPanel();
		// creates and enemy at 200, 200,  width = 100, height = 100; 
		TestEnemy testEnemy = new TestEnemy(200,200, 100, 100);
		testEnemy.setHP(50);
		
		List<Enemy> enemyList = new ArrayList<Enemy>();
		enemyList.add(testEnemy);
		
		Bomb testBomb = new Bomb(testEnemy, 200, 200, 1, false);
		// method for tester sets the speed.
		testBomb.setSpeed(30);
		
		testBomb.doAction(enemyList);
		assertFalse(testEnemy.getHP() == 50);
		// bomb should need to be removed so it will not do damage.
		testEnemy.setHP(50);
		testBomb.doAction(enemyList);
		assertEquals(testEnemy.getHP(),50);
		
		// distance around 141 from target enemy
		// arrows speed is 30, will need 5 calls to hit
		testEnemy.setHP(50);
		testBomb = new Bomb(testEnemy, 100, 100, 1, false);
		testBomb.setSpeed(30);
		
		testBomb.doAction(enemyList);
		testBomb.doAction(enemyList);
		testBomb.doAction(enemyList);
		testBomb.doAction(enemyList);
		assertEquals(testEnemy.getHP(),50);
		testBomb.doAction(enemyList);
		testEnemy.getHP();
		
	}
	
	
	@Test
    void testBombAOE() {
		
		JFXPanel makeItWork = new JFXPanel();
		// creates and enemy at 200, 200,  width = 100, height = 100; 
		TestEnemy testEnemy = new TestEnemy(200,200, 100, 100);
		testEnemy.setHP(50);
		
		List<Enemy> enemyList = new ArrayList<Enemy>();
		enemyList.add(testEnemy);
		
		// will target testEnemy's current location
		Bomb testBomb = new Bomb(testEnemy, 200, 200, 1, false);
		// method for tester sets the speed.
		testBomb.setSpeed(30);
		testBomb.setAOE(100);
		
		// enemy moves just outside of range so no damage delt
		testEnemy.moveTo(351, 200);
		testBomb.doAction(enemyList);
		assertTrue(testEnemy.getHP() == 50);
		
		// bomb should need to be removed so it will not do damage.
		testEnemy.setHP(50);
		testBomb.doAction(enemyList);
		assertEquals(testEnemy.getHP(),50);
		
		
		
		TestEnemy inRange1 = new TestEnemy(200,200, 100, 100);
		inRange1.setHP(50);
		
		testBomb = new Bomb(inRange1, 200, 200, 1, false);
		// method for tester sets the speed.
		testBomb.setSpeed(30);
		testBomb.setAOE(100);
		
		TestEnemy inRange2 = new TestEnemy(320,200, 100, 100);
		inRange2.setHP(50);
		
		TestEnemy inRange3 = new TestEnemy(200, 90, 100, 100);
		inRange3.setHP(50);
		
		TestEnemy outOfRange1 = new TestEnemy(400,200, 100, 100);
		outOfRange1.setHP(50);
		
		TestEnemy outOfRange2 = new TestEnemy(200,400, 100, 100);
		outOfRange2.setHP(50);
		
		enemyList.clear();
		enemyList.add(inRange1);
		enemyList.add(inRange2);
		enemyList.add(inRange3);
		enemyList.add(outOfRange1);
		enemyList.add(outOfRange2);
		
		testBomb.doAction(enemyList);
		
		assertFalse(inRange1.getHP() == 50);
		assertFalse(inRange2.getHP() == 50);
		assertFalse(inRange3.getHP() == 50);
		assertTrue(outOfRange1.getHP() == 50);
		assertTrue(outOfRange2.getHP() == 50);
		
	}
	
	
	@Test
    void testIceWaveAOE() {
		
		JFXPanel makeItWork = new JFXPanel();
		// creates and enemy at 200, 200,  width = 100, height = 100; 
		TestEnemy testEnemy = new TestEnemy(200,200, 100, 100);
		testEnemy.setHP(50);
		
		List<Enemy> enemyList = new ArrayList<Enemy>();
		enemyList.add(testEnemy);
		
		// will target testEnemy's current location
		IceWave testIce = new IceWave( 200, 200, 100, 1, false);
		// enemy moves just outside of range so no damage delt
		testEnemy.moveTo(351, 200);
		testIce.doAction(enemyList);
		assertTrue(testEnemy.getHP() == 50);
		
		// bomb should need to be removed so it will not do damage.
		testEnemy.setHP(50);
		testIce.doAction(enemyList);
		assertEquals(testEnemy.getHP(),50);
		
		testIce = new IceWave( 200, 200, 100, 1, false);
		// method for tester sets the speed.
		testIce.setSpeed(30);
		
		TestEnemy inRange1 = new TestEnemy(200,200, 100, 100);
		inRange1.setHP(50);
		
		TestEnemy inRange2 = new TestEnemy(320,200, 100, 100);
		inRange2.setHP(50);
		
		TestEnemy inRange3 = new TestEnemy(200, 90, 100, 100);
		inRange3.setHP(50);
		
		TestEnemy outOfRange1 = new TestEnemy(400,200, 100, 100);
		outOfRange1.setHP(50);
		
		TestEnemy outOfRange2 = new TestEnemy(200,400, 100, 100);
		outOfRange2.setHP(50);
		
		enemyList.clear();
		enemyList.add(inRange1);
		enemyList.add(inRange2);
		enemyList.add(inRange3);
		enemyList.add(outOfRange1);
		enemyList.add(outOfRange2);
		
		testIce.doAction(enemyList);
		
		assertFalse(inRange1.getHP() == 50);
		assertFalse(inRange2.getHP() == 50);
		assertFalse(inRange3.getHP() == 50);
		assertTrue(outOfRange1.getHP() == 50);
		assertTrue(outOfRange2.getHP() == 50);
		
	}
	
	
	
	@Test
    void testBounce() {
		
		JFXPanel makeItWork = new JFXPanel();
		// creates and enemy at 200, 200,  width = 100, height = 100; 
		TestEnemy testEnemy = new TestEnemy(200,200, 100, 100);
		testEnemy.setHP(500);
		
		List<Enemy> enemyList = new ArrayList<Enemy>();
		enemyList.add(testEnemy);
		
		// will target testEnemy
		Bounce testBounce = new Bounce(testEnemy, 200, 200, 1, false);
		testBounce.setBounceRange(100);
		
		testBounce.doAction(enemyList);
		assertFalse(testEnemy.getHP() == 500);
		
		// wont bounce to the same enemy
		testEnemy.setHP(500);
		testBounce.doAction(enemyList);
		assertEquals(testEnemy.getHP(),500);
		
		
		TestEnemy inRange1 = new TestEnemy(200,200, 100, 100);
		inRange1.setHP(500);
		
		testBounce = new  Bounce(inRange1, 200, 200, 1, false);
		// method for tester sets the speed.
		testBounce.setSpeed(30);
		testBounce.setBounceRange(50);
		
		
		
		TestEnemy inRange2 = new TestEnemy(210,200, 100, 100);
		inRange2.setHP(500);
		
		TestEnemy inRange3 = new TestEnemy(200, 210, 100, 100);
		inRange3.setHP(500);
		
		TestEnemy outOfRange1 = new TestEnemy(400,200, 100, 100);
		outOfRange1.setHP(500);
		
		TestEnemy outOfRange2 = new TestEnemy(200,400, 100, 100);
		outOfRange2.setHP(500);
		
		
		
		enemyList.clear();
		enemyList.add(inRange1);
		enemyList.add(inRange2);
		
		//enemyList.add(inRange3);
		//enemyList.add(outOfRange1);
		//enemyList.add(outOfRange2);
		
		testBounce.doAction(enemyList);
		
		assertFalse(inRange1.getHP() == 500);
		assertTrue(inRange2.getHP() == 500);
		
		inRange1.setHP(500);
		testBounce.doAction(enemyList);
		assertTrue(inRange1.getHP() == 500);
		assertFalse(inRange2.getHP() == 500);
		
		
		testBounce = new  Bounce(inRange1, 200, 200, 1, false);
		// method for tester sets the speed.
		testBounce.setSpeed(30);
		testBounce.setBounceRange(50);
		
		enemyList.clear();
		enemyList.add(inRange1);
		enemyList.add(outOfRange1);
		enemyList.add(outOfRange2);
		
		inRange1.setHP(500);
		
		testBounce.doAction(enemyList);
		
		assertFalse(inRange1.getHP() == 500);
		assertTrue(outOfRange1.getHP() == 500);
		assertTrue(outOfRange2.getHP() == 500);
		
		
		inRange1.setHP(500);
		testBounce.doAction(enemyList);
		
		assertTrue(inRange1.getHP() == 500);
		assertTrue(outOfRange1.getHP() == 500);
		assertTrue(outOfRange2.getHP() == 500);
		
		testBounce = new  Bounce(inRange1, 200, 200, 1, false);
		// method for tester sets the speed.
		testBounce.setSpeed(30);
		testBounce.setBounceRange(50);
		
		testBounce.upgrade();
		
	}
	
	
	@Test
    void testGettersSetters() {
		
		JFXPanel makeItWork = new JFXPanel();
		// creates and enemy at 200, 200,  width = 100, height = 100; 
		TestEnemy testEnemy = new TestEnemy(200,200, 100, 100);
		testEnemy.setHP(50);
		
		Arrow tester = new Arrow(testEnemy, 200, 200, 1, false);
		
		assertFalse( tester.isRemovable());
		
		assertTrue( tester.getSpeed() > 0);
		
		assertTrue( tester.getDamage().getPhysicalDamage() > 0.0);
		
		assertTrue( tester.getProjectileType() == ProjectileType.ARROW);
		
		
	}
	
	
	
}
