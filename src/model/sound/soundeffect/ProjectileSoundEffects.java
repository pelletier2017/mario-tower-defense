package model.sound.soundeffect;

import java.util.HashMap;

public class ProjectileSoundEffects {
	
	private static final String SOUND_PATH = "tower/";
    private static final double DEFAULT_VOLUME = 0.1;
    
    private static HashMap<String, SoundEffect> map = new HashMap<>();
    
    private static void putAndPlay(String fileName) {
        putAndPlay(fileName, DEFAULT_VOLUME);
    }
    
    private static void putAndPlay(String fileName, double volume) {
        String path = SOUND_PATH + fileName + ".wav";
        if (!map.containsKey(path)) {
            map.put(path, new SoundEffect(path, volume));
        }
        map.get(path).play();
    }
    
    public static void fireball_shoot() {
        putAndPlay("fireball");
    }
    
    public static void bomb_hit() {
        putAndPlay("bomb_hit", 0.3);
    }
    
    public static void whomp_hit() {
        putAndPlay("whomp");
    }
    
    public static void bounce_shoot() {
        putAndPlay("throw_shell");
    }
    
    public static void bounce_hit() {
        putAndPlay("shell_hit", 0.3);
    }
    
    public static void tower_build() {
        putAndPlay("build", 0.5);
    }
    
    public static void tower_upgrade() {
        putAndPlay("tower_upgrade", 0.5);
    }
    
    public static void bomb_shoot() {
    	putAndPlay("bomb_throw", 0.5);
    }

}
