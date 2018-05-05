package model.entity.tower.focus;

import model.entity.enemy.Enemy;
/**
 *  Author: Matthew Chambers
 *		CSC335, TowerDefense
 *		Focus
 *			Will target the enemy that is farthest from the end 
 */
public class FocusLast extends Focus{

	
	@Override
	public void addChoice(Enemy newEnemy) {
		if(currentTarget == null) {
			currentTarget = newEnemy;
			return;
		}
		
		if(newEnemy.getDistance() >= currentTarget.getDistance()) {
			currentTarget = newEnemy;
		}
		
	}
}
