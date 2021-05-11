/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.logic;

/**
 *
 * @author Corbi
 */
public class Treasure
{
    public enum TreasureType {
        s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12,
        s13, s14, s15, s16, s17, s18, s19, s20, s21, s22, s23, s24,
        p1, p2, p3, p4
    };
    private final TreasureType type;
    
    /**
     * Creates a treasure which holds images for displaying on a tile and in a
     * player's icons
     * Note: this also includes starting locations
     * @param treasureType The type of treasure or starting location
     */
    public Treasure(TreasureType treasureType)
    {
        type = treasureType;
    }
    
    /**
     * Returns the name of the treasure image
     * @return The treasure image for a tile
     */
    public String getTreasureImageName()
    {
        return type.toString();
    }
    
    /**
     * Returns the type of treasure
     * @return The type of the treasure
     */
    public TreasureType getTreasureType()
    {
        return type;
    }
}
