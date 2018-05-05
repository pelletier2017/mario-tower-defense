package model.entity.enemy;

import model.sound.EnemySoundEffects;

/**
 * Princess enemy for hard level
 * Extends simple enemy
 */
public class HardPrincess extends SimpleEnemy {

	private static final long serialVersionUID = -3995935753219677988L;
	
	// scales hp according to SimpleEnemy default HP
    private static final double HP_MULTIPLIER = 1.2;

	public HardPrincess(double x, double y) {
		super(EnemyType.HARDPRINCESS, x, y, HP_MULTIPLIER);
		super.setSpeed(this.speed+(this.speed*.20));
		super.setMoneyForKill(this.moneyForKill/2);
	}

	@Override
	public void playDeathSound() {
		EnemySoundEffects.rosalina_dies();
	}

}
