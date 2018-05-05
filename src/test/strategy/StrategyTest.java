package test.strategy;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import javafx.embed.swing.JFXPanel;
import model.entity.enemy.Enemy;
import model.strategy.Easy;
import model.strategy.GameStrategy;
import model.strategy.Hard;

class StrategyTest {

	//check next level and get strategy in hard
	@Test
	void testHard() {
		GameStrategy hard= new Hard();
		GameStrategy hard2= hard.setNextLevel();
		assertEquals(hard2.getStrategy(),hard.getStrategy());
	}

	//Make a thousand enemies to check that they all enemies get made
	@Test
	void testEasy() {
		new JFXPanel();
		GameStrategy easy= new Easy();
		int i=0;
		while(i<1000) {
			Enemy enemy= easy.makeNewEnemy(1.3, 1.3);
			i++;
		}

	}
}
