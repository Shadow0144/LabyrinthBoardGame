/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.gui;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import labyrinthboardgame.logic.GUIConnector;
import labyrinthboardgame.logic.Player;

/**
 *
 * @author Corbi
 */
public final class PlayerSelectorBox extends VBox 
{
    private final PlayerSelector[] selectors;
    
    public PlayerSelectorBox()
    {
        super();
        setAlignment(Pos.CENTER);
        
        selectors = new PlayerSelector[4];
        selectors[0] = new PlayerSelector(1, Color.YELLOW);
        selectors[1] = new PlayerSelector(2, Color.BLUE);
        selectors[2] = new PlayerSelector(3, Color.GREEN);
        selectors[3] = new PlayerSelector(4, Color.RED);
        
        for (int i = 0; i < 4; i++)
        {
            this.getChildren().add(selectors[i]);
        }
    }
    
    /**
     * Sets the controller on all the selectors so they can alert it when changes occur
     * @param controller The controller
     */
    public void setController(PlayerSelectController controller)
    {
        for (int i = 0; i < 4; i++)
        {
            selectors[i].setController(controller);
        }
    }
    
    /**
     * Returns an array of players based on the player selectors
     * @return Array of 4 Players
     */
    public Player[] getPlayers(GUIConnector connector)
    {
        Player[] players = new Player[4];
        
        for (int i = 0; i < 4; i++)
        {
            players[i] = new Player(i+1, selectors[i].getPlayerType(), selectors[i].getPlayerName(), connector);
        }
        
        return players;
    }
}
