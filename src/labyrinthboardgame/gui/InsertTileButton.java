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
public class InsertTileButton extends StackPane
{
    LabyrinthGameBoard board;
    
    public enum Arrow
    {
        TopLeft, TopCenter, TopRight,
        LeftTop, LeftCenter, LeftBottom,
        BottomLeft, BottomCenter, BottomRight,
        RightTop, RightCenter, RightBottom,
    };
    private final Arrow arrow;
    
    private final int ARROW_SIZE = 20;
    
    private final double PREVIEW_OPACITY = 0.5;
    
    private Tile previewTile;
    
    private boolean enabled;
    private final double DISABLED = 0.25;
    
    private ImageView arrowImageView;
    private double rotation;
    public double getRotation() { return rotation; }
    public void setRotation(double rot) 
    { 
        rotation = rot;
        arrowImageView.setRotate(rotation);
    }
    
    public InsertTileButton()
    {
        arrowImageView = new ImageView();
        String arrowImageString = getClass().getResource("assets/arrow.png").toString();
        Image arrowImage = new Image(arrowImageString, ARROW_SIZE, ARROW_SIZE, false, true);
        arrowImageView.setImage(arrowImage);
        arrowImageView.setRotate(rotation);
        getChildren().add(arrowImageView);
        setAlignment(arrowImageView, Pos.CENTER);
        this.arrow = Arrow.TopLeft;
    }
    
    public InsertTileButton(LabyrinthGameBoard gameBoard, int rotation, Arrow arrow)
    {
        board = gameBoard;
        
        arrowImageView = new ImageView();
        String arrowImageString = getClass().getResource("assets/arrow.png").toString();
        Image arrowImage = new Image(arrowImageString, ARROW_SIZE, ARROW_SIZE, false, true);
        arrowImageView.setImage(arrowImage);
        arrowImageView.setRotate(rotation);
        getChildren().add(arrowImageView);
        setAlignment(arrowImageView, Pos.CENTER);
        this.arrow = arrow;
        
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
    
    public void removePreviewTile()
    {
        if (previewTile != null)
        {
            getChildren().remove(previewTile.getTileView());
            previewTile = null;
        }
    }
    
    public void rotatePreviewClockwise()
    {
        if (enabled && previewTile != null)
        {
            previewTile.rotateClockwise();
        }
    }
    
    public void rotatePreviewCounterClockwise()
    {
        if (enabled && previewTile != null)
        {
            previewTile.rotateCounterClockwise();
        }
    }
    
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
    
    public void enable()
    {
        enabled = true;
        setOpacity(1);
    }
    
    public void disable()
    {
        enabled = false;
        setOpacity(DISABLED);
    }
}
