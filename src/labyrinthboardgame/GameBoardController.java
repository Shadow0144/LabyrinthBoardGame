/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 *
 * @author Corbi
 */
public class GameBoardController implements Initializable {
    
    @FXML
    private AnchorPane gamePane;
    @FXML
    private Button menuButton;
    @FXML
    private Text titleText;
    @FXML
    private AnchorPane boardPane;
    @FXML
    private HBox playerHBox;
    @FXML
    private Pane player1Pane;
    @FXML
    private Pane player2Pane;
    @FXML
    private Pane player3Pane;
    @FXML
    private Pane player4Pane;
    @FXML
    private Pane nextTilePane;
    @FXML
    private LabyrinthGameBoard gameBoard;
    
    private Tile nextTile;
    
    private int currentPlayer;
    private final int PLAYERS = 2;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        player1Pane.getChildren().add(new Player(1));
        player2Pane.getChildren().add(new Player(2));
        
        currentPlayer = -1;
        switchPlayers();
    }
    
    public void setupBoard(TileSet tileSet)
    {
        gameBoard.setGameBoardController(this);
        gameBoard.setupTiles(tileSet);
        updateNextTile();
    }
    
    private void switchPlayers()
    {
        currentPlayer = (currentPlayer + 1) % PLAYERS;
        /*switch (currentPlayer)
        {
            case 0:
                player1Icon.setStrokeWidth(3);
                player2Icon.setStrokeWidth(1);
                break;
            case 1:
                player1Icon.setStrokeWidth(1);
                player2Icon.setStrokeWidth(3);
                break;
        }*/
    }
    
    public void rotateTileClockwise()
    {
        nextTile.rotateClockwise();
        gameBoard.rotatePreviewClockwise();
    }
    
    public void rotateTileCounterClockwise()
    {
        nextTile.rotateCounterClockwise();
        gameBoard.rotatePreviewCounterClockwise();
    }
    
    public void updateNextTile()
    {
        nextTile = gameBoard.getNextTile();
        nextTilePane.getChildren().clear();
        nextTilePane.getChildren().add(nextTile);
    }
}
