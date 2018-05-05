package model.entity.tower;
/**
 *  Author: Matthew Chambers
 *		CSC335, TowerDefense
 *		ArrowTower
 *			Tower that shoots 'Arrow' at enemies in range
 */
import java.util.List;

import model.entity.enemy.Enemy;
import model.entity.tower.projectiles.Bounce;
import model.entity.tower.projectiles.Projectile;

// temp tower for sprint 1
public class BounceTower extends Tower {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5009754434686990722L;
	private static final int BUILD_COST = 150;
	private static final int UPGRADE_COST = 75;
	private static final double RELOAD_MULTIPLIER = 1.5;
	private static final double RANGE = 0.25;
	private static final double WIDTH = 0.05;
	private static final double HEIGHT = 0.05;
    
	public BounceTower( double startXaxis, double startYaxis, boolean isReal) {
		super(TowerType.BOUNCE_TOWER, BUILD_COST, UPGRADE_COST, RELOAD_MULTIPLIER, RANGE, 
		        startXaxis, startYaxis, WIDTH, HEIGHT, isReal);
	}

	@Override
	public void attack(Enemy target, List<Projectile> allProjectiles) {
		
	
		   allProjectiles.add( new Bounce( target, super.x , super.y , this.getLevel(), true ));
		
	}

	// 
	@Override
	public void increaseStats() {
		this.range += 0.05;
	}

	@Override
	public Projectile getProjectile() {
		return new Bounce( null, super.x , super.y , this.getLevel() , false);
	}





}
