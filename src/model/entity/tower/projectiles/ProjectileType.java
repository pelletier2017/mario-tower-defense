package model.entity.tower.projectiles;

import model.sprite.SpriteSheet;
import model.sprite.SpriteSheetLoader;

public enum ProjectileType {

	ARROW(SpriteSheetLoader.getSheet("projectiles/PlantProjectile", 58, 58, 6, new int[] {4,0,4,0,4,0})),
	BOMB(SpriteSheetLoader.getSheet("projectiles/HammerGuyProjectiles", 46, 46, 6, new int[] {4,3,4,3,3,3})),
	ICE(SpriteSheetLoader.getSheet("projectiles/ThwompProjectiles", 100, 100, 6, new int[] {0,6,0,6,0,6})),
	BOUNCE(SpriteSheetLoader.getSheet("projectiles/CloudGuyProjectiles", 46, 46, 6, new int[] {4,0,4,0,4,0}));
	
	private SpriteSheet sSheet;
	
	ProjectileType(SpriteSheet sSheet) {
		this.sSheet = sSheet;
	}
	
	public SpriteSheet getSpriteSheet() {
		return this.sSheet;
	}
	
//	Old code for projectiles
//    ARROW("arrow.png"),
//	BOMB("bomb.png"),
//	ICE("ice.png"),
//	BOUNCE("bounce.png");
//
//    private String fileName;
//
//    ProjectileType(String fileName) {
//        this.fileName = fileName;
//    }
//
//    public String getFileName() {
//        return fileName;
//    }
    
}
