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
    private Arrow arrow;
    
    private final int ARROW_SIZE = 20;
    
    private final double PREVIEW_OPACITY = 0.5;
    
    private Tile previewTile;
    
    public InsertTileButton(LabyrinthGameBoard gameBoard, int rotation, Arrow arrow)
    {
        board = gameBoard;
        
        ImageView arrowImageView = new ImageView();
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
    }
    
    public void setPreviewTile(Tile previewTile)
    {
        this.previewTile = previewTile;
        previewTile.setOpacity(PREVIEW_OPACITY);
        getChildren().add(previewTile);
        setAlignment(previewTile, Pos.CENTER);
    }
    
    public void removePreviewTile()
    {
        getChildren().remove(previewTile);
        previewTile = null;
    }
    
    public void rotatePreviewClockwise()
    {
        if (previewTile != null)
        {
            previewTile.rotateClockwise();
        }
    }
    
    public void rotatePreviewCounterClockwise()
    {
        if (previewTile != null)
        {
            previewTile.rotateCounterClockwise();
        }
    }
    
    private void insertTile()
    {
        board.insertTile(arrow);
        if (previewTile != null)
        {
            removePreviewTile();
            setPreviewTile(board.getNextTile());
        }
        else {}
    }
}
