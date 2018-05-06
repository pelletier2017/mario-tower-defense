package model.strategy;

import java.io.Serializable;
import java.util.Random;

import model.entity.enemy.Enemy;
import model.entity.enemy.damageresistant.MediumToad;
import model.entity.enemy.fast.MediumPrincess;
import model.entity.enemy.simple.HardPrincess;
/**
 * Implements game strategy
 * 
 * Medium level 
 * @author genevievepatterson
 *
 */
public class Medium  implements GameStrategy, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3764109744154246038L;

	/**
	 * Returns the number of enemy waves
	 */
	@Override
	public int getNumWaves() {
		return 5;
	}

	/**
	 * Returns the number of enemies per wave
	 */
	@Override
	public int getNumEnemies() {
		return 10;
	}

	/**
	 * Makes new enemies
	 * 60% simple enemies, 20% fast enemies, 20% damage resistant
	 */
	@Override
	public Enemy makeNewEnemy(double x, double y) {
		//use random number to return percentages of 
		//certain enemy types
		Enemy newEnemy;
		Random rand= new Random();
		int decider= rand.nextInt(100);
		//return simple 60% of the time
		if(decider<60) {
			//Simple enemies are now:
			// 1. EasyPrincess
			// 2. HardPrincess
			// 3. Warrio
			newEnemy= new HardPrincess(x,y);
		}
		//fast 20%
		else if (decider<80){
			//Fast enemies are now:
			// 1. MediumPrincess
			// 2. Luigi
			newEnemy= new MediumPrincess(x,y);
		}
		//damage resistant 20%
		else {
			//DamageResistant Enemy are now:
			// 1. EasyToad
			// 2. MediumToad
			// 3. HardToad
			newEnemy= new MediumToad(x,y);
		}
		return newEnemy;
	}

	/**
	 * Returns the next level (Hard)
	 */
	@Override
	public GameStrategy setNextLevel() {
		return new Hard();
	}

	/**
	 * Gets strategy type
	 */
	@Override
	public StrategyType getStrategy() {
		return StrategyType.MEDIUM;
	}


}
