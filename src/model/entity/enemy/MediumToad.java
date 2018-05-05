package model.entity.enemy;

import model.sound.EnemySoundEffects;

/**
 * Toad enemy for medium level
 * Damage resistant enemy
 */
public class MediumToad extends DamageResistant {
    
	private static final long serialVersionUID = -3684856677262547482L;

	// scales hp according to DamageResistant default HP
    private static final double HP_MULTIPLIER = 1.5;
	
	public MediumToad(double x, double y) {
		super(EnemyType.MEDIUMTOAD, x, y, HP_MULTIPLIER);
		super.setMoneyForKill(this.moneyForKill/2);
	}
	
	@Override
    public void playDeathSound() {
        EnemySoundEffects.toadette_dies();
    }

}
