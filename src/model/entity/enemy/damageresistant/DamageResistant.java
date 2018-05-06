package model.entity.enemy.damageresistant;

import model.entity.enemy.Enemy;
import model.entity.enemy.EnemyType;
import model.sound.soundeffect.EnemySoundEffects;

/**
 * DamageResistant enemy type, high hp, slow speed
 *
 */
public abstract class DamageResistant extends Enemy {

	private static final long serialVersionUID = 4102337800609135322L;
	
	private static final int DEFAULT_HP = 1000;
	
	public DamageResistant(EnemyType type, double x, double y, double hpMultipler) {
	    // x, y, width, height, hp, money, speed
		super(type, x, y, 0.06, 0.06, DEFAULT_HP * hpMultipler, 100, .002);	
		//this.setSpeed(.002); //bigger numbers = faster, default 0.005
	}

}
