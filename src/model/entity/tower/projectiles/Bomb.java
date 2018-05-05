package model.entity.tower.projectiles;

import java.util.List;

import model.entity.enemy.Enemy;
import model.sound.ProjectileSoundEffects;

public class Bomb extends Projectile{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6673091850723386336L;
	private static final double INITIAL_RANGE = 0.07;
	private static final double DAMAGE_MULTIPLIER = 2.5;
	private static final double DAMAGE_INCREASE_PER_LEVEL = 20;
	private static final double INITIAL_SPEED = 0.0085;
	private static final double INITIAL_WIDTH = 0.05;
	private static final double INITIAL_HEIGHT = 0.05;
	private static final double RANGE_INCREASE_PER_LEVEL = 0.005;

	private double targetX;
	private double targetY;
	private double radiusOfAOE;
	
	
	public Bomb( Enemy target, double startX, double startY, int level, boolean isReal) {

		super(ProjectileType.BOMB, INITIAL_SPEED, startX, startY, INITIAL_WIDTH, INITIAL_HEIGHT, level, DAMAGE_MULTIPLIER);
		
		radiusOfAOE = INITIAL_RANGE;
		
		if(target != null) {
			targetX = target.getX();
			targetY = target.getY();
		} else {
			targetX = startX;
			targetY = startY;
		}
		
		
		if(isReal) {
			ProjectileSoundEffects.bomb_shoot();
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
		
		this.setSize(radiusOfAOE * 6.0);
		
		for( Enemy curEnemy: allEnemies) {
			if( curEnemy.canBeHit(targetX, targetY, radiusOfAOE)) {
				curEnemy.takeDamage(this.damage);
			}
			
		}
		
		ProjectileSoundEffects.bomb_hit();
		super.explode();
	}

	@Override
	public void upgrade() {
		radiusOfAOE += RANGE_INCREASE_PER_LEVEL;
		super.damage.increasePhysical(DAMAGE_INCREASE_PER_LEVEL);
		
	}
	
	// for testing purposes only
	public void setAOE(double newRadius) {
		radiusOfAOE = newRadius;
	}
}
