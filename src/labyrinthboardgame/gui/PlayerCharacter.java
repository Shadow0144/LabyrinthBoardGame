/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import labyrinthboardgame.logic.Player;

/**
 *
 * @author Corbi
 */
public class PlayerCharacter extends ImageView {
    
    private final Player player;
    private final Image playerImage;
    
    private final int WIDTH = 15;
    private final int HEIGHT = 42;
    
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
    
    public Player getPlayer()
    {
        return player;
    }
}
