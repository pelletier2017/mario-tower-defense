package model.entity.tower;
/**
 *  Author: Matthew Chambers
 *		CSC335, TowerDefense
 *		BombTower
 *			Tower that shoots 'Bomb' at the location of Enemies in range
 */
import java.util.List;

import model.entity.enemy.Enemy;
import model.entity.enemy.TestEnemy;
import model.entity.tower.projectiles.Arrow;
import model.entity.tower.projectiles.Bomb;
import model.entity.tower.projectiles.Projectile;

public class BombTower extends Tower{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5084882537801338271L;
	private static final int BUILD_COST = 200;
	private static final int UPGRADE_COST = 150;
	private static final double RELOAD_MULTIPLIER = 2.0;
	
	private static final double INITIAL_RANGE = 0.2;
	private static final double RANGE_INCREASE_PER_LEVEL = 0.04;
	
	
	private static final double INITIAL_WIDTH = 0.05;
	private static final double INITIAL_HEIGHT = 0.05;
	
	public BombTower( double startXaxis, double startYaxis, boolean isReal) {
		// TODO set towerType
	    // int buildCost, int upgradeCost, int reloadTime, double range, double startXaxis, 
	    // double startYaxis,  Projectile projectile, int width, int height
		super(TowerType.BOMB_TOWER, BUILD_COST, UPGRADE_COST, RELOAD_MULTIPLIER, INITIAL_RANGE, startXaxis, startYaxis, INITIAL_WIDTH, INITIAL_HEIGHT, isReal);
	}


	// makes a 'Bomb' projectile to attack a 'Enemy' s location.
	@Override
	public void attack(Enemy target, List<Projectile> allProjectiles){
		
		allProjectiles.add( new Bomb( target, super.x , super.y , super.getLevel(), true));
	    
	}

	// used to upgrade the tower to the next level
	@Override
	public void increaseStats() {
		
		this.range += RANGE_INCREASE_PER_LEVEL;
		
	}


	@Override
	public Projectile getProjectile() {
		
		return new Bomb( null, super.x , super.y , super.getLevel(), false);
	}


}
