package test.entity.tower;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import javafx.embed.swing.JFXPanel;
import javafx.scene.canvas.Canvas;
import model.entity.enemy.Enemy;
import model.entity.enemy.TestEnemy;
import model.entity.tower.ArrowTower;
import model.entity.tower.focus.FocusLast;
import model.entity.tower.projectiles.Projectile;

public class ArrowTowerTester {

	@Test
	void testShootEnemy() {
		
		JFXPanel makeItWork = new JFXPanel();
		
		ArrayList<Projectile> projList = new ArrayList<Projectile>();
		ArrayList<Enemy> enemyList = new ArrayList<Enemy>();

		ArrowTower arrowTower = new ArrowTower(200, 200, false);
		arrowTower.setRange(200);
		arrowTower.setReloadTime(3);
		// x axis
		// creates and enemy just out of range
		enemyList.add(new TestEnemy(100, 451, 100,100));

		assertTrue(projList.isEmpty());
		arrowTower.doAction(enemyList, projList);
		assertTrue(projList.isEmpty());

		enemyList.add(new TestEnemy(200, 450, 100,100));

		arrowTower.doAction(enemyList, projList);
		assertFalse(projList.isEmpty());

		// reloading
		projList.clear();
		while(! arrowTower.canFire()) {
			arrowTower.doAction(enemyList, projList);
			assertTrue(projList.isEmpty());
		}

		arrowTower.doAction(enemyList, projList);
		assertFalse(projList.isEmpty());
		arrowTower.doAction(enemyList, projList);
		arrowTower.doAction(enemyList, projList);
		arrowTower.doAction(enemyList, projList);

		projList.clear();
		enemyList.clear();
		// y axis
		// creates and enemy just out of range
		enemyList.add(new TestEnemy(451, 200,100,100));
		assertTrue(projList.isEmpty());
		arrowTower.doAction(enemyList, projList);
		assertTrue(projList.isEmpty());

		enemyList.add(new TestEnemy(450, 200, 100, 100));

		arrowTower.doAction(enemyList, projList);
		assertFalse(projList.isEmpty());
		
		// reloading.
		while(! arrowTower.canFire()) {
			arrowTower.doAction(enemyList, projList);
		}
		

		arrowTower.setFocus(new FocusLast());
		projList.clear();
		enemyList.clear();
		// corner case
		// creates and enemy just out of range
		enemyList.add(new TestEnemy(400, 400, 100, 100));
		assertTrue(projList.isEmpty());
		arrowTower.doAction(enemyList, projList);
		assertTrue(projList.isEmpty());

		enemyList.add(new TestEnemy(390, 390, 100, 100));

		arrowTower.doAction(enemyList, projList);
		assertFalse(projList.isEmpty());
		
		

	}
	
	@Test
	void testUpgrade() {
		ArrowTower arrowTower = new ArrowTower(200, 200, false);
		Projectile initial = arrowTower.getProjectile();
		arrowTower.upgrade();
		assertTrue(arrowTower.getRange() != 200);
		
		arrowTower.upgrade();
		arrowTower.upgrade();
		arrowTower.upgrade();
		assertTrue(arrowTower.getRange() != 200);
		
		Projectile upgraded = arrowTower.getProjectile();
		arrowTower.getProjectile().draw((new Canvas()).getGraphicsContext2D(), 700);
		assertTrue( upgraded.getDamage().getPhysicalDamage() > initial.getDamage().getPhysicalDamage());
		
	}

}
