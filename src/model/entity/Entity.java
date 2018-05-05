package model.entity;

import java.io.Serializable;

/**
 * Used by the game to create Entities that can be globally passed like Java's Object class
 * @author Collective of the Group
 *
 */
public abstract class Entity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3212579033190304043L;
	
	protected double x;
	protected double y;
	protected double width;
	protected double height;

	/**
	 * Create a game Entity
	 * 
	 * @param x
	 *            it's x-position
	 * @param y
	 *            it's y-position
	 * @param width
	 *            it's width
	 * @param height
	 *            it's height
	 */
	public Entity(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * get it's x-position
	 * 
	 * @return Double
	 */
	public double getX() {
		return x;
	}

	/**
	 * Get the y-position
	 * 
	 * @return Double
	 */
	public double getY() {
		return y;
	}

	/**
	 * Get the width
	 * 
	 * @return Double
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * Get the Height
	 * 
	 * @return Double
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * Ask if this point is inside the entities hitbox width/height
	 * 
	 * @param x
	 *            point to check
	 * @param y
	 *            point to check
	 * @return Boolean true/false if the x,y point are within it's hitbox or not
	 */
	public boolean inHitBox(double x, double y) {
		double enityX = getX();
		double entityY = getY();
		double height = getHeight();
		double width = getWidth();

		boolean withinX = x >= enityX - (width / 2) && x < enityX + (width / 2);
		boolean withinY = y >= entityY - (height / 2) && y < entityY + (height / 2);

		return withinX && withinY;
	}
	
	public void setSize(double newSize) {
		height = newSize;
		width = newSize;
		
	}
}
