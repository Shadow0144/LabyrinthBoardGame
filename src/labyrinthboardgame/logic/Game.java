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
    
    public Game(GameBoardController controller, Player[] players, int treasureCount)
    {
        // Create a new set of tiles and fill the board with them
        tileSet = new TileSet();
        controller.setupBoard(tileSet);
        
        // Set up the players and treasures
        treasureSet = new TreasureSet();
        this.players = players;
        for (int i = 0; i < 4; i++)
        {
            controller.addPlayer(players[i]);
            treasureSet.assignTreasuresToPlayer(players[i], treasureCount);
            players[i].showNextTreasure();
        }
    }
}
