/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.gui;

import javafx.collections.ObservableList;
import javafx.scene.Node;
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
public class JavaFXGUI implements GUIConnector 
{
    private static GameBoardController controller;
    private static BoardView boardView;
    private static PlayerIconTray playerIconTray;
    
    @Override
    public void setupBoard(GameBoardController gbcontroller)
    {
        controller = gbcontroller;
        boardView = gbcontroller.getGameBoardView();
        playerIconTray = gbcontroller.getPlayerIconTray();
    }
    
    @Override
    public void addTileView(int i, int j, Tile tile, Game game)
    {   
        if (boardView != null && tile != null)
        {   
            TileView tileView = new TileView(this, tile);
            boardView.add(tileView, j, i);
            tileView.setListener(game, tile);
        }
        else {}
    }
    
    @Override
    public void removeTileView(int i, int j)
    {
        if (boardView != null)
        {
            ObservableList<Node> children = boardView.getChildren();
            for(Node node : children) 
            {
                if(node instanceof TileView && BoardView.getRowIndex(node) == i && BoardView.getColumnIndex(node) == j) 
                {
                    TileView tileView = (TileView)(node); // use what you want to remove
                    boardView.getChildren().remove(tileView);
                    break;
                }
                else {}
            } 
        }
        else {}
    }
    
    @Override
    public void enableArrows(boolean human)
    {
        if (boardView != null)
        {
            boardView.enableArrows(human);
        }
        else {}
    }
    
    @Override
    public void disableArrows(int disabledArrow)
    {
        if (boardView != null)
        {
            boardView.disableArrows(disabledArrow);
        }
        else {}
    }
    
    @Override
    public void updateTrayNextTile(Game game, Tile nextTile)
    {
        playerIconTray.updateNextTile(game, nextTile);
    }
    
    @Override
    public PlayerIcon getPlayerIcon(int playerNumber)
    {
        return playerIconTray.getPlayerIcon(playerNumber);
    }
    
    @Override
    public void removePlayerIcon(int playerNumber)
    {
        playerIconTray.removePlayerIcon(playerNumber);
    }
    
    @Override
    public void updateCurrentTreasure(Treasure treasure, Color playerColor)
    {
        controller.updateCurrentTreasure(treasure, playerColor);
    }
    
    @Override
    public void updateCurrentTreasure(Treasure treasure, int playerNum)
    {
        controller.updateCurrentTreasure(treasure, getPlayerIcon(playerNum).getColor());
    }
    
    @Override
    public void setWinningPlayer(int currentPlayer)
    {
        controller.setWinningPlayer(currentPlayer-1);
    }
    
    @Override
    public PlayerCharacter getPlayerCharacter(int playerIndex)
    {
        return boardView.getPlayerCharacter(playerIndex);
    }
    
    @Override
    public PlayerIcon getIcon(int playerIndex)
    {
        return playerIconTray.getPlayerIcon(playerIndex+1);
    }
    
    @Override
    public void setIconPlayerName(int playerIndex, String playerName)
    {
        getIcon(playerIndex).setPlayerName(playerName);
    }
    
    @Override
    public void updateTreasuresRemaining(int playerIndex, int playerTreasuresRemaining)
    {
        getIcon(playerIndex).updateTreasuresRemaining(playerTreasuresRemaining);
    }
    
    @Override
    public void setIconHasWon(int playerIndex)
    {
        getIcon(playerIndex).setHasWon();
    }
    
    /**
     * Sets this player as active, which updates the player's icon
     * @param playerIndex The index of the player whose icon is to be retrieved
     */
    @Override
    public void setIconActive(int playerIndex)
    {
        getIcon(playerIndex).setActive();
    }
    
    /**
     * Sets this player as inactive, which updates the player's icon
     * @param playerIndex The index of the player whose icon is to be set inactive
     */
    @Override
    public void setInactive(int playerIndex)
    {
        getIcon(playerIndex).setInactive();
    }
    
    @Override
    public void createPlayerCharacter(int playerIndex, Player player)
    {
        boardView.createPlayerCharacter(playerIndex, player);
    }
    
    /**
     * Adds a player's character to this tile to be displayed
     * @param row The row to add the character to
     * @param col The col to add the character to
     * @param playerIndex The index of the player to add from this tile
     */
    @Override
    public void addPlayerCharacter(int row, int col, int playerIndex)
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
     * @param playerIndex The index of the player to remove from this tile
     */
    @Override
    public void removePlayerCharacter(int playerIndex)
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
    
    @Override
    public void addPlayerCharacters(Tile tile, boolean[] players)
    {
        TileView tileView = boardView.getTileView(tile.getRow(), tile.getCol());
        tileView.addPlayerCharacters(this, players);
    }
    
    @Override
    public void removePlayerCharacters(Tile tile)
    {
        TileView tileView = boardView.getTileView(tile.getRow(), tile.getCol());
        tileView.removePlayerCharacters();
    }
    
    @Override
    public void showPaths()
    {
        boardView.showPaths();
    }
    
    @Override
    public void hidePaths()
    {
        boardView.hidePaths();
    }
    
    @Override
    public void showTreasure(Tile tile)
    {
        if (tile != null)
        {
            TileView tileView = boardView.getTileView(tile.getRow(), tile.getCol());
            tileView.showTreasure();
        }
        else {}
    }
    
    @Override
    public void hideTreasure(Tile tile)
    {
        if (tile != null)
        {
            TileView tileView = boardView.getTileView(tile.getRow(), tile.getCol());
            tileView.hideTreasure();
        }
        else {}
    }
    
    @Override
    public void rotatePreviewTileClockwise()
    {
        boardView.rotatePreviewClockwise();
        playerIconTray.updateNextTileRotation();
    }
    
    @Override
    public void rotatePreviewTileCounterClockwise()
    {
        boardView.rotatePreviewCounterClockwise();
        playerIconTray.updateNextTileRotation();
    }
    
    @Override
    public void animateTileDown(Tile tile)
    {
        TileView tileView = boardView.getTileView(tile.getRow(), tile.getCol());
        tileView.animateDown();
    }
    
    @Override
    public void animateTileUp(Tile tile)
    {
        TileView tileView = boardView.getTileView(tile.getRow(), tile.getCol());
        tileView.animateUp();
    }
    
    @Override
    public void animateTileRight(Tile tile)
    {
        TileView tileView = boardView.getTileView(tile.getRow(), tile.getCol());
        tileView.animateRight();
    }
    
    @Override
    public void animateTileLeft(Tile tile)
    {
        TileView tileView = boardView.getTileView(tile.getRow(), tile.getCol());
        tileView.animateLeft();
    }
}
