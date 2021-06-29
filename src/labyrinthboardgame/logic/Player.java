/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.logic;

import labyrinthboardgame.gui.PlayerCharacter;
import java.util.LinkedList;
import javafx.scene.paint.Color;
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
    
    private final String playerName;
    
    // For loading
    private int x;
    private int y;
    
    private BasicAI basicAI;
    private AdvancedAI advancedAI;
    
    /**
     * A player, which has a list of treasures to collect and a character to
     * move around the board
     * @param playerNumber The number of this player, which also determines
     * @param playerType The type of player (e.g. absent, human, or an A.I.)
     * their color
     * @param playerName The name of the player
     */
    public Player(int playerNumber, PlayerType playerType, String playerName)
    {
        this.playerType = playerType;
        this.playerName = playerName;
        this.number = playerNumber;
        currentTile = null;
        hasWon = false;
        
        character = new PlayerCharacter(this);
        
        treasures = new LinkedList<Treasure>();
        playerTreasuresRemaining = 1; // Prevent off-by-one error
    }
    
    /**
     * Sets up the AIs
     * @param game A reference to the Game
     */
    public void setupAI(Game game)
    {
        switch (playerType) 
        {
            case ai:
                basicAI = new BasicAI(this, game);
                advancedAI = null;
                break;
            case advanced_ai:
                basicAI = null;
                advancedAI = new AdvancedAI(this, game);
                break;
            default:
                basicAI = null;
                advancedAI = null;
                break;
        }
    }
    
    /**
     * Kills all AI timers
     */
    public void killAllTimers()
    {
        if (basicAI != null)
        {
            basicAI.killTimer();
        }
        else {}
        if (advancedAI != null)
        {
            advancedAI.killTimer();
        }
        else {}
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
     * Returns the player name
     * @return The player name
     */
    public String getPlayerName()
    {
        return playerName;
    }
    
    /**
     * Returns the current treasure
     * @return The current Treasure
     */
    public Treasure getCurrentTreasure()
    {
        return currentTreasure;
    }
    
    /**
     * Returns if the player is in the game (i.e. not type none)
     * @return If the player is in the game
     */
    public boolean inGame()
    {
        return (playerType != PlayerType.none);
    }
    
    /**
     * Sets a reference to the icon for this player
     * @param icon This player's icon
     */
    public void setIcon(PlayerIcon icon)
    {
        display = icon;
        display.setPlayerName(playerName);
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
     * Returns the player's color
     * @return The player's color
     */
    public Color getPlayerColor()
    {
        return display.getColor();
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
        display.updateTreasuresRemaining(--playerTreasuresRemaining);
    }
    
    /**
     * Returns a list of treasures the player must collect
     * @return A list of treasures the player must collect
     */
    public LinkedList<Treasure> getTreasures()
    {
        LinkedList<Treasure> allTreasures = new LinkedList<Treasure>();
        if (currentTreasure != null) // The current treasure is popped off the list, so we need to readd it
        {
            allTreasures.add(currentTreasure);
        }
        else {}
        allTreasures.addAll(treasures);
        return allTreasures;
    }
    
    /**
     * Returns the current tile
     * @return The current tile
     */
    public Tile getCurrentTile()
    {
        return currentTile;
    }
    
    /**
     * Sets the position of the player when loading
     * @param loadedX The row of the loaded player
     * @param loadedY The column of the loaded player
     */
    public void setLoadedTilePosition(int loadedX, int loadedY)
    {
        x = loadedX;
        y = loadedY;
    }
    
    /**
     * Returns the row of the loaded player
     * @return the row of the loaded player
     */
    public int getLoadedX()
    {
        return x;
    }
    
    /**
     * Returns the column of the loaded player
     * @return the column of the loaded player
     */
    public int getLoadedY()
    {
        return y;
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
    
    /**
     * Performs the player's turn if it is AI-controlled
     */
    public void performTurn()
    {
        switch (playerType)
        {
            case none:
            case human:
                // Do nothing
                break;
            case ai:
                basicAI.performTurn();
                break;
            case advanced_ai:
                advancedAI.performTurn();
                break;
        }
    }
}
