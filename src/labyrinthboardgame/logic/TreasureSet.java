/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.logic;

import labyrinthboardgame.logic.Player;
import labyrinthboardgame.gui.Treasure;
import java.util.ArrayList;
import java.util.Collections;
import javafx.event.EventType;

/**
 *
 * @author Corbi
 */
public class TreasureSet {
    private ArrayList<Treasure> treasures;
    
    private int index;
    
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
    
    public void assignTreasuresToPlayer(Player player, int treasureCount)
    {
        for (int i = 0; i < 1; i++)
        {
            player.assignTreasure(treasures.get(index++));
        }
    }
}
