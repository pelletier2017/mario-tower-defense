package test;

import org.junit.jupiter.api.Test;

import javafx.embed.swing.JFXPanel;
import model.sound.MapSoundEffects;
import model.sound.Music;

public class TestMapSound {
	
	
	@Test
	public void playSounds() {
		new JFXPanel();
		MapSoundEffects.clickNothing();
		MapSoundEffects.enemyReachedEnd();
		MapSoundEffects.gameLost();
		MapSoundEffects.gameWon();
		MapSoundEffects.marioLetsGo();
		MapSoundEffects.marioSpawn();
		MapSoundEffects.readyPart1();
		MapSoundEffects.readyPart2();
		MapSoundEffects.selectEntity();
		MapSoundEffects.shopTowerCannotBuild();
		MapSoundEffects.shopTowerSelected();
		MapSoundEffects.upgradeTowerFail();
		
		Music music = new Music("galaxy.mp3");
		music.pause();
		music.resume();
//		music.play();
		music.interrupt();
	}
}
