package model.strategy;

import java.io.Serializable;
import java.util.Random;

import model.entity.enemy.Enemy;
import model.entity.enemy.damageresistant.EasyToad;
import model.entity.enemy.fast.MediumPrincess;
import model.entity.enemy.simple.EasyPrincess;
/**
 * Implements game strategy
 * 
 * Easy level (fewest waves, number of enemies, and mostly simple enemies)
 * 
 * @author genevievepatterson
 *
 */
public class Easy implements GameStrategy, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6055202162671238105L;


	/**
	 * Returns the number of enemy waves
	 */
	@Override
	public int getNumWaves() {
		return 3;
	}

	/**
	 * Returns the number of enemies per wave
	 */
	@Override
	public int getNumEnemies() {
		return 5;
	}

	/**
	 * Makes new enemies
	 * 80% simple enemies, 10% fast enemies, 10% damage resistant
	 */
	@Override
	public Enemy makeNewEnemy(double x, double y) {
		Enemy newEnemy;
		Random rand= new Random();
		int decider= rand.nextInt(100);
		//return simple enemy 80% of the time
		if(decider<80) {
			//Simple enemies are now:
			// 1. EasyPrincess
			// 2. HardPrincess
			// 3. Warrio
			newEnemy= new EasyPrincess(x,y);
		}
		//return fast enemy 10%
		else if (decider<90){
			//Fast enemies are now:
			// 1. MediumPrincess
			// 2. Luigi
			newEnemy= new MediumPrincess(x,y);
		}
		//return damage resistant 10%
		else {
			//DamageResistant Enemy are now:
			// 1. EasyToad
			// 2. MediumToad
			// 3. HardToad
			newEnemy= new EasyToad(x,y);
		}
		return newEnemy;
	}

	/**
	 * Returns the next level (Medium)
	 */
	@Override
	public GameStrategy setNextLevel() {
		return new Medium();
	}


	/**
	 * Gets strategy type
	 */
	@Override
	public StrategyType getStrategy() {
		return StrategyType.EASY;
	}

}
