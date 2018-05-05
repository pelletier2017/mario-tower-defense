package model.sprite;

import java.io.Serializable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Load a sprite sheet, and either render a specific sprite tile, or animate
 * over a set of given tiles in the same row
 * 
 * @author William Hastings (wbhastings@email.arizona.edu)
 *
 */
public class SpriteSheet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7661867088493278561L;

	private String imagePath;
	private int xOffset, yOffset;
	private int rows, columns[];

	/**
	 * Create a sprite
	 * 
	 * @param image
	 *            the sprite-sheet, or sprite to use
	 * @param xOffset
	 *            the x-offset of each tile box
	 * @param yOffset
	 *            the y-offset of each tile box
	 * @param rows
	 *            the number of rows in the sprite sheet
	 * @param columns
	 *            the number of columns in the sprite sheet by row. Must be the same
	 *            as the number of rows.
	 */
//	public SpriteSheet(Image image, int xOffset, int yOffset, int rows, int columns[]) {
//		this.image = image;
	public SpriteSheet(String imagePath, int xOffset, int yOffset, int rows, int columns[]) {
		this.imagePath = imagePath;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.rows = rows;
		this.columns = columns;
	}

	/**
	 * Get the XOffset of this sprite-sheet
	 * 
	 * @return integer value of the xOffset
	 */
	public int getXOffset() {
		return this.xOffset;
	}

	/**
	 * Get the YOffset of this sprite-sheet
	 * 
	 * @return integer value of the yOffset
	 */
	public int getYOffset() {
		return this.yOffset;
	}

	/**
	 * Get the number of rows in this sprite-sheet
	 * 
	 * @return integer value of the rows
	 */
	public int getRows() {
		return this.rows;
	}

	/**
	 * Get the number of Columns in this sprite-sheet
	 * 
	 * @return integer value of the number of columns by the row requested
	 */
	public int getColumns(int row) {
		return this.columns[row];
	}

	/**
	 * Render a static (non-atimated) tile in the sprites sprite-sheet at 100% size
	 * 
	 * @param gc
	 *            the GraphicsContext in which to generate the pixels
	 * @param row
	 *            the row of the sprite sheet
	 * @param col
	 *            the col of the sprite sheet
	 * @param xPos
	 *            the x-Position
	 * @param yPos
	 *            the y-Position
	 */
	public void staticRender(GraphicsContext gc, int row, int col, int xPos, int yPos) {
		gc.drawImage(ImageLoader.getImage(this.imagePath), this.xOffset * row, this.yOffset * col, this.xOffset, this.yOffset, xPos, yPos,
				this.xOffset, this.yOffset);
	}

	/**
	 * Render a static (non-atimated) tile in the sprites sprite-sheet with custom
	 * size
	 * 
	 * @param gc
	 *            the GraphicsContext in which to generate the pixels
	 * @param row
	 *            the row of the sprite sheet
	 * @param col
	 *            the col of the sprite sheet
	 * @param xPos
	 *            the x-Position
	 * @param yPos
	 *            the y-Position
	 * @param width
	 *            render the static image with a specific width
	 * @param height
	 *            render the static image with a specific height
	 */
	public void staticRender(GraphicsContext gc, int row, int col, double xPos, double yPos, double width,
			double height) {
		gc.drawImage(ImageLoader.getImage(this.imagePath), this.xOffset * col, this.yOffset * row, this.xOffset, this.yOffset, xPos, yPos, width,
				height);
	}
}
