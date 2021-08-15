/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;
import labyrinthboardgame.logic.Game;
import labyrinthboardgame.logic.Tile;
import labyrinthboardgame.logic.Treasure;

/**
 *
 * @author Corbi
 */
public final class PlayerIconTray extends VBox 
{
    private final VBox nextTilePane;
    private final ImageView currentTreasureImageView;
    private final Circle currentPlayerCircle;
    private final PlayerIcon icon1;
    private final PlayerIcon icon2;
    private final PlayerIcon icon3;
    private final PlayerIcon icon4;
    private Tile nextTile;
    
    private final int PLAYER_TREASURE_SIZE = 75;
    
    private boolean showTreasure; // or show circle
    
    private TileView nextTileView;
        
    /**
     * Builds a tray to hold the player icons and the next tile to be inserted
     */
    public PlayerIconTray()
    {
        Label nextTileLabel = new Label("Next Tile:");
        nextTileLabel.setTextAlignment(TextAlignment.CENTER);
        nextTilePane = new VBox();
        nextTilePane.setPadding(new Insets(8, 8, 8, 8));
        
        Label currentTreasureLabel = new Label("Current Treasure:");
        currentTreasureLabel.setTextAlignment(TextAlignment.CENTER);
        VBox currentTreasureBox = new VBox(); // For centering
        currentTreasureBox.setBackground(new Background(new BackgroundFill(
                Color.rgb(253, 254, 242), CornerRadii.EMPTY, Insets.EMPTY)));
        currentTreasureBox.setAlignment(Pos.CENTER);
        currentTreasureBox.setPadding(new Insets(8, 8, 8, 8));
        
        StackPane currentTreasurePane = new StackPane();
        currentTreasureBox.getChildren().add(currentTreasurePane);
        
        currentTreasureImageView = new ImageView();
        currentTreasurePane.getChildren().add(currentTreasureImageView);
        
        currentPlayerCircle = new Circle(PLAYER_TREASURE_SIZE / 2);
        currentPlayerCircle.setStroke(Color.BLACK);
        currentPlayerCircle.setStrokeWidth(1);
        currentTreasurePane.getChildren().add(currentPlayerCircle);
        Label showCurrentTreasureLabel = new Label("Hold 'H' to\nshow treasure");
        showCurrentTreasureLabel.setAlignment(Pos.CENTER);
        showCurrentTreasureLabel.setTextAlignment(TextAlignment.CENTER);
        
        icon1 = new PlayerIcon(Color.YELLOW);
        icon2 = new PlayerIcon(Color.BLUE);
        icon3 = new PlayerIcon(Color.GREEN);
        icon4 = new PlayerIcon(Color.RED);
        
        getChildren().add(nextTileLabel);
        getChildren().add(nextTilePane);
        getChildren().add(currentTreasureLabel);
        getChildren().add(currentTreasureBox);
        getChildren().add(showCurrentTreasureLabel);
        getChildren().add(icon1);
        getChildren().add(icon2);
        getChildren().add(icon3);
        getChildren().add(icon4);
        
        setAlignment(Pos.CENTER);
    }
    
    /**
     * Returns a reference to a player icon
     * @param player The player number
     * @return A reference to a player icon
     */
    public PlayerIcon getPlayerIcon(int player)
    {
        PlayerIcon rIcon = null;
        switch (player)
        {
            case 1:
                rIcon = icon1;
                break;
            case 2:
                rIcon = icon2;
                break;
            case 3:
                rIcon = icon3;
                break;
            case 4:
                rIcon = icon4;
                break;
        }
        return rIcon;
    }
    
    /**
     * Disable showing players not playing
     * @param playerNumber The players not in the game
     */
    public void removePlayerIcon(int playerNumber)
    {
        getChildren().remove(getPlayerIcon(playerNumber));
    }
    
    /**
     * Updates the preview of the next tile to be inserted
     * @param game A reference to the game
     * @param nextTile The next tile
     */
    public void updateNextTile(Game game, Tile nextTile)
    {
        this.nextTile = nextTile;
        nextTilePane.getChildren().clear();
        nextTileView = new TileView(game.getConnector(), nextTile);
        nextTileView.setListener(game, nextTile); // Add a listener to the last remaining tile as well
        nextTilePane.getChildren().add(nextTileView);
    }
    
    /**
     * Set the next tile's rotation
     * @param rotation The rotation of the next tile
     */
    public void updateNextTileRotation(int rotation)
    {
        nextTile.setRotation(rotation);
        nextTileView.setRotation(rotation);
    }
    
    /**
     * Set the next tile's rotation
     */
    public void updateNextTileRotation()
    {
        nextTileView.setRotation(nextTile.getRotation());
    }
    
    /**
     * Updates the current treasure to display and the player color
     * @param currentTreasure The current treasure to display
     * @param playerColor The current player's color
     */
    public void updateCurrentTreasure(Treasure currentTreasure, Color playerColor)
    {
        if (currentTreasure != null)
        {
            String treasureType = currentTreasure.getTreasureImageName();
            String treasureImageString = getClass().getResource("assets/" + treasureType + ".png").toString();
            Image treasureImage = new Image(treasureImageString, PLAYER_TREASURE_SIZE, PLAYER_TREASURE_SIZE, false, true);
            currentTreasureImageView.setImage(treasureImage);
            showTreasure = true;
            hideTreasure();
        }
        else
        {
            currentTreasureImageView.setImage(null);
            currentPlayerCircle.setFill(playerColor);
            showTreasure = false;
        }
    }
    
    /**
     * Makes the current treasure image visible
     */
    public void showTreasure()
    {
        if (showTreasure)
        {
            currentTreasureImageView.setOpacity(1);
        }
        else
        {
            currentPlayerCircle.setOpacity(1);
        }
    }
    
    /**
     * Makes the current treasure image invisible
     */
    public void hideTreasure()
    {
        currentTreasureImageView.setOpacity(0);
        currentPlayerCircle.setOpacity(0);
    }
    
    /**
     * Returns the TileView of the next tile
     * @return The TileView of the next tile
     */
    public TileView getNextTileView()
    {
        return nextTileView;
    }
}
