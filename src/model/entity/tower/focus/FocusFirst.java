package model.entity.tower.focus;

import model.entity.enemy.Enemy;
/**
 *  Author: Matthew Chambers
 *		CSC335, TowerDefense
 *		FocusFirst
 *			Will target the enemy that is closest to the end 
 */
public class FocusFirst extends Focus{

	
	@Override
	public void addChoice(Enemy newEnemy) {
		if(currentTarget == null) {
			currentTarget = newEnemy;
			return;
		}
		
		if(newEnemy.getDistance() < currentTarget.getDistance()) {
			currentTarget = newEnemy;
		}
		
	}

}
