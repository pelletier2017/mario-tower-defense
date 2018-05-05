package model.sound;

import java.util.HashMap;

public class MapSoundEffects {

    private static final String MAP_PATH = "map/";
    private static final double DEFAULT_VOLUME = 0.5;
    
    private static HashMap<String, SoundEffect> map = new HashMap<>();
    
    private static void putAndPlay(String fileName) {
        putAndPlay(fileName, DEFAULT_VOLUME);
    }
    
    private static void putAndPlay(String fileName, double volume) {
        String path = MAP_PATH + fileName + ".wav";
        if (!map.containsKey(path)) {
            map.put(path, new SoundEffect(path, volume));
        }
        map.get(path).play();
    }
    
    public static void gameWon() {
        putAndPlay("game_won_victory");
    }
    
    public static void gameLost() {
        putAndPlay("game_lost_whistle");
    }
    
    public static void enemyReachedEnd() {
        putAndPlay("enemy_end_2");
    }
    
    public static void shopTowerSelected() {
        putAndPlay("tower_select_2");
    }
    
    public static void shopTowerCannotBuild() {
        putAndPlay("tower_cannot_place", 0.8);
    }
    
    public static void upgradeTowerFail() {
        putAndPlay("tower_cannot_place", 0.8);
    }
    
    public static void selectEntity() {
        putAndPlay("select_entity", 0.8);
    }
    
    public static void clickNothing() {
        putAndPlay("cursor_click", 0.8);
    }
    
    public static void readyPart1() {
        putAndPlay("ready_set_go_part_1", 0.8);
    }
    
    public static void readyPart2() {
        putAndPlay("ready_set_go_part_2", 0.8);
    }
    
    public static void marioLetsGo() {
        putAndPlay("mario_lets_go", 0.8);
    }
    
    public static void marioSpawn() {
        putAndPlay("mario_spawn", 0.8);
    }
}
