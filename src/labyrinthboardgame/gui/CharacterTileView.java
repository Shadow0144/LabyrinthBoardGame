/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.gui;

import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import static javafx.scene.layout.StackPane.setAlignment;
import javafx.util.Duration;
import labyrinthboardgame.logic.GUIConnector;
import labyrinthboardgame.logic.Tile;

/**
 *
 * @author Corbi
 */
public final class CharacterTileView extends StackPane 
{    
    private final GridPane playerGridPane;
    
    private final PlayerCharacter[] playerCharacters;
    
    public static final int TILE_SIZE = 70;
    private final int ANIMATION_OFFSET = TILE_SIZE;
    
    private final int SLIDE_ANIMATION_DURATION = 500;
    
    /**
     * A graphical representation for a tile, containing images for treasures,
     * starting locations, paths, and players
     * @param connector Connects the logic and GUI packages
     * @param tile The tile to draw information from
     */
    public CharacterTileView(GUIConnector connector, Tile tile)
    {
        playerGridPane = new GridPane();
        playerGridPane.setAlignment(Pos.CENTER);
        playerGridPane.setPadding(new Insets(4, 4, 4, 4));
        getChildren().add(playerGridPane);
        playerGridPane.toFront();
        setAlignment(playerGridPane, Pos.CENTER);
        playerCharacters = new PlayerCharacter[4];
        for (int i = 0; i < 4; i++)
        {
            playerCharacters[i] = null;
        }
        boolean[] players = tile.getPlayers();
        for (int i = 0; i < 4; i++)
        {
            if (players[i])
            {
                addPlayerCharacter(connector.getPlayerCharacter(i));
            }
        }
    }
    
    /**
     * Adds a player character to be displayed on this tile, moving other 
     * characters as appropriate
     * @param player The player character to add to this tile
     */
    public void addPlayerCharacter(PlayerCharacter player)
    {
         // First clear all the displayed characters and readd them all and the new character
        playerGridPane.getChildren().clear();
        for (int i = 0; i < 4; i++)
        {
            if (playerCharacters[i] == player) // No duplicates
            {
                break;
            }
            else if (playerCharacters[i] == null)
            {
                playerCharacters[i] = player;
                playerGridPane.add(playerCharacters[i], i % 2, (i < 2) ? 0 : 1);
                GridPane.setMargin(playerCharacters[i], new Insets(4, 4, 4, 4));
                break;
            }
            else
            {
                playerGridPane.add(playerCharacters[i], i % 2, (i < 2) ? 0 : 1);
                GridPane.setMargin(playerCharacters[i], new Insets(4, 4, 4, 4));
            }
        }
    }
    
    /**
     * Removes a player character from this tile, readjusting the remaining
     * characters as appropriate
     * @param player The player character to remove from this tile
     */
    public void removePlayerCharacter(PlayerCharacter player)
    {
        for (int i = 0; i < 4; i++)
        {
            if (playerCharacters[i] == player)
            {
                for (; i < 3; i++) // Shift everything left (which removes the current item)
                {
                    playerCharacters[i] = playerCharacters[i+1];
                }
                playerCharacters[3] = null; // Set the final item to null
                break;
            }
            else {}
        }
        // Remove and readd everything to the correct spots
        playerGridPane.getChildren().clear();
        for (int i = 0; i < 4; i++)
        {
            if (playerCharacters[i] == null)
            {
                break;
            }
            else
            {
                playerGridPane.add(playerCharacters[i], i % 2, (i < 2) ? 0 : 1);
            }
        }
    }
    
    /**
     * Adds a list of player characters to the tile view
     * @param connector Connects the logic and GUI packages
     * @param players The list of player characters to add by index
     */
    public void addPlayerCharacters(GUIConnector connector, boolean[] players)
    {
        for (int i = 0; i < 4; i++)
        {
            if (players[i])
            {
                addPlayerCharacter(connector.getPlayerCharacter(i));
            }
            else {}
        }
    }
    
    /**
     * Removes all player characters from the tile view
     */
    public void removePlayerCharacters()
    {
        playerGridPane.getChildren().clear();
        for (int i = 0; i < 4; i++)
        {
            playerCharacters[i] = null;
        }
    }
    
    /**
     * Animates the tile sliding down
     */
    public void animateDown()
    {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.millis(SLIDE_ANIMATION_DURATION));
        translateTransition.setFromY(-ANIMATION_OFFSET);
        translateTransition.setToY(0);
        translateTransition.setNode(this);
        translateTransition.play();
    }
    
    /**
     * Animates the tile sliding up
     */
    public void animateUp()
    {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.millis(SLIDE_ANIMATION_DURATION));
        translateTransition.setFromY(ANIMATION_OFFSET);
        translateTransition.setToY(0);
        translateTransition.setNode(this);
        translateTransition.play();
    }
    
    /**
     * Animates the tile sliding right
     */
    public void animateRight()
    {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.millis(SLIDE_ANIMATION_DURATION));
        translateTransition.setFromX(-ANIMATION_OFFSET);
        translateTransition.setToX(0);
        translateTransition.setNode(this);
        translateTransition.play();
    }
    
    /**
     * Animates the tile sliding left
     */
    public void animateLeft()
    {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.millis(SLIDE_ANIMATION_DURATION));
        translateTransition.setFromX(ANIMATION_OFFSET);
        translateTransition.setToX(0);
        translateTransition.setNode(this);
        translateTransition.play();
    }
}