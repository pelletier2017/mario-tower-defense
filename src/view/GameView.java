package view;

import controller.GameController;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;

/**
 * This class is a container that will align each of the views to the correct
 * spot within the screen.
 * 
 * @author Andrew Pelletier
 *
 */
public class GameView extends BorderPane {

    private GameController controller;
    
    public GameView(GameController controller, MapView mapView, ShopView shopView, DetailView detailView) {

        this.controller = controller;
        
        // left side is MapView where the game is played
        setLeft(mapView);

        // split right side between ShopView (top) and DetailView (bottom)
        BorderPane rightPane = new BorderPane();
        
        rightPane.setTop(shopView);
        rightPane.setBottom(detailView);
        
        setRight(rightPane);
        
        setOnMouseClicked( e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                controller.rightClicked();
            }
        });
    }

}
