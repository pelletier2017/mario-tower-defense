package view;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This class is a wrapper around a View that will add a Pause box overlay. The
 * pause box can be set to visible/invisible depending on if the game is paused.
 * It uses a StackPane so it does not modify the View it wraps other than adding
 * a pause overlay ontop of it.
 * 
 * @author Andrew Pelletier
 *
 */
public class PausableWrapper extends StackPane {

    private Rectangle rectangle;
    private Label pauseLabel = new Label("Paused");

    public PausableWrapper(Parent node) {
        rectangle = new Rectangle(300, 180);
        rectangle.setFill(Color.GREY);
        rectangle.setStroke(Color.BLACK);
        rectangle.setArcHeight(20);
        rectangle.setArcWidth(20);

        pauseLabel = new Label("Paused");
        pauseLabel.setStyle("-fx-font-size: 30pt;");

        rectangle.setVisible(false);
        pauseLabel.setVisible(false);

        getChildren().addAll(node, rectangle, pauseLabel);
    }

    /**
     * Makes the pause overlay visible.
     */
    public void pause() {
        rectangle.setVisible(true);
        pauseLabel.setVisible(true);
    }

    /**
     * Makes the pause overlay invisible.
     */
    public void unpause() {
        rectangle.setVisible(false);
        pauseLabel.setVisible(false);
    }

}
