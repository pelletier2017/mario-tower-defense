package test;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

import javafx.embed.swing.JFXPanel;
import model.Game;
import model.entity.enemy.Enemy;
import model.entity.tower.ArrowTower;
import model.entity.tower.Tower;
import model.entity.tower.TowerType;
import model.strategy.Easy;
import model.strategy.Hard;
import model.strategy.Medium;

class GameTest {

	@Test
	void testGameGetters() {
		Game game= new Game(1);
		assertEquals(game.getEnemies().size(),0);
		assertEquals(game.getTowers().size(),0);
		assertEquals(game.getProjectiles().size(),0);
		Easy easy= new Easy();
		game.setGameDifficulty(easy);
		assertEquals(game.getDifficulty(),easy);
		assertEquals(game.getPlayerHp(),20);
		assertEquals(game.getMoney(),350);
		//get map	
	}
	
	@Test
	void testGameStart() {
		Game game= new Game(1);
		assertFalse(game.isGameOver());
		assertFalse(game.didPlayerWin());
	}
	
	@Test
	void testTowers() {
		Game game= new Game(1);
		assertFalse(game.towerExists(1.3250000000000002, 1.5250000000000001));
		game.addTower(TowerType.ARROW_TOWER, 1.3, 1.5);
		assertEquals(game.getTowers().size(),1);
		assertTrue(game.towerExists(1.3250000000000002, 1.5250000000000001));
	}
	
	@Test
	void testIncrementEasy() {
		new JFXPanel();
		Game game= new Game(1);
		game.setGameDifficulty(new Easy());
		while(!game.isGameOver()) {
			game.incrementTime();
		}
		assertTrue(game.isGameOver());
	}
	
	@Test
	void testIncrementMedium() {
		new JFXPanel();
		Game game= new Game(1);
		game.setGameDifficulty(new Medium());
		while(!game.isGameOver()) {
			game.incrementTime();
		}
		assertTrue(game.isGameOver());
	}
	
	@Test
	void testIncrementHard() {
		new JFXPanel();
		Game game= new Game(1);
		game.setGameDifficulty(new Hard());
		while(!game.isGameOver()) {
			game.incrementTime();
		}
		assertTrue(game.isGameOver());
	}
	
	@Test
	void testKillProjectile() {
		Game game= new Game(1);
		game.killProjectiles();
		assertEquals(game.getProjectiles().size(), 0);
	}
	
	@Test
	void testIncrementLevel() {
		Game game= new Game(1);
		Easy easy= new Easy();
		game.setGameDifficulty(easy);
		game.incrementLevel();
		assertFalse(game.getDifficulty()==easy);
	}
	
	@Test
	void testPathAndMap() {
		new JFXPanel();
		Game game= new Game(1);
		Enemy newEnemy= game.getDifficulty().makeNewEnemy(game.getMap().getSpawnX(), game.getMap().getSpawnY());
		newEnemy.setPath(game.getMap().getPath());
		assertEquals(newEnemy.getPath(),game.getMap().getPath());	
	}
	
	@Test
	void testPlayerAction() {
		new JFXPanel();
		Game game= new Game(1);
		game.addTower(TowerType.ARROW_TOWER, 1.3, 1.5);
		game.addTower(TowerType.ICE_TOWER, 1.6, 1.4);
		game.addTower(TowerType.BOMB_TOWER, 0.5, 0.3);
		game.addTower(TowerType.BOUNCE_TOWER, 1.2, 0.1);
		Tower tower= new ArrowTower(1.3,1.5, true);
		double oldMoney= game.getMoney();
		game.purchaseUpgrade(tower);
		assertEquals(game.getMoney(),oldMoney-tower.getUpgradeCost());
		while(!game.isGameOver()) {
			game.incrementTime();
		}
	}
	
	@Test
	void testSpawnBoss() {
		new JFXPanel();
		Game game= new Game(1);
		game.spawnBoss();
	}
	
	@Test
	void testReadAndWrite() {
		new JFXPanel();
		Game game= Game.newGame(1);
		game.saveToFile();
		game= Game.load(1);
	}
}
