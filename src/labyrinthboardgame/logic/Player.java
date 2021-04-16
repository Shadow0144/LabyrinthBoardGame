/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.logic;

import labyrinthboardgame.gui.Treasure;
import labyrinthboardgame.gui.PlayerCharacter;
import java.util.LinkedList;
import javafx.scene.paint.Color;
import labyrinthboardgame.gui.PlayerDisplay;

/**
 *
 * @author Corbi
 */
public class Player {

    private final int number;
    private final PlayerDisplay display;
    private final PlayerCharacter character;
    private Tile currentTile;
    
    private int playerTreasuresRemaining;
    
    private boolean hasWon;
    
    private LinkedList<Treasure> treasures;
    private Treasure currentTreasure;
    
    public Player(int playerNumber)
    {
        number = playerNumber;
        currentTile = null;
        hasWon = false;
        
        character = new PlayerCharacter(this);
        
        treasures = new LinkedList<Treasure>();
        playerTreasuresRemaining = 1; // Prevent off-by-one error
        Color color = Color.WHITE;
        switch (number)
        {
            case 1:
                color = Color.YELLOW;
                break;
            case 2:
                color = Color.BLUE;
                break;
            case 3:
                color = Color.GREEN;
                break;
            case 4:
                color = Color.RED;
                break;
        }
        display = new PlayerDisplay(color);
    }
    
    public PlayerDisplay getDisplay()
    {
        return display;
    }
    
    public PlayerCharacter getCharacter()
    {
        return character;
    }
    
    public int getPlayerNumber()
    {
        return number;
    }
    
    public void assignTreasure(Treasure treasure)
    {
        treasures.add(treasure);
        display.updateTreasuresRemaining(++playerTreasuresRemaining);
    }
    
    public void showNextTreasure()
    {
        currentTreasure = treasures.poll();
        if (currentTreasure != null)
        {
            display.updateTreasureImage(currentTreasure.getPlayerTreasureImage());
        }
        else 
        {
            display.updateTreasureImage(null);
        }
        display.updateTreasuresRemaining(--playerTreasuresRemaining);
    }
    
    public void showPaths()
    {
        if (currentTile != null)
        {
            currentTile.showPaths(number);
        }
        else {}
    }
    
    private void hidePaths()
    {
        if (currentTile != null)
        {
            currentTile.hidePaths();
        }
        else {}
    }
    
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
    
    public boolean getHasWon()
    {
        return hasWon;
    }
    
    public void setActive()
    {
        display.setActive();
    }
    
    public void setInactive()
    {
        display.setInactive();
    }
}
