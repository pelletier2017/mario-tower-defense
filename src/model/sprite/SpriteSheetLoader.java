package model.sprite;

import java.util.HashMap;

import javafx.scene.image.Image;

/**
 * This is a static class that uses the Flyweight design pattern to reduce the
 * amount of large spritesheets that are loaded into memory. It will return an
 * already loaded image, or load the image and return it adding it to the loaded
 * image map
 * 
 * @author William Hastings (wbhastings@email.arizona.edu)
 */
public class SpriteSheetLoader {

	public static final String SPRITE_PATH = "file:src/resources/sprites/";
	public static final String SPRITE_EXT = ".png";
	private static HashMap<String, SpriteSheet> spriteMap = new HashMap<String, SpriteSheet>();

	/**
	 * Get the SpriteSheet by it's name and sub-path
	 * 
	 * @param sheetName
	 *            the sprite sheets name and sub-path in the resources/sprites
	 *            directory
	 * @param xOffset
	 *            the yOffset of each image
	 * @param yOffset
	 *            the xOffset of each image
	 * @param rows
	 *            the number of rows
	 * @param columns
	 *            array of column lengths in order of the rows 5 rows means 5 int
	 *            length array of columns
	 * @return
	 */
	public static SpriteSheet getSheet(String sheetName, int xOffset, int yOffset, int rows, int columns[]) {
		if (spriteMap.containsKey(sheetName))
			return spriteMap.get(sheetName);
		else {
//			Image i = new Image(SPRITE_PATH + sheetName + SPRITE_EXT, false);
			SpriteSheet newSprite = new SpriteSheet(SPRITE_PATH + sheetName + SPRITE_EXT, xOffset, yOffset, rows, columns);
			spriteMap.put(sheetName, newSprite);
			return newSprite;
		}
	}

}
