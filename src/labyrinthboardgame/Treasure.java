/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame;

import javafx.scene.image.Image;

/**
 *
 * @author Corbi
 */
public class Treasure {
    
    public enum TreasureType {
        s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12,
        s13, s14, s15, s16, s17, s18, s19, s20, s21, s22, s23, s24,
        p1, p2, p3, p4
    };
    private final TreasureType type;
    
    private final Image tileTreasureImage;
    private final int TILE_TREASURE_SIZE = 50;
    private final Image playerTreasureImage;
    private final int PLAYER_TREASURE_SIZE = 50;
    
    public Treasure(TreasureType treasureType)
    {
        type = treasureType;
        
        String treasureImageString = getClass().getResource("assets/" + type.toString() + ".png").toString();
        tileTreasureImage = new Image(treasureImageString, TILE_TREASURE_SIZE, TILE_TREASURE_SIZE, false, true);
        playerTreasureImage = new Image(treasureImageString, PLAYER_TREASURE_SIZE, PLAYER_TREASURE_SIZE, false, true);
    }
    
    public Image getTileTreasureImage()
    {
        return tileTreasureImage;
    }
    
    public Image getPlayerTreasureImage()
    {
        return playerTreasureImage;
    }
}
