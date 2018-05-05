package model.entity;

public class Moveable extends Entity  {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6938869818364391975L;

	public Moveable(double x, double y, double width, double height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}
	
	public void moveTo(double newX, double newY) {
		super.x = newX;
		super.y = newY;
	}
	
	public Boolean atTarget(double targetX, double targetY) {
		if(super.x == targetX && super.y == targetY )
			return true;
		
		return false;
	}

	public void moveToward(double targetX, double targetY, double speed) {
		double distance = Math.sqrt((targetX - super.x ) *  (targetX - super.x ) + (targetY -super.y) *  (targetY -super.y));
		if( distance < speed) {
			this.moveTo(targetX, targetY);
			return;
		}
		
		double newX = super.x + ((targetX - super.x ) * ( speed/ distance));
		double newY = super.y + ((targetY -super.y) * ( speed/ distance));
		
		this.moveTo(newX, newY);
	}
}
