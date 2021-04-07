/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

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
    
    private boolean[] availableNeighbors;
    private boolean[] connectedNeighbors;
    
    private int currentRotation;
    
    private ImageView tileImageView;
    private ImageView treasureImageView;
    private Circle playerStart;
    
    private Image tileImage;
    private final int TILE_SIZE = 70;
    private final int PLAYER_START_RADIUS = 15;
    
    public Tile(Tile tile)
    {
        super();
        if (tile.getTreasure() != null)
        {
            setupTile(tile.getTileShape(), tile.getRotation());
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
            setupTile(tile.getTileShape(), tile.getRotation());
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
            setupTile(tile.getTileShape(), tile.getRotation());
        }
    }
    
    public Tile(Shape shape, int rotation)
    {
        super();
        setupTile(shape, rotation);
        tileTreasure = null;
        player = -1;
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
    }
    
    public void setRotation(int rotation)
    {
        currentRotation = rotation;
        tileImageView.setRotate(currentRotation);
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
    }
    
    public void rotateCounterClockwise()
    {
        currentRotation -= 90;
        tileImageView.setRotate(currentRotation);
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
}
