package model.entity.tower;

import model.sprite.SpriteSheet;
import model.sprite.SpriteSheetLoader;

/**
 * This enum is used to determine which tower is selected by the Shop View and
 * the location of sprites used to draw a certain Tower.
 * 
 * @author Andrew Pelletier
 *
 */
public enum TowerType {

	ARROW_TOWER(SpriteSheetLoader.getSheet("towers/PlantAttackSheet", 46, 46, 6, new int[] {5,4,5,4,5,4})),
	BOMB_TOWER(SpriteSheetLoader.getSheet("towers/HammerGuyAttackSheet", 46, 46, 6, new int[] {8,6,8,6,8,6})),
	ICE_TOWER(SpriteSheetLoader.getSheet("towers/ThwompAttackSheet", 74, 74, 6, new int[] {2,9,2,9,2,8})),
	BOUNCE_TOWER(SpriteSheetLoader.getSheet("towers/CloudGuyAttackSheet", 80, 80, 6, new int[] {2,16,4,16,4,16}));

	private SpriteSheet ss;
	
	TowerType(SpriteSheet ss) {
		this.ss = ss;
	}
	
	public SpriteSheet getSpriteSheet() {
		return this.ss;
	}
//
//    ARROW_TOWER("arrow_tower.png"),
//	BOMB_TOWER("bomb_tower.png"),
//	ICE_TOWER("ice_tower.png"),
//	BOUNCE_TOWER("bounce_tower.png");
//
//    private String fileName;
//
//    TowerType(String fileName) {
//        this.fileName = fileName;
//    }
//
//    public String getFileName() {
//        return fileName;
//    }
//
}
