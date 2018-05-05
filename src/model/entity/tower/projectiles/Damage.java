package model.entity.tower.projectiles;

import java.io.Serializable;

/**
 *  Author: Matthew Chambers
 *		CSC335, TowerDefense
 *		Damage
 *			Holds different stats for types of damage that can be done to an 'Enemy'
 */
public class Damage implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8789509949714238908L;

	private double physical;
	
	private static final int SLOW_DURATION = 150;
	private double slowPercent;
	
	
	public Damage(double physical) {
		this.physical = physical;
		slowPercent = 0;
	}
	
	public double getPhysicalDamage() {
		return physical;
		
	}
	
	public double getSlowPercent() {
		return slowPercent;
	}


	public void increasePhysical(double increasePerLevel) {
		physical += increasePerLevel;
		
	}

	public void increaseSlow(Double moreSlow) {
		slowPercent += moreSlow;
		
		if(slowPercent > 1.0) {
			slowPercent = 1.0;
		}
		
	}

	public int getSlowDuration() {
		return SLOW_DURATION;
	}

}
