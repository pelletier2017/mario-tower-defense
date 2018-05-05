package model.entity.enemy;

import model.sprite.SpriteSheet;
import model.sprite.SpriteSheetLoader;

public enum EnemyType {
    
    SIMPLE_ENEMY(SpriteSheetLoader.getSheet("test/MarioWithDeath", 46, 46, 5, new int[] {2,2,8,8,6})),
//    BOSS(SpriteSheetLoader.getSheet("test/MarioWithDeath", 46, 46, 5, new int[] {2,2,8,8,6})),
    FAST_ENEMY(SpriteSheetLoader.getSheet("test/RedToadDeath", 52, 52, 5, new int[] {8,8,8,8,7})),
    DAMAGE_RESISTANT(SpriteSheetLoader.getSheet("test/RedToadDeath", 52, 52, 5, new int[] {8,8,8,8,7})),
	EASYPRINCESS(SpriteSheetLoader.getSheet("enemies/Daisy", 46, 46, 5, new int[] {2,2,4,4,6})),
	MEDIUMPRINCESS(SpriteSheetLoader.getSheet("enemies/Peach", 46, 56, 5, new int[] {2,2,4,4,6})),
	HARDPRINCESS(SpriteSheetLoader.getSheet("enemies/Rosalina", 46, 46, 5, new int[] {2,2,4,4,6})),
	LUIGI(SpriteSheetLoader.getSheet("enemies/Luigi", 46, 46, 5, new int[] {2,2,2,2,6})),
	BOSS(SpriteSheetLoader.getSheet("enemies/Mario", 46, 46, 5, new int[] {2,2,8,8,6})),
	EASYTOAD(SpriteSheetLoader.getSheet("enemies/OldToad", 52, 52, 5, new int[] {8,8,8,8,7})),
	MEDIUMTOAD(SpriteSheetLoader.getSheet("enemies/PinkToad", 52, 52, 5, new int[] {8,8,8,8,7})),
	HARDTOAD(SpriteSheetLoader.getSheet("enemies/RedToad", 52, 52, 5, new int[] {8,8,8,8,7})),
	WALUGI(SpriteSheetLoader.getSheet("enemies/Waluigi", 46, 46, 5, new int[] {2,2,9,9,6})),
	WARIO(SpriteSheetLoader.getSheet("enemies/Wario", 46, 46, 5, new int[] {2,2,9,9,6}));

    private SpriteSheet sheet;

    EnemyType(SpriteSheet sheet) {
        this.sheet = sheet;
    }

    public SpriteSheet getSpriteSheet() {
        return this.sheet;
    }
}
