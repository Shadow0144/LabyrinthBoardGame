/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.gui;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import static javafx.scene.layout.StackPane.setAlignment;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import labyrinthboardgame.logic.Tile;

/**
 *
 * @author Corbi
 */
public class TileView extends StackPane 
{
    
    private Rectangle[] paths;
    private Circle pathIntersection;
    
    private ImageView tileImageView;
    private ImageView treasureImageView;
    private Circle playerStart;
    
    private GridPane playerGridPane;
        
    private Image tileImage;
    public static final int TILE_SIZE = 70;
    private final int PLAYER_START_RADIUS = 12;
    
    private final int INTERSECTION_RADIUS = 10;
    private final int PATH_WIDTH = 10;
    private final int PATH_LENGTH = 40;
    private final double PATH_OPACITY = 0.75;
    
    private PlayerCharacter[] playerCharacters;
    
    public TileView(Tile.Shape shape, int rotation)
    {
        String tileImageString;
        switch (shape)
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
        tileImageView.setImage(tileImage);
        tileImageView.setRotate(rotation);
        setupOverlays();
    }
    
    public void addTreasure(Image treasureImage)
    {
        treasureImageView = new ImageView();
        treasureImageView.setImage(treasureImage);
        getChildren().add(treasureImageView);
        setAlignment(treasureImageView, Pos.CENTER);
    }
    
    public void addPlayerStart(Color color)
    {
        playerStart = new Circle();
        playerStart.setRadius(PLAYER_START_RADIUS);
        playerStart.setFill(color);
        playerStart.setStroke(Color.BLACK);
        playerStart.setStrokeWidth(1);
        getChildren().add(playerStart);
        setAlignment(playerStart, Pos.CENTER);
    }
    
    public void setupOverlays()
    {
        paths = new Rectangle[4];
        for (int i = 0; i < 4; i++)
        {
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
    }
    
    public void setupPlayers()
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
    }
    
    public void setRotation(int rotation)
    {
        tileImageView.setRotate(rotation);
    }
    
    public Image getTileImage()
    {
        return tileImage;
    }
    
    public void showIntersection(Color playerColor)
    {
        pathIntersection.setOpacity(PATH_OPACITY);
        pathIntersection.setFill(playerColor);
    }
    
    public void showPath(int path, Color playerColor)
    {
        paths[path].setOpacity(PATH_OPACITY);
        paths[path].setFill(playerColor);
    }
    
    public void hidePath(int path)
    {
        paths[path].setOpacity(0);
    }
    
    public void hideAllPaths()
    {
        pathIntersection.setOpacity(0);
        for (int i = 0; i < 4; i++)
        {
            paths[i].setOpacity(0);
        }
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
    
    public void moveCharacters(Tile newTile)
    {
        while (playerCharacters[0] != null)
        {
            playerCharacters[0].getPlayer().moveCharacter(newTile);
        }
    }
}
