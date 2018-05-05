package model.entity.tower.focus;

import model.entity.enemy.Enemy;
/**
 *  Author: Matthew Chambers
 *		CSC335, TowerDefense
 *		Focus
 *			Will target the enemy that has the most HP
 */
public class FocusStrong extends Focus{

	
	@Override
	public void addChoice(Enemy newEnemy) {
		if(currentTarget == null) {
			currentTarget = newEnemy;
			return;
		}
		
		if(newEnemy.getHP() > currentTarget.getHP()) {
			currentTarget = newEnemy;
		}
		
	}
}
