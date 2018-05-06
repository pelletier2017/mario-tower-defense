package model.strategy;

import java.io.Serializable;
import java.util.Random;

import model.entity.enemy.Enemy;
import model.entity.enemy.damageresistant.HardToad;
import model.entity.enemy.fast.Luigi;
import model.entity.enemy.simple.Wario;
/**
 * Implements game strategy
 * 
 * Hardest level (most enemy waves, enemies per wave, and challenging enemies)
 * @author genevievepatterson
 *
 */
public class Hard  implements GameStrategy, Serializable {

	/**
	 * Returns the number of enemy waves
	 */
	@Override
	public int getNumWaves() {
		return 7;
	}
	
	/**
	 * Returns the number of enemies per wave
	 */
	@Override
	public int getNumEnemies() {
		return 15;
	}

	/**
	 * Makes new enemies
	 * 33% simple enemies, 33% fast enemies, 33% damage resistant
	 */
	@Override
	public Enemy makeNewEnemy(double x, double y) {
		//use random number to return percentages of 
		//certain enemy types
		Enemy newEnemy;
		Random rand= new Random();
		int decider= rand.nextInt(100);
		//return simple 33% of the time
		if(decider<33) {
			//Simple enemies are now:
			// 1. EasyPrincess
			// 2. HardPrincess
			// 3. Warrio
			newEnemy= new Wario(x,y);
		}
		//fast 33%
		else if (decider<66){
			//Fast enemies are now:
			// 1. MediumPrincess
			// 2. Luigi
			newEnemy= new Luigi(x,y);
		}
		//damage resistant 33%
		else {
			//DamageResistant Enemy are now:
			// 1. EasyToad
			// 2. MediumToad
			// 3. HardToad
			newEnemy= new HardToad(x,y);
		}
		return newEnemy;
	}

	/**
	 * Returns the next level
	 * Since this is the hardest level, returns the current level
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
		return StrategyType.HARD;
	}

}
