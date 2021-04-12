/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Corbi
 */
public class TileSet
{    
    int tileIndex;
    ArrayList<Tile> tiles;
    
    final int Is = 13;
    final int Ls = 15;
    final int Ts = 6;
    
    Tile nextTile;
    
    Random random;
    
    public TileSet()
    {
        tileIndex = 0;
        tiles = new ArrayList<Tile>();
        
        random = new Random();
        
        int i; 
        // I's
        for (i = 0; i < Is; i++)
        {
            tiles.add(new Tile(Tile.Shape.I, getRandomRotation(), null)); // No treasure on I tiles
        }
        
        // L's
        for (i = 0; i < Ls; i++)
        {
            Treasure treasure = null;
            switch (i)
            {
                case 0:
                    treasure = new Treasure(Treasure.TreasureType.s3); // Scarab
                    break;
                case 1:
                    treasure = new Treasure(Treasure.TreasureType.s7); // Lizard
                    break;
                case 2:
                    treasure = new Treasure(Treasure.TreasureType.s8); // Spider
                    break;
                case 3:
                    treasure = new Treasure(Treasure.TreasureType.s14); // Owl
                    break;
                case 4:
                    treasure = new Treasure(Treasure.TreasureType.s16); // Moth
                    break;
                case 5:
                    treasure = new Treasure(Treasure.TreasureType.s19); // Mouse
                    break;
            }
            tiles.add(new Tile(Tile.Shape.L, getRandomRotation(), treasure));
        }
        
        // T's
        for (i = 0; i < Ts; i++)
        {
            Treasure treasure = null;
            switch (i)
            {
                case 0:
                    treasure = new Treasure(Treasure.TreasureType.s10); // Sorceress
                    break;
                case 1:
                    treasure = new Treasure(Treasure.TreasureType.s13); // Monster
                    break;
                case 2:
                    treasure = new Treasure(Treasure.TreasureType.s11); // Genie
                    break;
                case 3:
                    treasure = new Treasure(Treasure.TreasureType.s1); // Ghost
                    break;
                case 4:
                    treasure = new Treasure(Treasure.TreasureType.s12); // Bat
                    break;
                case 5:
                    treasure = new Treasure(Treasure.TreasureType.s20); // Dragon
                    break;
            }
            tiles.add(new Tile(Tile.Shape.T, getRandomRotation(), treasure));
        }
        
        Collections.shuffle(tiles);
        nextTile = tiles.get(33); // 34 tiles
    }
    
    public Tile getNextInitialTile()
    {
        return tiles.get(tileIndex++);
    }
    
    public void setNextTile(Tile next)
    {
        nextTile = next;
    }
    
    public Tile getNextTile()
    {
        return nextTile;
    }
    
    public int getRandomRotation()
    {
        return (random.nextInt(3) * 90);
    }
    
    public void setNextTileRotation(int rotation)
    {
        nextTile.setRotation(rotation);
    }
}
