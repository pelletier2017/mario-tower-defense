package model;

import java.util.List;

import model.entity.Entity;
import model.entity.map.Map;
import model.entity.tower.ArrowTower;
import model.entity.tower.BombTower;
import model.entity.tower.IceTower;
import model.entity.tower.Tower;
import model.entity.tower.TowerType;
import model.sound.soundeffect.MapSoundEffects;
import model.sound.soundeffect.ProjectileSoundEffects;
import view.MapView;

public class SelectionManager {

    private TowerType selectedShopTower;
    private Tower hoverTower;
    private boolean validPlacementLocation;
    
    private Entity selectedEntity;
    private boolean isHoveringUpgrade;
    
    private Game game;
    
    public SelectionManager(Game game) {
        this.game = game;
    }
    
    /**
     * Determines if there is a shop tower selected.
     * 
     * @return True if a shop tower is selected
     */
    public boolean hasSelectedShopTower() {
        return selectedShopTower != null;
    }
    
    /**
     * Sets the tower selected. Can set the towerSelected to null to effectively de-select a tower.
     * 
     * @param newTowerType New Tower type to be selected.
     */
    public void setSelectedShopTower(TowerType newTowerType) {
        
        // de-selects tower from shop
        if (newTowerType == null) {
            hoverTower = null;
            validPlacementLocation = false;
        }
        
        this.selectedShopTower = newTowerType;
    }
    
    /**
     * Gets the tower that the user is hovering their mouse over and ready to build.
     * 
     * @return Tower where mouse is hovering
     */
    public Tower getHoverTower() {
        return hoverTower;
    }
    
    /**
     * Sets the temporary hover tower to the selectedShopTower at a given x, y.
     * Will not add the hover tower if it is an invalid tower position.
     * 
     * @param x position mouse is dragging over
     * @param y position mouse is dragging over
     */
    public void setHoverTower(double x, double y) {
        
        Map map = game.getMap();
        
        double centerX = map.centerXFromX(x);
        double centerY = map.centerYFromY(y);
        
        if (map.isWall(x, y) && !game.towerExists(centerX, centerY)) {
            validPlacementLocation = true;
        } else {
            validPlacementLocation = false;
        }
        
        this.hoverTower = game.makeTower(selectedShopTower, centerX, centerY, false);
    }
    
    public void removeHoverTower() {
        hoverTower = null;
    }
    
    /**
     * Will handle clicking enemies/selecting towers and adding towers
     * @param x
     * @param y
     */
    public void clickedPosition(double x, double y) {
        
        if (game.isGameOver()) {
            return;
        }
        
        // first check for upgrade box
        if (selectedEntity instanceof Tower) {
            Tower tower = (Tower) selectedEntity;
            if (MapView.towerUpgradeBoxClicked(tower, x, y) && !tower.isMaxLevel()) {
                System.out.println("" + x + " " + y);
                if (canAffordUpgrade()) {
                    tower.upgrade();
                    game.purchaseUpgrade(tower);
                } else {
                    MapSoundEffects.upgradeTowerFail();
                }
                return;
            }
        }
        
        Entity clickedEntity = null;
        
        // if no upgrade box clicked on, check for enemy
        if (clickedEntity == null) {
            clickedEntity = entityAtLocation(game.getEnemies(), x, y);
        }
        
        // if no enemy found, check for tower
        if (clickedEntity == null) {
            clickedEntity = entityAtLocation(game.getTowers(), x, y);
        }
        
        // if anything found, select it
        if (clickedEntity != null && !hasSelectedShopTower()) {
            MapSoundEffects.selectEntity();
            setSelectedEntity(clickedEntity);
            return;
        }
        
        // deselect any unit previously selected
        setSelectedEntity(null);
        
        if (hasSelectedShopTower()) {
            if (game.getMap().isWall(x, y)) {
                // sound effect of build tower in Game where validity is decided
                game.addTower(selectedShopTower, x, y);
            } else {
                MapSoundEffects.shopTowerCannotBuild();
            }
        }
    }

    /**
     * Returns Entity if x,y position is within bounds of the entity's height and width.
     * 
     * @param x position X direction
     * @param y position Y direction
     * @return First Entity within bounds or null if no enemy within bounds
     */
    private <T extends Entity> Entity entityAtLocation(List<T> entities, double x, double y) {
        for (Entity entity : entities) {
            if (entity.inHitBox(x, y)) {
                return entity;
            }
        }
        return null;
    }
    
    /**
     * Determines if the hovering tower will be green or not.
     * 
     * @return True if the hovering tower should be green.
     */
    public boolean isGreenHover() {
        return validPlacementLocation && canAffordTower();
    }

    /**
     * Sets the selected entity to a new entity.
     * 
     * @param selectedEntity new entity
     */
    public void setSelectedEntity(Entity selectedEntity) {
        this.selectedEntity = selectedEntity;
    }
    
    /**
     * Gets the selected entity selected by the user.
     * @return Entity selected
     */
    public Entity getSelectedEntity() {
        return selectedEntity;
    }
    
    /**
     * Returns the selected tower if one was selected by the user. Will be null if nothing is selected
     * or if an enemy is selected instead.
     * 
     * @return Tower if it exists.
     */
    public Tower getSelectedTower() {
        if (selectedEntity instanceof Tower) {
            Tower tower = (Tower) selectedEntity;
            return tower;
        } else {
            return null;
        }
    }

    /**
     * Returns true if the user can afford the currently selected hoverTower.
     * 
     * @return True if can afford the tower
     */
    public boolean canAffordTower() {
        return hoverTower != null && hoverTower.getBuildCost() <= game.getMoney();
    }
    
    /**
     * Returns true if the user can afford the upgrade for the currently selected tower.
     * 
     * @return True if can afford the upgrade
     */
    public boolean canAffordUpgrade() {
        return getSelectedTower() != null && getSelectedTower().getUpgradeCost() <= game.getMoney();
    }

    /**
     * Returns true if the user is currently hovering their mouse over the selected tower's upgrade.
     * 
     * @return True if the user's mouse is currently over the tower's upgrade.
     */
    public boolean isHoveringUpgrade() {
        return isHoveringUpgrade;
    }
    
    /**
     * Sets the boolean for if the user is currently hovering over a tower.
     * 
     * @param b new isHoveringUpgrade
     */
    public void setIsHoveringUpgrade(boolean b) {
        isHoveringUpgrade = b;
    }
    
}
