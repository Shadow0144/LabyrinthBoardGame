/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.gui;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
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
            boardView.getBoardPane().add(tileView, j, i);
            CharacterTileView cTileView = new CharacterTileView(this, tile);
            boardView.getCharacterPane().add(cTileView, j, i);
            tileView.setListener(game, tile);
        }
        else {}
    }
    
    @Override
    public void removeTileView(int i, int j)
    {
        if (boardView != null)
        {
            ObservableList<Node> children = boardView.getBoardPane().getChildren();
            for (Node node : children) 
            {
                if (node instanceof TileView && GridPane.getRowIndex(node) == i && GridPane.getColumnIndex(node) == j) 
                {
                    TileView tileView = (TileView)(node);
                    boardView.getBoardPane().getChildren().remove(tileView);
                    break;
                }
                else {}
            } 
            children = boardView.getCharacterPane().getChildren();
            for (Node node : children) 
            {
                if (node instanceof CharacterTileView && GridPane.getRowIndex(node) == i && GridPane.getColumnIndex(node) == j) 
                {
                    CharacterTileView cTileView = (CharacterTileView)(node);
                    boardView.getCharacterPane().getChildren().remove(cTileView);
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
    public void setWinningPlayer(String playerName)
    {
        controller.setWinningPlayer(playerName);
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
            tileView.addPlayerCharacter();
        }
        else {}
        CharacterTileView cTileView = boardView.getCharacterTileView(row, col);
        if (cTileView != null)
        {
            cTileView.addPlayerCharacter(getPlayerCharacter(playerIndex));
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
        CharacterTileView cTileView = boardView.getCharacterTileView(row, col);
        if (cTileView != null)
        {
            cTileView.removePlayerCharacter(getPlayerCharacter(playerIndex));
        }
        else {}
    }
    
    @Override
    public void addPlayerCharacters(Tile tile, boolean[] players)
    {
        TileView tileView = boardView.getTileView(tile.getRow(), tile.getCol());
        tileView.addPlayerCharacters(players);
        CharacterTileView cTileView = boardView.getCharacterTileView(tile.getRow(), tile.getCol());
        cTileView.addPlayerCharacters(this, players);
        for (int i = 0; i < 4; i++)
        {
            if (players[i])
            {
                controller.getGame().movePlayerToTile(tile, i);
            }
            else {}
        }
    }
    
    @Override
    public void removePlayerCharacters(Tile tile)
    {
        CharacterTileView cTileView = boardView.getCharacterTileView(tile.getRow(), tile.getCol());
        cTileView.removePlayerCharacters();
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
    public void highlightTreasure(Tile tile)
    {
        if (tile != null)
        {
            TileView tileView = boardView.getTileView(tile.getRow(), tile.getCol());
            if (tileView == null)
            {
                tileView = playerIconTray.getNextTileView();
            }
            else {}
            if (tileView != null)
            {
                tileView.highlightTreasure();
            }
            else {}
        }
        else {}
    }
    
    @Override
    public void unhighlightTreasure(Tile tile)
    {
        if (tile != null)
        {
            TileView tileView = boardView.getTileView(tile.getRow(), tile.getCol());
            if (tileView == null)
            {
                tileView = playerIconTray.getNextTileView();
            }
            else {}
            if (tileView != null)
            {
                tileView.unhighlightTreasure();
            }
            else {}
        }
        else {}
    }
    
    @Override
    public void showTreasure(Tile tile)
    {
        if (tile != null)
        {
            tile.showTreasure();
            TileView tileView = boardView.getTileView(tile.getRow(), tile.getCol());
            if (tileView == null)
            {
                tileView = playerIconTray.getNextTileView();
            }
            else {}
            if (tileView != null)
            {
                tileView.showTreasure();
            }
            else {}
        }
        else {}
    }
    
    @Override
    public void hideTreasure(Tile tile)
    {
        if (tile != null)
        {
            tile.hideTreasure();
            TileView tileView = boardView.getTileView(tile.getRow(), tile.getCol());
            if (tileView == null)
            {
                tileView = playerIconTray.getNextTileView();
            }
            else {}
            if (tileView != null)
            {
                tileView.hideTreasure();
            }
            else {}
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
        CharacterTileView cTileView = boardView.getCharacterTileView(tile.getRow(), tile.getCol());
        cTileView.animateDown();
    }
    
    @Override
    public void animateTileUp(Tile tile)
    {
        TileView tileView = boardView.getTileView(tile.getRow(), tile.getCol());
        tileView.animateUp();
        CharacterTileView cTileView = boardView.getCharacterTileView(tile.getRow(), tile.getCol());
        cTileView.animateUp();
    }
    
    @Override
    public void animateTileRight(Tile tile)
    {
        TileView tileView = boardView.getTileView(tile.getRow(), tile.getCol());
        tileView.animateRight();
        CharacterTileView cTileView = boardView.getCharacterTileView(tile.getRow(), tile.getCol());
        cTileView.animateRight();
    }
    
    @Override
    public void animateTileLeft(Tile tile)
    {
        TileView tileView = boardView.getTileView(tile.getRow(), tile.getCol());
        tileView.animateLeft();
        CharacterTileView cTileView = boardView.getCharacterTileView(tile.getRow(), tile.getCol());
        cTileView.animateLeft();
    }
    
    @Override
    public void animateCharacter(int playerIndex, int x, int y)
    {
        getPlayerCharacter(playerIndex).animate(x, y);
    }
}
