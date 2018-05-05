package model.strategy;

import model.entity.enemy.Enemy;
/**
 * Interface for the strategy of the game
 * 
 * Determines difficulty based upon the number of waves, number of enemies, and type of enemies
 * 
 * @author genevievepatterson
 *
 */
public interface GameStrategy {
	
	//make waves increase in difficulty within level
	public int getNumWaves();
	
	public int getNumEnemies();
	
	public Enemy makeNewEnemy(double x, double y);
	
	public GameStrategy setNextLevel();
	
	public StrategyType getStrategy();
	
}
