package model.entity.enemy.damageresistant;

import model.entity.enemy.EnemyType;
import model.sound.soundeffect.EnemySoundEffects;

/**	
 * Toad enemy for easy level, damage resistant enemy
 */
public class EasyToad extends DamageResistant {

	private static final long serialVersionUID = -6691860187257273883L;

	// scales hp according to DamageResistant default HP
	private static final double HP_MULTIPLIER = 1.0;
	
	public EasyToad(double x, double y) {
		super(EnemyType.EASYTOAD, x, y, HP_MULTIPLIER);
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public void playDeathSound() {
        EnemySoundEffects.old_toad_dies();
    }

}
