/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame;

import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Corbi
 */
public class LabyrinthGameBoard extends GridPane
{
    //private GameBoardController gbController;
    private Tile[][] tiles;
    
    public LabyrinthGameBoard()
    {
        super();
        
        createArrows();
    }
    
    /*public void setNextTileRotation(int rotation)
    {
        tileSet.setNextTileRotation(rotation);
    }
    
    public void placeTileTopLeft()
    {
        Tile temp = tiles[6][1];
        tiles[6][1] = tiles[5][1];
        tiles[5][1] = tiles[4][1];
        tiles[4][1] = tiles[3][1];
        tiles[3][1] = tiles[2][1];
        tiles[2][1] = tiles[1][1];
        tiles[1][1] = tiles[0][1];
        tiles[0][1] = tileSet.getNextTile();
        tileSet.setNextTile(temp);
        
        gbController.setTiles(tiles, tileSet.getNextTile());
    }
    
    public void placeTileTopCenter()
    {
        Tile temp = tiles[6][3];
        tiles[6][3] = tiles[5][3];
        tiles[5][3] = tiles[4][3];
        tiles[4][3] = tiles[3][3];
        tiles[3][3] = tiles[2][3];
        tiles[2][3] = tiles[1][3];
        tiles[1][3] = tiles[0][3];
        tiles[0][3] = tileSet.getNextTile();
        tileSet.setNextTile(temp);
        
        gbController.setTiles(tiles, tileSet.getNextTile());
    }
    
    public void placeTileTopRight()
    {
        Tile temp = tiles[6][5];
        tiles[6][5] = tiles[5][5];
        tiles[5][5] = tiles[4][5];
        tiles[4][5] = tiles[3][5];
        tiles[3][5] = tiles[2][5];
        tiles[2][5] = tiles[1][5];
        tiles[1][5] = tiles[0][5];
        tiles[0][5] = tileSet.getNextTile();
        tileSet.setNextTile(temp);
        
        gbController.setTiles(tiles, tileSet.getNextTile());
    }
    
    public void placeTileLeftTop()
    {
        Tile temp = tiles[1][6];
        tiles[1][6] = tiles[1][5];
        tiles[1][5] = tiles[1][4];
        tiles[1][4] = tiles[1][3];
        tiles[1][3] = tiles[1][2];
        tiles[1][2] = tiles[1][1];
        tiles[1][1] = tiles[1][0];
        tiles[1][0] = tileSet.getNextTile();
        tileSet.setNextTile(temp);
        
        gbController.setTiles(tiles, tileSet.getNextTile());
    }
    
    public void placeTileLeftCenter()
    {
        Tile temp = tiles[3][6];
        tiles[3][6] = tiles[3][5];
        tiles[3][5] = tiles[3][4];
        tiles[3][4] = tiles[3][3];
        tiles[3][3] = tiles[3][2];
        tiles[3][2] = tiles[3][1];
        tiles[3][1] = tiles[3][0];
        tiles[3][0] = tileSet.getNextTile();
        tileSet.setNextTile(temp);
        
        gbController.setTiles(tiles, tileSet.getNextTile());
    }
    
    public void placeTileLeftBottom()
    {
        Tile temp = tiles[5][6];
        tiles[5][6] = tiles[5][5];
        tiles[5][5] = tiles[5][4];
        tiles[5][4] = tiles[5][3];
        tiles[5][3] = tiles[5][2];
        tiles[5][2] = tiles[5][1];
        tiles[5][1] = tiles[5][0];
        tiles[5][0] = tileSet.getNextTile();
        tileSet.setNextTile(temp);
        
        gbController.setTiles(tiles, tileSet.getNextTile());
    }
    
    public void placeTileBottomLeft()
    {
        Tile temp = tiles[0][1];
        tiles[0][1] = tiles[1][1];
        tiles[1][1] = tiles[2][1];
        tiles[2][1] = tiles[3][1];
        tiles[3][1] = tiles[4][1];
        tiles[4][1] = tiles[5][1];
        tiles[5][1] = tiles[6][1];
        tiles[6][1] = tileSet.getNextTile();
        tileSet.setNextTile(temp);
        
        gbController.setTiles(tiles, tileSet.getNextTile());
    }
    
    public void placeTileBottomCenter()
    {
        Tile temp = tiles[0][3];
        tiles[0][3] = tiles[1][3];
        tiles[1][3] = tiles[2][3];
        tiles[2][3] = tiles[3][3];
        tiles[3][3] = tiles[4][3];
        tiles[4][3] = tiles[5][3];
        tiles[5][3] = tiles[6][3];
        tiles[6][3] = tileSet.getNextTile();
        tileSet.setNextTile(temp);
        
        gbController.setTiles(tiles, tileSet.getNextTile());
    }
    
    public void placeTileBottomRight()
    {
        Tile temp = tiles[0][5];
        tiles[0][5] = tiles[1][5];
        tiles[1][5] = tiles[2][5];
        tiles[2][5] = tiles[3][5];
        tiles[3][5] = tiles[4][5];
        tiles[4][5] = tiles[5][5];
        tiles[5][5] = tiles[6][5];
        tiles[6][5] = tileSet.getNextTile();
        tileSet.setNextTile(temp);
        
        gbController.setTiles(tiles, tileSet.getNextTile());
    }
    
    public void placeTileRightTop()
    {
        Tile temp = tiles[1][0];
        tiles[1][0] = tiles[1][1];
        tiles[1][1] = tiles[1][2];
        tiles[1][2] = tiles[1][3];
        tiles[1][3] = tiles[1][4];
        tiles[1][4] = tiles[1][5];
        tiles[1][5] = tiles[1][6];
        tiles[1][6] = tileSet.getNextTile();
        tileSet.setNextTile(temp);
        
        gbController.setTiles(tiles, tileSet.getNextTile());
    }
    
    public void placeTileRightCenter()
    {
        Tile temp = tiles[3][0];
        tiles[3][0] = tiles[3][1];
        tiles[3][1] = tiles[3][2];
        tiles[3][2] = tiles[3][3];
        tiles[3][3] = tiles[3][4];
        tiles[3][4] = tiles[3][5];
        tiles[3][5] = tiles[3][6];
        tiles[3][6] = tileSet.getNextTile();
        tileSet.setNextTile(temp);
        
        gbController.setTiles(tiles, tileSet.getNextTile());
    }
    
    public void placeTileRightBottom()
    {
        Tile temp = tiles[5][0];
        tiles[5][0] = tiles[5][1];
        tiles[5][1] = tiles[5][2];
        tiles[5][2] = tiles[5][3];
        tiles[5][3] = tiles[5][4];
        tiles[5][4] = tiles[5][5];
        tiles[5][5] = tiles[5][6];
        tiles[5][6] = tileSet.getNextTile();
        tileSet.setNextTile(temp);
        
        gbController.setTiles(tiles, tileSet.getNextTile());
    }*/
    
