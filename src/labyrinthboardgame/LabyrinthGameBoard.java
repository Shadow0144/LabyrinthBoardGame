/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame;

import javafx.scene.layout.GridPane;

/**
 *
 * @author Corbi
 */
public class LabyrinthGameBoard extends GridPane
{
    private GameBoardController gbController;
    private Tile[][] tiles;
    private Tile nextTile;
    private TileSet tileSet;
    
    private InsertTileButton[] arrows;
    
    public LabyrinthGameBoard()
    {
        super();
        
        createArrows();
    }
    
    public void setGameBoardController(GameBoardController controller)
    {
        gbController = controller;
    }
    
    public void insertTile(InsertTileButton.Arrow arrow)
    {
        switch (arrow)
        {
            // Top
            case TopLeft:
                placeTileVertical(1, true);
                break;
            case TopCenter:
                placeTileVertical(3, true);
                break;
            case TopRight:
                placeTileVertical(5, true);
                break;
            // Left
            case LeftTop:
                placeTileHorizontal(1, true);
                break;
            case LeftCenter:
                placeTileHorizontal(3, true);
                break;
            case LeftBottom:
                placeTileHorizontal(5, true);
                break;
            // Bottom
            case BottomLeft:
                placeTileVertical(1, false);
                break;
            case BottomCenter:
                placeTileVertical(3, false);
                break;
            case BottomRight:
                placeTileVertical(5, false);
                break;
            // Right
            case RightTop:
                placeTileHorizontal(1, false);
                break;
            case RightCenter:
                placeTileHorizontal(3, false);
                break;
            case RightBottom:
                placeTileHorizontal(5, false);
                break;
        }
    }
    
    public void placeTileVertical(int column, boolean fromAbove)
    {
        int i = (fromAbove) ? 6 : 0;
        int j = column;
        int next = (fromAbove) ? -1 : +1;
        Tile temp = tiles[i][j];
        this.getChildren().remove(temp);
        for (int count = 0; count < 6; count++)
        {
            tiles[i][j] = tiles[i+next][j];
            this.getChildren().remove(tiles[i][j]);
            this.add(tiles[i][j], j+1, i+1);
            i += next;
        }
        tiles[i][j] = tileSet.getNextTile();
        this.add(tiles[i][j], j+1, i+1);
        nextTile = temp;
        tileSet.setNextTile(nextTile);
        
        gbController.updateNextTile();
    }
    
    public void placeTileHorizontal(int row, boolean fromLeft)
    {
        int i = row;
        int j = (fromLeft) ? 6 : 0;
        int next = (fromLeft) ? -1 : +1;
        Tile temp = tiles[i][j];
        this.getChildren().remove(temp);
        for (int count = 0; count < 6; count++)
        {
            tiles[i][j] = tiles[i][j+next];
            this.getChildren().remove(tiles[i][j]);
            this.add(tiles[i][j], j+1, i+1);
            j += next;
        }
        tiles[i][j] = tileSet.getNextTile();
        this.add(tiles[i][j], j+1, i+1);
        nextTile = temp;
        tileSet.setNextTile(nextTile);
        
        gbController.updateNextTile();
    }
    
    public void placeTileLeftTop()
    {
        /*Tile temp = tiles[1][6];
        tiles[1][6] = tiles[1][5];
        tiles[1][5] = tiles[1][4];
        tiles[1][4] = tiles[1][3];
        tiles[1][3] = tiles[1][2];
        tiles[1][2] = tiles[1][1];
        tiles[1][1] = tiles[1][0];
        tiles[1][0] = tileSet.getNextTile();
        tileSet.setNextTile(temp);
        
        gbController.setTiles(tiles, tileSet.getNextTile());*/
    }
    
    public void placeTileLeftCenter()
    {
        /*Tile temp = tiles[3][6];
        tiles[3][6] = tiles[3][5];
        tiles[3][5] = tiles[3][4];
        tiles[3][4] = tiles[3][3];
        tiles[3][3] = tiles[3][2];
        tiles[3][2] = tiles[3][1];
        tiles[3][1] = tiles[3][0];
        tiles[3][0] = tileSet.getNextTile();
        tileSet.setNextTile(temp);
        
        gbController.setTiles(tiles, tileSet.getNextTile());*/
    }
    
    public void placeTileLeftBottom()
    {
        /*Tile temp = tiles[5][6];
        tiles[5][6] = tiles[5][5];
        tiles[5][5] = tiles[5][4];
        tiles[5][4] = tiles[5][3];
        tiles[5][3] = tiles[5][2];
        tiles[5][2] = tiles[5][1];
        tiles[5][1] = tiles[5][0];
        tiles[5][0] = tileSet.getNextTile();
        tileSet.setNextTile(temp);
        
        gbController.setTiles(tiles, tileSet.getNextTile());*/
    }
    
    public void placeTileBottomLeft()
    {
        /*Tile temp = tiles[0][1];
        tiles[0][1] = tiles[1][1];
        tiles[1][1] = tiles[2][1];
        tiles[2][1] = tiles[3][1];
        tiles[3][1] = tiles[4][1];
        tiles[4][1] = tiles[5][1];
        tiles[5][1] = tiles[6][1];
        tiles[6][1] = tileSet.getNextTile();
        tileSet.setNextTile(temp);
        
        gbController.setTiles(tiles, tileSet.getNextTile());*/
    }
    
