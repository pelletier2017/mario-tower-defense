package test.entity.tower;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import javafx.embed.swing.JFXPanel;
import model.entity.enemy.TestEnemy;
import model.entity.tower.ArrowTower;
import model.entity.tower.focus.Focus;
import model.entity.tower.focus.FocusFirst;
import model.entity.tower.focus.FocusLast;
import model.entity.tower.focus.FocusStrong;
import model.entity.tower.focus.FocusWeak;

public class FocusTester {
	
	@Test
	void testFocus() {
		
		JFXPanel makeItWork = new JFXPanel();
		Focus focusFirst = new FocusFirst();
		Focus focusLast = new FocusLast();
		Focus focusStrong = new FocusStrong();
		Focus FocusWeak = new FocusWeak();
		
		ArrowTower arrowTower = new ArrowTower(200, 200, false);
		
		arrowTower.setFocus(focusLast);
		
		assertTrue(true);
		
		TestEnemy testEnemyStrong = new TestEnemy(451, 200,100,100);
		testEnemyStrong.setHP(500);
		
		TestEnemy testEnemyWeak = new TestEnemy(451, 200,100,100);
		testEnemyWeak.setHP(5);
		
		focusStrong.addChoice(testEnemyWeak);
		focusStrong.addChoice(testEnemyStrong);
		
		assertEquals(focusStrong.getChoice().getHP(), 500);
		assertEquals(focusStrong.getChoice(), null);
		
		FocusWeak.addChoice(testEnemyStrong);
		FocusWeak.addChoice(testEnemyWeak);
		
		assertEquals(FocusWeak.getChoice().getHP(), 5);
		assertEquals(FocusWeak.getChoice(), null);
		
		focusLast.addChoice(testEnemyStrong);
		focusLast.addChoice(testEnemyWeak);
		
		// neither have a path so both are distance 0
		assertEquals(focusLast.getChoice().getHP(), 5);
		assertEquals(focusLast.getChoice(), null);
		
		focusFirst.addChoice(testEnemyStrong);
		focusFirst.addChoice(testEnemyWeak);
		
		assertEquals(focusFirst.getChoice().getHP(), 500);
		assertEquals(focusFirst.getChoice(), null);
		
	}

}