    public void setupTiles(TileSet tileSet)
    {
        tiles = new Tile[7][7];
        tileSet = new TileSet();
        
        tiles[0][0] = new Tile(Tile.Shape.L, 90, 4); // Red
        tiles[0][1] = tileSet.getNextInitialTile(); // Random
        tiles[0][2] = new Tile(Tile.Shape.T, 0, new Treasure(Treasure.TreasureType.s24)); // Book
        tiles[0][3] = tileSet.getNextInitialTile(); // Random
        tiles[0][4] = new Tile(Tile.Shape.T, 0, new Treasure(Treasure.TreasureType.s18)); // Money
        tiles[0][5] = tileSet.getNextInitialTile(); // Random
        tiles[0][6] = new Tile(Tile.Shape.L, 180, 1); // Yellow
        
        tiles[1][0] = tileSet.getNextInitialTile(); // Random
        tiles[1][1] = tileSet.getNextInitialTile(); // Random
        tiles[1][2] = tileSet.getNextInitialTile(); // Random
        tiles[1][3] = tileSet.getNextInitialTile(); // Random
        tiles[1][4] = tileSet.getNextInitialTile(); // Random
        tiles[1][5] = tileSet.getNextInitialTile(); // Random
        tiles[1][6] = tileSet.getNextInitialTile(); // Random
        
        tiles[2][0] = new Tile(Tile.Shape.T, 270, new Treasure(Treasure.TreasureType.s6)); // Map
        tiles[2][1] = tileSet.getNextInitialTile(); // Random
        tiles[2][2] = new Tile(Tile.Shape.T, 270, new Treasure(Treasure.TreasureType.s9)); // Crown
        tiles[2][3] = tileSet.getNextInitialTile(); // Random
        tiles[2][4] = new Tile(Tile.Shape.T, 0, new Treasure(Treasure.TreasureType.s5)); // Keys
        tiles[2][5] = tileSet.getNextInitialTile(); // Random
        tiles[2][6] = new Tile(Tile.Shape.T, 90, new Treasure(Treasure.TreasureType.s17)); // Skull
        
        tiles[3][0] = tileSet.getNextInitialTile(); // Random
        tiles[3][1] = tileSet.getNextInitialTile(); // Random
        tiles[3][2] = tileSet.getNextInitialTile(); // Random
        tiles[3][3] = tileSet.getNextInitialTile(); // Random
        tiles[3][4] = tileSet.getNextInitialTile(); // Random
        tiles[3][5] = tileSet.getNextInitialTile(); // Random
        tiles[3][6] = tileSet.getNextInitialTile(); // Random
        
        tiles[4][0] = new Tile(Tile.Shape.T, 270, new Treasure(Treasure.TreasureType.s22)); // Ring
        tiles[4][1] = tileSet.getNextInitialTile(); // Random
        tiles[4][2] = new Tile(Tile.Shape.T, 180, new Treasure(Treasure.TreasureType.s21)); // Chest
        tiles[4][3] = tileSet.getNextInitialTile(); // Random
        tiles[4][4] = new Tile(Tile.Shape.T, 90, new Treasure(Treasure.TreasureType.s2)); // Emerald
        tiles[4][5] = tileSet.getNextInitialTile(); // Random
        tiles[4][6] = new Tile(Tile.Shape.T, 90, new Treasure(Treasure.TreasureType.s4)); // Sword
        
        tiles[5][0] = tileSet.getNextInitialTile(); // Random
        tiles[5][1] = tileSet.getNextInitialTile(); // Random
        tiles[5][2] = tileSet.getNextInitialTile(); // Random
        tiles[5][3] = tileSet.getNextInitialTile(); // Random
        tiles[5][4] = tileSet.getNextInitialTile(); // Random
        tiles[5][5] = tileSet.getNextInitialTile(); // Random
        tiles[5][6] = tileSet.getNextInitialTile(); // Random
        
        tiles[6][0] = new Tile(Tile.Shape.L, 0, 3); // Green
        tiles[6][1] = tileSet.getNextInitialTile(); // Random
        tiles[6][2] = new Tile(Tile.Shape.T, 180, new Treasure(Treasure.TreasureType.s15)); // Candelabra
        tiles[6][3] = tileSet.getNextInitialTile(); // Random
        tiles[6][4] = new Tile(Tile.Shape.T, 180, new Treasure(Treasure.TreasureType.s23)); // Helmet
        tiles[6][5] = tileSet.getNextInitialTile(); // Random
        tiles[6][6] = new Tile(Tile.Shape.L, 270, 2); // Blue
        
        for (int i = 0; i < 7; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                this.add(tiles[i][j], j+1, i+1);
            }
        }
    }
    
    private void createArrows()
    {
        // Top
         this.add(new InsertTileButton(0, InsertTileButton.Arrow.TopLeft), 2, 0);
         this.add(new InsertTileButton(0, InsertTileButton.Arrow.TopCenter), 4, 0);
         this.add(new InsertTileButton(0, InsertTileButton.Arrow.TopRight), 6, 0);
        // Left
         this.add(new InsertTileButton(270, InsertTileButton.Arrow.LeftTop), 0, 2);
         this.add(new InsertTileButton(270, InsertTileButton.Arrow.LeftCenter), 0, 4);
         this.add(new InsertTileButton(270, InsertTileButton.Arrow.LeftBottom), 0, 6);
        // Bottom
         this.add(new InsertTileButton(180, InsertTileButton.Arrow.BottomLeft), 2, 8);
         this.add(new InsertTileButton(180, InsertTileButton.Arrow.BottomCenter), 4, 8);
         this.add(new InsertTileButton(180, InsertTileButton.Arrow.BottomRight), 6, 8);
        // Right
         this.add(new InsertTileButton(90, InsertTileButton.Arrow.RightTop), 8, 2);
         this.add(new InsertTileButton(90, InsertTileButton.Arrow.RightCenter), 8, 4);
         this.add(new InsertTileButton(90, InsertTileButton.Arrow.RightBottom), 8, 6);
    }
}
