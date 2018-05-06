package model.entity.enemy.fast;

import model.entity.enemy.EnemyType;
import model.sound.soundeffect.EnemySoundEffects;

/**
 * Princess enemies for medium level
 * Extends fast enemy
 */
public class MediumPrincess extends FastEnemy {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3282711789439009107L;

	public MediumPrincess(double x, double y) {
		super(EnemyType.MEDIUMPRINCESS, x, y);
		super.setSpeed(this.speed+(this.speed*.50));
		super.setMoneyForKill(this.moneyForKill/2);
	}

	@Override
	public void playDeathSound() {
		EnemySoundEffects.peach_dies();
		
	}

}
