package model.entity.enemy;

import java.awt.Color;
import java.io.Serializable;

import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import model.entity.animation.Animation;
import model.entity.animation.AnimationEvent;
import model.entity.animation.AnimationState;
import model.entity.map.path.Path;
import model.entity.statusbar.StatusBar;
import model.entity.tower.projectiles.Damage;
import model.sound.EnemySoundEffects;
import model.sound.SoundEffect;
import view.MapView;

/**
 * Enemy abstract class
 * Contains all methods that all enemies share
 * 
 */
public abstract class Enemy extends Animation implements Serializable {

	private static final long serialVersionUID = 6654302210255757820L;

	private static final double DEFAULT_SPEED = 0.005;
	private static final double HEALTH_BAR_HEIGHT = 3.0;

	protected boolean alive;
	protected boolean remove;
	protected boolean madeItToEndZone;
	protected Path path;
	protected double speed;

	EnemyType enemyType;

	protected double hitPoints;
	protected double moneyForKill;
	
	protected StatusBar healthBar;
	
	private double slowPerc;
	private int slowTimeRem;
	
	private EnemyStats stats;

	public Enemy(EnemyType type, double x, double y, double width, double height, double hp, double money, double speed) {
		super(type.getSpriteSheet(), x, y, width, height);
		super.setOnLoop(new AnimationLoopHandler());
		enemyType = type;
		hitPoints = hp;
		moneyForKill= money;
		alive = true;
		this.remove = false;
		madeItToEndZone = false;		
		/*
		 * Draw a health bar on all enemies
		 */
		this.healthBar = new StatusBar(width * MapView.CANVAS_SIZE, HEALTH_BAR_HEIGHT, hp);
		this.healthBar.setFillColor(Color.WHITE);
		this.healthBar.setBarColor(Color.RED);

		this.speed = speed;
		stats= new EnemyStats(type, speed, hp, money);
	}
 
	/**
	 * Enemy loses health from being hit by projectiles
	 * If no hp left, enemy dies
	 * @param damage
	 */
	public void takeDamage(Damage damage) {
		this.hitPoints -= damage.getPhysicalDamage();
		this.healthBar.take(damage.getPhysicalDamage());
		if (this.hitPoints <= 0) {
			setAlive(false);
		}
		
		if(damage.getSlowPercent() > 0.0) {
			slowPerc = damage.getSlowPercent();
			
			if( slowTimeRem < damage.getSlowDuration()) {
				slowTimeRem = damage.getSlowDuration();
			}
		}
	}

	//public abstract void attack();

	/**
	 * Returns enemy stats
	 * @return EnemyStats
	 */
	public EnemyStats getStats() {
		return stats;
	}

	/**
	 * Gets enemy width for tower to check hitbox
	 * @return double, enemy width
	 */
	public double getHitBoxWidth() {
		return width;
	}

	/**
	 * Gets enemy height for tower to check hitbox
	 * @return double, enemy height
	 */
	public double getHitBoxHeight() {
		return height;
	}

	/**
	 * Checks if enemy is still alive
	 * @return boolean, true if alive & false if not
	 */
	public boolean isAlive() {
		return alive;
	}

	/**
	 * Checks if enemy can be removed from game (if death animation is over)
	 * @return boolean, true if death animation over & false if not
	 */
	public boolean canRemove() {
		return this.remove;
	}
	
