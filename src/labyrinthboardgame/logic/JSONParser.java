/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.logic;

import java.io.File;

/**
 *
 * @author Corbi
 */
public class JSONParser 
{
    private Player[] players;
    private Treasure[] treasures;
    
    public void parseSave(File save)
    {
        players = new Player[4];
        treasures = new Treasure[4];
    }
    
    public Player[] getPlayers()
    {
        return players;
    }
    
    public Treasure[] getTreasures()
    {
        return treasures;
    }
    
    public void saveGame(Game game)
    {
        
    }
}
