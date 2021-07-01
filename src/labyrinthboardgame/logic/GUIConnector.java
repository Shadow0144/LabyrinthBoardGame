/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.logic;

import labyrinthboardgame.gui.BoardView;
import labyrinthboardgame.gui.PlayerIconTray;
import labyrinthboardgame.gui.TileView;

/**
 *
 * @author Corbi
 */
public class GUIConnector 
{
    private static BoardView boardView;
    private static PlayerIconTray playerIconTray;
    
    public static void setupBoard(BoardView view, PlayerIconTray tray)
    {
        boardView = view;
        playerIconTray = tray;
    }
    
    public static void addTileView(int i, int j, TileView tileView)
    {   
        if (boardView != null)
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
}
