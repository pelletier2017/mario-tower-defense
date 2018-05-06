package model.entity.enemy.simple;

import model.entity.enemy.EnemyType;
import model.sound.soundeffect.EnemySoundEffects;

/**
 * Princess enemy for easy level, simple enemy type
 *
 */
public class EasyPrincess extends SimpleEnemy {

	private static final long serialVersionUID = -1476182962906340238L;
	
	// scales hp according to SimpleEnemy default HP
	private static final double HP_MULTIPLIER = 1.0;

	public EasyPrincess(double x, double y) {
		super(EnemyType.EASYPRINCESS, x, y, HP_MULTIPLIER);
	}

	@Override
	public void playDeathSound() {
		EnemySoundEffects.daisy_dies();
		
	}

}
