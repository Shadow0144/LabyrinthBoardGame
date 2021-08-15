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
public final class Tile
{
    public enum Shape {
        I,
        L,
        T
    };
    private final Tile.Shape tileShape;
    private int currentRotation;
    
    private final Treasure tileTreasure;
    private final int playerStartNumber;
    
    private boolean[] possibleNeighbors;
    private Tile[] connectedNeighbors;
    private boolean accessible;
    private boolean buildingPath;
    
    private boolean isTreasureShown;
    
    private int row;
    private int col;
    
    public enum Type {
        None,
        Treasure,
        Start
    };
    private final Tile.Type type;
    
    private final boolean[] players;
    
    /**
     * Creates a tile of a particular shape and rotation, but no treasure
     * or starting area
     * @param shape The shape of the tile (i.e. I, L, or T)
     * @param rotation The rotation of the tile
     */
    public Tile(Shape shape, int rotation)
    {
        tileShape = shape;
        currentRotation = rotation;
        type = Type.None;
        setupTile();
        tileTreasure = null;
        isTreasureShown = false;
        playerStartNumber = -1;
        players = new boolean[4];
        for (int i = 0; i < 4; i++)
        {
            players[i] = false;
        }
        setupOverlays();
    }
    
    /**
     * Creates a tile of a particular shape and rotation with a player's
     * starting location
     * @param shape The shape of the tile (i.e. I, L, or T)
     * @param rotation The rotation of the tile
     * @param player The number of the player which starts here
     */
    public Tile(Shape shape, int rotation, int player)
    {
        tileShape = shape;
        currentRotation = rotation;
        type = Type.Start;
        setupTile();
        
        tileTreasure = null;
        isTreasureShown = false;
        this.playerStartNumber = player;
        
        players = new boolean[4];
        for (int i = 0; i < 4; i++)
        {
            players[i] = false;
        }
        
        setupOverlays();
    }
    
    /**
     * Creates a tile of a particular shape and rotation with a treasure
     * @param shape The shape of the tile (i.e. I, L, or T)
     * @param rotation The rotation of the tile
     * @param treasure The treasure on this tile
     */
    public Tile(Shape shape, int rotation, Treasure treasure)
    {
        tileShape = shape;
        currentRotation = rotation;
        type = Type.Treasure;
        setupTile();
        
        playerStartNumber = -1;
        tileTreasure = treasure;
        isTreasureShown = false;
        
        players = new boolean[4];
        for (int i = 0; i < 4; i++)
        {
            players[i] = false;
        }
        
        setupOverlays();
    }
    
    public Tile(Tile copy)
    {
        tileShape = copy.tileShape;
        currentRotation = copy.currentRotation;
        type = copy.type;
        accessible = false;
        row = copy.row;
        col = copy.col;
        
        playerStartNumber = copy.playerStartNumber;
        tileTreasure = copy.tileTreasure;
        isTreasureShown = copy.isTreasureShown;
        
        players = new boolean[4];
        System.arraycopy(copy.players, 0, players, 0, 4);
        
        setupOverlays();
    }
    
    /**
     * Returns the type of the tile
     * @return The type of the tile
     */
    public Type getType()
    {
        return type;
    }
    
    /**
     * Sets up the graphics for this tile
     */
    private void setupTile()
    {
        row = -1;
        col = -1;
        accessible = false;
    }
    
    /**
     * Sets up the logic for displaying paths
     */
    private void setupOverlays()
    {
        buildingPath = false;
        connectedNeighbors = new Tile[4];
        for (int i = 0; i < 4; i++)
        {
            connectedNeighbors[i] = null;
        }
        
        possibleNeighbors = new boolean[4];
        refreshPossibleNeighbors();
    }
    
    /**
     * Returns If the treasure is shown on this tile
     * @return If the treasure is shown on this tile
     */
    public boolean getIsTreasureShown()
    {
        return isTreasureShown;
    }
    
    /**
     * Enables the treasure on this tile for viewing
     */
    public void showTreasure()
    {
        isTreasureShown = true;
    }
    
