package model.entity.tower.projectiles;
/**
 *  Author: Matthew Chambers
 *		CSC335, TowerDefense
 *		Arrow
 *			Projectile that targets a single enemy
 */
import java.util.List;

import model.entity.enemy.Enemy;
import model.sound.ProjectileSoundEffects;

public class Arrow extends Projectile{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1877639888775587323L;
	private static final double DAMAGE_MULTIPLIER = 1.0;
	private static final double DAMAGE_INCREASE_PER_LEVEL = 10;
	private static final double INITIAL_SPEED = 0.02;
	private static final double INITIAL_WIDTH = 0.02;
	private static final double INITIAL_HEIGHT = 0.02;

	private Enemy target;
	
	public Arrow( Enemy target, double startX, double startY, int level, boolean isReal) {
		super(ProjectileType.ARROW, INITIAL_SPEED, startX, startY,
				INITIAL_WIDTH, INITIAL_HEIGHT, level, DAMAGE_MULTIPLIER);

		this.target = target;
		
		if(isReal) {
			ProjectileSoundEffects.fireball_shoot();
		}
		
	}
	
	@Override
	public double targetLocationYaxis() {
		
		return target.getY();
	}

	@Override
	public double targetLocationXaxis() {
		return target.getX();
	}

	@Override
	protected void attack(List<Enemy> allEnemies) {
		
		target.takeDamage(this.damage);
		if(!super.exploding) super.explode();
	}

	@Override
	public void upgrade() {
		super.damage.increasePhysical(DAMAGE_INCREASE_PER_LEVEL);
		
	}

	

}
