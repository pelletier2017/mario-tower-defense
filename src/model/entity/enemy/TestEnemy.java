package model.entity.enemy;

/**
 * Used for tower testing
 */
public class TestEnemy extends Enemy{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EnemyStats stats;
	public TestEnemy( double x, double y, double width, double height) {
		// x, y, width, height, hp, money,speed
		super(EnemyType.SIMPLE_ENEMY, x, y, width, height, 500,20,.005);
		stats= new EnemyStats(EnemyType.SIMPLE_ENEMY,.005,500,20);
	}


	/*@Override
	public void attack() {
		// TODO Auto-generated method stub
		
	}*/

	@Override
	public EnemyStats getStats() {
		return stats;
	}


	@Override
	public void playDeathSound() {
		
	}

}
