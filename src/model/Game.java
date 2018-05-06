package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import model.entity.enemy.Enemy;
import model.entity.enemy.EnemyType;
import model.entity.enemy.boss.BossMario;
import model.entity.map.Map;
import model.entity.tower.ArrowTower;
import model.entity.tower.BombTower;
import model.entity.tower.BounceTower;
import model.entity.tower.IceTower;
import model.entity.tower.Tower;
import model.entity.tower.TowerType;
import model.entity.tower.projectiles.Projectile;
import model.sound.soundeffect.MapSoundEffects;
import model.sound.soundeffect.ProjectileSoundEffects;
import model.strategy.Easy;
import model.strategy.GameStrategy;
import model.strategy.Medium;
import model.strategy.StrategyType;
/**
 * Main game class
 * 
 * Controls enemy waves & spawns, adds towers, updates enemies towers and projectiles
 *
 */
public class Game implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4988373688542040432L;
	
	Map map;
	GameStrategy difficulty;
	List<Enemy> enemies;
	List<Tower> towers;
	List<Projectile> projectiles;
	
	private int playerHp;
	private int money;
	private int timeUntilNextSpawn;
	private int timeToNextEnemy;
	private int waveNumber;
	private int numEnemies;
	
	private static final String SAVING_PATH = "src/saves/";
	private int mapNumber;
	
	public Game(int mapNumber) {
	    this.mapNumber = mapNumber;
	    String mapFileName = "map" + mapNumber + ".txt";
		map= new Map(mapFileName);
		
		difficulty= new Easy();
		enemies= new Vector<Enemy>();
		towers= new Vector<Tower>();
		projectiles= new Vector<Projectile>();
		//boss= new EnemyBoss();
		
		playerHp = 20;
		money = 350;
		
		//intializeWaves
		waveNumber=0;
		numEnemies=0;
		timeUntilNextSpawn= 300; //number just chosen to test
		timeToNextEnemy=20;
	}
	
	/**
	 * Sets the difficulty of the current game
	 * @param difficulty
	 */
	public void setGameDifficulty(GameStrategy difficulty) {
		this.difficulty= difficulty;
	}
	
	/**
	 * Get's the game map
	 * @return Map of the current Game's map
	 */
	public Map getMap() {
		return map;
	}
	
	/**
	 * 
	 * @return the current Game's list of enemies
	 */		
	public List<Enemy> getEnemies(){
		return enemies;
	}
	
	/**
	 * 
	 * @return the current Game's list of towers
	 */
	public List<Tower> getTowers(){
		return towers;
	}
	
	/**
	 * 
	 * @return the current Game's projectiles
	 */
	public List<Projectile> getProjectiles(){
		return projectiles;
	}
	
	/**
	 * Get's the game's current difficulty
	 * @return GameStrategy of the game's difficulty
	 */
	public GameStrategy getDifficulty() {
		return difficulty;
	}
	
	/**
	 * Returns if the game is still running
	 * Game is over when all enemies are dead or have reached the end zone
	 * and the last wave is done
	 * @return boolean true if game is over, false if still running
	 */
	public boolean isGameOver() {
		//System.out.println("Wave Number is"+waveNumber);
		if(playerHp<=0) {
			return true;
		}
		return enemies.size() == 0 && waveNumber>difficulty.getNumWaves();
	}
	
	/**
	 * Returns whether the player won the game
	 * @return boolean true if player won, false if not
	 */
	public boolean didPlayerWin() {
		return isGameOver() && playerHp > 0;
	}
	
	/**
	 * Spawns the enemies in waves based on difficulty level
	 */
	public void spawnWaves() {
	    
	    int startSound = 135;
	    int timeBetweenSound = 40;
	    
	    // after first wave mario doesnt speak, so wave starts sooner
	    if (waveNumber != 0) {
	        startSound -= timeBetweenSound;
	    }
	    
	    if (timeUntilNextSpawn == startSound) {
	        MapSoundEffects.readyPart1();
	    } else if (timeUntilNextSpawn == startSound - timeBetweenSound) {
            MapSoundEffects.readyPart1();
        } else if (timeUntilNextSpawn == startSound - (timeBetweenSound * 2)) {
	        MapSoundEffects.readyPart2();
	    } else if (timeUntilNextSpawn == startSound - (timeBetweenSound * 3) && waveNumber == 0) {
	        MapSoundEffects.marioLetsGo();
	    }
	    
		if(timeUntilNextSpawn<=0) {
			//System.out.println("Called spawnWaves & spawnedEnemy");
			spawnEnemies();
			if(numEnemies>=difficulty.getNumEnemies()) {
				timeUntilNextSpawn=500; //increased time between waves
				waveNumber++;
				numEnemies=0;
			}
		}
		else {
			//System.out.println("wave not ready");
			timeUntilNextSpawn--;
		}
	}
	
	/**
	 * Spawns enemies based off type and number determined by current difficulty
	 * 
	 */
	public void spawnEnemies() {
		if(timeToNextEnemy<=0) {
			Enemy newEnemy= difficulty.makeNewEnemy(map.getSpawnX(), map.getSpawnY());
			newEnemy.setPath(map.getPath());
			enemies.add(newEnemy);
			numEnemies++;
			//System.out.println("Enemies size is: "+enemies.size());
			timeToNextEnemy=20;
		}
		else {
			timeToNextEnemy--;
		}
	}
	
	/**
	 * Spawns boss for last wave
	 */
	public void spawnBoss() {
	    MapSoundEffects.marioSpawn();
		Enemy boss = new BossMario(map.getSpawnX(), map.getSpawnY());
		boss.setPath(map.getPath());
		enemies.add(boss);
		waveNumber++;
		//System.out.println("Spawned Boss");
	}

	/**
	 * Increases the difficulty of the game, if already
	 * at the highest difficulty stays on hard
	 */
	public void incrementLevel() {
		difficulty= difficulty.setNextLevel();
	}
	
	/**
	 * Updates the tower objects  in the game
	 */
	public void updateTowers() {
	    try {
    		for (Tower tower: towers) {
    			tower.doAction(enemies, projectiles);
    		}
	    } catch (ConcurrentModificationException e) {
	        System.out.println("caught concurrent modification");
	    }
	}
	
	/**
	 *  Updates the projectile objects in the game
	 */
	public void updateProjectiles() {
	    
	    // remove all projectiles that have hit target
	    List<Projectile> tempProjectiles = new Vector<>();
	    
	    try {
            for (Projectile projectile : projectiles) {
                if (!projectile.isRemovable()) {
                    tempProjectiles.add(projectile);
                }
            }
            projectiles = tempProjectiles;
    	    
    		for (Projectile projectile: projectiles) {
    			projectile.doAction(enemies);
    		}
	    } catch (ConcurrentModificationException e) {
	        System.out.println("caught concurrent modification");
	    }
	}
	
	/**
	 * Updates enemy objects  in the game
	 */
	public void updateEnemies() {
	    
	    
	    try {
    	    // remove all enemies that are dead or that have made it to the endZone
    	    List<Enemy> tempEnemies = new Vector<>();
    	    for (Enemy enemy : enemies) {
    	    		if(enemy.canRemove()) {
    	    			money+=enemy.getMoneyForKill();
    	    		}
    	        if (!enemy.canRemove() && !enemy.madeItToEndZone()) {
    	            tempEnemies.add(enemy);
    	        }
    	    }
    	    enemies = tempEnemies;
    	    
    	    // update movement
    	    for (Enemy enemy : enemies) {
    	        enemy.update();
    	        if (enemy.madeItToEndZone()) {
    	        	if(enemy.getEnemyType() == EnemyType.BOSS) {
    		        	playerHp = 0;
    		        } else{
    	        	 	playerHp--;
    	        	}
    	        	
    	        	// only play sound if this enemy did not end the game
    	        	if (playerHp > 0) {
    	        	    MapSoundEffects.enemyReachedEnd();
    	        	}
    	        }
    	       
    	    }
	    } catch (ConcurrentModificationException e) {
	        System.out.println("caught concurrent modification");
	    }
	}
	
	/**
	 * Updates Game Loop
	 * Called by GUI
	 */
    public void incrementTime() {
    		//spawning waves based on incrementing ticks
    		if(waveNumber<difficulty.getNumWaves()) {
    			spawnWaves();
    		}
    		else if(waveNumber==difficulty.getNumWaves() && difficulty.getStrategy()!=StrategyType.HARD) {
    			incrementLevel();
    		}
    		else if(waveNumber==difficulty.getNumWaves() && difficulty.getStrategy()==StrategyType.HARD){
    			spawnBoss();
    		}
        updateEnemies();
        updateTowers();
        updateProjectiles();
    }

    /**
     * Adds tower to the game
     * @param TowerType
     * @param x coordinate
     * @param y coordinate
     */
    public void addTower(TowerType type, double x, double y) {
        
        // centers tower within a tile
        double towerX = map.centerXFromX(x);
        double towerY = map.centerYFromY(y);
        
        Tower tower = makeTower(type, towerX, towerY, true);
        
        if (tower.getBuildCost() <= money && !towerExists(towerX, towerY)) {
            System.out.println("adding tower");
            ProjectileSoundEffects.tower_build();
            towers.add(tower);
            money -= tower.getBuildCost();
        } else {
            MapSoundEffects.shopTowerCannotBuild();
            System.out.println("Cant build");
        }
        
    }
    
    /**
     * Makes tower in game
     * @param type
     * @param x
     * @param y
     * @return new tower
     */
    public Tower makeTower(TowerType type, double x, double y, boolean isReal) {
        
        Tower tower = null;
        switch (type) {
            case ARROW_TOWER:
                tower = new ArrowTower(x, y, isReal);
                break;
                
            case BOMB_TOWER:
                tower = new BombTower(x, y, isReal);
                break;
                
            case ICE_TOWER:
                tower = new IceTower(x, y, isReal);
                break;
                
            case BOUNCE_TOWER:
                tower = new BounceTower(x, y, isReal);
                break;
                
            default:
                throw new IllegalStateException("Unknown tower type: " + type);
        }
        return tower;
    }
    
    /**
     * Upgrades tower
     * @param tower
     */
    public void purchaseUpgrade(Tower tower) {
        money -= tower.getUpgradeCost();
    }
    
    /**
     * Determines if a tower exists at the given x, y coordinates
     * @param x center of tile X position
     * @param y center of tile Y position
     * @return true if tower exists at that position
     */
    public boolean towerExists(double x, double y) {
        for (Tower tower : towers) {
            if (x == tower.getX() && y == tower.getY()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes projectiles, called when the game is over. Later it can trigger their death animation too.
     */
    public void killProjectiles() {
        projectiles = new Vector<>();
    }
    
    /**
     * Gets player's remaining hit points
     * @return the hit points of the player
     */
    public int getPlayerHp() {
        return playerHp;
    }

    /**
     * Gets player's remaining money
     * @return the player's money
     */
    public int getMoney() {
        return money;
    }
    
    /**
     * Saves the current state of game to a file.
     */
    public void saveToFile() {
        
        File accountFile = new File(SAVING_PATH + "game" + mapNumber + ".dat");
        try {
            ObjectOutputStream fileOut = new ObjectOutputStream(new FileOutputStream(accountFile));
            fileOut.writeObject(this);
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static Game newGame(int mapNumber) {
        return new Game(mapNumber);
    }
    
    public static Game load(int mapNumber) {
        File accountFile = new File(SAVING_PATH + "game" + mapNumber + ".dat");
        if (!accountFile.exists()) {
            System.out.println("File did not exist");
            throw new IllegalArgumentException(accountFile.toString() + " does not exist");
        }
        
        Game game;
        try {
            ObjectInputStream fileIn = new ObjectInputStream(new FileInputStream(accountFile));
            game = (Game) fileIn.readObject();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not load file");
        }
        return game;
    }
    
}
