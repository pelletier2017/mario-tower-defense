package model.sound;

import java.util.HashMap;

import javafx.scene.media.AudioClip;

public class EnemySoundEffects {
	
	private static final String SOUND_PATH = "enemy/";
	private static final String EXT = ".wav";
    private static final double DEFAULT_VOLUME = 0.5;
    
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
    
    public static void mario_dies() {
        putAndPlay("mario_die");
    }
    
    public static void luigi_dies() {
        putAndPlay("luigi_die");
    }
    
    public static void wario_dies() {
        putAndPlay("wario_die");
    }
    
    public static void waluigi_dies() {
        putAndPlay("waluigi_die");
    }
    
    public static void toad_dies() {
        putAndPlay("toad_die");
    }
    
    public static void toadette_dies() {
        putAndPlay("toadette_die");
    }
    
    public static void old_toad_dies() {
        putAndPlay("old_toad_die");
    }
    
    public static void peach_dies() {
        putAndPlay("peach_die");
    }
    
    public static void daisy_dies() {
        putAndPlay("generic_female_die");
    }
    
    public static void rosalina_dies() {
        putAndPlay("rosalina_die");
    }

	public static void mario_summond() {
		putAndPlay("mario_spawn");
	}
    
}
