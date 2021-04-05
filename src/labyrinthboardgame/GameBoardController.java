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
    private ImageView currentTileImageView;
    @FXML
    private Circle player1Icon;
    @FXML
    private Circle player2Icon;
    
    private String tLocation;
    private Image tImage;
    private String iLocation;
    private Image iImage;
    private String lLocation;
    private Image lImage;
    
    private boolean[] setTiles;
    
    private int currentRotation;
    
    private ImageView previewTile;
    private ImageView previewTreasure;
    
    private Random random;
    
    private int currentPlayer;
    private final int PLAYERS = 2;
    
    @FXML
    private AnchorPane gamePane;
    @FXML
    private Button menuButton;
    @FXML
    private Text titleText;
    @FXML
    private AnchorPane boardPane;
    @FXML
    private GridPane gameGridPane;
    @FXML
    private ImageView imageView00;
    @FXML
    private ImageView imageView01;
    @FXML
    private ImageView imageView02;
    @FXML
    private ImageView imageView03;
    @FXML
    private ImageView imageView04;
    @FXML
    private ImageView imageView05;
    @FXML
    private ImageView imageView06;
    @FXML
    private ImageView imageView10;
    @FXML
    private ImageView imageView11;
    @FXML
    private ImageView imageView12;
    @FXML
    private ImageView imageView13;
    @FXML
    private ImageView imageView14;
    @FXML
    private ImageView imageView15;
    @FXML
    private ImageView imageView16;
    @FXML
    private ImageView imageView20;
    @FXML
    private ImageView imageView21;
    @FXML
    private ImageView imageView22;
    @FXML
    private ImageView imageView23;
    @FXML
    private ImageView imageView24;
    @FXML
    private ImageView imageView25;
    @FXML
    private ImageView imageView26;
    @FXML
    private ImageView imageView30;
    @FXML
    private ImageView imageView31;
    @FXML
    private ImageView imageView32;
    @FXML
    private ImageView imageView33;
    @FXML
    private ImageView imageView34;
    @FXML
    private ImageView imageView35;
    @FXML
    private ImageView imageView36;
    @FXML
    private ImageView imageView40;
    @FXML
    private ImageView imageView41;
    @FXML
    private ImageView imageView42;
    @FXML
    private ImageView imageView43;
    @FXML
    private ImageView imageView44;
    @FXML
    private ImageView imageView45;
    @FXML
    private ImageView imageView46;
    @FXML
    private ImageView imageView50;
    @FXML
    private ImageView imageView51;
    @FXML
    private ImageView imageView52;
    @FXML
    private ImageView imageView53;
    @FXML
    private ImageView imageView54;
    @FXML
    private ImageView imageView55;
    @FXML
    private ImageView imageView56;
    @FXML
    private ImageView imageView60;
    @FXML
    private ImageView imageView61;
    @FXML
    private ImageView imageView62;
    @FXML
    private ImageView imageView63;
    @FXML
    private ImageView imageView64;
    @FXML
    private ImageView imageView65;
    @FXML
    private ImageView imageView66;
    @FXML
    private ImageView previewImageTL;
    @FXML
    private ImageView insertButtonTL;
    @FXML
    private ImageView previewImageTC;
    @FXML
    private ImageView insertButtonTC;
    @FXML
    private ImageView previewImageTR;
    @FXML
    private ImageView insertButtonTR;
    @FXML
    private ImageView previewImageLT;
    @FXML
    private ImageView insertButtonLT;
    @FXML
    private ImageView previewImageLC;
    @FXML
    private ImageView insertButtonLC;
    @FXML
    private ImageView previewImageLB;
    @FXML
    private ImageView insertButtonLB;
    @FXML
    private ImageView previewImageRT;
    @FXML
    private ImageView insertButtonRT;
    @FXML
    private ImageView previewImageRC;
    @FXML
    private ImageView insertButtonRC;
    @FXML
    private ImageView previewImageRB;
    @FXML
    private ImageView insertButtonRB;
    @FXML
    private ImageView previewImageBL;
    @FXML
    private ImageView insertButtonBL;
    @FXML
    private ImageView previewImageBC;
    @FXML
    private ImageView insertButtonBC;
    @FXML
    private ImageView previewImageBR;
    @FXML
    private ImageView insertButtonBR;
    @FXML
    private Circle player1Start;
    @FXML
    private Circle player2Start;
    @FXML
    private Circle player3Start;
    @FXML
    private Circle player4Start;
    @FXML
    private HBox playerHBox;
    @FXML
    private Pane player1Pane;
    @FXML
    private ImageView player1Treasure;
    @FXML
    private Text player1TreasuresLeft;
    @FXML
    private Pane player2Pane;
    @FXML
    private ImageView player2Treasure;
    @FXML
    private Text player2TreasuresLeft;
    @FXML
    private Pane player3Pane;
    @FXML
    private Circle player3Icon;
    @FXML
    private ImageView player3Treasure;
    @FXML
    private Text player3TreasuresLeft;
    @FXML
    private Pane player4Pane;
    @FXML
    private Circle player4Icon;
    @FXML
    private ImageView player4Treasure;
    @FXML
    private Text player4TreasuresLeft;
    @FXML
    private ImageView treasureImageView01;
    @FXML
    private ImageView treasureImageView02;
    @FXML
    private ImageView treasureImageView03;
    @FXML
    private ImageView treasureImageView04;
    @FXML
    private ImageView treasureImageView05;
    @FXML
    private ImageView treasureImageView10;
    @FXML
    private ImageView treasureImageView11;
    @FXML
    private ImageView treasureImageView12;
    @FXML
    private ImageView treasureImageView13;
    @FXML
    private ImageView treasureImageView14;
    @FXML
    private ImageView treasureImageView15;
    @FXML
    private ImageView treasureImageView16;
    @FXML
    private ImageView treasureImageView20;
    @FXML
    private ImageView treasureImageView21;
    @FXML
    private ImageView treasureImageView22;
    @FXML
    private ImageView treasureImageView23;
    @FXML
    private ImageView treasureImageView24;
    @FXML
    private ImageView treasureImageView25;
    @FXML
    private ImageView treasureImageView26;
    @FXML
    private ImageView treasureImageView30;
    @FXML
    private ImageView treasureImageView31;
    @FXML
    private ImageView treasureImageView32;
    @FXML
    private ImageView treasureImageView33;
    @FXML
    private ImageView treasureImageView34;
    @FXML
    private ImageView treasureImageView35;
    @FXML
    private ImageView treasureImageView36;
    @FXML
    private ImageView treasureImageView40;
    @FXML
    private ImageView treasureImageView41;
    @FXML
    private ImageView treasureImageView42;
    @FXML
    private ImageView treasureImageView43;
    @FXML
    private ImageView treasureImageView44;
    @FXML
    private ImageView treasureImageView45;
    @FXML
    private ImageView treasureImageView46;
    @FXML
    private ImageView treasureImageView50;
    @FXML
    private ImageView treasureImageView51;
    @FXML
    private ImageView treasureImageView52;
    @FXML
    private ImageView treasureImageView53;
    @FXML
    private ImageView treasureImageView54;
    @FXML
    private ImageView treasureImageView55;
    @FXML
    private ImageView treasureImageView56;
    @FXML
    private ImageView treasureImageView61;
    @FXML
    private ImageView treasureImageView62;
    @FXML
    private ImageView treasureImageView63;
    @FXML
    private ImageView treasureImageView64;
    @FXML
    private ImageView treasureImageView65;
    @FXML
    private Pane nextTilePane;
    @FXML
    private ImageView currentTreasureImageView;
    
    private LabyrinthGameBoard gameboard;
    @FXML
    private ImageView previewTreasureImageTL;
    @FXML
    private ImageView previewTreasureImageTC;
    @FXML
    private ImageView previewTreasureImageTR;
    @FXML
    private ImageView previewTreasureImageLT;
    @FXML
    private ImageView previewTreasureImageLC;
    @FXML
    private ImageView previewTreasureImageLB;
    @FXML
    private ImageView previewTreasureImageBL;
    @FXML
    private ImageView previewTreasureImageBC;
    @FXML
    private ImageView previewTreasureImageBR;
    @FXML
    private ImageView previewTreasureImageRT;
    @FXML
    private ImageView previewTreasureImageRC;
    @FXML
    private ImageView previewTreasureImageRB;
    
    @FXML
    private void addPreviewTL(MouseEvent event) 
    {
        previewImageTL.setImage(gameboard.getNextTileImage());
        previewTile = previewImageTL;
        previewTreasureImageTL.setImage(gameboard.getNextTreasureImage());
        previewTreasure = previewTreasureImageTL;
    }
    
    @FXML
    private void addPreviewTC(MouseEvent event) 
    {
        previewImageTC.setImage(gameboard.getNextTileImage());
        previewTile = previewImageTC;
        previewTreasureImageTC.setImage(gameboard.getNextTreasureImage());
        previewTreasure = previewTreasureImageTC;
    }
    
    @FXML
    private void addPreviewTR(MouseEvent event) 
    {
        previewImageTR.setImage(gameboard.getNextTileImage());
        previewTile = previewImageTR;
        previewTreasureImageTR.setImage(gameboard.getNextTreasureImage());
        previewTreasure = previewTreasureImageTR;
    }
    
    @FXML
    private void addPreviewLT(MouseEvent event) 
    {
        previewImageLT.setImage(gameboard.getNextTileImage());
        previewTile = previewImageLT;
        previewTreasureImageLT.setImage(gameboard.getNextTreasureImage());
        previewTreasure = previewTreasureImageLT;
    }
    
    @FXML
    private void addPreviewLC(MouseEvent event) 
    {
        previewImageLC.setImage(gameboard.getNextTileImage());
        previewTile = previewImageLC;
        previewTreasureImageLC.setImage(gameboard.getNextTreasureImage());
        previewTreasure = previewTreasureImageLC;
    }
    
    @FXML
    private void addPreviewLB(MouseEvent event) 
    {
        previewImageLB.setImage(gameboard.getNextTileImage());
        previewTile = previewImageLB;
        previewTreasureImageLB.setImage(gameboard.getNextTreasureImage());
        previewTreasure = previewTreasureImageLB;
    }
    
    @FXML
    private void addPreviewBL(MouseEvent event) 
    {
        previewImageBL.setImage(gameboard.getNextTileImage());
        previewTile = previewImageBL;
        previewTreasureImageBL.setImage(gameboard.getNextTreasureImage());
        previewTreasure = previewTreasureImageBL;
    }
    
    @FXML
    private void addPreviewBC(MouseEvent event) 
    {
        previewImageBC.setImage(gameboard.getNextTileImage());
        previewTile = previewImageBC;
        previewTreasureImageBC.setImage(gameboard.getNextTreasureImage());
        previewTreasure = previewTreasureImageBC;
    }
    
    @FXML
    private void addPreviewBR(MouseEvent event) 
    {
        previewImageBR.setImage(gameboard.getNextTileImage());
        previewTile = previewImageBR;
        previewTreasureImageBR.setImage(gameboard.getNextTreasureImage());
        previewTreasure = previewTreasureImageBR;
    }
    
    @FXML
    private void addPreviewRT(MouseEvent event) 
    {
        previewImageRT.setImage(gameboard.getNextTileImage());
        previewTile = previewImageRT;
        previewTreasureImageRT.setImage(gameboard.getNextTreasureImage());
        previewTreasure = previewTreasureImageRT;
    }
    
    @FXML
    private void addPreviewRC(MouseEvent event) 
    {
        previewImageRC.setImage(gameboard.getNextTileImage());
        previewTile = previewImageRC;
        previewTreasureImageRC.setImage(gameboard.getNextTreasureImage());
        previewTreasure = previewTreasureImageRC;
    }
    
    @FXML
    private void addPreviewRB(MouseEvent event) 
    {
        previewImageRB.setImage(gameboard.getNextTileImage());
        previewTile = previewImageRB;
        previewTreasureImageRB.setImage(gameboard.getNextTreasureImage());
        previewTreasure = previewTreasureImageRB;
    }
    
    @FXML
    private void removePreview(MouseEvent event) 
    {
        previewTile.setImage(null);
        previewTile = null;
        previewTreasureImageTL.setImage(null);
        previewTreasureImageTL = null;
    }
    
    @FXML
    private void addHighlight(MouseEvent event) 
    {
        
    }
    
    @FXML
    private void removeHighlight(MouseEvent event) 
    {
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        previewTile = null;
        previewTreasure = null;
        
        currentPlayer = -1;
        switchPlayers();
    }
    
    public void setGameBoard(LabyrinthGameBoard board)
    {
        gameboard = board;
    }
    
    private void switchPlayers()
    {
        currentPlayer = (currentPlayer + 1) % PLAYERS;
        switch (currentPlayer)
        {
            case 0:
                player1Icon.setStrokeWidth(3);
                player2Icon.setStrokeWidth(1);
                break;
            case 1:
                player1Icon.setStrokeWidth(1);
                player2Icon.setStrokeWidth(3);
                break;
        }
    }
    
    public void rotateTileClockwise()
    {
        //System.out.println("You rotated me!");
        currentRotation = (currentRotation + 90) % 360;
        currentTileImageView.setRotate(currentRotation);
        //previewTile.setRotate(currentRotation);
        gameboard.setNextTileRotation(currentRotation);
    }
    
    public void rotateTileCounterClockwise()
    {
        //System.out.println("You unrotated me!");
        currentRotation = (currentRotation - 90) % 360;
        currentTileImageView.setRotate(currentRotation);
        //previewTile.setRotate(currentRotation);
        gameboard.setNextTileRotation(currentRotation);
    }

    @FXML
    private void placeTileTopLeft(MouseEvent event)
    {
        gameboard.placeTileTopLeft();
    }
    
    @FXML
    private void placeTileTopCenter(MouseEvent event)
    {
        gameboard.placeTileTopCenter();
    }
    
    @FXML
    private void placeTileTopRight(MouseEvent event)
    {
        gameboard.placeTileTopRight();
    }
    
    @FXML
    private void placeTileLeftTop(MouseEvent event)
    {
        gameboard.placeTileLeftTop();
    }
    
    @FXML
    private void placeTileLeftCenter(MouseEvent event)
    {
        gameboard.placeTileLeftCenter();
    }
    
    @FXML
    private void placeTileLeftBottom(MouseEvent event)
    {
        gameboard.placeTileLeftBottom();
    }
    
    @FXML
    private void placeTileBottomLeft(MouseEvent event)
    {
        gameboard.placeTileBottomLeft();
    }
    
    @FXML
    private void placeTileBottomCenter(MouseEvent event)
    {
        gameboard.placeTileBottomCenter();
    }
    
    @FXML
    private void placeTileBottomRight(MouseEvent event)
    {
        gameboard.placeTileBottomRight();
    }
    
    @FXML
    private void placeTileRightTop(MouseEvent event)
    {
        gameboard.placeTileRightTop();
    }
    
    @FXML
    private void placeTileRightCenter(MouseEvent event)
    {
        gameboard.placeTileRightCenter();
    }
    
    @FXML
    private void placeTileRightBottom(MouseEvent event)
    {
        gameboard.placeTileRightBottom();
    }
    
    @FXML
    private void moveToTile(MouseEvent event)
    {
        
    }
    
    public void setTiles(Tile[][] tiles, Tile nextTile)
    {
        // Row 1
        
        imageView00.setImage(tiles[0][0].getTileImage());
        imageView00.setRotate(tiles[0][0].getRotation());
        
        imageView01.setImage(tiles[0][1].getTileImage());
        imageView01.setRotate(tiles[0][1].getRotation());
        treasureImageView01.setImage(tiles[0][1].getTreasureImage());
        
        imageView02.setImage(tiles[0][2].getTileImage());
        imageView02.setRotate(tiles[0][2].getRotation());
        treasureImageView02.setImage(tiles[0][2].getTreasureImage());
        
        imageView03.setImage(tiles[0][3].getTileImage());
        imageView03.setRotate(tiles[0][3].getRotation());
        treasureImageView03.setImage(tiles[0][3].getTreasureImage());
        
        imageView04.setImage(tiles[0][4].getTileImage());
        imageView04.setRotate(tiles[0][4].getRotation());
        treasureImageView04.setImage(tiles[0][4].getTreasureImage());
        
        imageView05.setImage(tiles[0][5].getTileImage());
        imageView05.setRotate(tiles[0][5].getRotation());
        treasureImageView05.setImage(tiles[0][5].getTreasureImage());
        
        imageView06.setImage(tiles[0][6].getTileImage());
        imageView06.setRotate(tiles[0][6].getRotation());
        
        // Row 2
        
        imageView10.setImage(tiles[1][0].getTileImage());
        imageView10.setRotate(tiles[1][0].getRotation());
        treasureImageView10.setImage(tiles[1][0].getTreasureImage());
        
        imageView11.setImage(tiles[1][1].getTileImage());
        imageView11.setRotate(tiles[1][1].getRotation());
        treasureImageView11.setImage(tiles[1][1].getTreasureImage());
        
        imageView12.setImage(tiles[1][2].getTileImage());
        imageView12.setRotate(tiles[1][2].getRotation());
        treasureImageView12.setImage(tiles[1][2].getTreasureImage());
        
        imageView13.setImage(tiles[1][3].getTileImage());
        imageView13.setRotate(tiles[1][3].getRotation());
        treasureImageView13.setImage(tiles[1][3].getTreasureImage());
        
        imageView14.setImage(tiles[1][4].getTileImage());
        imageView14.setRotate(tiles[1][4].getRotation());
        treasureImageView14.setImage(tiles[1][4].getTreasureImage());
        
        imageView15.setImage(tiles[1][5].getTileImage());
        imageView15.setRotate(tiles[1][5].getRotation());
        treasureImageView15.setImage(tiles[1][5].getTreasureImage());
        
        imageView16.setImage(tiles[1][6].getTileImage());
        imageView16.setRotate(tiles[1][6].getRotation());
        treasureImageView16.setImage(tiles[1][6].getTreasureImage());
        
        // Row 3
        
        imageView20.setImage(tiles[2][0].getTileImage());
        imageView20.setRotate(tiles[2][0].getRotation());
        treasureImageView20.setImage(tiles[2][0].getTreasureImage());
        
        imageView21.setImage(tiles[2][1].getTileImage());
        imageView21.setRotate(tiles[2][1].getRotation());
        treasureImageView21.setImage(tiles[2][1].getTreasureImage());
        
        imageView22.setImage(tiles[2][2].getTileImage());
        imageView22.setRotate(tiles[2][2].getRotation());
        treasureImageView22.setImage(tiles[2][2].getTreasureImage());
        
        imageView23.setImage(tiles[2][3].getTileImage());
        imageView23.setRotate(tiles[2][3].getRotation());
        treasureImageView23.setImage(tiles[2][3].getTreasureImage());
        
        imageView24.setImage(tiles[2][4].getTileImage());
        imageView24.setRotate(tiles[2][4].getRotation());
        treasureImageView24.setImage(tiles[2][4].getTreasureImage());
        
        imageView25.setImage(tiles[2][5].getTileImage());
        imageView25.setRotate(tiles[2][5].getRotation());
        treasureImageView25.setImage(tiles[2][5].getTreasureImage());
        
        imageView26.setImage(tiles[2][6].getTileImage());
        imageView26.setRotate(tiles[2][6].getRotation());
        treasureImageView26.setImage(tiles[2][6].getTreasureImage());
        
        // Row 4
        
        imageView30.setImage(tiles[3][0].getTileImage());
        imageView30.setRotate(tiles[3][0].getRotation());
        treasureImageView30.setImage(tiles[3][0].getTreasureImage());
        
        imageView31.setImage(tiles[3][1].getTileImage());
        imageView31.setRotate(tiles[3][1].getRotation());
        treasureImageView31.setImage(tiles[3][1].getTreasureImage());
        
        imageView32.setImage(tiles[3][2].getTileImage());
        imageView32.setRotate(tiles[3][2].getRotation());
        treasureImageView32.setImage(tiles[3][2].getTreasureImage());
        
        imageView33.setImage(tiles[3][3].getTileImage());
        imageView33.setRotate(tiles[3][3].getRotation());
        treasureImageView33.setImage(tiles[3][3].getTreasureImage());
        
        imageView34.setImage(tiles[3][4].getTileImage());
        imageView34.setRotate(tiles[3][4].getRotation());
        treasureImageView34.setImage(tiles[3][4].getTreasureImage());
        
        imageView35.setImage(tiles[3][5].getTileImage());
        imageView35.setRotate(tiles[3][5].getRotation());
        treasureImageView35.setImage(tiles[3][5].getTreasureImage());
        
        imageView36.setImage(tiles[3][6].getTileImage());
        imageView36.setRotate(tiles[3][6].getRotation());
        treasureImageView36.setImage(tiles[3][6].getTreasureImage());
        
        // Row 5
        
        imageView40.setImage(tiles[4][0].getTileImage());
        imageView40.setRotate(tiles[4][0].getRotation());
        treasureImageView40.setImage(tiles[4][0].getTreasureImage());
        
        imageView41.setImage(tiles[4][1].getTileImage());
        imageView41.setRotate(tiles[4][1].getRotation());
        treasureImageView41.setImage(tiles[4][1].getTreasureImage());
        
        imageView42.setImage(tiles[4][2].getTileImage());
        imageView42.setRotate(tiles[4][2].getRotation());
        treasureImageView42.setImage(tiles[4][2].getTreasureImage());
        
        imageView43.setImage(tiles[4][3].getTileImage());
        imageView43.setRotate(tiles[4][3].getRotation());
        treasureImageView43.setImage(tiles[4][3].getTreasureImage());
        
        imageView44.setImage(tiles[4][4].getTileImage());
        imageView44.setRotate(tiles[4][4].getRotation());
        treasureImageView44.setImage(tiles[4][4].getTreasureImage());
        
        imageView45.setImage(tiles[4][5].getTileImage());
        imageView45.setRotate(tiles[4][5].getRotation());
        treasureImageView45.setImage(tiles[4][5].getTreasureImage());
        
        imageView46.setImage(tiles[4][6].getTileImage());
        imageView46.setRotate(tiles[4][6].getRotation());
        treasureImageView46.setImage(tiles[4][6].getTreasureImage());
        
        // Row 6
        
        imageView50.setImage(tiles[5][0].getTileImage());
        imageView50.setRotate(tiles[5][0].getRotation());
        treasureImageView50.setImage(tiles[5][0].getTreasureImage());
        
        imageView51.setImage(tiles[5][1].getTileImage());
        imageView51.setRotate(tiles[5][1].getRotation());
        treasureImageView51.setImage(tiles[5][1].getTreasureImage());
        
        imageView52.setImage(tiles[5][2].getTileImage());
        imageView52.setRotate(tiles[5][2].getRotation());
        treasureImageView52.setImage(tiles[5][2].getTreasureImage());
        
        imageView53.setImage(tiles[5][3].getTileImage());
        imageView53.setRotate(tiles[5][3].getRotation());
        treasureImageView53.setImage(tiles[5][3].getTreasureImage());
        
        imageView54.setImage(tiles[5][4].getTileImage());
        imageView54.setRotate(tiles[5][4].getRotation());
        treasureImageView54.setImage(tiles[5][4].getTreasureImage());
        
        imageView55.setImage(tiles[5][5].getTileImage());
        imageView55.setRotate(tiles[5][5].getRotation());
        treasureImageView55.setImage(tiles[5][5].getTreasureImage());
        
        imageView56.setImage(tiles[5][6].getTileImage());
        imageView56.setRotate(tiles[5][6].getRotation());
        treasureImageView56.setImage(tiles[5][6].getTreasureImage());
        
        // Row 7
        
        imageView60.setImage(tiles[6][0].getTileImage());
        imageView60.setRotate(tiles[6][0].getRotation());
        
        imageView61.setImage(tiles[6][1].getTileImage());
        imageView61.setRotate(tiles[6][1].getRotation());
        treasureImageView61.setImage(tiles[6][1].getTreasureImage());
        
        imageView62.setImage(tiles[6][2].getTileImage());
        imageView62.setRotate(tiles[6][2].getRotation());
        treasureImageView62.setImage(tiles[6][2].getTreasureImage());
        
        imageView63.setImage(tiles[6][3].getTileImage());
        imageView63.setRotate(tiles[6][3].getRotation());
        treasureImageView63.setImage(tiles[6][3].getTreasureImage());
        
        imageView64.setImage(tiles[6][4].getTileImage());
        imageView64.setRotate(tiles[6][4].getRotation());
        treasureImageView64.setImage(tiles[6][4].getTreasureImage());
        
        imageView65.setImage(tiles[6][5].getTileImage());
        imageView65.setRotate(tiles[6][5].getRotation());
        treasureImageView65.setImage(tiles[6][5].getTreasureImage());
        
        imageView66.setImage(tiles[6][6].getTileImage());
        imageView66.setRotate(tiles[6][6].getRotation());
        
        // Preview
        
        currentTileImageView.setImage(nextTile.getTileImage());
        currentTileImageView.setRotate(nextTile.getRotation());
        currentTreasureImageView.setImage(nextTile.getTreasureImage());
    }
}
