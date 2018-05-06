package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import javafx.embed.swing.JFXPanel;
import model.Game;
import model.SelectionManager;
import model.entity.enemy.Enemy;
import model.entity.enemy.EnemyType;
import model.entity.enemy.damageresistant.EasyToad;
import model.entity.enemy.simple.SimpleEnemy;
import model.entity.tower.ArrowTower;
import model.entity.tower.Tower;
import model.entity.tower.TowerType;

class SelectionManagerTest {

    private static Game game = new Game(1);
    
    @Test
    void testGettersAndSetters() {
        
        // used to initialize enemies which need sprite sheets
        new JFXPanel();
        
        SelectionManager manager = new SelectionManager(game);
        assertFalse(manager.isGreenHover());
        assertFalse(manager.isHoveringUpgrade());
        
        manager.setSelectedShopTower(TowerType.ARROW_TOWER);
        assertTrue(manager.hasSelectedShopTower());
        
        manager.setHoverTower(0, 0);
        assertTrue(manager.getHoverTower() != null);
        
        // depends on game having enough money
        assertTrue(manager.isGreenHover());
        
        manager.removeHoverTower();
        assertTrue(manager.getHoverTower() == null);
        
        Enemy enemy = new EasyToad(0, 0);
        manager.setSelectedEntity(enemy);
        assertEquals(enemy, manager.getSelectedEntity());
        assertEquals(null, manager.getSelectedTower());
        
        Tower tower = new ArrowTower(0, 0, true);
        manager.setSelectedEntity(tower);
        assertEquals(tower, manager.getSelectedEntity());
        assertEquals(tower, manager.getSelectedTower());
        
        assertFalse(manager.isHoveringUpgrade());
        manager.setIsHoveringUpgrade(true);
        assertTrue(manager.isHoveringUpgrade());
        
        manager.setHoverTower(0.55, 0.55);
    }
    
    @Test
    void testCanAfford() {
        SelectionManager manager = new SelectionManager(game);
        
        // no tower selected
        assertFalse(manager.canAffordTower());
        
        manager.setSelectedShopTower(TowerType.ARROW_TOWER);
        manager.setHoverTower(0.5, 0.5);
        assertTrue(manager.canAffordTower());
        
        manager.setSelectedEntity(new ArrowTower(0.6, 0.6, true));
        assertTrue(manager.canAffordUpgrade());
    }
    
    @Test
    void testClick() {
        SelectionManager manager = new SelectionManager(game);
        
        game.getTowers().add(new ArrowTower(0.9, 0.5, true));
        manager.clickedPosition(0.9, 0.5);
        assertTrue(manager.getSelectedEntity() != null);
        
        game.getEnemies().add(new EasyToad(0, 0));
        manager.clickedPosition(0, 0);
        assertTrue(manager.getSelectedEntity() != null);
        
        manager.setSelectedEntity(null);
        manager.clickedPosition(0.2, 0.2);
        assertEquals(null, manager.getSelectedEntity());
        
        manager.clickedPosition(0.2, 0.2);
        manager.setSelectedShopTower(TowerType.ARROW_TOWER);
        manager.clickedPosition(0.2, 0.2);
        manager.clickedPosition(0.2, 0.2);
        
        manager.setSelectedShopTower(null);
    }
    
    @Test
    void testClickBuildTower() {
        SelectionManager manager = new SelectionManager(game);
        Tower tower = new ArrowTower(0.125, 0.125, true);
        manager.setSelectedEntity(tower);
        manager.clickedPosition(0.121, 0.0825);
        manager.clickedPosition(0.121, 0.0825);
        manager.clickedPosition(0.121, 0.0825);
        manager.clickedPosition(0.121, 0.0825);
    }

}
