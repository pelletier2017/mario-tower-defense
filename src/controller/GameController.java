package controller;

import java.io.File;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Game;
import model.SelectionManager;
import model.Shop;
import model.entity.Entity;
import model.entity.enemy.Enemy;
import model.entity.tower.Tower;
import model.sound.Music;
import model.sound.soundeffect.GuiSoundEffects;
import model.sound.soundeffect.MapSoundEffects;
import view.DetailView;
import view.GameView;
import view.InstructionsView;
import view.LoadingView;
import view.MapView;
import view.MenuView;
import view.PausableWrapper;
import view.PopupWrapper;
import view.ShopView;

/**
 * This class is a Controller that will create a Game (model) and a number of
 * Views. This is the link between the model and views. If an event is triggered
 * in one of the views, it will route through the controller to modify the
 * model.
 * 
 * @author Andrew Pelletier
 *
 */
public class GameController extends Application {

    // used to update views
    private MenuView menuView;
    private MapView mapView;
    private ShopView shopView;
    private DetailView detailView;
    private InstructionsView instructionsView;
    private LoadingView loadingView;

    // used to switch between scenes
    private Stage stage;
    private Scene gameScene;
    private Scene menuScene;
    private Scene instructionsScene;

    // needed for embedded pause window
    private PausableWrapper pausableGame;
    private PausableWrapper pausableMenu;
    private PausableWrapper pausableInstructions;
    
    // embedded popup for loading game
    private PopupWrapper popupGame;

    private static final int WIDTH = 1100;
    private static final int HEIGHT = 800;

    private Game game;
    private SelectionManager selectionManager;
    private int mapNumber;

    private volatile boolean isPaused;

    // animation related
    private double backgroundXPos = 0;
    private double backgroundYPos = 0;
    private AnimationTimer animationTimer;

    private Thread gameThread;
    private double gameSpeed = 1;
    
    private Music music;
    private static final String MENU_SONG = "sunshine.mp3";
    private static final String GAME_SONG = "galaxy.mp3";
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        menuView = new MenuView(this);
        instructionsView = new InstructionsView(this);

        // create Views and give to container GameView
        mapView = new MapView(this);
        shopView = new ShopView(this);
        detailView = new DetailView();
        
        GameView gameView = new GameView(this, mapView, shopView, detailView);

        // wrapped with PauseWrapper to allow screen to pause
        pausableGame = new PausableWrapper(gameView);
        pausableMenu = new PausableWrapper(menuView);
        pausableInstructions = new PausableWrapper(instructionsView);
        
        // wrapped with popup to show prompt for loading game
        popupGame = new PopupWrapper(pausableGame, this);

        // create scenes to apply different CSS and switch between scenes
        gameScene = new Scene(popupGame, WIDTH, HEIGHT);
        menuScene = new Scene(pausableMenu, WIDTH, HEIGHT);
        instructionsScene = new Scene(pausableInstructions, WIDTH, HEIGHT);
        
        // set cursor on each scene
        setCursor(gameScene);
        setCursor(menuScene);
        setCursor(instructionsScene);

        // CSS style sheets applied to each scene
        String cssPath = "/resources/css/";
        pausableMenu.getStylesheets().add(cssPath + "menu.css");
        popupGame.getStylesheets().add(cssPath + "game.css");
        pausableInstructions.getStylesheets().add(cssPath + "instructions.css");
        shopView.getStylesheets().add(cssPath + "shop.css");
        detailView.getStylesheets().add(cssPath + "detail.css");

