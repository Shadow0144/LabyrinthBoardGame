/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author Corbi
 */
public final class TileSet
{    
    int tileIndex;
    ArrayList<Tile> tiles;
    
    final int Is = 13;
    final int Ls = 15;
    final int Ts = 6;
    
    Tile nextTile;
    
    Random random;
    
    /**
     * Creates a set of tiles that can be moved around the game board
     * Note: this does not include the fixed in place tiles attached to the board
     */
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
    
    public TileSet(Tile[][] loadedTiles, Tile loadedNextTile)
    {
        tileIndex = 0;
        tiles = new ArrayList<Tile>();
        for (int i = 0; i < 7; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                if (((i % 2) == 0) && ((j % 2) == 0))
                {
                    // Throw out the static tiles
                }
                else
                {
                    tiles.add(loadedTiles[i][j]);
                }
            }
        }
        nextTile = loadedNextTile;
    }
    
    /**
     * Returns the next tile to add to the board
     * @return The next tile to add to the board
     */
    public Tile getNextInitialTile()
    {
        return tiles.get(tileIndex++);
    }
    
    /**
     * Sets a reference to the next tile
     * @param next The next tile to be inserted into the board during play
     */
    public void setNextTile(Tile next)
    {
        nextTile = next;
    }
    
    /**
     * Returns the next tile to be inserted during play
     * @return The next tile to be inserted during play
     */
    public Tile getNextTile()
    {
        return nextTile;
    }
    
    /**
     * Returns a random rotation between the values of 0, 90, 180, and 270
     * @return A random 90-degree rotation
     */
    public int getRandomRotation()
    {
        return (random.nextInt(3) * 90);
    }
    
    /**
     * Sets the rotation of the next tile
     * @param rotation The new rotation of the next tile
     */
    public void setNextTileRotation(int rotation)
    {
        nextTile.setRotation(rotation);
    }
    
    public void rotateNextTileClockwise()
    {
        nextTile.rotateClockwise();
    }
    
    public void rotateNextTileCounterClockwise()
    {
        nextTile.rotateCounterClockwise();
    }
}
