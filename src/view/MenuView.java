package view;

import controller.GameController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * This class is a BorderPane containing elements of the Menu Screen.
 * 
 * @author Andrew Pelletier
 *
 */
public class MenuView extends BorderPane {

    private static final String IMG_PATH = "src/resources/images/";
    
    private LoadingView loadingView;
    private VBox buttonVbox;
    
    public MenuView(GameController controller) {

        loadingView = new LoadingView(controller);
        
        // Create Label at top
        Image img = new Image("file:" + IMG_PATH + "super_mario_logo.png", 500, 500, true, true);
        ImageView imgView = new ImageView(img);
        
        setTop(imgView);
        BorderPane.setAlignment(imgView, Pos.CENTER);

        // Buttons on Menu Screen
        Button mapBtn1 = new Button("Easy");
        Button mapBtn2 = new Button("Medium");
        Button mapBtn3 = new Button("Hard");
        Button instructionsBtn = new Button("How to play");

        // Add all buttons to Vbox
        buttonVbox = new VBox(28);
        buttonVbox.setAlignment(Pos.CENTER);
        buttonVbox.setPadding(new Insets(0, 0, 60, 0));
        buttonVbox.getChildren().addAll(mapBtn1, mapBtn2, mapBtn3, instructionsBtn);

        setBottom(buttonVbox);
        
        setPadding(new Insets(60));

        // button event handlers
        mapBtn1.setOnAction(e -> controller.mapButtonPressed(1));
        mapBtn2.setOnAction(e -> controller.mapButtonPressed(2));
        mapBtn3.setOnAction(e -> controller.mapButtonPressed(3));
        
        mapBtn1.setOnMouseEntered(e -> controller.menuButtonHovered());
        mapBtn2.setOnMouseEntered(e -> controller.menuButtonHovered());
        mapBtn3.setOnMouseEntered(e -> controller.menuButtonHovered());
        instructionsBtn.setOnMouseEntered(e -> controller.menuButtonHovered());
        
        
        instructionsBtn.setOnAction(e -> controller.instructionsButtonPressed());
        
        this.setOnMouseClicked(e -> controller.menuBackgroundClick());

    }
    
    public void showLoadPrompt() {
        setBottom(loadingView);
    }
    
    public void showMenuButtons() {
        setBottom(buttonVbox);
    }

}
