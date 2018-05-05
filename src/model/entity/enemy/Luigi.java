package model.entity.enemy;

import model.sound.EnemySoundEffects;

/**
 * Luigi enemy for hard level
 * Extends fast enemy
 */
public class Luigi extends FastEnemy {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2733606622879689492L;

	public Luigi(double x, double y) {
		super(EnemyType.LUIGI, x, y);
		super.setSpeed(this.speed * 1.5);
    super.setMoneyForKill(this.moneyForKill/2);
	}

	@Override
	public void playDeathSound() {
		EnemySoundEffects.luigi_dies();
	}

}
