package model.entity.statusbar;

import java.io.Serializable;

import javafx.scene.canvas.GraphicsContext;

/**
 * JavaFX Serializable status bars that can be drawn on a graphics context
 * 
 * @author William Hastings (wbhastings@email.arizona.edu)
 *
 */
public class StatusBar implements Serializable {

	private static final long serialVersionUID = -8874004236566045445L;

	/*
	 * Default values
	 */
	private static final boolean DEFAULT_DRAW_BORDER = true;
	private static final double BORDER_THICKNESS = 1.0;
	private static final java.awt.Color BORDER_COLOR = java.awt.Color.BLACK;
	private static final java.awt.Color DEFAULT_BAR_COLOR = java.awt.Color.RED;
	private static final java.awt.Color DEFAULT_FILL_COLOR = java.awt.Color.WHITE;
	
	/*
	 * Instance Variables
	 */
	private double width;
	private double height;
	private double max;
	private double current;
	private boolean drawBorder;
	private java.awt.Color barColor;
	private java.awt.Color fillColor;
	
	/**
	 * Construct a StatusBar for a GraphicsContext
	 * @param width width of the bar in "real" coordinates
	 * @param height height of the bar in "real" coordinates
	 * @param max the max or "100%" value of the status bar
	 */
	public StatusBar(double width, double height, double max) {
		this.width = width;
		this.height = height;
		this.barColor = DEFAULT_BAR_COLOR;
		this.fillColor = DEFAULT_FILL_COLOR;
		this.max = max;
		this.current = max;
		this.drawBorder = DEFAULT_DRAW_BORDER;
	}

//	/**
//	 * Reset the status bar back to 100%
//	 */
//	public void resetMax() {
//		this.current = this.max;
//	}
//
//	/**
//	 * Reset the status bar back to 0%
//	 */
//	public void resetMin() {
//		this.current = 0;
//	}

	/**
	 * Take a double amt from the current.
	 * @param amt Double
	 */
	public void take(double amt) {
		this.current -= amt;
	}

//	/**
//	 * Add a double amt to the current total up to max
//	 * @param amt Double
//	 */
//	public void give(double amt) {
//		this.current += amt;
//	}

	/**
	 * Get the percentage of the status bar filled
	 * @return Double - Percent
	 */
	public double getPercent() {
		return (this.current / this.max) * 100.0;
	}

	/**
	 * Set the bar color for the status bar. JavaAWT color
	 * @param barColor JavaAWT color
	 */
	public void setBarColor(java.awt.Color barColor) {
		this.barColor = barColor;
	}

	/**
	 * Set the status bars fill color behind the main bars color. JavaAWT color
	 * @param fillColor JavaAWT color
	 */
	public void setFillColor(java.awt.Color fillColor) {
		this.fillColor = fillColor;
	}

	/**
	 * Draw the status bar with a given Graphics Context at a given x, y coordinate
	 * @param gc GraphicsContext
	 * @param x real x position Double
	 * @param y real y position Double
	 */
	public void draw(GraphicsContext gc, double x, double y) {
		if (this.drawBorder) {
			gc.setFill(this.AWTtoJFX(BORDER_COLOR));
			gc.rect(x - BORDER_THICKNESS, y - BORDER_THICKNESS, this.width + BORDER_THICKNESS,
					this.height + BORDER_THICKNESS);
		}
		gc.setFill(this.AWTtoJFX(this.fillColor));
		gc.fillRect(x, y, this.width, this.height);
		gc.setFill(this.AWTtoJFX(this.barColor));
		gc.fillRect(x, y, (this.getPercent() / 100) * this.width, this.height);
	}

	/**
	 * Used internally to convert javaAWT colors to javaFX colors since javaFX colors are NOT serializable
	 * @param awtColor javaAWT color to convert
	 * @return javafxColor equivalent returned
	 */
	private javafx.scene.paint.Color AWTtoJFX(java.awt.Color awtColor) {
		return javafx.scene.paint.Color.rgb(awtColor.getRed(), awtColor.getGreen(), awtColor.getBlue(),
				awtColor.getAlpha()/255.0);
	}
}
