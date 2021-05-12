/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.logic;

import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import labyrinthboardgame.gui.BoardView;
import labyrinthboardgame.gui.PlayerCharacter;
import labyrinthboardgame.gui.TileView;

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
    
    private Treasure tileTreasure;
    private int playerStartNumber;
    
    private boolean[] possibleNeighbors;
    private Tile[] connectedNeighbors;
    private boolean accessible;
    private boolean buildingPath;
    
    private TileView tileView;
    
    /**
     * Copy constructor, used for creating a copy to display such as in previews
     * @param tile The tile to copy
     */
    public Tile(Tile tile)
    {
        super();
        tileShape = tile.getTileShape();
        currentRotation = tile.getRotation();
        setupTile();
        if (tile.getTreasure() != null) // Has treasure
        {
            playerStartNumber = -1;
            tileTreasure = tile.getTreasure();
            if (tileTreasure != null)
            {
                tileView.addTreasure(tileTreasure.getTreasureImageName());
            }
            else {}
        }
        else if (tile.getPlayer() != -1) // Hsa a player start
        {
            tileTreasure = null;
            playerStartNumber = tile.getPlayer();
            switch (playerStartNumber)
            {
                case 1:
                    tileView.addPlayerStart(Color.YELLOW);
                    break;
                case 2:
                    tileView.addPlayerStart(Color.BLUE);
                    break;
                case 3:
                    tileView.addPlayerStart(Color.GREEN);
                    break;
                case 4:
                    tileView.addPlayerStart(Color.RED);
                    break;
            }
        }
        else
        {
            // Nothing to add
        }
        tileView.setupPlayers();
        
        setupOverlays();
    }
    
    /**
     * Creates a tile of a particular shape and rotation, but no treasure
     * or starting area
     * @param shape The shape of the tile (i.e. I, L, or T)
     * @param rotation The rotation of the tile
     */
    public Tile(Shape shape, int rotation)
    {
        super();
        tileShape = shape;
        currentRotation = rotation;
        setupTile();
        tileTreasure = null;
        playerStartNumber = -1;
        tileView.setupPlayers();
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
        super();
        tileShape = shape;
        currentRotation = rotation;
        setupTile();
        
        tileTreasure = null;
        this.playerStartNumber = player;
        switch (player)
        {
            case 1:
                tileView.addPlayerStart(Color.YELLOW);
                break;
            case 2:
                tileView.addPlayerStart(Color.BLUE);
                break;
            case 3:
                tileView.addPlayerStart(Color.GREEN);
                break;
            case 4:
                tileView.addPlayerStart(Color.RED);
                break;
        }
        tileView.setupPlayers();
        
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
        super();
        tileShape = shape;
        currentRotation = rotation;
        setupTile();
        
        playerStartNumber = -1;
        tileTreasure = treasure;
        if (treasure != null)
        {
            tileView.addTreasure(tileTreasure.getTreasureImageName());
        }
        else {}
        tileView.setupPlayers();
        
        setupOverlays();
    }
    
    /**
     * Sets up the graphics for this tile
     */
    private void setupTile()
    {
        accessible = false;
        tileView = new TileView(tileShape, currentRotation);
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
     * Returns a reference to the graphical representation of the tile
     * @return The tile view
     */
    public TileView getTileView()
    {
        return tileView;
    }
    
    /**
     * Adds a listener to move player characters
     * @param gameBoard A reference to the parent game board
     */
    public void setListener(BoardView gameBoard)
    {
        tileView.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) ->
            { 
                if (accessible)
                {
                    gameBoard.movePlayerToTile(this); 
                }
                else {}
            });
    }
    
    /**
     * Sets the tile's rotation, updating the graphics and neighbors
     * @param rotation The tile's new rotation
     */
    public void setRotation(int rotation)
    {
        currentRotation = rotation;
        tileView.setRotation(currentRotation);
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
        currentRotation += 90;
        tileView.setRotate(currentRotation);
        refreshPossibleNeighbors();
    }
    
    /**
     * Rotates the tile and updates the graphics and possible neighbors
     */
    public void rotateCounterClockwise()
    {
        currentRotation -= 90;
        tileView.setRotate(currentRotation);
        refreshPossibleNeighbors();
    }
    
    /**
     * Gets a reference to the image used to display the tile
     * @return The tile image
     */
    public Image getTileImage()
    {
        return tileView.getTileImage();
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
            Color playerColor = Color.WHITE;
            switch (player)
            {
                case 1:
                    playerColor = Color.YELLOW;
                    break;
                case 2:
                    playerColor = Color.BLUE;
                    break;
                case 3:
                    playerColor = Color.GREEN;
                    break;
                case 4:
                    playerColor = Color.RED;
                    break;
            }
            tileView.showIntersection(playerColor);
            for (int i = 0; i < 4; i++)
            {
                if (connectedNeighbors[i] != null)
                {
                    tileView.showPath(i, playerColor);
                    connectedNeighbors[i].showPaths(player);
                }
                else 
                {
                    tileView.hidePath(i);
                }
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
            
            tileView.hideAllPaths();
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
     * Adds a player's character to this tile to be displayed
     * @param player The character to add to this tile
     */
    public void addPlayerCharacter(PlayerCharacter player)
    {
        tileView.addPlayerCharacter(player);
    }
    
    /**
     * Removes a player's character from being displayed on this tile
     * @param player The character to remove from this tile
     */
    public void removePlayerCharacter(PlayerCharacter player)
    {
        tileView.removePlayerCharacter(player);
    }
    
    /**
     * Moves all player characters on this tile when the tile is pushed off
     * the board
     * @param newTile The tile to move all the characters to
     */
    public void movePlayers(Tile newTile)
    {
        tileView.moveCharacters(newTile);
    }
}
