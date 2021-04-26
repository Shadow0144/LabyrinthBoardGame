/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.gui;

import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import static javafx.scene.layout.StackPane.setAlignment;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Corbi
 */
public class PlayerIcon extends StackPane
{
    private Circle playerIcon;
    private ImageView playerTreasure;
    private Text playerTreasuresRemainingText;
    
    private final int PLAYER_ICON_RADIUS = 30;
    private final int UNSELECTED_STROKE = 1;
    private final int SELECTED_STROKE = 3;
    private final int VICTORY_STROKE = 5;
    
    private Color color;
    public Color getColor() { return color; }
    public void setColor(Color col)
    {
        color = col;
        playerIcon.setFill(color);
    }
    
    public PlayerIcon()
    {
        playerIcon = new Circle();
        playerIcon.setRadius(PLAYER_ICON_RADIUS);
        playerIcon.setStroke(Color.BLACK);
        playerIcon.setStrokeWidth(UNSELECTED_STROKE);
        
        getChildren().add(playerIcon);
        
        AnchorPane treasuresRemainingPane = new AnchorPane();
        
        Circle textBackground = new Circle();
        textBackground.setFill(Color.WHITE);
        textBackground.setLayoutX(75);
        textBackground.setLayoutY(30);
        textBackground.setRadius(10);
        textBackground.setStroke(Color.BLACK);
        textBackground.setStrokeWidth(1);
        treasuresRemainingPane.getChildren().add(textBackground);
        
        playerTreasuresRemainingText = new Text();
        playerTreasuresRemainingText.setLayoutX(71);
        playerTreasuresRemainingText.setLayoutY(30);
        playerTreasuresRemainingText.setText("" + 0);
        playerTreasuresRemainingText.setTextAlignment(TextAlignment.CENTER);
        playerTreasuresRemainingText.setTextOrigin(VPos.CENTER);
        treasuresRemainingPane.getChildren().add(playerTreasuresRemainingText);
        
        getChildren().add(treasuresRemainingPane);
        setAlignment(treasuresRemainingPane, Pos.TOP_RIGHT);
        
        playerTreasure = new ImageView();
        getChildren().add(playerTreasure);
    }
    
    public PlayerIcon(Color color)
    {
        playerIcon = new Circle();
        playerIcon.setRadius(PLAYER_ICON_RADIUS);
        playerIcon.setStroke(Color.BLACK);
        playerIcon.setStrokeWidth(UNSELECTED_STROKE);
        playerIcon.setFill(color);
        
        getChildren().add(playerIcon);
        
        AnchorPane treasuresRemainingPane = new AnchorPane();
        
        Circle textBackground = new Circle();
        textBackground.setFill(Color.WHITE);
        textBackground.setLayoutX(75);
        textBackground.setLayoutY(30);
        textBackground.setRadius(10);
        textBackground.setStroke(Color.BLACK);
        textBackground.setStrokeWidth(1);
        treasuresRemainingPane.getChildren().add(textBackground);
        
        playerTreasuresRemainingText = new Text();
        playerTreasuresRemainingText.setLayoutX(71);
        playerTreasuresRemainingText.setLayoutY(30);
        playerTreasuresRemainingText.setText("" + 0);
        playerTreasuresRemainingText.setTextAlignment(TextAlignment.CENTER);
        playerTreasuresRemainingText.setTextOrigin(VPos.CENTER);
        treasuresRemainingPane.getChildren().add(playerTreasuresRemainingText);
        
        getChildren().add(treasuresRemainingPane);
        setAlignment(treasuresRemainingPane, Pos.TOP_RIGHT);
        
        playerTreasure = new ImageView();
        getChildren().add(playerTreasure);
    }
    
    public void updateTreasureImage(Image treasureImage)
    {
        playerTreasure.setImage(treasureImage);
    }
    
    public void updateTreasuresRemaining(int treasuresRemaining)
    {
        playerTreasuresRemainingText.setText("" + treasuresRemaining);
    }
    
    public void setHasWon()
    {
        playerIcon.setStroke(Color.GOLD);
        playerIcon.setStrokeWidth(VICTORY_STROKE);
    }
    
    public void setActive()
    {
        playerIcon.setStrokeWidth(SELECTED_STROKE);
    }
    
    public void setInactive()
    {
        playerIcon.setStrokeWidth(UNSELECTED_STROKE);
    }
}
