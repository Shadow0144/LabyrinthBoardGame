/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Corbi
 */
public class Tile extends StackPane
{
    public enum Shape {
        I,
        L,
        T
    };
    private Shape tileShape;
    
    private Treasure tileTreasure;
    private int player;
    
    private boolean[] possibleNeighbors;
    private Tile[] connectedNeighbors;
    
    private Rectangle[] paths;
    private Circle pathIntersection;
    
    private int currentRotation;
    
    private ImageView tileImageView;
    private ImageView treasureImageView;
    private Circle playerStart;
    
    private GridPane playerGridPane;
    private PlayerCharacter[] playerCharacters;
    
    private Image tileImage;
    public static final int TILE_SIZE = 70;
    private final int PLAYER_START_RADIUS = 12;
    
    private final int INTERSECTION_RADIUS = 10;
    private final int PATH_WIDTH = 10;
    private final int PATH_LENGTH = 40;
    private final double PATH_OPACITY = 0.75;
    
    private boolean accessible;
    private boolean buildingPath;
    
    private LabyrinthGameBoard board;
    
    public Tile(Tile tile)
    {
        super();
        setupTile(tile.getTileShape(), tile.getRotation());
        if (tile.getTreasure() != null)
        {
            player = -1;
            tileTreasure = tile.getTreasure();
            if (tileTreasure != null)
            {
                treasureImageView = new ImageView();
                treasureImageView.setImage(tileTreasure.getTileTreasureImage());
                getChildren().add(treasureImageView);
                setAlignment(treasureImageView, Pos.CENTER);
            }
            else {}
        }
        else if (tile.getPlayer() != -1)
        {
            tileTreasure = null;
            player = tile.getPlayer();
            switch (player)
            {
                case 1:
                    addPlayerStart(Color.YELLOW);
                    break;
                case 2:
                    addPlayerStart(Color.BLUE);
                    break;
                case 3:
                    addPlayerStart(Color.GREEN);
                    break;
                case 4:
                    addPlayerStart(Color.RED);
                    break;
            }
        }
        else
        {
            // Nothing to add
        }
        
        setupOverlays();
    }
    
    public Tile(Shape shape, int rotation)
    {
        super();
        setupTile(shape, rotation);
        tileTreasure = null;
        player = -1;
        setupOverlays();
    }
    
    public Tile(Shape shape, int rotation, int player)
    {
        super();
        setupTile(shape, rotation);
        
        tileTreasure = null;
        this.player = player;
        switch (player)
        {
            case 1:
                addPlayerStart(Color.YELLOW);
                break;
            case 2:
                addPlayerStart(Color.BLUE);
                break;
            case 3:
                addPlayerStart(Color.GREEN);
                break;
            case 4:
                addPlayerStart(Color.RED);
                break;
        }
        
        setupOverlays();
    }
    
    public Tile(Shape shape, int rotation, Treasure treasure)
    {
        super();
        setupTile(shape, rotation);
        
        player = -1;
        tileTreasure = treasure;
        if (treasure != null)
        {
            treasureImageView = new ImageView();
            treasureImageView.setImage(treasure.getTileTreasureImage());
            getChildren().add(treasureImageView);
            setAlignment(treasureImageView, Pos.CENTER);
        }
        else {}
        
        setupOverlays();
    }
    
    private void setupTile(Shape shape, int rotation)
    {
        String tileImageString;
        tileShape = shape;
        switch (tileShape)
        {
            case I:
                tileImageString = getClass().getResource("assets/I.png").toString();
                tileImage = new Image(tileImageString, TILE_SIZE, TILE_SIZE, false, true);
                break;
            case L:
                tileImageString = getClass().getResource("assets/L.png").toString();
                tileImage = new Image(tileImageString, TILE_SIZE, TILE_SIZE, false, true);
                break;
            case T:
                tileImageString = getClass().getResource("assets/T.png").toString();
                tileImage = new Image(tileImageString, TILE_SIZE, TILE_SIZE, false, true);
                break;
        }
        tileImageView = new ImageView();
        getChildren().add(tileImageView);
        setAlignment(tileImageView, Pos.CENTER);
        currentRotation = rotation;
        tileImageView.setImage(tileImage);
        tileImageView.setRotate(currentRotation);
        accessible = false;
        
        board = null;
        addEventHandler(MouseEvent.MOUSE_CLICKED, (e) ->
            { 
                if (board != null && accessible)
                {
                    board.movePlayerToTile(this); 
                }
                else {}
            });
    }
    
