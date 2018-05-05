package model.entity.enemy;

/**
 * Fast enemy type, low hp, high speed
 *
 */
public abstract class FastEnemy extends Enemy {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6601436286655772492L;
	private EnemyStats stats;
	public FastEnemy(EnemyType type, double x, double y) {
	    // x, y, width, height, hp, money, speed
		super(type, x, y, 0.06, 0.06, 350, 50, .008);	

	}

	/*@Override
	public void attack() {
		// TODO Auto-generated method stub

	}*/

}