        // event handler to pause anywhere in game if esc pressed
        stage.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                
                // needed to flip isPaused boolean before calling start or stop
                boolean paused = isPaused;
                isPaused = !isPaused;
                if (paused) {
                    stopPause();
                } else {
                    startPause();
                }
            }
        });
        
        stage.setOnCloseRequest( e ->  {
            GuiSoundEffects.exitGame();
            music.interrupt();
            interruptGame();
            
            if (game != null) {
                // save progress if player beat a map
                if (!game.isGameOver() || game.didPlayerWin()) {
                    game.saveToFile();
                }
            }
        });

        music = new Music(MENU_SONG);
        
        stage.setScene(menuScene);
        stage.show();

        startMenuAnimation();
    }


    /**
     * Changes scenes to the GameScene with the correct map loaded. This is called
     * by MenuView.
     * 
     * @param i
     *            the number of the map between 1 and 3.
     */
    public void mapButtonPressed(int i) {
        mapNumber = i;
        File gameSaved = new File("src/saves/game" + i + ".dat");
        if (gameSaved.exists()) {
            menuView.showLoadPrompt();
        } else {
            game = Game.newGame(i);
            playGame();
        }
    }
    
    public void playGame() {
        
        // clean up menu
        stopMenuAnimation();
        music.interrupt();
        music = new Music(GAME_SONG);
        
        GuiSoundEffects.chooseMap();
        
        shopView.unfocusButtons();
        popupGame.hideWinOrLose();
        stage.setScene(gameScene);
        
        selectionManager = new SelectionManager(game);
        
        gameThread = new Thread(() -> {
            while (!game.isGameOver()) {
                
                // increment game 1 tick
                game.incrementTime();

                if (game.getEnemies().size() != 0) {
                    //System.out.println("hp: " + game.getEnemies().get(0).getHP());
                }
                Platform.runLater(() -> {
                    // updating all views with state of game
                    mapView.drawGame(game, selectionManager);
                    shopView.setLives(game.getPlayerHp());
                    shopView.setMoneyLabel(game.getMoney());
                    detailView.showEntity(selectionManager.getSelectedEntity());
                });

                try {
                    // 25ms sleep is normal speed, gamespeed determines multiplier
                    Thread.sleep((long)(25 / gameSpeed));
                } catch (InterruptedException e) {
                    if (interruptEndsGame()) {
                        break;
                    }
                }
            }

            // clean up game left-overs
            game.killProjectiles();
            removeHoverTower();
            selectionManager.setSelectedEntity(null);

            Platform.runLater(() -> {
                mapView.drawGame(game, selectionManager);
                
                if (game.isGameOver()) {
                    isPaused = true;
                    if (game.didPlayerWin()) {
                        showWin();
                        game.saveToFile();
                    } else {
                        showLoss();
                    }
                }
                
            });
            
        });
        gameThread.start();

    }
    
    private void showLoss() {
        music.interrupt();
        MapSoundEffects.gameLost();
        popupGame.showLoss();
    }

    private void showWin() {
        music.interrupt();
        MapSoundEffects.gameWon();
        popupGame.showWin();
    }

    /**
     * Determines if the interrupt will end the game or continue after unpausing.
     * 
     * @return True if game will continue.
     */
    private boolean interruptEndsGame() {
        if (isPaused) {
            // sleep until un-paused
            while (isPaused) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e1) {
                    // game closed while paused, end game
                    return true;
                }
            }
            // game unpaused, continue playing
            return false;
        }
        // interrupt while not paused, end game
        return true;
        
    }

    /**
     * Handles logic for when a mouse click event happens on the MapView.
     * 
     * @param x
     *            percent in x direction between 0.0-1.0
     * @param y
     *            percent in y direction between 0.0-1.0
     */
    public void clickedOnMap(double x, double y) {
        selectionManager.clickedPosition(x, y);
        Entity entity = selectionManager.getSelectedEntity();
        
        if (entity == null) {
            detailView.displayNothing();
            
        } else if (entity instanceof Tower) {
            Tower tower = (Tower) entity;
            detailView.display(tower);
            
        } else if (entity instanceof Enemy) {
            Enemy enemy = (Enemy) entity;
            detailView.display(enemy);
        }
        
    }

    /**
     * Changes scene to the MenuScene. Called by ShopView.
     */
    public void menuButtonPressedFromShop() {
        stage.setScene(menuScene);
        startMenuAnimation();
        interruptGame();
        menuView.showMenuButtons();
        
        if (!game.isGameOver() || game.didPlayerWin()) {
            game.saveToFile();
        }
        
        // fade out game music and start menu music
        music.interrupt();
        music = new Music(MENU_SONG);
    }
    
    /**
     * Changes scene to the MenuScene without changing the animation. Called by
     * InstructionsView.
     */
    public void menuButtonPressedFromInstructions() {
        stage.setScene(menuScene);
    }

    /**
     * Interrupts gameThread which will temporarily pause the game if user presses pause
     * or it will end the game if not paused.
     */
    private void interruptGame() {
        if (gameThread != null) {
            gameThread.interrupt();
        }
        
        // if interrupting when not paused, remove game thread
        if (!isPaused) {
            gameThread = null;
        }
    }

    /**
     * Change scene to the InstructionScene. Called by MenuView.
     */
    public void instructionsButtonPressed() {
        GuiSoundEffects.instructionsBtn();
        stage.setScene(instructionsScene);
    }

    /**
     * Causes a built-in popup to show "Paused" and disable all components until
     * unpaused.
     */
    private void startPause() {
        
        music.pause();
        GuiSoundEffects.pause();
        
        pausableGame.pause();
        pausableMenu.pause();
        pausableInstructions.pause();
        
        interruptGame();
        
        stopMenuAnimation();
        disableComponents();
    }

    /**
     * Causes a built-in popup to go away and re-enable all components.
     */
    private void stopPause() {
        if (stage.getScene() == gameScene && game.isGameOver()) {
            music.interrupt();
            music = new Music(MENU_SONG);
            menuView.showMenuButtons();
            stage.setScene(menuScene);
            
        } else {
            music.resume();
        }
        
        pausableGame.unpause();
        pausableMenu.unpause();
        pausableInstructions.unpause();
        
        startMenuAnimation();
        enableComponents();
    }

    /**
     * Disable all components while game is paused.
     */
    private void disableComponents() {
        menuView.setDisable(true);
        instructionsView.setDisable(true);
        shopView.setDisable(true);
        detailView.setDisable(true);
    }

    /**
     * Re-enable all components after game is unpaused.
     */
    private void enableComponents() {
        menuView.setDisable(false);
        instructionsView.setDisable(false);
        shopView.setDisable(false);
        detailView.setDisable(false);
    }

    /**
     * Updates Game with tower selected from shop so it is ready to build tower.
     * 
     * @param i
     *            0-based index in tower shop for tower clicked on.
     */
    public void clickedOnShopTower(int i) {
        MapSoundEffects.shopTowerSelected();
        selectionManager.setSelectedShopTower(Shop.getTower(i).getTowerType());
        selectionManager.setSelectedEntity(null);
    }

    /**
     * When user right clicks, the towerSelected will become null. This allows the
     * user to de-select a tower after clicking the button.
     */
    public void rightClicked() {
        selectionManager.setSelectedShopTower(null);
        selectionManager.setSelectedEntity(null);
        removeHoverTower();
        detailView.displayNothing();
        shopView.unfocusButtons();
    }

    /**
     * Removes hover tower so it won't be drawn on the canvas Will be called when
     * the mouse moves outside of the canvas.
     */
    public void removeHoverTower() {
        selectionManager.removeHoverTower();
    }

    /**
     * Updates DetailView with tower stats before buying.
     * 
     * @param i
     *            0-based index in tower shop for tower hovered on.
     */
    public void startHoverOnShopTower(int i) {
        detailView.display(Shop.getTower(i));
    }

    /**
     * Restores DetailView with what it was originally displaying.
     * 
     * @param i
     *            0-based index in tower shop for tower hovered on.
     */
    public void stopHoverOnShopTower(int i) {
        detailView.displayNothing();
    }

    /**
     * When the mouse moves over canvas, set the hover tower.
     * 
     * @param x
     *            X position of mouse
     * @param y
     *            Y position of mouse
     */
    public void mouseMovedOnCanvas(double x, double y) {
        
        if (MapView.towerUpgradeBoxClicked(selectionManager.getSelectedTower(), x, y)) {
            selectionManager.setIsHoveringUpgrade(true);
        } else {
            selectionManager.setIsHoveringUpgrade(false);
        }
        
        if (selectionManager.hasSelectedShopTower()) {
            selectionManager.setHoverTower(x, y);
        }
    }
    
    /**
     * Uses a custom mouse cursor from image. Must be set on each scene.
     */
    private void setCursor(Scene scene) {
        scene.setCursor(new ImageCursor(new Image("resources/images/green_mouse_cursor.png"), 97, 10));
    }
    
    /**
     * Changes game speed between three states
     */
    public void toggleGameSpeed() {
        if (gameSpeed == 1) {
            gameSpeed = 2;
        } else if (gameSpeed == 2) {
            gameSpeed = 3;
        } else {
            gameSpeed = 1;
        }
    }
    
    /**
     * Private inner class used to move image background on menu screen.
     * 
     * @author Andrew Pelletier
     *
     */
    private class MenuAnimation extends AnimationTimer {

        private double xSpeed = 1;
        private double ySpeed = -0.1;
        private double xFullLoop = stage.getWidth() - 35;
        private double yFullLoop = stage.getHeight() + 55;

        @Override
        public void handle(long now) {
            backgroundXPos = (backgroundXPos + xSpeed) % xFullLoop;
            backgroundYPos = (backgroundYPos + ySpeed) % yFullLoop;
            pausableMenu.setStyle("-fx-background-position: " + backgroundXPos + "px " + backgroundYPos + "px");
            pausableInstructions.setStyle("-fx-background-position: " + backgroundXPos + "px " + backgroundYPos + "px");
        }

    }
    
    /**
     * Starts a thread that will update the background on the menu screen.
     */
    private void startMenuAnimation() {

        // don't start thread more than once
        if (animationTimer != null) {
            return;
        }

        // dont start menu animation while in game scene (this is called when
        // unpausing)
        if (stage.getScene().equals(gameScene)) {
            return;
        }

        System.out.println("Starting menu animation");

        animationTimer = new MenuAnimation();
        animationTimer.start();

    }

    /**
     * Interrupts menu animation thread to stop background from moving.
     */
    public void stopMenuAnimation() {

        // no thread currently exists
        if (animationTimer == null) {
            return;
        }
        System.out.println("Animation Stopped");

        animationTimer.stop();
        animationTimer = null;
    }

    public void menuButtonHovered() {
        GuiSoundEffects.hoverButton();
    }

    public void menuBackgroundClick() {
        GuiSoundEffects.click();
    }

    public void startNewGame() {
        game = Game.newGame(mapNumber);
        popupGame.hideWinOrLose();
        playGame();
    }
    
    public void loadGame() {
        game = Game.load(mapNumber);
        if (!game.isGameOver()) {
            GuiSoundEffects.loadGame();
        }
        popupGame.hideWinOrLose();
        playGame();
    }

}
