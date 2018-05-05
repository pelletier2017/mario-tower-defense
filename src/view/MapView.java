package view;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import controller.GameController;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import model.Game;
import model.SelectionManager;
import model.entity.Entity;
import model.entity.enemy.Enemy;
import model.entity.map.path.BranchingPath;
import model.entity.map.path.Path;
import model.entity.map.tiles.MapTile;
import model.entity.map.tiles.TileType;
import model.entity.tower.Tower;
import model.entity.tower.projectiles.Projectile;
import model.sprite.ImageLoader;
import model.utils.ImageUtils;
import model.utils.RotationUtils;

/**
 * This Class contains logic for drawing to the Map that the user will interact
 * with. There are event handlers that will register when the user clicks on the
 * map and it will send that back to the controller to handle.
 * 
 * @author Andrew Pelletier
 *
 */
public class MapView extends BorderPane {

    public static final double CANVAS_SIZE = 800;
    public static final double TOWER_UPGRADE_BOX_SIZE = 0.04;
    public static final double TOWER_UPGRADE_BOX_OFFSET = 0.005;

    private GameController controller;
    private GraphicsContext gc;
    
    // just for lightning test
    private int lightningLength = 0;
    private Double[] a = new Double[] {0.27, 0.72};
    private Double[] b = new Double[] {0.27, 0.85};
    private ArrayList<Double[]> lightningPoints = RotationUtils.createLightning(a, b);

    // view having a SpriteManager kind of breaks the concept of separating model
    // and view, but not sure how else to do it
    private ImageLoader imageLoader = new ImageLoader();

    public MapView(GameController gameController) {
        controller = gameController;

        Canvas canvas = new Canvas(CANVAS_SIZE, CANVAS_SIZE);
        gc = canvas.getGraphicsContext2D();
        setCenter(canvas);

        setStyle("-fx-background-color: black;");

        // registers click at percent x, y (leaves flexibility for resizing canvas)
        canvas.setOnMouseClicked(e -> {
            
            if (e.getButton() == MouseButton.PRIMARY) {
                controller.clickedOnMap(e.getX() / CANVAS_SIZE, e.getY() / CANVAS_SIZE);
            } else if (e.getButton() == MouseButton.SECONDARY) {
                controller.rightClicked();
            }
            
        });
        
        canvas.setOnMouseExited( e -> controller.removeHoverTower());
        canvas.setOnMouseMoved( e -> controller.mouseMovedOnCanvas(e.getX() / CANVAS_SIZE, e.getY() / CANVAS_SIZE));
    }

