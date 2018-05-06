package model.entity.enemy.boss;

import model.entity.enemy.Enemy;
import model.entity.enemy.EnemyType;

/**
 * Enemy boss, very high hp, slow speed, bigger size
 *
 */
public abstract class EnemyBoss extends Enemy {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1981341229580956328L;
	
	public EnemyBoss(EnemyType type, double x, double y) {
		// x, y, width, height, hp, money,speed
		super(type, x, y, 0.08, 0.08, 10_000,500,.0018);
	}


	/*@Override
	public void attack() {
		// TODO Auto-generated method stub

	}*/
}
