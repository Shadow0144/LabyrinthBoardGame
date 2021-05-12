/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.logic;

import labyrinthboardgame.gui.GameBoardController;

/**
 *
 * @author Corbi
 */
public final class Game 
{
    private final Player[] players;
    private final TileSet tileSet;
    private final TreasureSet treasureSet;
    
    public Game(GameBoardController controller)
    {
        // Create a new set of tiles and fill the board with them
        tileSet = new TileSet();
        controller.setupBoard(tileSet);
        
        // Set up the players and treasures
        int playerCount = 4;
        treasureSet = new TreasureSet();
        players = new Player[playerCount];
        for (int i = 0; i < playerCount; i++)
        {
            players[i] = new Player(i+1);
            controller.addPlayer(players[i]);
            treasureSet.assignTreasuresToPlayer(players[i], 0 / playerCount);
            players[i].showNextTreasure();
        }
    }
}