    public void placeTileBottomCenter()
    {
        /*Tile temp = tiles[0][3];
        tiles[0][3] = tiles[1][3];
        tiles[1][3] = tiles[2][3];
        tiles[2][3] = tiles[3][3];
        tiles[3][3] = tiles[4][3];
        tiles[4][3] = tiles[5][3];
        tiles[5][3] = tiles[6][3];
        tiles[6][3] = tileSet.getNextTile();
        tileSet.setNextTile(temp);
        
        gbController.setTiles(tiles, tileSet.getNextTile());*/
    }
    
    public void placeTileBottomRight()
    {
        /*Tile temp = tiles[0][5];
        tiles[0][5] = tiles[1][5];
        tiles[1][5] = tiles[2][5];
        tiles[2][5] = tiles[3][5];
        tiles[3][5] = tiles[4][5];
        tiles[4][5] = tiles[5][5];
        tiles[5][5] = tiles[6][5];
        tiles[6][5] = tileSet.getNextTile();
        tileSet.setNextTile(temp);
        
        gbController.setTiles(tiles, tileSet.getNextTile());*/
    }
    
    public void placeTileRightTop()
    {
        /*Tile temp = tiles[1][0];
        tiles[1][0] = tiles[1][1];
        tiles[1][1] = tiles[1][2];
        tiles[1][2] = tiles[1][3];
        tiles[1][3] = tiles[1][4];
        tiles[1][4] = tiles[1][5];
        tiles[1][5] = tiles[1][6];
        tiles[1][6] = tileSet.getNextTile();
        tileSet.setNextTile(temp);
        
        gbController.setTiles(tiles, tileSet.getNextTile());*/
    }
    
    public void placeTileRightCenter()
    {
        /*Tile temp = tiles[3][0];
        tiles[3][0] = tiles[3][1];
        tiles[3][1] = tiles[3][2];
        tiles[3][2] = tiles[3][3];
        tiles[3][3] = tiles[3][4];
        tiles[3][4] = tiles[3][5];
        tiles[3][5] = tiles[3][6];
        tiles[3][6] = tileSet.getNextTile();
        tileSet.setNextTile(temp);
        
        gbController.setTiles(tiles, tileSet.getNextTile());*/
    }
    
    public void placeTileRightBottom()
    {
        /*Tile temp = tiles[5][0];
        tiles[5][0] = tiles[5][1];
        tiles[5][1] = tiles[5][2];
        tiles[5][2] = tiles[5][3];
        tiles[5][3] = tiles[5][4];
        tiles[5][4] = tiles[5][5];
        tiles[5][5] = tiles[5][6];
        tiles[5][6] = tileSet.getNextTile();
        tileSet.setNextTile(temp);
        
        gbController.setTiles(tiles, tileSet.getNextTile());*/
    }
    
    public Tile getNextTile()
    {
        return new Tile(nextTile);
    }
    
    public void rotatePreviewClockwise()
    {
        nextTile.rotateClockwise();
        for (int i = 0; i < 12; i++)
        {
            arrows[i].rotatePreviewClockwise();
        }
    }
    
    public void rotatePreviewCounterClockwise()
    {
        nextTile.rotateCounterClockwise();
        for (int i = 0; i < 12; i++)
        {
            arrows[i].rotatePreviewCounterClockwise();
        }
    }
    
    public void setupTiles(TileSet tileSet)
    {
        tiles = new Tile[7][7];
        //tileSet = new TileSet();
        this.tileSet = tileSet;
        
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
        
        nextTile = tileSet.getNextTile();
    }
    
    private void createArrows()
    {
        arrows = new InsertTileButton[12];
        
        // Top
        arrows[0] = new InsertTileButton(this, 0, InsertTileButton.Arrow.TopLeft);
        this.add(arrows[0], 2, 0);
        arrows[1] = new InsertTileButton(this, 0, InsertTileButton.Arrow.TopCenter);
        this.add(arrows[1], 4, 0);
        arrows[2] = new InsertTileButton(this, 0, InsertTileButton.Arrow.TopRight);
        this.add(arrows[2], 6, 0);
        // Left
        arrows[3] = new InsertTileButton(this, 270, InsertTileButton.Arrow.LeftTop);
        this.add(arrows[3], 0, 2);
        arrows[4] = new InsertTileButton(this, 270, InsertTileButton.Arrow.LeftCenter);
        this.add(arrows[4], 0, 4);
        arrows[5] = new InsertTileButton(this, 270, InsertTileButton.Arrow.LeftBottom);
        this.add(arrows[5], 0, 6);
        // Bottom
        arrows[6] = new InsertTileButton(this, 180, InsertTileButton.Arrow.BottomLeft);
        this.add(arrows[6], 2, 8);
        arrows[7] = new InsertTileButton(this, 180, InsertTileButton.Arrow.BottomCenter);
        this.add(arrows[7], 4, 8);
        arrows[8] = new InsertTileButton(this, 180, InsertTileButton.Arrow.BottomRight);
        this.add(arrows[8], 6, 8);
        // Right
        arrows[9] = new InsertTileButton(this, 90, InsertTileButton.Arrow.RightTop);
        this.add(arrows[9], 8, 2);
        arrows[10] = new InsertTileButton(this, 90, InsertTileButton.Arrow.RightCenter);
        this.add(arrows[10], 8, 4);
        arrows[11] = new InsertTileButton(this, 90, InsertTileButton.Arrow.RightBottom);
        this.add(arrows[11], 8, 6);
    }
}
