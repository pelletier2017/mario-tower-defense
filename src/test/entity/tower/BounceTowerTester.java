package test.entity.tower;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import javafx.embed.swing.JFXPanel;
import model.entity.enemy.Enemy;
import model.entity.enemy.TestEnemy;
import model.entity.tower.ArrowTower;
import model.entity.tower.BounceTower;
import model.entity.tower.projectiles.Projectile;

public class BounceTowerTester {

	@Test
	void testShootEnemy() {
		
		JFXPanel makeItWork = new JFXPanel();
		
		ArrayList<Projectile> projList = new ArrayList<Projectile>();
		ArrayList<Enemy> enemyList = new ArrayList<Enemy>();

		BounceTower bounceTower = new BounceTower(200, 200, false);
		bounceTower.setRange(200);
		bounceTower.setReloadTime(3);
		// x axis
		// creates and enemy just out of range
		enemyList.add(new TestEnemy(100, 451, 100,100));

		assertTrue(projList.isEmpty());
		bounceTower.doAction(enemyList, projList);
		assertTrue(projList.isEmpty());

		enemyList.add(new TestEnemy(200, 450, 100,100));

		bounceTower.doAction(enemyList, projList);
		assertFalse(projList.isEmpty());

		// reloading
		projList.clear();
		while(! bounceTower.canFire()) {
			bounceTower.doAction(enemyList, projList);
			assertTrue(projList.isEmpty());
		}

		bounceTower.doAction(enemyList, projList);
		assertFalse(projList.isEmpty());
		bounceTower.doAction(enemyList, projList);
		bounceTower.doAction(enemyList, projList);
		bounceTower.doAction(enemyList, projList);

		projList.clear();
		enemyList.clear();
		// y axis
		// creates and enemy just out of range
		enemyList.add(new TestEnemy(451, 200,100,100));
		assertTrue(projList.isEmpty());
		bounceTower.doAction(enemyList, projList);
		assertTrue(projList.isEmpty());

		enemyList.add(new TestEnemy(450, 200, 100, 100));

		bounceTower.doAction(enemyList, projList);
		assertFalse(projList.isEmpty());
		
		// reloading.
		while(! bounceTower.canFire()) {
			bounceTower.doAction(enemyList, projList);
		}

		projList.clear();
		enemyList.clear();
		// corner case
		// creates and enemy just out of range
		enemyList.add(new TestEnemy(400, 400, 100, 100));
		assertTrue(projList.isEmpty());
		bounceTower.doAction(enemyList, projList);
		assertTrue(projList.isEmpty());

		enemyList.add(new TestEnemy(390, 390, 100, 100));

		bounceTower.doAction(enemyList, projList);
		assertFalse(projList.isEmpty());
		
		

	}
	
	@Test
	void testUpgrade() {
		BounceTower bounceTower = new BounceTower(200, 200, false);
		Projectile initial = bounceTower.getProjectile();
		bounceTower.upgrade();
		assertTrue(bounceTower.getRange() != 200);
		
		bounceTower.upgrade();
		bounceTower.upgrade();
		bounceTower.upgrade();
		assertTrue(bounceTower.getRange() != 200);
		
		Projectile upgraded = bounceTower.getProjectile();
		assertTrue( upgraded.getDamage().getPhysicalDamage() > initial.getDamage().getPhysicalDamage());
		
	}

}
