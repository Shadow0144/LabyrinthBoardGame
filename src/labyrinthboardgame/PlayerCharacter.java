/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Corbi
 */
public class PlayerCharacter extends ImageView {
    
    private int playerNumber;
    private Image playerImage;
    
    private int currentRow;
    private int currentColumn;
    
    private final int WIDTH = 15;
    private final int HEIGHT = 42;
    
    public PlayerCharacter(int playerNumber)
    {
        this.playerNumber = playerNumber;
        String playerImageString = "";
        switch (playerNumber)
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
}