    /**
     * Draws the background of the map using its tiles.
     * 
     * @param tiles
     *            Tile[][] representing the map
     */
    public void drawBackground(MapTile[][] tiles) {

        double tileSize = CANVAS_SIZE / tiles.length;

        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[0].length; col++) {

                TileType type = tiles[row][col].getTileType();
                Image img = imageLoader.getImage(type);

                double startX = col * tileSize;
                double startY = row * tileSize;
                gc.drawImage(img, startX, startY, tileSize, tileSize);

            }
        }
    }

    /**
     * Draws lines connecting the path, used for debugging path.
     * 
     * @param path
     *            linked list of path objects
     */
    public void drawPath(Path path) {

        if (path.getNext() == null) {
            return;
        }

        Path next = path.getNext();

        double x1 = path.getX() * CANVAS_SIZE;
        double y1 = path.getY() * CANVAS_SIZE;
        double x2 = next.getX() * CANVAS_SIZE;
        double y2 = next.getY() * CANVAS_SIZE;

        gc.setStroke(Color.BLACK);
        gc.strokeLine(x1, y1, x2, y2);
        drawPath(next);

        if (path instanceof BranchingPath) {
            next = path.getNext();
            x2 = next.getX() * CANVAS_SIZE;
            y2 = next.getY() * CANVAS_SIZE;

            gc.setStroke(Color.BLACK);
            gc.strokeLine(x1, y1, x2, y2);
            drawPath(next);
        }

    }
    
    /**
     * Draws all components of the game onto the canvas.
     * @param game Game to be drawn to canvas
     */
    public void drawGame(Game game, SelectionManager selectionManager) {
        drawBackground(game.getMap().getTiles());
        
        drawEntities(game.getEnemies());
        drawEntities(game.getTowers());
        drawEntities(game.getProjectiles());
        
        drawHoverTower(selectionManager.getHoverTower(), selectionManager.isGreenHover());
        
        drawRangeRing(selectionManager.getSelectedTower(), RangeRingFill.NO_FILL);
        drawTowerUpgrade(selectionManager.getSelectedTower(), selectionManager.canAffordUpgrade(), selectionManager.isHoveringUpgrade());
        drawSelectionBox(selectionManager.getSelectedEntity());
    }
    
    private void drawSelectionBox(Entity entity) {
        
        if (entity == null) {
            return;
        }
        
        // dont draw selection for dead enemies
        if (entity instanceof Enemy) {
            Enemy enemy = (Enemy) entity;
            if (!enemy.isAlive()) {
                return;
            }
        }
        
        double width = entity.getWidth() * CANVAS_SIZE;
        double height = entity.getHeight() * CANVAS_SIZE;
        double x = entity.getX() * CANVAS_SIZE;
        double y = entity.getY() * CANVAS_SIZE;
        
        double topLeftX = x - (width / 2);
        double topLeftY = y - (height / 2);
        
        gc.setStroke(Color.AQUAMARINE);
        gc.setLineWidth(2);
        gc.strokeRect(topLeftX, topLeftY, width, height);
    }

    /**
     * Draws entities to canvas using their x, y, width, and height.
     * @param entities Entities within Game
     */
    private <T extends Entity> void drawEntities(List<T> entities) {
        try {
            for (Entity entity : entities) {
                Image img = getImage(entity);
                drawEntity(img, entity);
            }
        } catch (ConcurrentModificationException e) {
            // Array being modified while drawing, it will redraw on next tick
        }
        
    }
    
    /**
     * Determines the Image based on the class type and imageLoader.
     * @param entity Entity to get image for
     * @return JavaFX image representing the entity
     */
    private Image getImage(Entity entity) {
        
        Image img = null;
        if (entity instanceof Tower) {
//            Tower tower = (Tower) entity;
//            img = imageLoader.getImage(tower.getTowerType());
        	img = null;  
        } else if (entity instanceof Enemy) {
//            Enemy enemy = (Enemy) entity;
//            img = imageLoader.getImage(enemy.getEnemyType());
            img = null;
        } else if (entity instanceof Projectile) {
//            Projectile projectile = (Projectile) entity;
//            img = imageLoader.getImage(projectile.getProjectileType());
            img = null;
        } else {
            throw new IllegalArgumentException("Entity does not have an image");
        }
        
        return img;
    }

    /**
     * Draws the image at the location, height, and width of the entity.
     * @param img Image representing the entity
     * @param entity entity containing a location and size
     */
    private void drawEntity(Image img, Entity entity) {
    	if (entity instanceof Enemy) {
    		Enemy e = (Enemy) entity;
    		e.draw(gc, CANVAS_SIZE);
    	} else if (entity instanceof Projectile) {
    		Projectile p = (Projectile) entity;
    		p.draw(gc, CANVAS_SIZE);
    	} else if (entity instanceof Tower) {
    		Tower t = (Tower) entity;
    		t.draw(gc, CANVAS_SIZE);
    	} else {
	        double x = entity.getX() * CANVAS_SIZE;
	        double y = entity.getY() * CANVAS_SIZE;
	        double width = entity.getWidth() * CANVAS_SIZE;
	        double height = entity.getHeight() * CANVAS_SIZE;
	        
	        double topLeftX = x - (width / 2);
	        double topLeftY = y - (height / 2);
	        
	        gc.drawImage(img, topLeftX, topLeftY, width, height);
    	}
    }
    
    /**
     * Draws phantom tower over location that the mouse is hovering ready to build.
     * Will not draw a tower if is null.
     * 
     * @param hoverTower Tower over mouse location.
     */
    private void drawHoverTower(Tower hoverTower, boolean valid) {
        
        if (hoverTower == null) {
            return;
        }
        
        Image img = getImage(hoverTower);
        img = ImageUtils.setOpacity(img, 0.7);
        if (valid) {
            // green tint
            img = ImageUtils.addTint(img, -10, 50, -10);
        } else {
            // red tint
            img = ImageUtils.addTint(img, 50, -10, -10);
        }
        
        drawEntity(img, hoverTower);
        
        RangeRingFill fill = null;
        if (valid) {
            fill = RangeRingFill.GREEN;
        } else {
            fill = RangeRingFill.RED;
        }
        drawRangeRing(hoverTower, fill);
        
    }

    /**
     * Draws a circular ring around the tower with the tower's range.
     * @param tower Tower to draw the circle around
     */
    private void drawRangeRing(Tower tower, RangeRingFill fill) {
        
        if (tower == null) {
            return;
        }
        
        // draw circle for tower range
        double range = tower.getRange();
        
        double topLeftX = (tower.getX() - range) * CANVAS_SIZE;
        double topLeftY = (tower.getY() - range) * CANVAS_SIZE;
        double width = (range * 2) * CANVAS_SIZE;
        double height = (range * 2) * CANVAS_SIZE;
        
        double opacity = 0.2;
        double strokeWidth = 2;
        
        switch (fill) {
            case GREEN:
                gc.setFill(Color.color(0, 1, 0, opacity));
                gc.fillOval(topLeftX, topLeftY, width, height);
                gc.setStroke(Color.color(0, 1, 0));
                break;
            
            case RED:
                gc.setFill(Color.color(1, 0, 0, opacity));
                gc.fillOval(topLeftX, topLeftY, width, height);
                gc.setStroke(Color.color(1, 0, 0));
                break;
            
            case NO_FILL:
                gc.setStroke(Color.BLACK);
                break;
        }
        gc.setLineWidth(strokeWidth);
        gc.strokeOval(topLeftX, topLeftY, width, height);
    }
    
    public void drawLightning(Double[] a, Double[] b, int length) {
        
        for (int i = Math.max(1, lightningLength - 5); i < length && i < lightningPoints.size(); i++) {
            Double[] prev = lightningPoints.get(i-1);
            Double[] curr = lightningPoints.get(i);
            double x1 = prev[0] * CANVAS_SIZE;
            double y1 = prev[1] * CANVAS_SIZE;
            double x2 = curr[0] * CANVAS_SIZE;
            double y2 = curr[1] * CANVAS_SIZE;
            
            gc.setStroke(Color.GOLD);
            gc.setLineWidth(3);
            gc.strokeLine(x1, y1, x2, y2);
        }
        lightningLength = (lightningLength + 1) % (lightningPoints.size() + 20);
    }
    
    private enum RangeRingFill {
        GREEN,
        RED,
        NO_FILL;
    }
    
    /**
     * Draws an upgrade square above the selected tower
     * @param tower Tower that will have upgrade box above it.
     */
    public void drawTowerUpgrade(Tower tower, boolean canAfford, boolean isHovered) {
        
        if (tower == null || tower.isMaxLevel()) {
            return;
        }
        
        double topLeftX = tower.getX() - (TOWER_UPGRADE_BOX_SIZE / 2);
        double topLeftY = tower.getY() - (tower.getHeight() / 2) - TOWER_UPGRADE_BOX_OFFSET - TOWER_UPGRADE_BOX_SIZE;
        
        double adjustedX = topLeftX * CANVAS_SIZE;
        double adjustedY = topLeftY * CANVAS_SIZE;
        double height = TOWER_UPGRADE_BOX_SIZE * CANVAS_SIZE;
        double width = TOWER_UPGRADE_BOX_SIZE * CANVAS_SIZE;
        
        Image arrowImg = new Image("resources/icons/arrow_up.png");
        
        if (canAfford) {
            if (isHovered) {
                gc.setFill(Color.LIGHTGREEN);
            } else {
                gc.setFill(Color.GREEN);
            }
        } else {
            if (isHovered) {
                gc.setFill(Color.RED);
            } else {
                gc.setFill(Color.INDIANRED);
            }
        }
        
        gc.fillRoundRect(adjustedX, adjustedY, width, height, 10, 10);
        gc.drawImage(arrowImg, adjustedX, adjustedY, width, height);
        
    }
    
    public static boolean towerUpgradeBoxClicked(Tower tower, double x, double y) {
        
        if (tower == null) {
            return false;
        }
        
        double boxTopLeftX = tower.getX() - (TOWER_UPGRADE_BOX_SIZE / 2);
        double boxTopLeftY = tower.getY() - (tower.getHeight() / 2) - TOWER_UPGRADE_BOX_OFFSET - TOWER_UPGRADE_BOX_SIZE;
        
        boolean withinX = x >= boxTopLeftX && x < boxTopLeftX + TOWER_UPGRADE_BOX_SIZE;
        boolean withinY = y >= boxTopLeftY && y < boxTopLeftY + TOWER_UPGRADE_BOX_SIZE;
        
        return withinX && withinY;
    }
    
}
