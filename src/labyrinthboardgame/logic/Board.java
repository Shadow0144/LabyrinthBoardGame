/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.logic;

import java.util.LinkedList;

/**
 *
 * @author Corbi
 */
public final class Board implements Cloneable
{
    // Position of the arrow
    public enum ArrowPosition
    {
        TopLeft, TopCenter, TopRight,
        LeftTop, LeftCenter, LeftBottom,
        BottomLeft, BottomCenter, BottomRight,
        RightTop, RightCenter, RightBottom,
    };
    
    private Tile[][] tiles;
    private Tile nextTile;
    private final TileSet tileSet;
    
    private int disabledArrow;
    
    private final boolean visible;
    
    private final GUIConnector connector;
    
    private Game game;
    
    public Board(Game game, TileSet tileSet, GUIConnector connector)
    {
        this.connector = connector;
        this.visible = true;
        this.tileSet = tileSet;
        this.disabledArrow = -1;
        setupTiles(game);
    }
    
    public Board(Board copy, GUIConnector connector)
    {
        this.connector = connector;
        this.visible = false;
        this.tileSet = new TileSet(copy.tileSet);
        this.disabledArrow = copy.disabledArrow;
        setupTiles(copy);
    }
    
    /**
     * Fill the board with tiles
     * @param game A reference to the game
     */
    public void setupTiles(Game game)
    {           
        this.game = game;
        
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
                // Add tiles to view
                connector.addTileView(i+1, j+1, tiles[i][j], game);
                tiles[i][j].setRowAndCol(i, j);
            }
        }
        
        nextTile = tileSet.getNextTile();
        connector.updateTrayNextTile(game, nextTile);
        updateTileNeighbors(); // Update the paths between tiles
    }
    
    /**
     * Sets up the tiles during copy construction
     * @param copy The board to copy from
     */
    private void setupTiles(Board copy)
    {                
        tiles = new Tile[7][7];
        
        for (int i = 0; i < 7; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                tiles[i][j] = new Tile(copy.tiles[i][j]);
            }
        }
        
        nextTile = copy.nextTile;
        updateTileNeighbors(); // Update the paths between tiles
    }
    
    /**
     * Get the starting tile for a player
     * @param playerNumber The player's number
     * @return The starting tile for that player
     */
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
    
    /**
     * Returns the tile containing the current treasure
     * @param currentTreasure The current treasure of the current player
     * @return The tile containing the current treasure
     */
    public Tile getTreasureTile(Treasure currentTreasure)
    {
        Tile rTile = null;
        
        if (currentTreasure != null)
        {
            boolean found = false;
            for (int i = 0; i < 7; i++)
            {
                if (!found)
                {
                    for (int j = 0; j < 7; j++)
                    {
                        Treasure tileTreasure = tiles[i][j].getTreasure();
                        if (tileTreasure != null &&
                                tileTreasure.getTreasureType() == currentTreasure.getTreasureType())
                        {
                            rTile = tiles[i][j];
                            found = true;
                            break;
                        }
                        else {}
                    }
                }
                else {}
            }
            if (rTile == null)
            {
                Treasure tileTreasure = nextTile.getTreasure();
                if (tileTreasure != null &&
                        tileTreasure.getTreasureType() == currentTreasure.getTreasureType())
                {
                    rTile = nextTile;
                }
                else {}
            }
            else {}
        }
        else {}
        
        return rTile;
    }
    
    /**
     * Enables the arrow buttons on the board
     * @param human If the current player is human or not
     */
    public void enableArrows(boolean human)
    {
        connector.enableArrows(human);
    }
    
    /**
     * Returns if the arrow button is unavailable
     * @param arrowPosition The arrow button to check
     * @return If the arrow button is unavailable
     */
    public boolean isInsertAvailable(ArrowPosition arrowPosition)
    {
        return (arrowPosition.ordinal() != disabledArrow);
    }
    
    /**
     * Returns if the arrow button is unavailable
     * @param arrowPosition The arrow button to check
     * @return If the arrow button is unavailable
     */
    public boolean isInsertAvailable(int arrowPosition)
    {
        return (arrowPosition != disabledArrow);
    }
    
    /**
     * Returns a list of tiles who have been flagged as accessible
     * @return All accessible tiles
     */
    public LinkedList<Tile> getAccessibleTiles()
    {
        LinkedList<Tile> tileList = new LinkedList<>();
        
        for (int i = 0; i < 7; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                if (tiles[i][j].getAccessible())
                {
                    tileList.add(tiles[i][j]);
                }
            }
        }
        
        return tileList;
    }
    
    /**
     * Inserts the next tile into the game board, moving all tiles in that line, and updating the new next tile
     * @param position Where and which direction to insert the next tile
     */
    public void insertTile(ArrowPosition position)
    {
        disabledArrow = -1;
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
        updateTileNeighbors(); 
        
        // Disable all the arrows and move into the player move phase
        connector.disableArrows(disabledArrow);
        connector.updateTrayNextTile(game, nextTile);
        connector.showPaths();
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
        boolean[] players = temp.getPlayers();
        connector.removePlayerCharacters(temp);
        for (int count = 0; count < 6; count++)
        {
            tiles[i][j] = tiles[i+next][j];
            tiles[i][j].setRowAndCol(i, j);
            if (visible)
            {
                connector.removeTileView(i+1, j+1);
                connector.addTileView(i+1, j+1, tiles[i][j], game);
                if (fromAbove)
                {
                    connector.animateTileDown(tiles[i][j]);
                }
                else
                {
                    connector.animateTileUp(tiles[i][j]);
                }
            }
            else {}
            i += next;
        }
        Tile newTile = tileSet.getNextTile();
        tiles[i][j] = newTile;
        newTile.setRowAndCol(i, j);
        connector.addPlayerCharacters(newTile, players); // Move any players off the old tile and onto the new one
        if (visible)
        {
            connector.removeTileView(i+1, j+1);
            connector.addTileView(i+1, j+1, tiles[i][j], game);
            if (fromAbove)
            {
                connector.animateTileDown(tiles[i][j]);
            }
            else
            {
                connector.animateTileUp(tiles[i][j]);
            }
        }
        else {}
        tileSet.setNextTile(temp); // The new next tile
        nextTile = temp; // Update the local reference
        nextTile.setRowAndCol(-1, -1);
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
        boolean[] players = temp.getPlayers();
        connector.removePlayerCharacters(temp);
        for (int count = 0; count < 6; count++)
        {
            tiles[i][j] = tiles[i][j+next];
            tiles[i][j].setRowAndCol(i, j);
            if (visible)
            {
                connector.removeTileView(i+1, j+1);
                connector.addTileView(i+1, j+1, tiles[i][j], game);
                if (fromLeft)
                {
                    connector.animateTileRight(tiles[i][j]);
                }
                else
                {
                    connector.animateTileLeft(tiles[i][j]);
                }
            }
            else {}
            j += next;
        }
        Tile newTile = tileSet.getNextTile();
        tiles[i][j] = newTile;
        newTile.setRowAndCol(i, j);
        connector.addPlayerCharacters(newTile, players); // Move any players off the old tile and onto the new one
        if (visible)
        {
            connector.removeTileView(i+1, j+1);
            connector.addTileView(i+1, j+1, tiles[i][j], game);
            if (fromLeft)
            {
                connector.animateTileRight(tiles[i][j]);
            }
            else
            {
                connector.animateTileLeft(tiles[i][j]);
            }
        }
        else {}
        tileSet.setNextTile(temp); // The new next tile
        nextTile = temp; // Update the local reference
        nextTile.setRowAndCol(-1, -1);
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
    
    /**
     * Returns the tile set used
     * @return The tile set used
     */
    public TileSet getTileSet()
    {
        return tileSet;
    }
    
    /**
     * Returns a tile at position (i, j)
     * @param i The row of the tile
     * @param j The column of the tile
     * @return The tile at position (i, j)
     */
    public Tile getTile(int i, int j)
    {
        return tiles[i][j];
    }
    
    /**
     * Finds the row of a tile in the board
     * @param tile The tile to locate
     * @return The row of the tile in the board
     */
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
    
    /**
     * Finds the column of a tile in the board
     * @param tile The tile to locate
     * @return The column of the tile in the board
     */
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
    
    /**
     * Disables the tiles after a player takes their turn so that the next one
     * cannot move without placing a tile
     */
    public void disableTiles()
    {
        for (int i = 0; i < 7; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                tiles[i][j].disable();
            }
        }
    }
    
    /**
     * Shows the treasures available in the game
     * @param players The players playing this game
     */
    public void showTreasures(Player[] players)
    {
        for (int p = 0; p < 4; p++)
        {
            LinkedList<Treasure> treasures = players[p].getTreasures();
            for (Treasure treasure : treasures)
            {
                for (int i = 0; i < 7; i++)
                {
                    for (int j = 0; j < 7; j++)
                    {
                        Treasure tileTreasure = tiles[i][j].getTreasure();
                        if (tileTreasure != null && tileTreasure.getTreasureType() == treasure.getTreasureType())
                        {
                            connector.showTreasure(tiles[i][j]);
                            break;
                        }
                        else {}
                    }
                }
                Treasure tileTreasure = nextTile.getTreasure();
                if (tileTreasure != null && tileTreasure.getTreasureType() == treasure.getTreasureType())
                {
                    connector.showTreasure(nextTile);
                }
                else {}
            }
        }
    }
}
