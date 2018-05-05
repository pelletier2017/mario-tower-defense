package test.entity.tower;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import javafx.embed.swing.JFXPanel;
import model.entity.enemy.Enemy;
import model.entity.enemy.TestEnemy;
import model.entity.tower.BounceTower;
import model.entity.tower.IceTower;
import model.entity.tower.projectiles.Projectile;

public class IceTowerTester {
	
	@Test
	void testShootEnemy() {

		JFXPanel makeItWork = new JFXPanel();

		ArrayList<Projectile> projList = new ArrayList<Projectile>();
		ArrayList<Enemy> enemyList = new ArrayList<Enemy>();

		IceTower iceTower = new IceTower(200, 200, false);
		iceTower.setRange(200);
		iceTower.setReloadTime(3);
		// x axis
		// creates and enemy just out of range
		enemyList.add(new TestEnemy(100, 451, 100, 100));

		assertTrue(projList.isEmpty());
		iceTower.doAction(enemyList, projList);
		assertTrue(projList.isEmpty());

		enemyList.add(new TestEnemy(200, 450, 100, 100));

		iceTower.doAction(enemyList, projList);
		assertFalse(projList.isEmpty());

		// reloading
		projList.clear();
		while (!iceTower.canFire()) {
			iceTower.doAction(enemyList, projList);
			assertTrue(projList.isEmpty());
		}

		iceTower.doAction(enemyList, projList);
		assertFalse(projList.isEmpty());
		
		// reloading.
		while (!iceTower.canFire()) {
			iceTower.doAction(enemyList, projList);
		}

		projList.clear();
		enemyList.clear();
		// y axis
		// creates and enemy just out of range
		enemyList.add(new TestEnemy(451, 200, 100, 100));
		assertTrue(projList.isEmpty());
		iceTower.doAction(enemyList, projList);
		assertTrue(projList.isEmpty());

		enemyList.add(new TestEnemy(450, 200, 100, 100));

		iceTower.doAction(enemyList, projList);
		assertFalse(projList.isEmpty());

		// reloading.
		while (!iceTower.canFire()) {
			iceTower.doAction(enemyList, projList);
		}

		projList.clear();
		enemyList.clear();
		// corner case
		// creates and enemy just out of range
		enemyList.add(new TestEnemy(400, 400, 100, 100));
		assertTrue(projList.isEmpty());
		iceTower.doAction(enemyList, projList);
		assertTrue(projList.isEmpty());

		enemyList.add(new TestEnemy(390, 390, 100, 100));

		iceTower.doAction(enemyList, projList);
		assertFalse(projList.isEmpty());

		iceTower.upgrade();
	}
	
	@Test
	void testUpgrade() {
		IceTower iceTower = new IceTower(200, 200, false);
		Projectile initial = iceTower.getProjectile();
		iceTower.upgrade();
		assertTrue(iceTower.getRange() != 200);
		
		iceTower.upgrade();
		iceTower.upgrade();
		iceTower.upgrade();
		assertTrue(iceTower.getRange() != 200);
		
		Projectile upgraded = iceTower.getProjectile();
		assertTrue( upgraded.getDamage().getPhysicalDamage() > initial.getDamage().getPhysicalDamage());
		
	}

}
