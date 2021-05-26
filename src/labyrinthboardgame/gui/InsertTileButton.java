/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.gui;

import labyrinthboardgame.logic.Tile;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import static javafx.scene.layout.StackPane.setAlignment;
import labyrinthboardgame.logic.Game;

/**
 *
 * @author Corbi
 */
public final class InsertTileButton extends StackPane
{
    // Position of the arrow
    public enum ArrowPosition
    {
        TopLeft, TopCenter, TopRight,
        LeftTop, LeftCenter, LeftBottom,
        BottomLeft, BottomCenter, BottomRight,
        RightTop, RightCenter, RightBottom,
    };
    private final ArrowPosition arrow;
    
    private final int ARROW_SIZE = 20;
    
    private final double PREVIEW_OPACITY = 0.5;
    
    private Tile previewTile;
    
    private boolean enabled;
    private final double DISABLED = 0.25;
    
    /**
     * A button for inserting tiles into the board
     * @param game A reference to the game the arrow belongs to
     * @param position The position the tiles are inserted from
     */
    public InsertTileButton(Game game, ArrowPosition position)
    {
        ImageView arrowImageView = new ImageView();
        String arrowImageString = getClass().getResource("assets/arrow.png").toString();
        Image arrowImage = new Image(arrowImageString, ARROW_SIZE, ARROW_SIZE, false, true);
        arrowImageView.setImage(arrowImage);
        getChildren().add(arrowImageView);
        setAlignment(arrowImageView, Pos.CENTER);
        
        arrow = position;
        int rotation = 0;
        switch (arrow)
        {
            case TopLeft:
            case TopCenter:
            case TopRight:
                rotation = 0;
                break;
            case LeftTop:
            case LeftCenter:
            case LeftBottom:
                rotation = 270;
                break;
            case BottomLeft:
            case BottomCenter:
            case BottomRight:
                rotation = 180;
                break;
            case RightTop:
            case RightCenter:
            case RightBottom:
                rotation = 90;
                break;
        }
        arrowImageView.setRotate(rotation);
        
        this.setOnMouseEntered(event -> {
            setPreviewTile(game.getNextTile());
        });
        this.setOnMouseExited(event -> {
            removePreviewTile();
        });
        this.setOnMouseClicked(event -> {
            game.insertTile(arrow);
        });
        
        enabled = true;
    }
    
    /**
     * Shows a preview of the tile to be inserted
     * @param previewTile The tile to be inserted
     */
    public void setPreviewTile(Tile previewTile)
    {
        if (previewTile != null)
        {
            if (enabled)
            {
                this.previewTile = previewTile;
                previewTile.getPreviewTileView().setOpacity(PREVIEW_OPACITY);
                getChildren().add(previewTile.getPreviewTileView());
                setAlignment(previewTile.getPreviewTileView(), Pos.CENTER);
            }
            else {}
        }
        else {}
    }
    
    /**
     * Hides the preview of the next tile such as when the mouse leaves
     */
    public void removePreviewTile()
    {
        if (previewTile != null)
        {
            getChildren().remove(previewTile.getPreviewTileView());
            previewTile = null;
        }
        else {}
    }
    
    /**
     * Rotates the preview of the next tile when it is displayed
     */
    public void rotatePreviewClockwise()
    {
        if (enabled && previewTile != null)
        {
            previewTile.rotateClockwise();
        }
    }
    
    /**
     * Rotates the preview of the next tile when it is displayed
     */
    public void rotatePreviewCounterClockwise()
    {
        if (enabled && previewTile != null)
        {
            previewTile.rotateCounterClockwise();
        }
    }
    
    /**
     * Enables the button
     * @param human Only enable the button for pressing for human players
     */
    public void enable(boolean human)
    {
        enabled = human;
        setDisable(!human);
        setOpacity(1);
    }
    
    /** 
     * Disables the button
     */
    public void disable()
    {
        enabled = false;
        setDisable(true);
        setOpacity(DISABLED);
    }
}
