/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.logic;

import labyrinthboardgame.gui.GameBoardController;
import labyrinthboardgame.gui.BoardView;
import labyrinthboardgame.gui.PlayerIconTray;

/**
 *
 * @author Corbi
 */
public class GameLoader 
{
    public static Game loadGame(String fileName, GameBoardController controller)
    {
        Player[] players = new Player[4];
        Tile[][] tiles = new Tile[7][7];
        Tile nextTile = null;
        
        Game game = new Game(controller, players, tiles, nextTile);
        
        return game;
    }
}
