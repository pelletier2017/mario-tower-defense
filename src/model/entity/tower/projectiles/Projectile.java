package model.entity.tower.projectiles;

import java.io.Serializable;
/**
 *  Author: Matthew Chambers
 *		CSC335, TowerDefense
 *		Projectile
 *			root class for all Projectile objects.
 *			is able to move toward its intended target and send 'Damage' to 'Enemy' objects.
 */
import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import model.entity.animation.Animation;
import model.entity.animation.AnimationEvent;
import model.entity.animation.AnimationState;
import model.entity.enemy.Enemy;

public abstract class Projectile extends Animation {
    
	private static final long serialVersionUID = -8156023009394785824L;
	private int level;
	protected double speed;
	protected Damage damage;
	protected boolean needsRemoving;
	protected boolean exploding;
	
    private ProjectileType projectileType;
    
    private static final double DEFAULT_DAMAGE = 100;

	public Projectile(ProjectileType projectileType, double speed,  double x, double y, double height, double width, int newLevel, double damageMultiplier) {
		super(projectileType.getSpriteSheet(), x, y, height, width);
		super.setAnimationState(AnimationState.PROJECTILE_MOVING);
		super.setTicPerChange(2);
		
		super.setOnLoop(new AnimationLoopHandler());
		
		this.speed = speed;
		this.level = 1;
		this.projectileType = projectileType;
		
		this.damage = new Damage(DEFAULT_DAMAGE * damageMultiplier);
		
		for( int i = 1; i < newLevel; i++) {
			level++;
			upgrade();
		}
		
		needsRemoving = false;
		this.exploding = false;
	}
	

	// the 'Projectile' will move toward its intended target,
	// 	 and send 'Damage' to 'Enemy' objects
	public void doAction(List<Enemy> allEnemies){
		super.update();
		
		if(needsRemoving || this.exploding) {
//			System.out.println("needsRemoving: " + this.needsRemoving + ", exploding: " + this.exploding);
			return;
		}
		
//		System.out.println("moving!");
		super.setAnimationState(AnimationState.PROJECTILE_MOVING);
		
		double targetXaxis = targetLocationXaxis();
		double targetYaxis = targetLocationYaxis();
		
		this.moveToward(targetXaxis, targetYaxis, this.speed);
		
		if(this.atTarget(targetXaxis, targetYaxis)) {
			this.attack(allEnemies);
		}
		
	}
	
	public void explode() {
		this.exploding = true;
		super.reset();
		super.setAnimationState(AnimationState.PROJECTILE_EXPLODING);
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
//		System.out.println("Animation Row: " + super.getRow() + ", Projectile Level: " + this.level + ", AnimationState: " + super.getAnimationState().toString());
		this.projectileType.getSpriteSheet().staticRender(gc, super.getRow() + ((this.level - 1) * 2), super.getCol(), x, y, w, h);
	}
	
	// needs to be able to send 'Damage' to 'Enemy'
	protected abstract void attack(List<Enemy> allEnemies);

	public boolean isRemovable() {
		return needsRemoving;
	}

	public abstract double targetLocationYaxis() ;

	public abstract double targetLocationXaxis();

	public double getSpeed() {
		return speed;
	}
	

	public abstract void upgrade() ;
	
	public void increaseLevel() {
		level++;
	}

	public Damage getDamage() {
        return damage;
    }
	
	public ProjectileType getProjectileType() {
        return projectileType;
    }
	
	// used for testing only
	public void setSpeed(int i) {
		speed = i;
	}

	private class AnimationLoopHandler implements EventHandler<AnimationEvent>, Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = -7317568492271559255L;


		@Override
		public void handle(AnimationEvent e) {
			if (exploding) {
//				System.out.println("one explosion");
				needsRemoving = true;
			}
		}
	}
}
