/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.logic;

import labyrinthboardgame.gui.BoardView;
import labyrinthboardgame.gui.InsertTileButton;
import labyrinthboardgame.gui.PlayerIconTray;

/**
 *
 * @author Corbi
 */
public final class Board 
{
    private final BoardView boardView;
    private final PlayerIconTray playerIconTray;
    
    private Tile[][] tiles;
    private Tile nextTile;
    private final TileSet tileSet;
    
    public Board(Game game,
            TileSet tileSet, 
            BoardView boardView, 
            PlayerIconTray playerIconTray)
    {
        this.tileSet = tileSet;
        this.boardView = boardView;
        this.playerIconTray = playerIconTray;
        setupTiles(game);
    }
    
    /**
     * Fill the board with tiles
     * @param game A reference to the game
     */
    public void setupTiles(Game game)
    {                
        tiles = new Tile[7][7];
        
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
        
        // Add listeners for moving the players
        for (int i = 0; i < 7; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                boardView.add(tiles[i][j].getTileView(), j+1, i+1);
                tiles[i][j].setListener(game);
            }
        }
        
        nextTile = tileSet.getNextTile();
        nextTile.setListener(game); // Add a listener to the last remaining tile as well
        playerIconTray.updateNextTile(nextTile);
        updateTileNeighbors(); // Update the paths between tiles
    }
    
    public Tile getStartingTile(int playerNumber)
    {
        Tile r = null;
        switch (playerNumber)
        {
            case 1:
                r = tiles[0][6];
                break;
            case 2:
                r = tiles[6][6];
                break;
            case 3:
                r = tiles[6][0];
                break;
            case 4:
                r = tiles[0][0];
                break;
        }
        return r;
    }
    
    public void enableArrows(boolean human)
    {
        boardView.enableArrows(human);
    }
    
    /**
     * Inserts the next tile into the game board, moving all tiles in that line, and updating the new next tile
     * @param position Where and which direction to insert the next tile
     */
    public void insertTile(InsertTileButton.ArrowPosition position)
    {
        int disabledArrow = -1;
        switch (position)
        {
            // Top
            case TopLeft:
                placeTileVertical(1, true);
                disabledArrow = 6;
                break;
            case TopCenter:
                placeTileVertical(3, true);
                disabledArrow = 7;
                break;
            case TopRight:
                placeTileVertical(5, true);
                disabledArrow = 8;
                break;
            // Left
            case LeftTop:
                placeTileHorizontal(1, true);
                disabledArrow = 9;
                break;
            case LeftCenter:
                placeTileHorizontal(3, true);
                disabledArrow = 10;
                break;
            case LeftBottom:
                placeTileHorizontal(5, true);
                disabledArrow = 11;
                break;
            // Bottom
            case BottomLeft:
                placeTileVertical(1, false);
                disabledArrow = 0;
                break;
            case BottomCenter:
                placeTileVertical(3, false);
                disabledArrow = 1;
                break;
            case BottomRight:
                placeTileVertical(5, false);
                disabledArrow = 2;
                break;
            // Right
            case RightTop:
                placeTileHorizontal(1, false);
                disabledArrow = 3;
                break;
            case RightCenter:
                placeTileHorizontal(3, false);
                disabledArrow = 4;
                break;
            case RightBottom:
                placeTileHorizontal(5, false);
                disabledArrow = 5;
                break;
        }
        updateTileNeighbors(); // Refresh the paths between tiles
        
        // Disable all the arrows and move into the player move phase
        boardView.disableArrows(disabledArrow);
        playerIconTray.updateNextTile(nextTile);
    }
    
    /**
     * Places the next tile along a column, moving all the other tiles up or down
     * @param column The column to insert the new tile into
     * @param fromAbove If true, the tiles all move down; else, up
     */
    public void placeTileVertical(int column, boolean fromAbove)
    {
        int i = (fromAbove) ? 6 : 0;
        int j = column;
        int next = (fromAbove) ? -1 : +1;
        Tile temp = tiles[i][j]; // The tile to remove
        boardView.getChildren().remove(temp.getTileView());
        for (int count = 0; count < 6; count++)
        {
            tiles[i][j] = tiles[i+next][j];
            boardView.getChildren().remove(tiles[i][j].getTileView());
            boardView.add(tiles[i][j].getTileView(), j+1, i+1);
            i += next;
        }
        Tile newTile = tileSet.getNextTile();
        temp.movePlayers(newTile); // Move any players off the old tile and onto the new one
        tiles[i][j] = newTile;
        boardView.add(tiles[i][j].getTileView(), j+1, i+1);
        tileSet.setNextTile(temp); // The new next tile
        nextTile = temp; // Update the local reference
    }
    
    /**
     * Places the next tile along a row, moving all the other tiles left or right
     * @param row The row to insert the new tile into
     * @param fromLeft If true, the tiles all move right; else, left
     */
    public void placeTileHorizontal(int row, boolean fromLeft)
    {
        int i = row;
        int j = (fromLeft) ? 6 : 0;
        int next = (fromLeft) ? -1 : +1;
        Tile temp = tiles[i][j]; // The tile to remove
        boardView.getChildren().remove(temp.getTileView());
        for (int count = 0; count < 6; count++)
        {
            tiles[i][j] = tiles[i][j+next];
            boardView.getChildren().remove(tiles[i][j].getTileView());
            boardView.add(tiles[i][j].getTileView(), j+1, i+1);
            j += next;
        }
        Tile newTile = tileSet.getNextTile();
        temp.movePlayers(newTile); // Move any players off the old tile and onto the new one
        tiles[i][j] = newTile;
        boardView.add(tiles[i][j].getTileView(), j+1, i+1);
        tileSet.setNextTile(temp); // The new next tile
        nextTile = temp; // Update the local reference
    }
    
    /**
     * Updates the paths between tiles
     */
    private void updateTileNeighbors()
    {
        for (int i = 0; i < 7; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                Tile top = (i > 0) ? tiles[i-1][j] : null;
                Tile right = (j < 6) ? tiles[i][j+1] : null;
                Tile bottom = (i < 6) ? tiles[i+1][j] : null;
                Tile left = (j > 0) ? tiles[i][j-1] : null;
                tiles[i][j].updateConnectedNeighbors(top, right, bottom, left);
            }
        }
    }
    
    public Tile getTile(int i, int j)
    {
        return tiles[i][j];
    }
    
    public int findTileRow(Tile tile)
    {
        int r = -1;
        
        for (int i = 0; i < 7; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                if (tile == tiles[i][j])
                {
                    return i; // Return early
                }
                else {}
            }
        }
        
        return r;
    }
    
    public int findTileCol(Tile tile)
    {
        int r = -1;
        
        for (int i = 0; i < 7; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                if (tile == tiles[i][j])
                {
                    return j; // Return early
                }
                else {}
            }
        }
        
        return r;
    }
}
