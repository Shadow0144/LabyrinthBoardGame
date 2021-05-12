/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.logic;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Corbi
 */
public final class TreasureSet 
{
    private final ArrayList<Treasure> treasures;
    private int index;
    
    /**
     * Creates a set of treasures to randomly assign to players
     */
    public TreasureSet()
    {
        treasures = new ArrayList<Treasure>();
        for (int i = 0; i < 24; i++)
        {
            treasures.add(new Treasure(Treasure.TreasureType.values()[i]));
        }
        Collections.shuffle(treasures);
        index = 0;
    }
    
    /**
     * Assigns a set of treasures to a player
     * @param player The player to assign to
     * @param treasureCount The amount of treasures to assign to the player
     */
    public void assignTreasuresToPlayer(Player player, int treasureCount)
    {
        for (int i = 0; i < treasureCount; i++)
        {
            player.assignTreasure(treasures.get(index++));
        }
    }
}
