package model.entity.tower.focus;
import java.io.Serializable;

/**
 *  Author: Matthew Chambers
 *		CSC335, TowerDefense
 *		Focus
 *			Class that gives a tower a strategy on
 *			what enemies to target. 
 */
import model.entity.enemy.Enemy;

public abstract class Focus implements Serializable {

	protected Enemy currentTarget;
	
	public Focus() {
		currentTarget = null;
	}
	
	public abstract void addChoice(Enemy enemy);
	
	public Enemy getChoice() {
		Enemy choice = currentTarget;
		currentTarget = null;
		return choice;
	}
}
