package view;

import java.util.ArrayList;

import controller.GameController;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.Shop;
import model.entity.tower.Tower;
import model.entity.tower.TowerType;

/**
 * This class contains logic for organizing the Shop presentation. It has event
 * handlers for buttons getting clicked and setters to modify text labels.
 * 
 * @author Andrew Pelletier
 *
 */
public class ShopView extends StackPane {

    private GameController controller;

    private Label moneyLabel;
    private Label livesLabel;
    
    // used to determine which game speed image to show on button
    private int gameSpeedImg = 0;
    
    public static final String ICON_PATH = "resources/icons/";
    public static final String GAME_SPEED_PATH = ICON_PATH + "game_speed_btn/";
    public static final String TOWER_PATH = "resources/sprites/towers/";
    
    public ShopView(GameController controller) {

        this.controller = controller;

        VBox vbox = setupMenuAndMoneyVbox();
        GridPane towerGrid = setupTowerGrid();
        
        Button gameSpeedBtn = new Button();
        gameSpeedBtn.setId("game-speed-btn");

        BorderPane borderPane = new BorderPane();
        
        borderPane.setTop(vbox);
        borderPane.setCenter(towerGrid);
        
        BorderPane.setAlignment(gameSpeedBtn, Pos.CENTER);
        borderPane.setBottom(gameSpeedBtn);
        
        setPadding(new Insets(40, 60, 40, 40));
        
        getChildren().addAll(borderPane);
        
        // sets gameSpeedButton to correct button according to the current game speed
        gameSpeedBtn.setOnAction( e -> {
            controller.toggleGameSpeed();
            gameSpeedImg = (gameSpeedImg + 1) % 3;
            String focusBackground = "-fx-background-image: url('" + GAME_SPEED_PATH + "f" + (gameSpeedImg + 1) + "-focus.png');";
            gameSpeedBtn.setStyle(focusBackground);
        });
        
        gameSpeedBtn.setOnMouseEntered( e -> {
            String focusBackground = "-fx-background-image: url('" + GAME_SPEED_PATH + "f" + (gameSpeedImg + 1) + "-focus.png');";
            gameSpeedBtn.setStyle(focusBackground);
            controller.menuButtonHovered();
        });
        
        gameSpeedBtn.setOnMouseExited( e -> {
            String normalBackground = "-fx-background-image: url('" + GAME_SPEED_PATH + "f" + (gameSpeedImg + 1) + ".png');";
            gameSpeedBtn.setStyle(normalBackground);
        });
        
    }

    private GridPane setupTowerGrid() {
        Button towerBtn1 = new Button();
        Button towerBtn2 = new Button();
        Button towerBtn3 = new Button();
        Button towerBtn4 = new Button();
        
        GridPane towerGrid = new GridPane();
        towerGrid.setHgap(20);
        towerGrid.setVgap(20);
        towerGrid.setAlignment(Pos.CENTER);
        towerGrid.setPadding(new Insets(40));

        towerGrid.add(towerBtn1, 0, 0);
        towerGrid.add(towerBtn2, 1, 0);
        towerGrid.add(towerBtn3, 0, 1);
        towerGrid.add(towerBtn4, 1, 1);

        // add buttons to list to iterate over with handlers
        ArrayList<Button> towerBtns = new ArrayList<>();
        towerBtns.add(towerBtn1);
        towerBtns.add(towerBtn2);
        towerBtns.add(towerBtn3);
        towerBtns.add(towerBtn4);

        // add handlers to each button
        for (int i = 0; i < towerBtns.size(); i++) {
            // final prevents lambda's variable must be final or effectively final
            final int index = i;
            
            Button towerBtn = towerBtns.get(i);
            
            TowerType towerType = Shop.getTower(i).getTowerType();
            String spritePath = TOWER_PATH + towerType.toString().toLowerCase() + ".png";
            
            String focusBackground = "-fx-background-image: url('" + spritePath + "');";
            String noRepeats = "-fx-background-repeat: no-repeat;";
            String centered = "-fx-background-position: center;";
            String roundedEdges = "-fx-border-radius: 0 0 0 0;" + 
                                  "-fx-background-radius: 20 20 20 20;";
            String cover = "-fx-background-size: cover;";
            String minSize = "-fx-min-height: 75px;" + 
                             "-fx-min-width: 75px;";
            towerBtn.setStyle(focusBackground + noRepeats + centered + roundedEdges + minSize);
            
            towerBtn.setOnMouseClicked(e -> controller.clickedOnShopTower(index));
            towerBtn.setOnMouseEntered( e -> controller.menuButtonHovered());
            
            Tower tower = Shop.getTower(index);
            Tooltip tooltip = new Tooltip(tower.getTowerType().toString() 
                    + "\n" + tower.getBuildCost() + " coins");
            
            // tooltip popup
            towerBtn.setOnMouseMoved(e -> {
                Bounds localBounds = towerBtns.get(index).getBoundsInLocal();
                Bounds screenBounds = towerBtns.get(index).localToScreen(localBounds);
                double x = screenBounds.getMinX() + e.getX() + 20;
                double y = screenBounds.getMinY() + e.getY() - 50;
                tooltip.show(towerBtns.get(index), x, y);
            });
            towerBtn.setOnMouseExited(e -> tooltip.hide());
            
        }

        return towerGrid;
    }

    private VBox setupMenuAndMoneyVbox() {

        Button menuBtn = new Button("Menu");

        menuBtn.setOnAction(e -> controller.menuButtonPressedFromShop());
        menuBtn.setOnMouseEntered( e -> controller.menuButtonHovered());
        
        moneyLabel = new Label();
        livesLabel = new Label();

        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(10);
        
        ImageView livesIcon = new ImageView(new Image(ICON_PATH + "heart.png", 50, 50, true, true));
        ImageView moneyIcon = new ImageView(new Image(ICON_PATH + "coin.png", 50, 50, true, true));
        
        grid.add(livesIcon, 4, 0);
        grid.add(livesLabel, 5, 0);
        grid.add(moneyIcon, 4, 1);
        grid.add(moneyLabel, 5, 1);
        
        VBox vbox = new VBox(30);
        vbox.getChildren().addAll(menuBtn, grid);
        vbox.setAlignment(Pos.CENTER);
        
        return vbox;
    }
    
    /**
     * Updates the label showing the players money.
     * 
     * @param money
     *            Amount of money the player currently has.
     */
    public void setMoneyLabel(int money) {
        moneyLabel.setText(String.format(Integer.toString(money)));
    }

    /**
     * Sets lives label to current lives.
     * 
     * @param lives
     *            new lives value
     */
    public void setLives(int lives) {
        livesLabel.setText(Integer.toString(lives));
    }
    
    /**
     * Used to remove focus from buttons.
     */
    public void unfocusButtons() {
        livesLabel.requestFocus();
    }
    
}
