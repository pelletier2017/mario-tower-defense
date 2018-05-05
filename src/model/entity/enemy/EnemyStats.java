package model.entity.enemy;

import java.io.Serializable;

/**
 * Enemy stats class combines enemy stats in one place
 * Can be accessed for each enemy by GUI and diplayed in any form
 * @author genevievepatterson
 *
 */
public class EnemyStats implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6749173370349271920L;
	private EnemyType type;
	private double speed;
	private double hp;
	private double killValue;
	
	EnemyStats(EnemyType type, double speed, double hp, double killValue){
		this.type= type;
		this.speed= speed;
		this.hp= hp;
		this.killValue=killValue;
	}
	
	/**
	 * Gets type
	 * @return EnemyType
	 */
	public EnemyType getType() {
		return type;
	}
	
	/**
	 * Gets speed
	 * @return speed
	 */
	public double getSpeed() {
		return speed;
	}
	
	/**
	 * Gets hit points (how damage resistant)
	 * @return hp
	 */
	public double getHp() {
		return hp;
	}
	
	/**
	 * Gets kill value
	 * @return money, value for kill
	 */
	public double getKillValue() {
		return killValue;
	}
}
