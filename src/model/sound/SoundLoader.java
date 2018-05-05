package model.sound;

import java.io.File;
import java.net.URI;
import java.util.HashMap;

import javafx.scene.media.Media;

public class SoundLoader {
	public static HashMap<String, Media> songList = new HashMap<>();
	
	public static Media loadMedia(String path) {
		if (songList.containsKey(path)) return songList.get(path);
		else {
			URI uri = new File(path).toURI();
	        Media media = new Media(uri.toString());
	        songList.put(path, media);
	        return media;
		}
	}
}
