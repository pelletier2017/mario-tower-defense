package model.entity.tower;


import java.io.Serializable;
import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import model.entity.Entity;
import model.entity.animation.Animation;
import model.entity.animation.AnimationEvent;
import model.entity.animation.AnimationState;
import model.entity.enemy.Enemy;
import model.entity.tower.focus.Focus;
import model.entity.tower.focus.FocusFirst;
import model.entity.tower.projectiles.Arrow;
import model.entity.tower.projectiles.Projectile;
import model.sound.ProjectileSoundEffects;

public abstract class Tower extends Animation {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5228674984090602105L;

	private static final int DEFAULT_RELOAD_TIME = 50;
	private static final int LEVEL_MAX = 3;

	private int buildCost;
	private int upgradeCost;
	
	private int level;
	private int reloadTime;
	protected double range;
	
	private int timeUntillCanShoot;
	
    private TowerType towerType;
    
    protected Focus focus;
    protected boolean shooting;


	/**
	 * Constructs a tower abstract
	 * @param isReal 
	 * 
	 * @param cost
	 *            the cost of the tower
	 * @param level
	 *            the level of the tower
	 * @param projectile
	 *            the Projectile in which the tower will use
	 * @param build
	 *            the build SoundEffect that the tower will play when it is finished
	 *            building
	 * @param destroy
	 *            the destroy SoundEffect that the tower will play when it is
	 *            destroyed
	 */

	public Tower(TowerType towerType, int buildCost, int upgradeCost, double reloadMultipler, double range, double startXaxis, double startYaxis, double width, double height, boolean isReal) {
		super(towerType.getSpriteSheet(), startXaxis, startYaxis, width, height);
		super.setAnimationState(AnimationState.TOWER_IDLE);
		super.setTicPerChange(5);
		super.setOnLoop(new AnimationLoopHandler());
		
        this.towerType = towerType;
		this.buildCost = buildCost;
		this.upgradeCost = upgradeCost;
		this.range = range;
		
		this.reloadTime = (int) (DEFAULT_RELOAD_TIME * reloadMultipler);
		this.level = 1;
		this.timeUntillCanShoot = 0;
		this.shooting = false;
		
		focus = new FocusFirst();
		
	}
	

	// called each increment of time to allow the tower to shoot and reload
	public void doAction(List<Enemy> allEnemies, List<Projectile> allProjectiles) {
		
		super.update();
		if(timeUntillCanShoot <= 0 ) {
			this.shooting = false;
			super.setAnimationState(AnimationState.TOWER_IDLE);
			boolean needsReload = this.findTarget(allEnemies, allProjectiles);
			if( needsReload) {
				this.shooting = true;
				super.setAnimationState(AnimationState.TOWER_SHOOT);
				super.reset();
				if (towerType == TowerType.BOUNCE_TOWER) super.setTicPerChange(2);
				timeUntillCanShoot = reloadTime;
			}
		} else {
			timeUntillCanShoot--;
		}
		
	
	}
	
	/**
	 * Called by a view to draw the enemy on a Canvas
	 * 
	 * @param gc
	 *            the GraphicsContext from the Canvas to use
	 * @param scale
	 *            the Canvas Size to Scale the internal coordinate system
	 */
	public void draw(GraphicsContext gc, double scale) {
		double w = this.width * scale;
		double h = this.height * scale;
		double x = (super.getX() * scale) - (w / 2);
		double y = (super.getY() * scale) - (h / 2);
		//System.out.println("Animation Row: " + super.getRow() + ", Tower Level: " + this.level + ", AnimationState: " + super.getAnimationState().toString() + ", True Row: " + ((this.level - 1) * 2));
		this.towerType.getSpriteSheet().staticRender(gc, super.getRow() + ((this.level - 1) * 2), super.getCol(), x, y, w, h);
	}
	
	private boolean findTarget(List<Enemy> allEnemies, List<Projectile> allProjectiles) {
		
		for (Enemy curEnemy : allEnemies) { 
		    if (curEnemy.canBeHit(super.x , super.y , this.range ) ) {
		    	focus.addChoice(curEnemy);	
		    }
		}
		
		Enemy target = focus.getChoice();
		if(target != null) {
			attack(target, allProjectiles);
			return true;
		}
	
		return false;
	}


	// method for tester
	public Boolean canFire() {
		if(timeUntillCanShoot <= 0 ) {
			return true;
		}
		
		return false;
	}
	
	public void setFocus(Focus newFocus) {
		if( newFocus != null) {
			focus = newFocus;
		}	
	}
	/**
	 * 
	 * @param target 
	 * @param allProjectiles 
	 * @param target
	 *            is the Enemy in which the tower is to attack
	 * @return 
	 */
	public abstract void attack(Enemy target, List<Projectile> allProjectiles);

	/**
	 * Applys an upgrade to the tower. Up to the sub-class to implement this code
	 * @return 
	 */
	public boolean upgrade() {
		if( level >= LEVEL_MAX) {
			return false;
		}
		
		level ++;
		ProjectileSoundEffects.tower_upgrade();
		increaseStats();
		return true;
	}
	
	/**
	 * Applys an upgrade to the tower. Up to the sub-class to implement this code
	 */
	public abstract void increaseStats() ;

	/**
	 * Change the cost of a tower
	 * 
	 * @param cost
	 *            the new cost to make the tower
	 */

	public void setUpgadeCost(int cost) {
		this.upgradeCost = cost;

	}

	/**
	 * Get the cost of tower
	 * 
	 * @return the towers cost
	 */

	public int getBuildCost() {
		return this.buildCost;
	}

	public int getUpgradeCost() {
		return this.upgradeCost;
	}

	/**
	 * Get the towers current level
	 * 
	 * @return towers level
	 */
	public int getLevel() {
		return this.level;
	}


	/**
	 * Play the towers built SoundEffect
	 */
	public void playBuiltSoundEffect() {
		// TODO: play sound for a tower as built
	}

	/**
	 * Play the towers destroyed SoundEffect
	 */
	public void playDestroyedSoundEffect() {
		// TODO: play destroyed sound effect
	}

	/**
	 * Get the Project the tower shoots
	 * 
	 * @return Projectile
	 */
	 public abstract Projectile getProjectile() ;

	/**
	 * Set, or change a towers Projectile
	 * 
	 * @param projectile
	 *            the Projectile the tower will use
	 *
	public void setProjectile(Projectile projectile) {
		this.projectile = projectile;
	}
	

	public void getStats() {
		// TODO: Return the towers stats
	}
	*/

	// added for testing purposes only
	public void setRange(double newRange) {
		range = newRange;
	}
	
	// added for testing purposes only
	public void setReloadTime(int i) {
		reloadTime = i;
		
	}
	
	public int getReloadTime() {
        return reloadTime;
    }
	
	public double getRange() {
        return range;
    }
	
	public TowerType getTowerType() {
	    return towerType;
	}
	
	public boolean isMaxLevel() {
	    return level == LEVEL_MAX;
	}
	
	private class AnimationLoopHandler implements EventHandler<AnimationEvent>, Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 3289619887947116858L;

		@Override
		public void handle(AnimationEvent event) {
			if (shooting) {
				shooting = false;
				reset();
				setAnimationState(AnimationState.TOWER_IDLE);
				
				if (towerType == TowerType.BOUNCE_TOWER) setTicPerChange(5);
			}
		}
	}
}
