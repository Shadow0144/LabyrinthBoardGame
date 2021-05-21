/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import labyrinthboardgame.logic.Tile;

/**
 *
 * @author Corbi
 */
public final class PlayerIconTray extends VBox 
{
    private final PlayerIcon icon1;
    private final PlayerIcon icon2;
    private final VBox nextTilePane;
    private final PlayerIcon icon3;
    private final PlayerIcon icon4;
    private Tile nextTile;
        
    /**
     * Builds a tray to hold the player icons and the next tile to be inserted
     */
    public PlayerIconTray()
    {
        icon1 = new PlayerIcon(Color.YELLOW);
        icon2 = new PlayerIcon(Color.BLUE);
        icon3 = new PlayerIcon(Color.GREEN);
        icon4 = new PlayerIcon(Color.RED);
        nextTilePane = new VBox();
        
        nextTilePane.setPadding(new Insets(8, 8, 8, 8));
        
        getChildren().add(nextTilePane);
        getChildren().add(icon1);
        getChildren().add(icon2);
        getChildren().add(icon3);
        getChildren().add(icon4);
        
        setAlignment(Pos.CENTER);
    }
    
    /**
     * Returns a reference to a player icon
     * @param player The player number
     * @return A reference to a player icon
     */
    public PlayerIcon getPlayerIcon(int player)
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
    
    /**
     * Disable showing players not playing
     * @param playerNumber The players not in the game
     */
    public void removePlayerIcon(int playerNumber)
    {
        getChildren().remove(getPlayerIcon(playerNumber));
    }
    
    /**
     * Updates the preview of the next tile to be inserted
     * @param nextTile The next tile
     */
    public void updateNextTile(Tile nextTile)
    {
        this.nextTile = nextTile;
        nextTilePane.getChildren().clear();
        nextTilePane.getChildren().add(nextTile.getTileView());
    }
    
    public void updateNextTileRotation(int rotation)
    {
        nextTile.setRotation(rotation);
    }
}
