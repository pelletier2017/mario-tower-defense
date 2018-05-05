package model.sprite;

import java.util.HashMap;

import javafx.scene.image.Image;
import model.entity.map.tiles.TileType;
import model.entity.tower.TowerType;
import model.entity.tower.projectiles.ProjectileType;

/**
 * Loads images using flyweight pattern. Only one instance of each Image 
 * is needed per type of Tile, Tower, and Projectile.
 * 
 * @author Andrew Pelletier
 *
 */
public class ImageLoader {

    // Javafx Image is treated differently from java File, so relative path starts at src not project root
    // This is why path starts at resources and not src
    private static final String TILE_PATH = "resources/tiles/";
    
    private static HashMap<TileType, Image> tileMap = new HashMap<>();
    private static HashMap<String, Image> spriteSheets = new HashMap<>();
    
    public static Image getImage(TileType type) {
        if (tileMap.get(type) == null) {
            Image img = new Image(TILE_PATH + type.getFileName());
            tileMap.put(type, img);
        }
        
        return tileMap.get(type);
    }
    
    public static Image getImage(String pathName) {
    	if(spriteSheets.containsKey(pathName)) {
    		return spriteSheets.get(pathName);
    	} else {
    		Image i = new Image(pathName, false);
    		spriteSheets.put(pathName, i);
    		return i;
    	}
    }

}