    /**
     * Disables the treasure on this tile for viewing
     */
    public void hideTreasure()
    {
        isTreasureShown = false;
    }
    
    /**
     * Sets the tile's rotation, updating the graphics and neighbors
     * @param rotation The tile's new rotation
     */
    public void setRotation(int rotation)
    {
        currentRotation = rotation;
        refreshPossibleNeighbors();
    }
    
    /**
     * Returns the tile's current rotation
     * @return The tile's current rotation
     */
    public int getRotation()
    {
        return currentRotation;
    }
    
    /**
     * Returns the tile's shape
     * @return The tile's shape
     */
    public Shape getTileShape()
    {
        return tileShape;
    }
    
    /**
     * Returns the player's number if this tile has a starting location
     * @return The starting player's number
     */
    public int getPlayer()
    {
        return playerStartNumber;
    }
    
    /**
     * Returns the treasure on this tile if there is any
     * @return The treasure on this tile
     */
    public Treasure getTreasure()
    {
        return tileTreasure;
    }
    
    /**
     * Rotates the tile and updates the graphics and possible neighbors
     */
    public void rotateClockwise()
    {
        currentRotation = (360 + currentRotation + 90) % 360;
        refreshPossibleNeighbors();
    }
    
    /**
     * Rotates the tile and updates the graphics and possible neighbors
     */
    public void rotateCounterClockwise()
    {
        currentRotation = (360 + currentRotation - 90) % 360;
        refreshPossibleNeighbors();
    }
    
    /**
     * Links neighboring tiles with a viable path
     * @param top The tile above this one
     * @param right The tile to the right of this one
     * @param bottom The tile below this one
     * @param left  The tile to the left of this one
     */
    public void updateConnectedNeighbors(Tile top, Tile right, Tile bottom, Tile left)
    {
        hidePaths();
        if (top != null && (top.possibleNeighbors[2] && possibleNeighbors[0]))
        {
            connectedNeighbors[0] = top;
        }
        else 
        {
            connectedNeighbors[0] = null;
        }
        if (right != null && (right.possibleNeighbors[3] && possibleNeighbors[1]))
        {
            connectedNeighbors[1] = right;
        }
        else 
        {
            connectedNeighbors[1] = null;
        }
        if (bottom != null && (bottom.possibleNeighbors[0] && possibleNeighbors[2]))
        {
            connectedNeighbors[2] = bottom;
        }
        else 
        {
            connectedNeighbors[2] = null;
        }
        if (left != null && (left.possibleNeighbors[1] && possibleNeighbors[3]))
        {
            connectedNeighbors[3] = left;
        }
        else 
        {
            connectedNeighbors[3] = null;
        }
    }
    
    /**
     * Displays viable paths which begin at this tile
     * @param player The player (and therefore color) of the path
     */
    public void showPaths(int player)
    {
        if (!buildingPath) // Prevent infinite looping
        {
            buildingPath = true;
            accessible = true;
            for (int i = 0; i < 4; i++)
            {
                if (connectedNeighbors[i] != null)
                {
                    connectedNeighbors[i].showPaths(player);
                }
                else { }
            }
            buildingPath = false;
        }
    }
    
    /**
     * Disables drawing paths from this tile
     */
    public void hidePaths()
    {
        if (!buildingPath) // Prevent infinite looping
        {
            buildingPath = true;
            accessible = false;
            
            for (int i = 0; i < 4; i++)
            {
                if (connectedNeighbors[i] != null)
                {
                    connectedNeighbors[i].hidePaths();
                }
                else {}
            }
            buildingPath = false;
        }
    }
    
    /**
     * Disables the tile so that players can't move onto it
     */
    public void disable()
    {
        accessible = false;
    }
    
    /**
     * Returns if a tile is reachable after building paths
     * @return If the tile is accessible to the current player
     */
    public boolean getAccessible()
    {
        return accessible;
    }
    