	/**
	 * Sets enemy location
	 * @param x, double representing x coordinate
	 * @param y, double representing y coordinate
	 */
	public void setLocation(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Sets life status of enemy
	 * @param boolean, new status (alive or dead)
	 */
	public void setAlive(boolean b) {
		if (alive && !b) {
			this.speed = 0.0;
			super.reset();
			super.setAnimationState(AnimationState.DIE);
			playDeathSound();
		}
		alive = b;
	}

	public abstract void playDeathSound() ;

	/**
	 * Updates if the enemy made it past all the towers to the end zone
	 */
	public void updateMadeItToEndZone() {
		madeItToEndZone = true;
	}

	/**
	 * Checks if the enemy made it to the end zone
	 * @return boolean, true if in end zone, false if not
	 */
	public boolean madeItToEndZone() {
		return madeItToEndZone;
	}

	/**
	 * Gets enemy's path on map
	 * @return Path, enemies path
	 */
	public Path getPath() {
		return path;
	}

	/**
	 * Sets enemy's path on map
	 * @param path
	 */
	public void setPath(Path path) {
		this.path = path;
		setLocation(path.getX(), path.getY());
	}

	/**
	 * Sets enemy's hit points
	 * @param hp
	 */
	public void setHP(double hp) {
		this.hitPoints = hp;
	}

	/**
	 * Gets enemy's hit points
	 * @return
	 */
	public double getHP() {
		return hitPoints;
	}
	
	/**
	 * Sets the monetary value for the enemy when the player kills it
	 * @param money
	 */
	public void setMoneyForKill(double money) {
		this.moneyForKill= money;
	}
	
	/**
	 * Gets the monetary value from the enemy when the player kills it
	 * @return double money
	 */
	public double getMoneyForKill() {
		return moneyForKill;
	}

	/*public void setStatus(double xAxis, double yAxis, double width, double height) {
		this.x = xAxis;
		this.y = yAxis;
		this.height = height;
		this.width = width;
	}*/

	// author: Matthew Chambers
	// returns true if any part of the hit box is within 'range' distance of the
	// 'xAxisFrom' 'yAxisFrom' point
	// returns false otherwise
	public boolean canBeHit(double xAxisFrom, double yAxisFrom, double range) {

		if (!this.isAlive()) return false;

		double topLeftX = x - (width / 2);
		double topLeftY = y - (height / 2);
		
		if (xAxisFrom >= topLeftX && xAxisFrom <= (topLeftX + width)) {
			if (yAxisFrom >= (topLeftY - range) && yAxisFrom <= (topLeftY + height + range)) {
				return true;
			}
			return false;
		} else if (yAxisFrom >= topLeftY && yAxisFrom <= (topLeftY + height)) {
			if (xAxisFrom >= (topLeftX - range) && xAxisFrom <= (topLeftX + width + range)) {
				return true;
			}
			return false;
			// corner cases
		} else if (inCornerRange(topLeftX, topLeftY, xAxisFrom, yAxisFrom, range)
				|| inCornerRange(topLeftX + width, topLeftY, xAxisFrom, yAxisFrom, range)
				|| inCornerRange(topLeftX, topLeftY + height, xAxisFrom, yAxisFrom, range)
				|| inCornerRange(topLeftX + width, topLeftY + height, xAxisFrom, yAxisFrom, range))
			return true;

		return false;
	}

	private boolean inCornerRange(double cornerX, double cornerY, double xAxisFrom, double yAxisFrom, double range) {
		double distance = Math.sqrt(Math.pow(cornerX - xAxisFrom, 2) + Math.pow(cornerY - yAxisFrom, 2));

		if (distance < range)
			return true;
		return false;
	}

	/**
	 * Gets the type of the enemy
	 * @return EnemyType
	 */
	public EnemyType getEnemyType() {
		return enemyType;
	}

	/**
	 * Sets the enemy's speed
	 * @param speed
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
	}

	/**
	 * Gets the enemy's speed
	 * @return
	 */
	public double getSpeed() {
		
		
		return (this.speed * (1 - this.slowPerc));
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
		this.enemyType.getSpriteSheet().staticRender(gc, super.getRow(), super.getCol(), x, y, w, h);
		this.healthBar.draw(gc, x, y - HEALTH_BAR_HEIGHT);
	}

	/**
	 * Overrides the animations update to update the animation as well update it's
	 * position
	 */
	@Override
	public void update() {
		if (this.madeItToEndZone())
			return;

		super.update();
		if (!this.isAlive()) return;
		
		updateSpeed();
		
		if (super.atTarget(this.path.getX(), this.path.getY())) {
			this.path = this.path.getNext();
			if (this.path == null) {
				this.updateMadeItToEndZone();
				return;
			}
		}

		double oldX = super.getX();
		double oldY = super.getY();
		super.moveToward(this.path.getX(), this.path.getY(), this.getSpeed());
		if (oldX > super.getX())
			super.setAnimationState(AnimationState.LEFT);
		else if (oldX < super.getX())
			super.setAnimationState(AnimationState.RIGHT);
		else if (oldY < super.getY())
			super.setAnimationState(AnimationState.DOWN);
		else if (oldY > super.getY())
			super.setAnimationState(AnimationState.UP);
	}

	private void updateSpeed() {
		if( slowTimeRem <= 0) {
			slowPerc = 0.0;
			return;
		}
		
		slowTimeRem--;
		
	}
	
	// TODO make this return the enemies distance from the goal.
	public double getDistance() {
		if(path == null) {
			return 0;
		}
		return path.getDistanceFromGoal();
	}
	
	private class AnimationLoopHandler implements EventHandler<AnimationEvent>, Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = -4576800941725324567L;

		@Override
		public void handle(AnimationEvent e) {
			if(!isAlive()) {
			    remove = true;
			}
		}
	}
}
