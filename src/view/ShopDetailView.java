package view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.entity.tower.Tower;

// TODO temporarily unused, may use for tooltips in Shop
public class ShopDetailView extends BorderPane {

    private Label name = new Label();
    private Label damage = new Label();
    private Label attackSpeed = new Label();
    private Label buildCost = new Label();
    
    public ShopDetailView() {

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(damage, attackSpeed, buildCost);
        
        setTop(name);
        BorderPane.setAlignment(name, Pos.CENTER);
        setCenter(vbox);
        
        setVisible(false);
    }
    
    public void display(Tower tower) {
        System.out.println("Display tower");
        name.setText(tower.getTowerType().toString());
        damage.setText(String.format("%d damage", -1));
        attackSpeed.setText(String.format("%.2f speed", tower.getReloadTime() / 10.0));
        buildCost.setText(String.format("%d coins", tower.getBuildCost()));
        setVisible(true);
    }

    public void displayNothing() {
        System.out.println("Display nothing");
        setVisible(false);
    }

}
