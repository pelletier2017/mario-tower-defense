package model.entity.tower;

import java.util.List;

import model.entity.enemy.Enemy;
import model.entity.tower.projectiles.IceWave;
import model.entity.tower.projectiles.Projectile;

public class IceTower extends Tower{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -548331556709395138L;
	private static final int BUILD_COST = 200;
	private static final int UPGRADE_COST = 100;
	
	private static final double RELOAD_MULIPLIER = 5;
	
	private static final double INITIAL_RANGE = 0.2;
	private static final double RANGE_INCREASE_PER_LEVEL = 0.05;
	
	private static final double INITIAL_WIDTH = 0.05;
	private static final double INITIAL_HEIGHT = 0.05;
	
	
	public IceTower( double startXaxis, double startYaxis, boolean isReal) {
		// TODO set towerType
	    // int buildCost, int upgradeCost, int reloadTime, double range, double startXaxis, 
	    // double startYaxis,  Projectile projectile, int width, int height
		super(TowerType.ICE_TOWER, BUILD_COST, UPGRADE_COST, RELOAD_MULIPLIER, INITIAL_RANGE, startXaxis, startYaxis, INITIAL_WIDTH, INITIAL_HEIGHT, isReal);
	}


	// makes a 'Bomb' projectile to attack a 'Enemy' s location.
	@Override
	public void attack(Enemy target, List<Projectile> allProjectiles) {
		
		allProjectiles.add( new IceWave( this.x,  this.y, this.range, super.getLevel(), true));
	
	
	}

	// used to upgrade the tower to the next level
	@Override
	public void increaseStats() {
		if(super.getLevel() >=3)
			return;

		this.range += RANGE_INCREASE_PER_LEVEL;
		
	}


	@Override
	public Projectile getProjectile() {
		return new IceWave( this.x,  this.y, this.range, super.getLevel(), false);
	}
}
