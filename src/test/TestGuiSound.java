package test;

import org.junit.jupiter.api.Test;

import model.sound.soundeffect.GuiSoundEffects;

public class TestGuiSound {
	@Test
	public void playSound() {
		GuiSoundEffects.chooseMap();
		GuiSoundEffects.click();
		GuiSoundEffects.exitGame();
		GuiSoundEffects.hoverButton();
		GuiSoundEffects.instructionsBtn();
		GuiSoundEffects.loadGame();
		GuiSoundEffects.pause();
	}
}
