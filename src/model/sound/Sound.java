package model.sound;

import java.io.File;
import java.net.URI;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * This class is extended by all sounds played in the game.
 * 
 * @author Andrew Pelletier
 *
 */
public abstract class Sound {

    MediaPlayer player;
    
    public Sound(String filePath, double volume) {
        
//        URI uri = new File(filePath).toURI();
//        Media media = new Media(uri.toString());
        
    	
    	Media media = SoundLoader.loadMedia(filePath);
        player = new MediaPlayer(media);
        player.setVolume(volume);
        player.setAutoPlay(true);
    }
    
}
