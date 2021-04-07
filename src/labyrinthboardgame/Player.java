/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame;

import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Corbi
 */
public class Player extends StackPane {
    Circle playerIcon;
    ImageView playerTreasure;
    Text playerTreasuresLeft;
    
    int player;
    
    final int PLAYER_ICON_RADIUS = 30;
    final int UNSELECTED_STROKE = 1;
    final int SELECTED_STROKE = 3;
    
    public Player(int playerNumber)
    {
        player = playerNumber;
        
        setupIcon();
    }
    
    private void setupIcon()
    {
        playerIcon = new Circle();
        playerIcon.setRadius(PLAYER_ICON_RADIUS);
        playerIcon.setStroke(Color.BLACK);
        playerIcon.setStrokeWidth(UNSELECTED_STROKE);
        switch (player)
        {
            case 1:
                playerIcon.setFill(Color.YELLOW);
                break;
            case 2:
                playerIcon.setFill(Color.BLUE);
                break;
            case 3:
                playerIcon.setFill(Color.GREEN);
                break;
            case 4:
                playerIcon.setFill(Color.RED);
                break;
            
        }
        getChildren().add(playerIcon);
        
        Circle textBackground = new Circle();
        textBackground.setFill(Color.WHITE);
        textBackground.setLayoutX(75);
        textBackground.setLayoutY(30);
        textBackground.setRadius(10);
        textBackground.setStroke(Color.BLACK);
        textBackground.setStrokeWidth(1);
        getChildren().add(textBackground);
        
        playerTreasuresLeft = new Text();
        playerTreasuresLeft.setLayoutX(71);
        playerTreasuresLeft.setLayoutY(30);
        playerTreasuresLeft.setText("0");
        playerTreasuresLeft.setTextAlignment(TextAlignment.CENTER);
        playerTreasuresLeft.setTextOrigin(VPos.CENTER);
        getChildren().add(playerTreasuresLeft);
    }
}
