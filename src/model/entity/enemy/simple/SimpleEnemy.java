package model.entity.enemy.simple;

import model.entity.enemy.Enemy;
import model.entity.enemy.EnemyType;

/**
 * Simple enemy type, average hp, average speed, average size
 *
 */
public abstract class SimpleEnemy extends Enemy {
	
	private static final long serialVersionUID = -8788237592097521195L;
	
	private static final int DEFAULT_HP = 500;
	
	public SimpleEnemy(EnemyType type, double x, double y, double hpMultiplier) {
	    // x, y, width, height, hp, money,speed
		super(type, x, y, 0.06, 0.06, DEFAULT_HP * hpMultiplier, 20, .005);
	}
 
	/*@Override
	public void attack() {
		// TODO Auto-generated method stub

	}*/

}
