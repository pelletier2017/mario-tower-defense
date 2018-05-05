package model.entity.tower.projectiles;

import java.util.List;

import model.entity.enemy.Enemy;
import model.sound.ProjectileSoundEffects;
import view.MapView;

public class IceWave extends Projectile {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1344821172870849282L;
	private static final double DAMAGE_MULTIPLIER = 0.5;
	private static final double DAMAGE_INCREASE_PER_LEVEL = 5;
	private static final double INITIAL_SPEED = 0.25;
//	private static final double INITIAL_WIDTH = 0.04;
//	private static final double INITIAL_HEIGHT = 0.04;
	
	private static final double INITIAL_SLOW_PERCENT = 0.75;
	private static final double SLOW_PERCENTINCREASE_PER_LEVEL = 0;
	


	private double targetX;
	private double targetY;
	private double radiusOfAOE;
	
	
	public IceWave( double startX, double startY, double range, int level, boolean isReal) {

		super(ProjectileType.ICE, INITIAL_SPEED, startX, startY, range * 2, range * 2, level, DAMAGE_MULTIPLIER);
		super.setTicPerChange(7);
		targetX = startX;
		targetY = startY;
		radiusOfAOE = range;
		
		super.damage.increaseSlow(INITIAL_SLOW_PERCENT);
		
		if(isReal) {
			ProjectileSoundEffects.whomp_hit();
		}
	}
	
	@Override
	public double targetLocationYaxis() {
		
		return targetY;
	}

	@Override
	public double targetLocationXaxis() {
		return targetX;
	}

	@Override
	protected void attack(List<Enemy> allEnemies) {
		
		for( Enemy curEnemy: allEnemies) {
			if( curEnemy.canBeHit(targetX, targetY, radiusOfAOE)) {
				curEnemy.takeDamage(this.damage);
			}
			
			super.explode();
		}
	}

	@Override
	public void upgrade() {
		super.damage.increasePhysical(DAMAGE_INCREASE_PER_LEVEL);
		this.damage.increaseSlow(SLOW_PERCENTINCREASE_PER_LEVEL);
	}
	

	
}
