package model.entity.animation;

/**
 * Used to allow Animation to change the row the animation animating on
 * 
 * @author William Hastings
 *
 */
public enum AnimationState {

	DOWN(0), UP(1), LEFT(2), RIGHT(3), DIE(4), PROJECTILE_MOVING(0), PROJECTILE_EXPLODING(1), TOWER_IDLE(0), TOWER_SHOOT(1);

	private int row;

	AnimationState(int row) {
		this.row = row;
	}

	public int getRow() {
		return this.row;
	}
}
