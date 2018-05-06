package model.entity.enemy.boss;

import model.entity.enemy.EnemyType;
import model.sound.soundeffect.EnemySoundEffects;

/**
 * Boss enemy, has Mario animation
 *
 */
public class BossMario extends EnemyBoss {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7034544334300338902L;

	public BossMario(double x, double y) {
		super(EnemyType.BOSS, x, y);
	}

	@Override
	public void playDeathSound() {
		EnemySoundEffects.mario_dies();
		
	}

}
