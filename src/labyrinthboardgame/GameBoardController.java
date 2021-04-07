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
    
    /*private void addPreviewTL(MouseEvent event) 
    {
        previewImageTL.setImage(gameboard.getNextTileImage());
        previewTile = previewImageTL;
        previewTreasureImageTL.setImage(gameboard.getNextTreasureImage());
        previewTreasure = previewTreasureImageTL;
    }
    
    private void addPreviewTC(MouseEvent event) 
    {
        previewImageTC.setImage(gameboard.getNextTileImage());
        previewTile = previewImageTC;
        previewTreasureImageTC.setImage(gameboard.getNextTreasureImage());
        previewTreasure = previewTreasureImageTC;
    }
    
    private void addPreviewTR(MouseEvent event) 
    {
        previewImageTR.setImage(gameboard.getNextTileImage());
        previewTile = previewImageTR;
        previewTreasureImageTR.setImage(gameboard.getNextTreasureImage());
        previewTreasure = previewTreasureImageTR;
    }
    
    private void addPreviewLT(MouseEvent event) 
    {
        previewImageLT.setImage(gameboard.getNextTileImage());
        previewTile = previewImageLT;
        previewTreasureImageLT.setImage(gameboard.getNextTreasureImage());
        previewTreasure = previewTreasureImageLT;
    }
    
    private void addPreviewLC(MouseEvent event) 
    {
        previewImageLC.setImage(gameboard.getNextTileImage());
        previewTile = previewImageLC;
        previewTreasureImageLC.setImage(gameboard.getNextTreasureImage());
        previewTreasure = previewTreasureImageLC;
    }
    
    private void addPreviewLB(MouseEvent event) 
    {
        previewImageLB.setImage(gameboard.getNextTileImage());
        previewTile = previewImageLB;
        previewTreasureImageLB.setImage(gameboard.getNextTreasureImage());
        previewTreasure = previewTreasureImageLB;
    }
    
    private void addPreviewBL(MouseEvent event) 
    {
        previewImageBL.setImage(gameboard.getNextTileImage());
        previewTile = previewImageBL;
        previewTreasureImageBL.setImage(gameboard.getNextTreasureImage());
        previewTreasure = previewTreasureImageBL;
    }
    
    private void addPreviewBC(MouseEvent event) 
    {
        previewImageBC.setImage(gameboard.getNextTileImage());
        previewTile = previewImageBC;
        previewTreasureImageBC.setImage(gameboard.getNextTreasureImage());
        previewTreasure = previewTreasureImageBC;
    }
    
    private void addPreviewBR(MouseEvent event) 
    {
        previewImageBR.setImage(gameboard.getNextTileImage());
        previewTile = previewImageBR;
        previewTreasureImageBR.setImage(gameboard.getNextTreasureImage());
        previewTreasure = previewTreasureImageBR;
    }
    
    private void addPreviewRT(MouseEvent event) 
    {
        previewImageRT.setImage(gameboard.getNextTileImage());
        previewTile = previewImageRT;
        previewTreasureImageRT.setImage(gameboard.getNextTreasureImage());
        previewTreasure = previewTreasureImageRT;
    }
    
    private void addPreviewRC(MouseEvent event) 
    {
        previewImageRC.setImage(gameboard.getNextTileImage());
        previewTile = previewImageRC;
        previewTreasureImageRC.setImage(gameboard.getNextTreasureImage());
        previewTreasure = previewTreasureImageRC;
    }
    
    private void addPreviewRB(MouseEvent event) 
    {
        previewImageRB.setImage(gameboard.getNextTileImage());
        previewTile = previewImageRB;
        previewTreasureImageRB.setImage(gameboard.getNextTreasureImage());
        previewTreasure = previewTreasureImageRB;
    }
    
    private void removePreview(MouseEvent event) 
    {
        previewTile.setImage(null);
        previewTile = null;
        previewTreasureImageTL.setImage(null);
        previewTreasureImageTL = null;
    }*/
    
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
        gameBoard.setupTiles(tileSet);
        nextTile = tileSet.getNextTile();
        nextTilePane.getChildren().add(nextTile);
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
    }
    
    public void rotateTileCounterClockwise()
    {
        nextTile.rotateCounterClockwise();
    }

    /*private void placeTileTopLeft(MouseEvent event)
    {
        gameboard.placeTileTopLeft();
    }
    
    private void placeTileTopCenter(MouseEvent event)
    {
        gameboard.placeTileTopCenter();
    }
    
    private void placeTileTopRight(MouseEvent event)
    {
        gameboard.placeTileTopRight();
    }
    
    private void placeTileLeftTop(MouseEvent event)
    {
        gameboard.placeTileLeftTop();
    }
    
    private void placeTileLeftCenter(MouseEvent event)
    {
        gameboard.placeTileLeftCenter();
    }
    
    private void placeTileLeftBottom(MouseEvent event)
    {
        gameboard.placeTileLeftBottom();
    }
    
    private void placeTileBottomLeft(MouseEvent event)
    {
        gameboard.placeTileBottomLeft();
    }
    
    private void placeTileBottomCenter(MouseEvent event)
    {
        gameboard.placeTileBottomCenter();
    }
    
    private void placeTileBottomRight(MouseEvent event)
    {
        gameboard.placeTileBottomRight();
    }
    
    private void placeTileRightTop(MouseEvent event)
    {
        gameboard.placeTileRightTop();
    }
    
    private void placeTileRightCenter(MouseEvent event)
    {
        gameboard.placeTileRightCenter();
    }
    
    private void placeTileRightBottom(MouseEvent event)
    {
        gameboard.placeTileRightBottom();
    }*/
}
