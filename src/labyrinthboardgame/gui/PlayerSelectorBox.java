/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.gui;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

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
        selectors[0] = new PlayerSelector(Color.YELLOW);
        selectors[1] = new PlayerSelector(Color.BLUE);
        selectors[2] = new PlayerSelector(Color.GREEN);
        selectors[3] = new PlayerSelector(Color.RED);
        
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
}
