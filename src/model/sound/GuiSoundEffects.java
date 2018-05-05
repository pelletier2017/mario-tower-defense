package model.sound;

import java.util.HashMap;

public class GuiSoundEffects {
    
    private static final String GUI_PATH = "gui/";
    private static final double DEFAULT_VOLUME = 0.5;
    
    private static HashMap<String, SoundEffect> map = new HashMap<>();
    
    private static void putAndPlay(String fileName) {
        putAndPlay(fileName, DEFAULT_VOLUME);
    }
    
    private static void putAndPlay(String fileName, double volume) {
        String path = GUI_PATH + fileName + ".wav";
        if (!map.containsKey(path)) {
            map.put(path, new SoundEffect(path, volume));
        }
        map.get(path).play();
    }
    
    public static void click() {
        putAndPlay("cursor_click");
    }
    
    public static void pause() {
        putAndPlay("pause", 0.2);
    }
    
    public static void chooseMap() {
        putAndPlay("choose_map");
    }
    
    public static void exitGame() {
        putAndPlay("exit_game", 0.7);
    }
    
    public static void hoverButton() {
        putAndPlay("hover_btn", 0.1);
    }
    
    public static void instructionsBtn() {
        putAndPlay("instructions_btn");
    }
    
    public static void loadGame() {
        putAndPlay("load_game", 0.7);
    }
}