    /**
     * Updates possible paths from this tile based on its shape and rotation
     */
    private void refreshPossibleNeighbors()
    {
        // Clockwise from Top
        // Top, Right, Bottom, Left
        switch (tileShape)
        {
            case I:
                switch (currentRotation)
                {
                    case 0:
                    case 180:
                        possibleNeighbors[0] = true;
                        possibleNeighbors[1] = false;
                        possibleNeighbors[2] = true;
                        possibleNeighbors[3] = false;
                        break;
                    case 90:
                    case 270:
                        possibleNeighbors[0] = false;
                        possibleNeighbors[1] = true;
                        possibleNeighbors[2] = false;
                        possibleNeighbors[3] = true;
                        break;
                }
                break;
            case L:
                switch (currentRotation)
                {
                    case 0:
                        possibleNeighbors[0] = true;
                        possibleNeighbors[1] = true;
                        possibleNeighbors[2] = false;
                        possibleNeighbors[3] = false;
                        break;
                    case 90:
                        possibleNeighbors[0] = false;
                        possibleNeighbors[1] = true;
                        possibleNeighbors[2] = true;
                        possibleNeighbors[3] = false;
                        break;
                    case 180:
                        possibleNeighbors[0] = false;
                        possibleNeighbors[1] = false;
                        possibleNeighbors[2] = true;
                        possibleNeighbors[3] = true;
                        break;
                    case 270:
                        possibleNeighbors[0] = true;
                        possibleNeighbors[1] = false;
                        possibleNeighbors[2] = false;
                        possibleNeighbors[3] = true;
                        break;
                }
                break;
            case T:
                switch (currentRotation)
                {
                    case 0:
                        possibleNeighbors[0] = false;
                        possibleNeighbors[1] = true;
                        possibleNeighbors[2] = true;
                        possibleNeighbors[3] = true;
                        break;
                    case 90:
                        possibleNeighbors[0] = true;
                        possibleNeighbors[1] = false;
                        possibleNeighbors[2] = true;
                        possibleNeighbors[3] = true;
                        break;
                    case 180:
                        possibleNeighbors[0] = true;
                        possibleNeighbors[1] = true;
                        possibleNeighbors[2] = false;
                        possibleNeighbors[3] = true;
                        break;
                    case 270:
                        possibleNeighbors[0] = true;
                        possibleNeighbors[1] = true;
                        possibleNeighbors[2] = true;
                        possibleNeighbors[3] = false;
                        break;
                }
                break;
        }
    }
    
    /**
     * Updates the row and column of the tile
     * @param newRow The new row of the tile
     * @param newCol The new column of the tile
     */
    public void setRowAndCol(int newRow, int newCol)
    {
        row = newRow;
        col = newCol;
    }
    
    /**
     * Returns the row of the tile
     * @return The row of the tile
     */
    public int getRow()
    {
        return row;
    }
    
    /**
     * Returns the column of the tile
     * @return The column of the tile
     */
    public int getCol()
    {
        return col;
    }
    
    /**
     * Adds a player character to the connected tile view
     * @param connector Connects the logic and GUI packages
     * @param playerIndex The index of the player character to add
     */
    public void addPlayerCharacter(GUIConnector connector, int playerIndex)
    {
        players[playerIndex] = true;
        connector.addPlayerCharacter(row, col, playerIndex);
    }
    
    /**
     * Removes a player character from the connected tile view
     * @param connector Connects the logic and GUI packages
     * @param playerIndex The index of the player character to remove
     */
    public void removePlayerCharacter(GUIConnector connector, int playerIndex)
    {
        players[playerIndex] = false;
        connector.removePlayerCharacter(playerIndex);
    }
    
    /**
     * Returns a list of the players on this tile
     * @return A list of the players on this tile
     */
    public boolean[] getPlayers()
    {
        return players;
    }
    
    /**
     * Moves the players on one tile to this tile when one slides off the board
     * @param moveTile The tile to move the players from which slide off the board
     */
    public void movePlayers(Tile moveTile)
    {
        System.arraycopy(moveTile.getPlayers(), 0, players, 0, 4);
        for (int i = 0; i < 4; i++)
        {
            moveTile.getPlayers()[i] = false;
        }
    }
}
