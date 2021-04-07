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
    public enum Arrow
    {
        TopLeft, TopCenter, TopRight,
        LeftTop, LeftCenter, LeftBottom,
        BottomLeft, BottomCenter, BottomRight,
        RightTop, RightCenter, RightBottom,
    };
    private Arrow arrow;
    
    private final int ARROW_SIZE = 20;
    
    public InsertTileButton(int rotation, Arrow arrow)
    {
        ImageView arrowImageView = new ImageView();
        String arrowImageString = getClass().getResource("assets/arrow.png").toString();
        Image arrowImage = new Image(arrowImageString, ARROW_SIZE, ARROW_SIZE, false, true);
        arrowImageView.setImage(arrowImage);
        arrowImageView.setRotate(rotation);
        getChildren().add(arrowImageView);
        setAlignment(arrowImageView, Pos.CENTER);
        this.arrow = arrow;
    }
    
    public void setPreviewTile(Tile previewTile)
    {
        getChildren().add(previewTile);
        setAlignment(previewTile, Pos.CENTER);
    }
    
    public void removePreviewTile(Tile previewTile)
    {
        getChildren().remove(previewTile);
    }
}
