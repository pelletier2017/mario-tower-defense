package model.entity.enemy;

import model.sound.EnemySoundEffects;

/**
 * Wario enemy for hard level
 * Extends simple enemy
 */
public class Wario extends SimpleEnemy {

	private static final long serialVersionUID = 6394455002635766581L;
	
	// scales hp according to SimpleEnemy default HP
	private static final double HP_MULTIPLIER = 1.3;

	public Wario(double x, double y) {
		super(EnemyType.WARIO, x, y, HP_MULTIPLIER);
		super.setSpeed(this.speed * 1.15);
    super.setMoneyForKill(this.moneyForKill/2);
	}

	@Override
	public void playDeathSound() {
		EnemySoundEffects.wario_dies();
	}

}
