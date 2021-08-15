/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.gui;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import static javafx.scene.layout.StackPane.setAlignment;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import labyrinthboardgame.logic.ConfigurationManager;
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
    
    private final Pane overlay;
        
    private Image tileImage;
    public static final int TILE_SIZE = 70;
    public static final int TILE_PADDING = 4;
    private final int PLAYER_START_RADIUS = 12;
    
    private final int TILE_TREASURE_SIZE = 50;
    
    private final int ANIMATION_OFFSET = TILE_SIZE + TILE_PADDING;
    
    private final Background accessibleBackground;
    private final Background inaccessibleBackground;
    private final Background treasureBackground;
    private final Background playerMovedBackground;
    
    private boolean showingPath;
    
    private final int FADE_ANIMATION_HALF_DURATION = 500;
    
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
        treasureBackground = new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY));
        playerMovedBackground = new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY));
        
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
                    addTreasure(treasure.getTreasureImageName(), tile.getIsTreasureShown());
                }
                else {}
                break;
        }
    }
    
    /**
     * Adds a treasure to be displayed on the tile
     * @param treasureType The name of the image of the treasure
     * @param treasureShown If the treasure is displayed or not
     */
    public void addTreasure(String treasureType, boolean treasureShown)
    {
        String treasureImageString = getClass().getResource("assets/" + treasureType + ".png").toString();
        Image treasureImage = new Image(treasureImageString, TILE_TREASURE_SIZE, TILE_TREASURE_SIZE, false, true);
        treasureImageView = new ImageView();
        treasureImageView.setImage(treasureImage);
        treasureImageView.setOpacity(treasureShown ? 1.0 : 0.0);
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
     * Lights up tile to show treasure
     */
    public void highlightTreasure()
    {
        overlay.setOpacity(0.5);
        overlay.setBackground(treasureBackground);
    }
    
    /**
     * Lights down to hide treasure
     */
    public void unhighlightTreasure()
    {
        overlay.setOpacity(0.0);
    }
    
    /**
     * Shows a treasure if it is used in the game
     */
    public void showTreasure()
    {
        treasureImageView.setOpacity(1.0);
    }
    
    /**
     * Hides a treasure after it has been collected
     */
    public void hideTreasure()
    {
        treasureImageView.setOpacity(0.0);
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
     * Plays the animation for a character moving to this tile
     */
    public void addPlayerCharacter()
    {
        if (overlay != null) // Potentially null for unknown reasons (perhaps the animations?)
        {
            overlay.setBackground(playerMovedBackground);
        }
        else {}
        
        FadeTransition fadeInTransition = new FadeTransition(Duration.millis(FADE_ANIMATION_HALF_DURATION), overlay);
        fadeInTransition.setFromValue(0.0);
        fadeInTransition.setToValue(0.5);
        fadeInTransition.play();
        
        FadeTransition fadeOutTransition = new FadeTransition(Duration.millis(FADE_ANIMATION_HALF_DURATION), overlay);
        fadeOutTransition.setFromValue(0.5);
        fadeOutTransition.setToValue(0.0);
        fadeOutTransition.setDelay(Duration.millis(FADE_ANIMATION_HALF_DURATION));
        fadeOutTransition.play();
    }
    
    /**
     * Plays the animation for a character moving to this tile if necessary
     * @param players The list of player characters to add by index
     */
    public void addPlayerCharacters(boolean[] players)
    {
        for (int i = 0; i < 4; i++)
        {
            if (players[i])
            {
                addPlayerCharacter();
            }
            else {}
        }
    }
    
    /**
     * Animates the tile sliding down
     */
    public void animateDown()
    {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.millis(ConfigurationManager.TILE_ANIMATION_SPEED));
        translateTransition.setFromY(-ANIMATION_OFFSET);
        translateTransition.setToY(0);
        translateTransition.setNode(this);
        translateTransition.play();
    }
    
    /**
     * Animates the tile sliding up
     */
    public void animateUp()
    {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.millis(ConfigurationManager.TILE_ANIMATION_SPEED));
        translateTransition.setFromY(ANIMATION_OFFSET);
        translateTransition.setToY(0);
        translateTransition.setNode(this);
        translateTransition.play();
    }
    
    /**
     * Animates the tile sliding right
     */
    public void animateRight()
    {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.millis(ConfigurationManager.TILE_ANIMATION_SPEED));
        translateTransition.setFromX(-ANIMATION_OFFSET);
        translateTransition.setToX(0);
        translateTransition.setNode(this);
        translateTransition.play();
    }
    
    /**
     * Animates the tile sliding left
     */
    public void animateLeft()
    {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.millis(ConfigurationManager.TILE_ANIMATION_SPEED));
        translateTransition.setFromX(ANIMATION_OFFSET);
        translateTransition.setToX(0);
        translateTransition.setNode(this);
        translateTransition.play();
    }
}
