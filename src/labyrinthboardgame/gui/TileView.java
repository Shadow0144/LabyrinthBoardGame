/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import static javafx.scene.layout.StackPane.setAlignment;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import labyrinthboardgame.logic.GUIConnector;
import labyrinthboardgame.logic.Game;
import labyrinthboardgame.logic.Tile;
import labyrinthboardgame.logic.Treasure;

/**
 *
 * @author Corbi
 */
public final class TileView extends StackPane 
{    
    private final ImageView tileImageView;
    private ImageView treasureImageView;
    private Circle playerStart;
    
    private final GridPane playerGridPane;
    
    private final Pane overlay;
        
    private Image tileImage;
    public static final int TILE_SIZE = 70;
    private final int PLAYER_START_RADIUS = 12;
    
    private final int TILE_TREASURE_SIZE = 50;
    
    private final PlayerCharacter[] playerCharacters;
    
    private final Background accessibleBackground;
    private final Background inaccessibleBackground;
    
    private boolean showingPath;
    
    /**
     * A graphical representation for a tile, containing images for treasures,
     * starting locations, paths, and players
     * @param connector Connects the logic and GUI packages
     * @param tile The tile to draw information from
     */
    public TileView(GUIConnector connector, Tile tile)
    {
        Tile.Shape shape = tile.getTileShape();
        int rotation = tile.getRotation();
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
        //setupOverlays();
        setupTileView(tile);
        
        accessibleBackground = new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY));
        inaccessibleBackground = new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY));
        
        playerGridPane = new GridPane();
        playerGridPane.setAlignment(Pos.CENTER);
        getChildren().add(playerGridPane);
        setAlignment(playerGridPane, Pos.CENTER);
        playerCharacters = new PlayerCharacter[4];
        for (int i = 0; i < 4; i++)
        {
            playerCharacters[i] = null;
        }
        boolean[] players = tile.getPlayers();
        for (int i = 0; i < 4; i++)
        {
            if (players[i])
            {
                addPlayerCharacter(connector.getPlayerCharacter(i));
            }
        }
        
        overlay = new Pane();
        overlay.setOpacity(0.0);
        getChildren().add(overlay);
        showingPath = false;
    }
    
    /**
     * Sets up the tile view based on a tile
     * @param tile A tile to build the view from
     */
    public void setupTileView(Tile tile)
    {
        switch (tile.getType())
        {
            case None:
                // Do nothing
                break;
            case Start:
                switch (tile.getPlayer())
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
                break;
            case Treasure:
                Treasure treasure = tile.getTreasure();
                if (treasure != null)
                {
                    addTreasure(treasure.getTreasureImageName());
                }
                else {}
                break;
        }
    }
    
    /**
     * Adds a treasure to be displayed on the tile
     * @param treasureType The name of the image of the treasure
     */
    public void addTreasure(String treasureType)
    {
        String treasureImageString = getClass().getResource("assets/" + treasureType + ".png").toString();
        Image treasureImage = new Image(treasureImageString, TILE_TREASURE_SIZE, TILE_TREASURE_SIZE, false, true);
        treasureImageView = new ImageView();
        treasureImageView.setImage(treasureImage);
        getChildren().add(treasureImageView);
        setAlignment(treasureImageView, Pos.CENTER);
    }
    
    /**
     * Adds a player starting location images to the tile
     * @param color The color of the starting location
     */
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
    
    /**
     * Sets up listeners for mouse events
     * @param game A reference to the game
     * @param tile A reference to the underlying tile
     */
    public void setListener(Game game, Tile tile)
    {
        addEventHandler(MouseEvent.MOUSE_ENTERED, (e) ->
            {
                if (showingPath)
                {
                    overlay.setOpacity(0.5);
                    if (tile.getAccessible())
                    {
                        overlay.setBackground(accessibleBackground);
                    }
                    else 
                    {
                        overlay.setBackground(inaccessibleBackground);
                    }
                }
                else {}
            });
        addEventHandler(MouseEvent.MOUSE_EXITED, (e) ->
            {
                overlay.setOpacity(0.0);
            });
        addEventHandler(MouseEvent.MOUSE_CLICKED, (e) ->
            { 
                if (tile.getAccessible())
                {
                    game.movePlayerToTile(tile); 
                    overlay.setOpacity(0.0);
                }
                else {}
            });
    }
    
    /**
     * Updates the graphics to match the rotation
     * @param rotation The rotation of the tile
     */
    public void setRotation(int rotation)
    {
        tileImageView.setRotate(rotation);
    }
    
    /**
     * Returns the image of the tile used by this tile
     * @return The image of the tile
     */
    public Image getTileImage()
    {
        return tileImage;
    }
    
    /**
     * Enables displaying which tiles are accessible or not
     */
    public void showPath()
    {
        showingPath = true;
    }
    
    /**
     * Disables displaying which tiles are accessible or not
     */
    public void hidePath()
    {
        showingPath = false;
    }
    
    /**
     * Adds a player character to be displayed on this tile, moving other 
     * characters as appropriate
     * @param player The player character to add to this tile
     */
    public void addPlayerCharacter(PlayerCharacter player)
    {
         // First clear all the displayed characters and readd them all and the new character
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
    
    /**
     * Removes a player character from this tile, readjusting the remaining
     * characters as appropriate
     * @param player The player character to remove from this tile
     */
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
    
    /**
     * Moves player characters from this tile to a new tile
     * @param connector Connects the logic and GUI packages
     * @param newTileView The tile to move the player characters to
     */
    public void movePlayerCharacters(GUIConnector connector, TileView newTileView)
    {
        if (newTileView != null)
        {
            for (int i = 0; i < 4; i++)
            {
                if (newTileView.playerCharacters[i] != null)
                {
                    addPlayerCharacter(newTileView.playerCharacters[i]);
                    newTileView.playerCharacters[i] = null;
                }
                else
                {
                    break;
                }
            }
            newTileView.playerGridPane.getChildren().clear();
        }
        else {}
    }
    
    /**
     * Adds a list of player characters to the tile view
     * @param connector Connects the logic and GUI packages
     * @param players The list of player characters to add by index
     */
    public void addPlayerCharacters(GUIConnector connector, boolean[] players)
    {
        for (int i = 0; i < 4; i++)
        {
            if (players[i])
            {
                addPlayerCharacter(connector.getPlayerCharacter(i));
            }
            else {}
        }
    }
    
    /**
     * Removes all player characters from the tile view
     */
    public void removePlayerCharacters()
    {
        playerGridPane.getChildren().clear();
        for (int i = 0; i < 4; i++)
        {
            playerCharacters[i] = null;
        }
    }
}
