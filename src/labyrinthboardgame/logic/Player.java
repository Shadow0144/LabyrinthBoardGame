/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.logic;

import labyrinthboardgame.gui.PlayerCharacter;
import java.util.LinkedList;
import labyrinthboardgame.gui.PlayerIcon;

/**
 *
 * @author Corbi
 */
public final class Player
{
    public enum PlayerType
    {
        none,
        human,
        ai,
        advanced_ai
    };
    private final PlayerType playerType;
    
    private final int number; // 1, 2, 3, or 4
    private final PlayerCharacter character;
    private PlayerIcon display;
    private Tile currentTile;
    
    private int playerTreasuresRemaining;
    
    private boolean hasWon;
    
    private final LinkedList<Treasure> treasures;
    private Treasure currentTreasure;
    
    /**
     * A player, which has a list of treasures to collect and a character to
     * move around the board
     * @param playerNumber The number of this player, which also determines
     * @param playerType The type of player (e.g. absent, human, or an A.I.)
     * their color
     */
    public Player(int playerNumber, PlayerType playerType)
    {
        this.playerType = playerType;
        number = playerNumber;
        currentTile = null;
        hasWon = false;
        
        character = new PlayerCharacter(this);
        
        treasures = new LinkedList<Treasure>();
        playerTreasuresRemaining = 1; // Prevent off-by-one error
    }
    
    /**
     * Returns the player type
     * @return The player type
     */
    public PlayerType getPlayerType()
    {
        return playerType;
    }
    
    /**
     * Sets a reference to the icon for this player
     * @param icon This player's icon
     */
    public void setIcon(PlayerIcon icon)
    {
        display = icon;
    }
    
    /**
     * Returns a reference to this player's icon
     * @return This player's icon
     */
    public PlayerIcon getIcon()
    {
        return display;
    }
    
    /**
     * Gets a reference to this player's character on the board
     * @return This player's character
     */
    public PlayerCharacter getCharacter()
    {
        return character;
    }
    
    /**
     * Returns the number of this player (i.e. 1, 2, 3, or 4)
     * Note: Begins with 1, not 0
     * @return This player's number
     */
    public int getPlayerNumber()
    {
        return number;
    }
    
    /**
     * Assigns a treasure to this player for them to collect and updates the UI
     * @param treasure A treasure the player must collect
     */
    public void assignTreasure(Treasure treasure)
    {
        treasures.add(treasure);
        display.updateTreasuresRemaining(++playerTreasuresRemaining);
    }
    
    /**
     * Displays the next treasure to collect and removes 1 from the counter
     */
    public void showNextTreasure()
    {
        currentTreasure = treasures.poll();
        if (currentTreasure != null)
        {
            display.updateTreasureImage(currentTreasure.getTreasureImageName());
        }
        else 
        {
            display.updateTreasureImage(null);
        }
        display.updateTreasuresRemaining(--playerTreasuresRemaining);
    }
    
    /**
     * Enables drawing paths from the player's current tile, showing all possible
     * paths from that tile
     */
    public void showPaths()
    {
        if (currentTile != null)
        {
            currentTile.showPaths(number);
        }
        else {}
    }
    
    /**
     * Disables drawing paths
     */
    private void hidePaths()
    {
        if (currentTile != null)
        {
            currentTile.hidePaths();
        }
        else {}
    }
    
    /**
     * Moves the player's character to another tile and checks if the player
     * gains a treasure or wins the game
     * @param tile The tile to move the character to
     */
    public void moveCharacter(Tile tile)
    {
        hidePaths();
        if (currentTile != null)
        {
            currentTile.removePlayerCharacter(character);
        }
        else {}
        currentTile = tile;
        currentTile.addPlayerCharacter(character);
        
        Treasure treasure = tile.getTreasure();
        if (treasure != null && currentTreasure != null &&
            treasure.getTreasureType() == currentTreasure.getTreasureType())
        {
            showNextTreasure();
        }
        else {}
        
        if (playerTreasuresRemaining == 0
                && tile.getPlayer() == number)
        {
            hasWon = true;
            display.setHasWon();
        }
        else {}
    }
    
    /**
     * Returns if this player has won the game
     * @return If this player has won the game
     */
    public boolean getHasWon()
    {
        return hasWon;
    }
    
    /**
     * Sets this player as active, which updates the player's icon
     */
    public void setActive()
    {
        display.setActive();
    }
    
    /**
     * Sets this player as inactive, which updates the player's icon
     */
    public void setInactive()
    {
        display.setInactive();
    }
}