    private void setupOverlays()
    {
        playerGridPane = new GridPane();
        playerGridPane.setAlignment(Pos.CENTER);
        getChildren().add(playerGridPane);
        setAlignment(playerGridPane, Pos.CENTER);
        playerCharacters = new PlayerCharacter[4];
        for (int i = 0; i < 4; i++)
        {
            playerCharacters[i] = null;
        }
        
        buildingPath = false;
        
        connectedNeighbors = new Tile[4];
        
        paths = new Rectangle[4];
        for (int i = 0; i < 4; i++)
        {
            connectedNeighbors[i] = null;
            paths[i] = new Rectangle();
            paths[i].setStrokeWidth(1);
            paths[i].setStroke(Color.BLACK);
            paths[i].setOpacity(0);
            getChildren().add(paths[i]);
        }
        
        pathIntersection = new Circle(INTERSECTION_RADIUS);
        pathIntersection.setOpacity(0);
        pathIntersection.setStrokeWidth(1);
        pathIntersection.setStroke(Color.BLACK);
        getChildren().add(pathIntersection);
        setAlignment(pathIntersection, Pos.CENTER);

        // Top
        paths[0].setWidth(PATH_WIDTH);
        paths[0].setHeight(PATH_LENGTH);
        setAlignment(paths[0], Pos.TOP_CENTER);
        
        // Right
        paths[1].setWidth(PATH_LENGTH);
        paths[1].setHeight(PATH_WIDTH);
        setAlignment(paths[1], Pos.CENTER_RIGHT);
        
        // Bottom
        paths[2].setWidth(PATH_WIDTH);
        paths[2].setHeight(PATH_LENGTH);
        setAlignment(paths[2], Pos.BOTTOM_CENTER);
        
        // Left
        paths[3].setWidth(PATH_LENGTH);
        paths[3].setHeight(PATH_WIDTH);
        setAlignment(paths[3], Pos.CENTER_LEFT);
        
        possibleNeighbors = new boolean[4];
        refreshPossibleNeighbors();
    }
    
    public void setBoard(LabyrinthGameBoard gameBoard)
    {
        board = gameBoard;
    }
    
    public void setRotation(int rotation)
    {
        currentRotation = rotation;
        tileImageView.setRotate(currentRotation);
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
        return player;
    }
    
    public Treasure getTreasure()
    {
        return tileTreasure;
    }
    
    public void rotateClockwise()
    {
        currentRotation += 90;
        tileImageView.setRotate(currentRotation);
        refreshPossibleNeighbors();
    }
    
    public void rotateCounterClockwise()
    {
        currentRotation -= 90;
        tileImageView.setRotate(currentRotation);
        refreshPossibleNeighbors();
    }
    
    public Image getTileImage()
    {
        return tileImage;
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
            pathIntersection.setOpacity(PATH_OPACITY);
            pathIntersection.setFill(playerColor);
            for (int i = 0; i < 4; i++)
            {
                if (connectedNeighbors[i] != null)
                {
                    paths[i].setOpacity(PATH_OPACITY);
                    paths[i].setFill(playerColor);
                    connectedNeighbors[i].showPaths(player);
                }
                else 
                {
                    paths[i].setOpacity(0);
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
            pathIntersection.setOpacity(0);
            for (int i = 0; i < 4; i++)
            {
                if (connectedNeighbors[i] != null)
                {
                    connectedNeighbors[i].hidePaths();
                }
                else {}
                paths[i].setOpacity(0);
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
    
    private void addPlayerStart(Color color)
    {
        playerStart = new Circle();
        playerStart.setRadius(PLAYER_START_RADIUS);
        playerStart.setFill(color);
        playerStart.setStroke(Color.BLACK);
        playerStart.setStrokeWidth(1);
        getChildren().add(playerStart);
        setAlignment(playerStart, Pos.CENTER);
    }
    
    public void addPlayerCharacter(PlayerCharacter player)
    {
        playerGridPane.getChildren().clear();
        for (int i = 0; i < 4; i++)
        {
            if (playerCharacters[i] == null)
            {
                playerCharacters[i] = player;
                playerGridPane.add(playerCharacters[i], i % 2, (i < 2) ? 0 : 1);
                break;
            }
            else
            {
                playerGridPane.add(playerCharacters[i], i % 2, (i < 2) ? 0 : 1);
            }
        }
    }
    
    public void removePlayerCharacter(PlayerCharacter player)
    {
        for (int i = 0; i < 4; i++)
        {
            if (playerCharacters[i] == player)
            {
                for (; i < 3; i++) // Shift everything left (which removes the current item)
                {
                    playerCharacters[i] = playerCharacters[i+1];
                }
                playerCharacters[3] = null; // Set the final item to null
                break;
            }
            else {}
        }
        // Remove and readd everything to the correct spots
        playerGridPane.getChildren().clear();
        for (int i = 0; i < 4; i++)
        {
            if (playerCharacters[i] == null)
            {
                break;
            }
            else
            {
                playerGridPane.add(playerCharacters[i], i % 2, (i < 2) ? 0 : 1);
            }
        }
    }
    
    public void movePlayers(Tile newTile)
    {
        while (playerCharacters[0] != null)
        {
            playerCharacters[0].getPlayer().moveCharacter(newTile);
        }
    }
}
