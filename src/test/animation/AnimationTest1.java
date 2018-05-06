package test.animation;

import javafx.embed.swing.JFXPanel;
import model.entity.animation.Animation;
import model.entity.animation.AnimationState;
import model.entity.enemy.EnemyType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class AnimationTest1 {
	
	@Test
	public void testAnimationState() {
		new JFXPanel();
		Animation a = new Animation(EnemyType.SIMPLE_ENEMY.getSpriteSheet(), 0.0, 0.0, 0.03, 0.03);
		assertEquals(a.getAnimationState(), Animation.DEFAULT_STATE);
		
		a.setAnimationState(AnimationState.DIE);
		assertEquals(a.getAnimationState(), AnimationState.DIE);
		assertNotEquals(a.getAnimationState(), AnimationState.RIGHT);
		assertEquals(a.getRow(), AnimationState.DIE.getRow());
	}
	
	@Test
	public void testColumnChanges() {
		new JFXPanel();
		Animation a = new Animation(EnemyType.SIMPLE_ENEMY.getSpriteSheet(), 0.0, 0.0, 0.03, 0.03);
		assertEquals(a.getCol(), 0);
		
		for(int i = 0; i < Animation.DEFAULT_CHANGE_ON; i++) {
			a.update();
		}
		assertEquals(a.getCol(), 1);
		
		for(int i = 0; i < Animation.DEFAULT_CHANGE_ON * 3; i++) {
			a.update();
		}
//		assertEquals(a.getCol(), 0);
	}
	
	
	int numLoops = 0;
	int numFrameChanges = 0;
	int numStateChanges = 0;
	int countedLoops = 0;
	int countedFrameChanges = 0;
	int countedStateChanges = 0;
	
//	@Test
//	public void testEvents() {
//		new JFXPanel();
//		Animation a = new Animation(EnemyType.SIMPLE_ENEMY, 0.0, 0.0, 0.03, 0.03);
//		
//		a.setOnFrameChange(e -> {
//			countedFrameChanges++;
//			assertEquals(countedFrameChanges, numFrameChanges);
//		});
//		a.setOnLoop(e -> {
//			countedLoops++;
//			assertEquals(countedLoops, numLoops);
//		});
//		a.setOnStateChange(e -> {
//			countedStateChanges++;
//			assertEquals(countedStateChanges, numStateChanges);
//			assertEquals(((AnimationEvent) e).getAnimation(), a);
//		});
//		
//		for (int i = 0; i < Animation.DEFAULT_CHANGE_ON * 10; i++) {
//			numFrameChanges = ((i != 0) && ((i % Animation.DEFAULT_CHANGE_ON) == 0)) ? numFrameChanges + 1 : numFrameChanges;
//			numLoops = ((numLoops != 0) && ((numFrameChanges % 4) == 0)) ? numLoops + 1 : numLoops;
//			a.update();
//		}
//		numStateChanges++;
//		a.setAnimationState(AnimationState.RIGHT);
//		numStateChanges++;
//		a.setAnimationState(AnimationState.DOWN);
//	}
	
	@Test
	public void testReset() {
		new JFXPanel();
		Animation a = new Animation(EnemyType.SIMPLE_ENEMY.getSpriteSheet(), 0.0, 0.0, 0.03, 0.03);
		assertEquals(a.getCol(), 0);
		for(int i = 0; i < Animation.DEFAULT_CHANGE_ON * 3; i++) {
			a.update();
		}
		assertNotEquals(a.getCol(), 0);
		a.reset();
		assertEquals(a.getCol(), 0);
	}
	
	@Test
	public void testSetTicChange() {
		new JFXPanel();
		Animation a = new Animation(EnemyType.SIMPLE_ENEMY.getSpriteSheet(), 0.0, 0.0, 0.03, 0.03);
		assertEquals(a.getCol(), 0);
		for(int i = 0; i < Animation.DEFAULT_CHANGE_ON * 3; i++) {
			a.update();
		}
		assertEquals(a.getCol(), 3);
		a.setTicPerChange(Animation.DEFAULT_CHANGE_ON * 2);
		for(int i = 0; i < Animation.DEFAULT_CHANGE_ON * 2; i++) {
			a.update();
		}
		assertNotEquals(a.getCol(), 1);
//		assertEquals(a.getCol(), 0);
	}
}
