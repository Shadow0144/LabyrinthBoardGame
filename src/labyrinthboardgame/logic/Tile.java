/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.logic;

import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import labyrinthboardgame.gui.LabyrinthGameBoard;
import labyrinthboardgame.gui.PlayerCharacter;
import labyrinthboardgame.gui.TileView;
import labyrinthboardgame.gui.Treasure;

/**
 *
 * @author Corbi
 */
public class Tile
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
                tileView.addTreasure(tileTreasure.getTileTreasureImage());
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
            tileView.addTreasure(tileTreasure.getTileTreasureImage());
        }
        else {}
        tileView.setupPlayers();
        
        setupOverlays();
    }
    
    private void setupTile()
    {
        accessible = false;
        tileView = new TileView(tileShape, currentRotation);
    }
    
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
    
    public TileView getTileView()
    {
        return tileView;
    }
    
    public void setListener(LabyrinthGameBoard gameBoard)
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
    
    public void setRotation(int rotation)
    {
        currentRotation = rotation;
        tileView.setRotation(currentRotation);
        refreshPossibleNeighbors();
    }
    
    public int getRotation()
    {
        return currentRotation;
    }
    
    public Shape getTileShape()
    {
        return tileShape;
    }
    
    public int getPlayer()
    {
        return playerStartNumber;
    }
    
    public Treasure getTreasure()
    {
        return tileTreasure;
    }
    
    public void rotateClockwise()
    {
        currentRotation += 90;
        tileView.setRotate(currentRotation);
        refreshPossibleNeighbors();
    }
    
    public void rotateCounterClockwise()
    {
        currentRotation -= 90;
        tileView.setRotate(currentRotation);
        refreshPossibleNeighbors();
    }
    
    public Image getTileImage()
    {
        return tileView.getTileImage();
    }
    
    public Image getTreasureImage()
    {
        if (tileTreasure != null)
        {
            return tileTreasure.getTileTreasureImage();
        }
        else
        {
            return null;
        }
    }
    
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
    
    public boolean getAccessible()
    {
        return accessible;
    }
    
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
    
    public void addPlayerCharacter(PlayerCharacter player)
    {
        tileView.addPlayerCharacter(player);
    }
    
    public void removePlayerCharacter(PlayerCharacter player)
    {
        tileView.removePlayerCharacter(player);
    }
    
    public void movePlayers(Tile newTile)
    {
        tileView.moveCharacters(newTile);
    }
}
