/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import static javafx.scene.layout.StackPane.setAlignment;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 *
 * @author Corbi
 */
public class PlayerSelector extends VBox {
    
    private Text nameText;
    private Label playerTypeLabel;
    
    private enum PlayerType {
      human,
      ai,
      advanced_ai
    };
    private PlayerType playerType;
    
    public PlayerSelector()
    {
        super();
        setAlignment(Pos.CENTER);
        
        nameText = new Text();
        getChildren().add(nameText);
        
        HBox selectionBox = new HBox();
        selectionBox.setAlignment(Pos.CENTER);
        
        Button leftButton = new Button();
        playerTypeLabel = new Label();
        Button rightButton = new Button();
        
        playerType = PlayerType.human;
        playerTypeLabel.setText("Human");
        
        selectionBox.getChildren().add(leftButton);
        selectionBox.getChildren().add(playerTypeLabel);
        selectionBox.getChildren().add(rightButton);
        getChildren().add(selectionBox);
    }
    
    public void setColor(Color playerColor)
    {
        this.setBackground(new Background(new BackgroundFill(playerColor, CornerRadii.EMPTY, Insets.EMPTY)));
    }
}
