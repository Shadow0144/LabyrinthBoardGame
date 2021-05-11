/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.gui;

import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import static javafx.scene.layout.StackPane.setAlignment;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Corbi
 */
public final class PlayerIcon extends StackPane
{
    private final Circle playerIcon;
    private final ImageView playerTreasure;
    private final Text playerTreasuresRemainingText;
    
    private final int PLAYER_ICON_RADIUS = 30;
    private final int UNSELECTED_STROKE = 1;
    private final int SELECTED_STROKE = 3;
    private final int VICTORY_STROKE = 5;
    
    private final int PLAYER_TREASURE_SIZE = 50;
    
    /**
     * Creates an icon for displaying details about a player, such as their current
     * treasure, a count of their treasures remaining, and if they are active or have
     * won the game
     * @param color The color for the player icon background 
     */
    public PlayerIcon(Color color)
    {
        playerIcon = new Circle();
        playerIcon.setRadius(PLAYER_ICON_RADIUS);
        playerIcon.setStroke(Color.BLACK);
        playerIcon.setStrokeWidth(UNSELECTED_STROKE);
        playerIcon.setFill(color);
        
        getChildren().add(playerIcon);
        
        AnchorPane treasuresRemainingPane = new AnchorPane();
        
        Circle textBackground = new Circle();
        textBackground.setFill(Color.WHITE);
        textBackground.setLayoutX(75);
        textBackground.setLayoutY(30);
        textBackground.setRadius(10);
        textBackground.setStroke(Color.BLACK);
        textBackground.setStrokeWidth(1);
        treasuresRemainingPane.getChildren().add(textBackground);
        
        playerTreasuresRemainingText = new Text();
        playerTreasuresRemainingText.setLayoutX(71);
        playerTreasuresRemainingText.setLayoutY(30);
        playerTreasuresRemainingText.setText("" + 0);
        playerTreasuresRemainingText.setTextAlignment(TextAlignment.CENTER);
        playerTreasuresRemainingText.setTextOrigin(VPos.CENTER);
        treasuresRemainingPane.getChildren().add(playerTreasuresRemainingText);
        
        getChildren().add(treasuresRemainingPane);
        setAlignment(treasuresRemainingPane, Pos.TOP_RIGHT);
        
        playerTreasure = new ImageView();
        getChildren().add(playerTreasure);
    }
    
    /**
     * Updates the player's current treasure goal image
     * @param treasureType The name of the image of the treasure (if not null)
     */
    public void updateTreasureImage(String treasureType)
    {
        if (treasureType != null)
        {
            String treasureImageString = getClass().getResource("assets/" + treasureType + ".png").toString();
            Image treasureImage = new Image(treasureImageString, PLAYER_TREASURE_SIZE, PLAYER_TREASURE_SIZE, false, true);
            playerTreasure.setImage(treasureImage);
        }
        else {}
    }
    
    /**
     * Updates the display of number of treasures the player has left to collect
     * @param treasuresRemaining The number of treasures left
     */
    public void updateTreasuresRemaining(int treasuresRemaining)
    {
        playerTreasuresRemainingText.setText("" + treasuresRemaining);
    }
    
    /**
     * Changes the color of the border of the icon to show the player has won
     */
    public void setHasWon()
    {
        playerIcon.setStroke(Color.GOLD);
        playerIcon.setStrokeWidth(VICTORY_STROKE);
    }
    
    /**
     * Changes the stroke of the border of the icon to show the player is the
     * current player
     */
    public void setActive()
    {
        playerIcon.setStrokeWidth(SELECTED_STROKE);
    }
    
    /**
     * Changes the stroke of the border of the icon to show the player is not 
     * the current player
     */
    public void setInactive()
    {
        playerIcon.setStrokeWidth(UNSELECTED_STROKE);
    }
}
