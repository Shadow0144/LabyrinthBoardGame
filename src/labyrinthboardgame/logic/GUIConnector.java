/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.logic;

import javafx.scene.paint.Color;
import labyrinthboardgame.gui.FakeGUI;
import labyrinthboardgame.gui.GameBoardController;
import labyrinthboardgame.gui.JavaFXGUI;
import labyrinthboardgame.gui.PlayerCharacter;
import labyrinthboardgame.gui.PlayerIcon;

/**
 *
 * @author Corbi
 */
public interface GUIConnector 
{
    public static GUIConnector getFakeGUI()
    {
        return new FakeGUI();
    }
    
    public static GUIConnector getJavaFXGUI()
    {
        return new JavaFXGUI();
    }
    
    public abstract void setupBoard(GameBoardController gbcontroller);
    
    public abstract void addTileView(int i, int j, Tile tile, Game game);
    
    public abstract void removeTileView(int i, int j);
    
    public abstract void enableArrows(boolean human);
    
    public abstract void disableArrows(int disabledArrow);
    
    public abstract void updateTrayNextTile(Game game, Tile nextTile);
    
    public abstract PlayerIcon getPlayerIcon(int playerNumber);
    
    public abstract void removePlayerIcon(int playerNumber);
    
    public abstract void updateCurrentTreasure(Treasure treasure, Color playerColor);
    
    public abstract void updateCurrentTreasure(Treasure treasure, int playerNum);
    
    public abstract void setWinningPlayer(String playerName);
    
    public abstract PlayerCharacter getPlayerCharacter(int playerIndex);
    
    public abstract PlayerIcon getIcon(int playerIndex);
    
    public abstract void setIconPlayerName(int playerIndex, String playerName);
    
    public abstract void updateTreasuresRemaining(int playerIndex, int playerTreasuresRemaining);
    
    public abstract void setIconHasWon(int playerIndex);

    public abstract void setIconActive(int playerIndex);
    
    public abstract void setInactive(int playerIndex);
    
    public abstract void createPlayerCharacter(int playerIndex, Player player);
    
    public abstract void addPlayerCharacter(int row, int col, int playerIndex);
    
    public abstract void removePlayerCharacter(int playerIndex);
    
    public abstract void addPlayerCharacters(Tile tile, boolean[] players);
    
    public abstract void removePlayerCharacters(Tile tile);
    
    public abstract void showPaths();
    
    public abstract void hidePaths();
    
    public abstract void highlightTreasure(Tile tile);
    
    public abstract void unhighlightTreasure(Tile tile);
    
    public abstract void showTreasure(Tile tile);
    
    public abstract void hideTreasure(Tile tile);
    
    public abstract void rotatePreviewTileClockwise();
    
    public abstract void rotatePreviewTileCounterClockwise();
    
    public abstract void animateTileDown(Tile tile);
    
    public abstract void animateTileUp(Tile tile);
    
    public abstract void animateTileRight(Tile tile);
    
    public abstract void animateTileLeft(Tile tile);
}
