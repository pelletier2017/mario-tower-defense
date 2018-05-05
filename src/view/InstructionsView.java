package view;

import controller.GameController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This class contains layout for the "How to Play" screen aka Instruction
 * screen. This is where instruction text will be written.
 * 
 * @author Andrew Pelletier
 *
 */
public class InstructionsView extends StackPane {

    public InstructionsView(GameController controller) {

        Button menuBtn = new Button("Menu");

        // instructions
        Label header = new Label("How to play");
        header.setPrefWidth(750);
        header.setAlignment(Pos.CENTER);
        Label a = new Label("Click to add tower");
        Label b = new Label("Right-click to de-select");
        Label f= new Label("Click on enemies and towers to view stats");
        Label c = new Label("Kill enemies to earn money");
        Label d = new Label("Press esc to pause");

        VBox labelVbox = new VBox(20);
        labelVbox.getChildren().addAll(header, a, b, f, c, d);
        labelVbox.setAlignment(Pos.BASELINE_LEFT);
        labelVbox.setMaxWidth(750);
        labelVbox.setPadding(new Insets(140, 0, 0, 0));
        
        // place items in BorderPane
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(labelVbox);
        borderPane.setBottom(menuBtn);
        BorderPane.setAlignment(labelVbox, Pos.CENTER);
        BorderPane.setAlignment(menuBtn, Pos.CENTER);

        // padding to give space at top and bottom
        borderPane.setPadding(new Insets(30));
        
        // white rectangular box
        Rectangle rectangle = new Rectangle(20, 20, 800, 400);
        rectangle.setArcWidth(50);
        rectangle.setArcHeight(50);
        rectangle.setFill(Color.web("#e5eefc"));
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(8);
        
        getChildren().addAll(rectangle, borderPane);
        setMargin(rectangle, new Insets(50, 0, 120, 0));
        
        // using "fromInstructions" to not pause the animation.
        menuBtn.setOnAction( e -> controller.menuButtonPressedFromInstructions());
        menuBtn.setOnMouseEntered( e -> controller.menuButtonHovered());
        
    }

}
