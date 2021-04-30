/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author Corbi
 */
public class PlayerSelector extends VBox
{
    private final TextField nameText;
    private final Label playerTypeLabel;
    
    private enum PlayerType
    {
      human,
      ai,
      advanced_ai
    };
    private PlayerType playerType;
    
    /**
     * Allows selecting the player type (e.g. human or ai) for each player
     * @param playerColor The background color for this selector
     */
    public PlayerSelector(Color playerColor)
    {
        super();
        setAlignment(Pos.CENTER);
        
        nameText = new TextField("Player Name");
        nameText.setAlignment(Pos.CENTER);
        nameText.setFont(Font.font(14));
        setMargin(nameText, new Insets(12, 12, 8, 12));
        getChildren().add(nameText);
        
        HBox selectionBox = new HBox();
        selectionBox.setAlignment(Pos.CENTER);
        
        Button leftButton = new Button("<");
        leftButton.setAlignment(Pos.CENTER);
        selectionBox.setMargin(leftButton, new Insets(0, 12, 12, 12));
        leftButton.setOnAction(new EventHandler<ActionEvent>() 
        {
            public void handle(ActionEvent event)
            {
                leftPressed();
            }
        });
        
        playerTypeLabel = new Label();
        playerTypeLabel.setAlignment(Pos.CENTER);
        playerTypeLabel.setMinWidth(100); // Prevents the buttons from moving when we change the text
        playerTypeLabel.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));
        playerTypeLabel.setFont(Font.font(14));
        selectionBox.setMargin(playerTypeLabel, new Insets(0, 12, 12, 12));
        
        Button rightButton = new Button(">");
        rightButton.setAlignment(Pos.CENTER);
        selectionBox.setMargin(rightButton, new Insets(0, 12, 12, 12));
        rightButton.setOnAction(new EventHandler<ActionEvent>() 
        {
            public void handle(ActionEvent event)
            {
                rightPressed();
            }
        });
        
        playerType = PlayerType.human;
        updatePlayerTypeText();
        
        selectionBox.getChildren().add(leftButton);
        selectionBox.getChildren().add(playerTypeLabel);
        selectionBox.getChildren().add(rightButton);
        getChildren().add(selectionBox);
        
        setMargin(this, new Insets(12, 12, 12, 12));
        this.setBackground(new Background(new BackgroundFill(playerColor, CornerRadii.EMPTY, Insets.EMPTY)));
    }
    
    /**
     * Updates the text box to display what type of controller the player has
     */
    private void updatePlayerTypeText()
    {
        switch (playerType)
        {
            case human:
                playerTypeLabel.setText("Human");
                break;
            case ai:
                playerTypeLabel.setText("AI");
                break;
            case advanced_ai:
                playerTypeLabel.setText("Advanced AI");
                break;
        }
    }
    
    /**
     * Called when the left button is pressed and changes the player type
     */
    private void leftPressed()
    {
        switch (playerType)
        {
            case human:
                playerType = PlayerType.advanced_ai;
                break;
            case ai:
                playerType = PlayerType.human;
                break;
            case advanced_ai:
                playerType = PlayerType.ai;
                break;
        }
        updatePlayerTypeText();
    }
    
    /**
     * Called when the right button is pressed and changes the player type
     */
    private void rightPressed()
    {
        switch (playerType)
        {
            case human:
                playerType = PlayerType.ai;
                break;
            case ai:
                playerType = PlayerType.advanced_ai;
                break;
            case advanced_ai:
                playerType = PlayerType.human;
                break;
        }
        updatePlayerTypeText();
    }
}
