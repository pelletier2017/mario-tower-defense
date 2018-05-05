package model.entity.enemy;

import model.sound.EnemySoundEffects;

/**
 * Toad enemy for hard level
 * Extends DamageResistant enemy
 */
public class HardToad extends DamageResistant {

	private static final long serialVersionUID = 8618837730509420892L;

	// scales hp according to DamageResistant default HP
    private static final double HP_MULTIPLIER = 3.0;
	
	public HardToad(double x, double y) {
		super(EnemyType.HARDTOAD, x, y, HP_MULTIPLIER);
		super.setMoneyForKill(this.moneyForKill/2);
	}

	@Override
    public void playDeathSound() {
        EnemySoundEffects.toad_dies();
    }
}
