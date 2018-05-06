package model.sound.soundeffect;

import java.io.File;
import java.net.URI;

import javafx.scene.media.AudioClip;

public class SoundEffect {

    private static final String PATH = "src/resources/sounds/";
    
    AudioClip audioClip;
    
    public SoundEffect(String fileName, double volume) {
        URI uri = new File(PATH + fileName).toURI();
        audioClip = new AudioClip(uri.toString());
        audioClip.setVolume(volume);
    }
    
    public void play() {
        audioClip.play();
    }

}
