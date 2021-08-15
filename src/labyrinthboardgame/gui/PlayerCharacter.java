/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.gui;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import labyrinthboardgame.logic.ConfigurationManager;
import labyrinthboardgame.logic.Player;

/**
 *
 * @author Corbi
 */
public final class PlayerCharacter extends ImageView
{
    private final Player player;
    private final Image playerImage;
    
    private final int WIDTH = 15;
    private final int HEIGHT = 42;
    
    /**
     * Creates a player character for moving around on the board
     * @param player The player controlling the character
     */
    public PlayerCharacter(Player player)
    {
        this.player = player;
        String playerImageString = "";
        switch (player.getPlayerNumber())
        {
            case 1:
                playerImageString = getClass().getResource("assets/p1.png").toString();
                break;
            case 2:
                playerImageString = getClass().getResource("assets/p2.png").toString();
                break;
            case 3:
                playerImageString = getClass().getResource("assets/p3.png").toString();
                break;
            case 4:
                playerImageString = getClass().getResource("assets/p4.png").toString();
                break;
        }
        playerImage = new Image(playerImageString, WIDTH, HEIGHT, false, true);
        this.setImage(playerImage);
    }
    
    /**
     * Returns a reference to the controlling player
     * @return The controlling player
     */
    public Player getPlayer()
    {
        return player;
    }
    
    /**
     * Animates the character moving to a new tile
     * @param x The starting x
     * @param y The starting y
     */
    public void animate(int x, int y)
    {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.millis(ConfigurationManager.CHARACTER_ANIMATION_SPEED));
        translateTransition.setFromX((x * (TileView.TILE_SIZE + TileView.TILE_PADDING)));
        translateTransition.setToX(0);
        translateTransition.setFromY((y * (TileView.TILE_SIZE + TileView.TILE_PADDING)));
        translateTransition.setToY(0);
        translateTransition.setNode(this);
        translateTransition.play();
    }
}
