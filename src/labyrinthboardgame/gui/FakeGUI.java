/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.gui;

import javafx.scene.paint.Color;
import labyrinthboardgame.logic.GUIConnector;
import labyrinthboardgame.logic.Game;
import labyrinthboardgame.logic.Player;
import labyrinthboardgame.logic.Tile;
import labyrinthboardgame.logic.Treasure;

/**
 *
 * @author Corbi
 */
public class FakeGUI implements GUIConnector 
{    
    @Override
    public void setupBoard(GameBoardController gbcontroller)
    {
        
    }
    
    @Override
    public void addTileView(int i, int j, Tile tile, Game game)
    {   
        
    }
    
    @Override
    public void removeTileView(int i, int j)
    {
        
    }
    
    @Override
    public void enableArrows(boolean human)
    {
        
    }
    
    @Override
    public void disableArrows(int disabledArrow)
    {
        
    }
    
    @Override
    public void updateTrayNextTile(Game game, Tile nextTile)
    {
        
    }
    
    @Override
    public PlayerIcon getPlayerIcon(int playerNumber)
    {
        return null;
    }
    
    @Override
    public void removePlayerIcon(int playerNumber)
    {
        
    }
    
    @Override
    public void updateCurrentTreasure(Treasure treasure, Color playerColor)
    {
        
    }
    
    @Override
    public void updateCurrentTreasure(Treasure treasure, int playerNum)
    {
        
    }
    
    @Override
    public void setWinningPlayer(int currentPlayer)
    {
        
    }
    
    @Override
    public PlayerCharacter getPlayerCharacter(int playerIndex)
    {
        return null;
    }
    
    @Override
    public PlayerIcon getIcon(int playerIndex)
    {
        return null;
    }
    
    @Override
    public void setIconPlayerName(int playerIndex, String playerName)
    {
        
    }
    
    @Override
    public void updateTreasuresRemaining(int playerIndex, int playerTreasuresRemaining)
    {
        
    }
    
    @Override
    public void setIconHasWon(int playerIndex)
    {
        
    }
    
    @Override
    public void setIconActive(int playerIndex)
    {
        
    }
    
    @Override
    public void setInactive(int playerIndex)
    {
        
    }
    
    @Override
    public void createPlayerCharacter(int playerIndex, Player player)
    {
        
    }
    
    @Override
    public void addPlayerCharacter(int row, int col, int playerIndex)
    {
        
    }
    
    @Override
    public void removePlayerCharacter(int playerIndex)
    {
        
    }
    
    @Override
    public void addPlayerCharacters(Tile tile, boolean[] players)
    {
        
    }
    
    @Override
    public void removePlayerCharacters(Tile tile)
    {
        
    }
    
    @Override
    public void showPaths()
    {
        
    }
    
    @Override
    public void hidePaths()
    {
        
    }
    
    @Override
    public void rotatePreviewTileClockwise()
    {
        
    }
    
    @Override
    public void rotatePreviewTileCounterClockwise()
    {
        
    }
}