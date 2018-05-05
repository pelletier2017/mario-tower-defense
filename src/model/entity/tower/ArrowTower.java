package model.entity.tower;
/**
 *  Author: Matthew Chambers
 *		CSC335, TowerDefense
 *		ArrowTower
 *			Tower that shoots 'Arrow' at enemies in range
 */
import java.util.List;

import model.entity.enemy.Enemy;
import model.entity.tower.projectiles.Arrow;
import model.entity.tower.projectiles.Projectile;

// temp tower for sprint 1
public class ArrowTower extends Tower {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2223775185449283209L;
	private static final int BUILD_COST = 100;
	private static final int UPGRADE_COST = 50;
	private static final double RELOAD_MULTIPLIER = 1.0;
	
	private static final double RANGE = 0.3;
	private static final double RANGE_INCREASE_PER_LEVEL = 0.05;
	
	private static final double WIDTH = 0.05;
	private static final double HEIGHT = 0.05;
    
	public ArrowTower( double startXaxis, double startYaxis, boolean isReal) {
		super(TowerType.ARROW_TOWER, BUILD_COST, UPGRADE_COST, RELOAD_MULTIPLIER, RANGE, 
		        startXaxis, startYaxis, WIDTH, HEIGHT, isReal);
	}

	@Override
	public void attack(Enemy target, List<Projectile> allProjectiles) {
		
			allProjectiles.add( new Arrow( target, super.x , super.y , this.getLevel() , true));
	
	}

	// 
	@Override
	public void increaseStats() {
		this.range += RANGE_INCREASE_PER_LEVEL;
	}

	@Override
	public Projectile getProjectile() {
		return new Arrow( null, super.x , super.y , this.getLevel(), false);
	}





}
