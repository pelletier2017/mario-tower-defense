package test.entity.tower;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import javafx.embed.swing.JFXPanel;
import model.entity.enemy.Enemy;
import model.entity.enemy.TestEnemy;
import model.entity.tower.ArrowTower;
import model.entity.tower.BombTower;
import model.entity.tower.projectiles.Projectile;

public class BombTowerTester {

	@Test
	void testShootEnemy() {

		JFXPanel makeItWork = new JFXPanel();

		ArrayList<Projectile> projList = new ArrayList<Projectile>();
		ArrayList<Enemy> enemyList = new ArrayList<Enemy>();

		BombTower bombTower = new BombTower(200, 200, false);
		bombTower.setRange(200);
		bombTower.setReloadTime(3);
		// x axis
		// creates and enemy just out of range
		enemyList.add(new TestEnemy(100, 451, 100, 100));

		assertTrue(projList.isEmpty());
		bombTower.doAction(enemyList, projList);
		assertTrue(projList.isEmpty());

		enemyList.add(new TestEnemy(200, 450, 100, 100));

		bombTower.doAction(enemyList, projList);
		assertFalse(projList.isEmpty());

		// reloading
		projList.clear();
		while (!bombTower.canFire()) {
			bombTower.doAction(enemyList, projList);
			assertTrue(projList.isEmpty());
		}

		bombTower.doAction(enemyList, projList);
		assertFalse(projList.isEmpty());
		
		// reloading.
		while (!bombTower.canFire()) {
			bombTower.doAction(enemyList, projList);
		}

		projList.clear();
		enemyList.clear();
		// y axis
		// creates and enemy just out of range
		enemyList.add(new TestEnemy(451, 200, 100, 100));
		assertTrue(projList.isEmpty());
		bombTower.doAction(enemyList, projList);
		assertTrue(projList.isEmpty());

		enemyList.add(new TestEnemy(450, 200, 100, 100));

		bombTower.doAction(enemyList, projList);
		assertFalse(projList.isEmpty());

		// reloading.
		while (!bombTower.canFire()) {
			bombTower.doAction(enemyList, projList);
		}

		projList.clear();
		enemyList.clear();
		// corner case
		// creates and enemy just out of range
		enemyList.add(new TestEnemy(400, 400, 100, 100));
		assertTrue(projList.isEmpty());
		bombTower.doAction(enemyList, projList);
		assertTrue(projList.isEmpty());

		enemyList.add(new TestEnemy(390, 390, 100, 100));

		bombTower.doAction(enemyList, projList);
		assertFalse(projList.isEmpty());

		
		
	}
	
	@Test
	void testUpgrade() {
		BombTower bombTower = new BombTower(200, 200, false);
		Projectile initial = bombTower.getProjectile();
		bombTower.upgrade();
		assertTrue(bombTower.getRange() != 200);
		
		bombTower.upgrade();
		bombTower.upgrade();
		bombTower.upgrade();
		assertTrue(bombTower.getRange() != 200);
		
		Projectile upgraded = bombTower.getProjectile();
		assertTrue( upgraded.getDamage().getPhysicalDamage() > initial.getDamage().getPhysicalDamage());
		
	}

}
