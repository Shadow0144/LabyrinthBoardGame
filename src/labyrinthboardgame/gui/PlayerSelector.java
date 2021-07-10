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
import labyrinthboardgame.logic.Player;

/**
 *
 * @author Corbi
 */
public final class PlayerSelector extends VBox
{
    private final TextField nameText;
    private final Label playerTypeLabel;
    
    private Player.PlayerType playerType;
    
    private PlayerSelectController controller;
    
    private final int playerNumber;
    private final Color playerColor;
    
    /**
     * Allows selecting the player type (e.g. human or ai) for each player
     * @param playerNumber The player number (i.e. 1, 2, 3, or 4)
     * @param playerColor The background color for this selector
     */
    public PlayerSelector(int playerNumber, Color playerColor)
    {
        super();
        setAlignment(Pos.CENTER);
        this.playerNumber = playerNumber;
        this.playerColor = playerColor;
        
        String defaultName = "";
        switch (playerNumber)
        {
            case 1:
                defaultName = "Yellow Mage";
                break;
            case 2:
                defaultName = "Blue Wizard";
                break;
            case 3:
                defaultName = "Green Warlock";
                break;
            case 4:
                defaultName = "Susan";
                break;
        }
        nameText = new TextField(defaultName);
        nameText.setAlignment(Pos.CENTER);
        nameText.setFont(Font.font(14));
        setMargin(nameText, new Insets(12, 12, 8, 12));
        getChildren().add(nameText);
        
        HBox selectionBox = new HBox();
        selectionBox.setAlignment(Pos.CENTER);
        
        Button leftButton = new Button("<");
        leftButton.setAlignment(Pos.CENTER);
        HBox.setMargin(leftButton, new Insets(0, 12, 12, 12));
        leftButton.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
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
        HBox.setMargin(playerTypeLabel, new Insets(0, 12, 12, 12));
        
        Button rightButton = new Button(">");
        rightButton.setAlignment(Pos.CENTER);
        HBox.setMargin(rightButton, new Insets(0, 12, 12, 12));
        rightButton.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event)
            {
                rightPressed();
            }
        });
        
        playerType = Player.PlayerType.human;
        updatePlayerTypeText();
        
        selectionBox.getChildren().add(leftButton);
        selectionBox.getChildren().add(playerTypeLabel);
        selectionBox.getChildren().add(rightButton);
        getChildren().add(selectionBox);
        
        setMargin(this, new Insets(12, 12, 12, 12));
        this.setBackground(new Background(new BackgroundFill(playerColor, CornerRadii.EMPTY, Insets.EMPTY)));
    }
    
    /**
     * Sets the controller so it can alert it when changes occur
     * @param controller The controller
     */
    public void setController(PlayerSelectController controller)
    {
        this.controller = controller;
    }
    
    /**
     * Updates the text box to display what type of controller the player has
     */
    private void updatePlayerTypeText()
    {
        switch (playerType)
        {
            case none:
                playerTypeLabel.setText("None");
                break;
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
            case none:
                playerType = Player.PlayerType.advanced_ai;
                controller.addPlayer(playerNumber);
                this.setBackground(new Background(new BackgroundFill(playerColor, CornerRadii.EMPTY, Insets.EMPTY)));
                break;
            case human:
                playerType = Player.PlayerType.none;
                controller.removePlayer(playerNumber);
                this.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
                break;
            case ai:
                playerType = Player.PlayerType.human;
                break;
            case advanced_ai:
                playerType = Player.PlayerType.ai;
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
            case none:
                playerType = Player.PlayerType.human;
                controller.addPlayer(playerNumber);
                this.setBackground(new Background(new BackgroundFill(playerColor, CornerRadii.EMPTY, Insets.EMPTY)));
                break;
            case human:
                playerType = Player.PlayerType.ai;
                break;
            case ai:
                playerType = Player.PlayerType.advanced_ai;
                break;
            case advanced_ai:
                playerType = Player.PlayerType.none;
                controller.removePlayer(playerNumber);
                this.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
                break;
        }
        updatePlayerTypeText();
    }
    
    /**
     * Returns the player type
     * @return The player type
     */
    public Player.PlayerType getPlayerType()
    {
        return playerType;
    }
    
    /**
     * Returns the player name
     * @return The player name
     */
    public String getPlayerName()
    {
        return nameText.getText();
    }
}
