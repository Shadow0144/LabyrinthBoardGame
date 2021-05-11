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

/**
 *
 * @author Corbi
 */
public final class InsertTileButton extends StackPane
{
    BoardView board;
    
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
     * @param gameBoard A reference to the game board the arrow belongs to
     * @param position The position the tiles are inserted from
     */
    public InsertTileButton(BoardView gameBoard, ArrowPosition position)
    {
        board = gameBoard;
        
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
            setPreviewTile(gameBoard.getNextTile());
        });
        this.setOnMouseExited(event -> {
            removePreviewTile();
        });
        this.setOnMouseClicked(event -> {
            insertTile();
        });
        
        enabled = true;
    }
    
    /**
     * Shows a preview of the tile to be inserted
     * @param previewTile The tile to be inserted
     */
    public void setPreviewTile(Tile previewTile)
    {
        if (enabled)
        {
            this.previewTile = previewTile;
            previewTile.getTileView().setOpacity(PREVIEW_OPACITY);
            getChildren().add(previewTile.getTileView());
            setAlignment(previewTile.getTileView(), Pos.CENTER);
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
            getChildren().remove(previewTile.getTileView());
            previewTile = null;
        }
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
     * Inserts the next tile into the board
     */
    private void insertTile()
    {
        if (enabled)
        {
            board.insertTile(arrow);
            if (previewTile != null)
            {
                removePreviewTile();
            }
            else {}
        }
    }
    
    /**
     * Enables the button
     */
    public void enable()
    {
        enabled = true;
        setOpacity(1);
    }
    
    /** 
     * Disables the button
     */
    public void disable()
    {
        enabled = false;
        setOpacity(DISABLED);
    }
}
