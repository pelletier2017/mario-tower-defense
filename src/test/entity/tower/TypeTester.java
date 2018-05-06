package test.entity.tower;

import org.junit.jupiter.api.Test;

import javafx.embed.swing.JFXPanel;
import model.entity.tower.TowerType;
import model.entity.tower.projectiles.ProjectileType;

import static org.junit.jupiter.api.Assertions.*;

public class TypeTester {
	
	@Test
	void testTypesGetter() {
		
		JFXPanel makeItWork = new JFXPanel();
		
		assertNotEquals(null, TowerType.ARROW_TOWER.getSpriteSheet().toString());
		assertNotEquals(null, ProjectileType.ARROW.getSpriteSheet().toString());

	}

}
