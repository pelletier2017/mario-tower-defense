package view;

import controller.GameController;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PopupWrapper extends StackPane {

    private Rectangle rectangle;
    private Label popupLabel;
    
    public PopupWrapper(Parent node, GameController controller) {
        
        rectangle = new Rectangle(300, 180);
        rectangle.setFill(Color.GREY);
        rectangle.setStroke(Color.BLACK);
        rectangle.setArcHeight(20);
        rectangle.setArcWidth(20);

        popupLabel = new Label();
        popupLabel.setStyle("-fx-font-size: 25pt;");

        rectangle.setVisible(false);
        popupLabel.setVisible(false);

        getChildren().addAll(node, rectangle, popupLabel);
    }
    
    /**
     * Makes the overlay visible.
     */
    private void show() {
        rectangle.setVisible(true);
        popupLabel.setVisible(true);
    }

    /**
     * Makes the overlay invisible.
     */
    public void hideWinOrLose() {
        rectangle.setVisible(false);
        popupLabel.setVisible(false);
    }
    
    /**
     * Changes the text of the pause label to show the player he won.
     */
    public void showWin() {
        show();
        popupLabel.setText("Game Over\nYou WIN!\npress esc");
    }
    
    /**
     * Changes the text of the pause label to show the player he lost.
     */
    public void showLoss() {
        show();
        popupLabel.setText("Game Over\nYou LOST!\npress esc");
    }

}
