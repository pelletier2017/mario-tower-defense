package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.entity.Entity;
import model.entity.enemy.Enemy;
import model.entity.tower.Tower;

/**
 * This class contains the layout for the Tower and Enemy details when clicked
 * on. It has multiple setters in order to change the text within the View.
 * 
 * @author Andrew Pelletier
 *
 */
public class DetailView extends BorderPane {

    private static final String BADGES_PATH = "/resources/icons/badges/";
    private static final double ICON_SIZE = 45;

    // shared labels
    private Label name = new Label();

    // enemy labels
    private Label hp = new Label();
    private Label movespeed = new Label();
    private Label killValue = new Label();

    // tower labels
    private Label damage = new Label();
    private Label attackSpeed = new Label();
    private Label range = new Label();

    // displays
    private GridPane enemyDisplay;
    private GridPane towerDisplay;
    
    private boolean dontPreserveRatio = false;

    public DetailView() {

        enemyDisplay = setupEnemyDisplay();
        towerDisplay = setupTowerDisplay();
        
        enemyDisplay.setPadding(new Insets(20, 0, 20, 100));
        towerDisplay.setPadding(new Insets(20, 0, 20, 100));

        setStyle("-fx-background-color: Grey;");
    }

    private GridPane setupEnemyDisplay() {

        GridPane enemyDisplay = new GridPane();

        // layout view in vertical line
        enemyDisplay.add(hp, 1, 1);
        enemyDisplay.add(movespeed, 1, 2);
        enemyDisplay.add(killValue, 1, 3);

        // add icons to left of each stat
        enemyDisplay.add(new ImageView(new Image(BADGES_PATH + "hp.png", ICON_SIZE, ICON_SIZE, dontPreserveRatio, true)), 0, 1);
        enemyDisplay.add(new ImageView(new Image(BADGES_PATH + "move_speed.png", ICON_SIZE, ICON_SIZE, dontPreserveRatio, true)), 0, 2);
        enemyDisplay.add(new ImageView(new Image(BADGES_PATH + "coin.png", ICON_SIZE, ICON_SIZE, dontPreserveRatio, true)), 0, 3);

        enemyDisplay.setHgap(10);
        return enemyDisplay;
    }

    private GridPane setupTowerDisplay() {

        GridPane towerDisplay = new GridPane();

        // layout view in vertical line
        towerDisplay.add(damage, 1, 1);
        towerDisplay.add(attackSpeed, 1, 2);
        towerDisplay.add(range, 1, 3);

        // add icons to left of each stat
        towerDisplay.add(new ImageView(new Image(BADGES_PATH + "damage.png", ICON_SIZE, ICON_SIZE, dontPreserveRatio, true)), 0, 1);
        towerDisplay.add(new ImageView(new Image(BADGES_PATH + "reload_speed.png", ICON_SIZE, ICON_SIZE, dontPreserveRatio, true)), 0, 2);
        towerDisplay.add(new ImageView(new Image(BADGES_PATH + "range.png", ICON_SIZE, ICON_SIZE, dontPreserveRatio, true)), 0, 3);

        towerDisplay.setHgap(10);
        return towerDisplay;
    }

    /**
     * Sets name text field to Entity's name.
     * 
     * @param newName
     *            name to change to
     */
    private void setName(String newName) {
        name.setText(newName);
    }

    /**
     * Sets hp text field to Entity's hp.
     * 
     * @param newHp
     *            hp to change to
     */
    private void setHp(double newHp) {

        // prevent hp from displaying negative value
        if (newHp >= 1) {
            hp.setText(String.format("%d", (int) newHp));
        } else {
            hp.setText("0");
        }

    }

    /**
     * Displays attributes of a tower.
     * 
     * @param tower
     *            Tower to show details
     */
    public void display(Tower tower) {
        name.setText(tower.getTowerType().toString());
        damage.setText(String.format("%d", (int) (tower.getProjectile().getDamage().getPhysicalDamage())));
        attackSpeed.setText(String.format("%.2f", tower.getReloadTime() / 10.0));
        range.setText(String.format("%d", (int) (tower.getRange() * 100)));
        
        setTop(name);
        BorderPane.setAlignment(name, Pos.CENTER);
        setCenter(towerDisplay);
    }

    /**
     * Displays attributes of an enemy.
     * 
     * @param enemy
     *            Enemy to show details
     */
    public void display(Enemy enemy) {
        
        name.setText(enemy.getEnemyType().toString());
        setHp(enemy.getHP());
        
        if (enemy.isAlive()) {
            movespeed.setText(String.format("%d", (int) (enemy.getSpeed() * 10_000)));
        }
        
        killValue.setText(String.format("%d", (int) enemy.getMoneyForKill()));
        
        setTop(name);
        BorderPane.setAlignment(name, Pos.CENTER);
        setCenter(enemyDisplay);
    }
    
    /**
     * Removes all display in detail view.
     */
    public void displayNothing() {
        setTop(null);
        setCenter(null);
    }

    /**
     * Displays an entity in the detail view. If it is null the detail view will close. Otherwise it will
     * show the entity according to its respective type.
     * 
     * @param entity Entity to display
     */
    public void showEntity(Entity entity) {
        if (entity == null) {
            displayNothing();
        }
        
        if (entity instanceof Enemy) {
            Enemy enemy = (Enemy) entity;
            display(enemy);
        } else if (entity instanceof Tower) {
            Tower tower = (Tower) entity;
            display(tower);
        }
    }
    
}
