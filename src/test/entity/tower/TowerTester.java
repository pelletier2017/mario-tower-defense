package test.entity.tower;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import javafx.embed.swing.JFXPanel;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import model.entity.tower.ArrowTower;
import model.entity.tower.TowerType;

public class TowerTester {
	
	@Test
	void testGettersSetters() {
		
		JFXPanel makeItWork = new JFXPanel();
		
		ArrowTower arrowTower = new ArrowTower(200, 200, true);
		arrowTower.setUpgadeCost(50);
		assertTrue( arrowTower.getUpgradeCost() == 50);
		assertTrue( arrowTower.getBuildCost( ) > 0 );
		
		arrowTower.playBuiltSoundEffect();
		arrowTower.playDestroyedSoundEffect();
		
		assertTrue( arrowTower.getReloadTime() > 0 );
		assertTrue( arrowTower.getTowerType() == TowerType.ARROW_TOWER);
		
		final Canvas canvas = new Canvas(250,250);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		arrowTower.draw(gc, 1.0);
		
		assertFalse(arrowTower.isMaxLevel());

		
	}

}
