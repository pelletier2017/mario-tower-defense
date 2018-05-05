package model.entity.tower.projectiles;
import java.util.HashSet;
/**
 *  Author: Matthew Chambers
 *		CSC335, TowerDefense
 *		Bounce
 *			Projectile that targets a single enemy, then will try to target a new enemy until it is out of bounces.
 */
import java.util.List;
import java.util.Set;

import model.entity.enemy.Enemy;
import model.sound.ProjectileSoundEffects;

public class Bounce extends Projectile{


	/**
	 * 
	 */
	private static final long serialVersionUID = -6745588328813617394L;
	private static final double INITIAL_BOUNCE_RANGE = .15;
	private static final int 	INITIAL_NUMBER_OF_BOUNCES = 2;
	private static final int BOUNCE_INCREASE_PER_LEVEL = 2;
	
	private static final double DAMAGE_MULTIPLIER = 1.0;
	private static final double DAMAGE_INCREASE_PER_LEVEL = 5;
	
	private static final double INITIAL_SPEED = 0.013;

	private static final double INITIAL_WIDTH = 0.04;
	private static final double INITIAL_HEIGHT = 0.04;

	private Set<Enemy> previousTargets = new HashSet<>();
	
	private Enemy target;
	private int bouncesToDo;
	private double bounceRange;
	
	public Bounce( Enemy target, double startX, double startY, int level, boolean isReal) {
		super(ProjectileType.BOUNCE, INITIAL_SPEED, startX, startY,
				INITIAL_WIDTH, INITIAL_HEIGHT, level, DAMAGE_MULTIPLIER);

		this.target = target;
		bouncesToDo = INITIAL_NUMBER_OF_BOUNCES + (BOUNCE_INCREASE_PER_LEVEL * (level - 1));
		bounceRange = INITIAL_BOUNCE_RANGE;
		
		if(isReal) {
			ProjectileSoundEffects.bomb_shoot();
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

	// will hit the current target doing damage, then look for another target if it can still bounce.
	@Override
	protected void attack(List<Enemy> allEnemies) {
		
		
		target.takeDamage(this.damage);
		ProjectileSoundEffects.bounce_hit();
		bouncesToDo--;
		previousTargets.add(target);
		
		if(bouncesToDo <= 0) {
			this.needsRemoving = true;
			return;
		}
		
		for( Enemy curEnemy: allEnemies) {
			if( curEnemy.canBeHit(super.x, super.y, bounceRange) && curEnemy != target && !previousTargets.contains(curEnemy)) {
				target = curEnemy;
				return;
			}
		}
		
		this.needsRemoving = true;
		
	}

	@Override
	public void upgrade() {
	    // constructor of Bounce will reset any instance variables not already in projectile (ex bouncesToDo)
		super.damage.increasePhysical(DAMAGE_INCREASE_PER_LEVEL);
		//bouncesToDo += BOUNCE_INCREASE_PER_LEVEL;
	}
	
	// tester method only
	public void setBounceRange(double newBounceRange) {
		bounceRange = newBounceRange;
	}

	

}
