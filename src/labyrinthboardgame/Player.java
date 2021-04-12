/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame;

import java.util.LinkedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
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
public class Player extends StackPane {
    private Circle playerIcon;
    private ImageView playerTreasure;
    private Text playerTreasuresRemainingText;
    private int playerTreasuresRemaining;
    
    private int number;
    private PlayerCharacter character;
    private Tile currentTile;
    
    private final int PLAYER_ICON_RADIUS = 30;
    private final int UNSELECTED_STROKE = 1;
    private final int SELECTED_STROKE = 3;
    
    public enum Phase
    {
        placingTile,
        moving
    };
    private Phase currentPhase;
    
    private LinkedList<Treasure> treasures;
    private Treasure currentTreasure;
    
    public Player(int playerNumber)
    {
        number = playerNumber;
        currentPhase = Phase.placingTile;
        character = new PlayerCharacter(playerNumber);
        currentTile = null;
        
        setupIcon();
    }
    
    private void setupIcon()
    {
        playerIcon = new Circle();
        playerIcon.setRadius(PLAYER_ICON_RADIUS);
        playerIcon.setStroke(Color.BLACK);
        playerIcon.setStrokeWidth(UNSELECTED_STROKE);
        switch (number)
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
        
        AnchorPane treasuresRemainingPane = new AnchorPane();
        
        Circle textBackground = new Circle();
        textBackground.setFill(Color.WHITE);
        textBackground.setLayoutX(75);
        textBackground.setLayoutY(30);
        textBackground.setRadius(10);
        textBackground.setStroke(Color.BLACK);
        textBackground.setStrokeWidth(1);
        treasuresRemainingPane.getChildren().add(textBackground);
        
        playerTreasuresRemaining = 0;
        playerTreasuresRemainingText = new Text();
        playerTreasuresRemainingText.setLayoutX(71);
        playerTreasuresRemainingText.setLayoutY(30);
        playerTreasuresRemainingText.setText("" + playerTreasuresRemaining);
        playerTreasuresRemainingText.setTextAlignment(TextAlignment.CENTER);
        playerTreasuresRemainingText.setTextOrigin(VPos.CENTER);
        treasuresRemainingPane.getChildren().add(playerTreasuresRemainingText);
        
        getChildren().add(treasuresRemainingPane);
        setAlignment(treasuresRemainingPane, Pos.TOP_RIGHT);
        
        treasures = new LinkedList<Treasure>();
        playerTreasure = new ImageView();
        getChildren().add(playerTreasure);
    }
    
    public int getPlayerNumber()
    {
        return number;
    }
    
    public void assignTreasure(Treasure treasure)
    {
        treasures.add(treasure);
        playerTreasuresRemainingText.setText("" + ++playerTreasuresRemaining);
    }
    
    public void showNextTreasure()
    {
        currentTreasure = treasures.poll();
        if (currentTreasure != null)
        {
            playerTreasure.setImage(currentTreasure.getPlayerTreasureImage());
        }
        else 
        {
            playerTreasure.setImage(null);
        }
    }
    
    public void showPaths()
    {
        if (currentTile != null)
        {
            currentTile.showPaths(number);
        }
        else {}
    }
    
    private void hidePaths()
    {
        if (currentTile != null)
        {
            currentTile.hidePaths();
        }
        else {}
    }
    
    public void moveCharacter(Tile tile)
    {
        hidePaths();
        if (currentTile != null)
        {
            currentTile.removePlayerCharacter(character);
        }
        else {}
        currentTile = tile;
        currentTile.addPlayerCharacter(character);
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
