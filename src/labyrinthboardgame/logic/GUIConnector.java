/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.logic;

import javafx.scene.paint.Color;
import labyrinthboardgame.gui.BoardView;
import labyrinthboardgame.gui.GameBoardController;
import labyrinthboardgame.gui.PlayerCharacter;
import labyrinthboardgame.gui.PlayerIcon;
import labyrinthboardgame.gui.PlayerIconTray;
import labyrinthboardgame.gui.TileView;

/**
 *
 * @author Corbi
 */
public class GUIConnector 
{
    private static GameBoardController controller;
    private static BoardView boardView;
    private static PlayerIconTray playerIconTray;
    
    public static void setupBoard(GameBoardController gbcontroller)
    {
        controller = gbcontroller;
        boardView = gbcontroller.getGameBoardView();
        playerIconTray = gbcontroller.getPlayerIconTray();
    }
    
    public static void addTileView(int i, int j, TileView tileView)
    {   
        if (boardView != null && tileView != null)
        {
            boardView.add(tileView, j, i);
        }
        else {}
    }
    
    public static void removeTileView(TileView tileView)
    {
        if (boardView != null)
        {
            boardView.getChildren().remove(tileView);
        }
        else {}
    }
    
    public static void enableArrows(boolean human)
    {
        if (boardView != null)
        {
            boardView.enableArrows(human);
        }
        else {}
    }
    
    public static void disableArrows(int disabledArrow)
    {
        if (boardView != null)
        {
            boardView.disableArrows(disabledArrow);
        }
        else {}
    }
    
    public static void updateTrayNextTile(Tile nextTile)
    {
        playerIconTray.updateNextTile(nextTile);
    }
    
    public static PlayerIcon getPlayerIcon(int playerNumber)
    {
        return playerIconTray.getPlayerIcon(playerNumber);
    }
    
    public static void removePlayerIcon(int playerNumber)
    {
        playerIconTray.removePlayerIcon(playerNumber);
    }
    
    public static void updateCurrentTreasure(Treasure treasure, Color playerColor)
    {
        controller.updateCurrentTreasure(treasure, playerColor);
    }
    
    public static void updateCurrentTreasure(Treasure treasure, int playerNum)
    {
        controller.updateCurrentTreasure(treasure, getPlayerIcon(playerNum).getColor());
    }
    
    public static void setWinningPlayer(int currentPlayer)
    {
        controller.setWinningPlayer(currentPlayer);
    }
    
    public static PlayerCharacter getPlayerCharacter(int playerIndex)
    {
        return boardView.getPlayerCharacter(playerIndex);
    }
    
    public static PlayerIcon getIcon(int playerIndex)
    {
        return playerIconTray.getPlayerIcon(playerIndex+1);
    }
    
    public static void setIconPlayerName(int playerIndex, String playerName)
    {
        getIcon(playerIndex).setPlayerName(playerName);
    }
    
    public static void updateTreasuresRemaining(int playerIndex, int playerTreasuresRemaining)
    {
        getIcon(playerIndex).updateTreasuresRemaining(playerTreasuresRemaining);
    }
    
    public static void setIconHasWon(int playerIndex)
    {
        getIcon(playerIndex).setHasWon();
    }
    
    /**
     * Sets this player as active, which updates the player's icon
     */
    public static void setIconActive(int playerIndex)
    {
        getIcon(playerIndex).setActive();
    }
    
    /**
     * Sets this player as inactive, which updates the player's icon
     */
    public static void setInactive(int playerIndex)
    {
        getIcon(playerIndex).setInactive();
    }
    
    public static void createPlayerCharacter(int playerIndex, Player player)
    {
        boardView.createPlayerCharacter(playerIndex, player);
    }
    
    /**
     * Adds a player's character to this tile to be displayed
     * @param player The character to add to this tile
     */
    public static void addPlayerCharacter(int row, int col, int playerIndex)
    {
        TileView tileView = boardView.getTileView(row, col);
        if (tileView != null)
        {
            tileView.addPlayerCharacter(getPlayerCharacter(playerIndex));
        }
        else {}
    }
    
    /**
     * Removes a player's character from being displayed on this tile
     * @param player The character to remove from this tile
     */
    public static void removePlayerCharacter(int playerIndex)
    {
        int row = controller.getGame().findPlayerRow(playerIndex);
        int col = controller.getGame().findPlayerCol(playerIndex);
        TileView tileView = boardView.getTileView(row, col);
        if (tileView != null)
        {
            tileView.removePlayerCharacter(getPlayerCharacter(playerIndex));
        }
        else {}
    }
}
