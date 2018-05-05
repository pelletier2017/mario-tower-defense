package view;

import controller.GameController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class LoadingView extends BorderPane {

    Label saveLabel;
    Button confirmBtn;
    Button cancelBtn;
    
    public LoadingView(GameController controller) {
        
        saveLabel = new Label("Would you like to load\nyour saved game?");
        saveLabel.setStyle("-fx-font-size: 25pt;");
        
        confirmBtn = new Button("Yes");
        cancelBtn = new Button("No");
        
        HBox btnHbox = new HBox(30);
        btnHbox.getChildren().addAll(confirmBtn, cancelBtn);
        btnHbox.setPadding(new Insets(20, 0, 0, 390));
        
        setCenter(saveLabel);
        setBottom(btnHbox);
        BorderPane.setAlignment(btnHbox, Pos.CENTER);
        
        confirmBtn.setOnAction(e -> controller.loadGame());
        cancelBtn.setOnAction(e -> controller.startNewGame());
        
        confirmBtn.setOnMouseEntered( e -> controller.menuButtonHovered());
        cancelBtn.setOnMouseEntered( e -> controller.menuButtonHovered());

        setPadding(new Insets(0, 0, 200, 0));
    }
    
    public void show() {
        confirmBtn.setVisible(true);
        cancelBtn.setVisible(true);
        setVisible(true);
        
        confirmBtn.setDisable(false);
        cancelBtn.setDisable(false);
    }

    public void hide() {
        setVisible(false);
        confirmBtn.setDisable(true);
        cancelBtn.setDisable(true);
    }
}
