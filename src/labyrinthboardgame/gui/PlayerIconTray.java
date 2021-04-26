/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.gui;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import labyrinthboardgame.logic.Tile;

/**
 *
 * @author Corbi
 */
public class PlayerIconTray extends HBox 
{
    private final PlayerIcon icon1;
    private final PlayerIcon icon2;
    private final Pane nextTilePane;
    private final PlayerIcon icon3;
    private final PlayerIcon icon4;
        
    public PlayerIconTray()
    {
        icon1 = new PlayerIcon(Color.YELLOW);
        icon2 = new PlayerIcon(Color.BLUE);
        icon3 = new PlayerIcon(Color.GREEN);
        icon4 = new PlayerIcon(Color.RED);
        nextTilePane = new Pane();
        
        getChildren().add(icon1);
        getChildren().add(icon2);
        getChildren().add(nextTilePane);
        getChildren().add(icon3);
        getChildren().add(icon4);
    }
    
    public PlayerIcon getIcon(int player)
    {
        PlayerIcon rIcon = null;
        switch (player)
        {
            case 1:
                rIcon = icon1;
                break;
            case 2:
                rIcon = icon2;
                break;
            case 3:
                rIcon = icon3;
                break;
            case 4:
                rIcon = icon4;
                break;
        }
        return rIcon;
    }
    
    public void updateNextTile(Tile nextTile)
    {
        nextTilePane.getChildren().clear();
        nextTilePane.getChildren().add(nextTile.getTileView());
    }
}
