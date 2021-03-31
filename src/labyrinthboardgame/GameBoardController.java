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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

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
    
    private Image nextTileImage;
    
    private boolean[] setTiles;
    
    private int currentRotation;
    
    private ImageView previewTile;
    
    private Random random;
    
    private int currentPlayer;
    private final int PLAYERS = 2;
    
    @FXML
    private void addHighlight(MouseEvent event) 
    {
        //System.out.println("You entered me!");
        ImageView iView = ((ImageView)event.getSource());
        int r = Character.getNumericValue(iView.getId().charAt(9));
        int c = Character.getNumericValue(iView.getId().charAt(10));
        int index = r*7+c;
        if (!setTiles[index])
        {
            iView.setImage(nextTileImage);
            iView.setOpacity(0.5);
            previewTile = iView;
        }
        else {}
    }
    
    @FXML
    private void removeHighlight(MouseEvent event) 
    {
        //System.out.println("You exited me!");
        ImageView iView = ((ImageView)event.getSource());
        int r = Character.getNumericValue(iView.getId().charAt(9));
        int c = Character.getNumericValue(iView.getId().charAt(10));
        int index = r*7+c;
        if (!setTiles[index])
        {
            iView.setImage(null);
            iView.setOpacity(1.0);
            previewTile = null;
        }
        else {}
    }
    
    @FXML
    private void addTile(MouseEvent event) 
    {
        //System.out.println("You clicked me!");
        ImageView iView = ((ImageView)event.getSource());
        int r = Character.getNumericValue(iView.getId().charAt(9));
        int c = Character.getNumericValue(iView.getId().charAt(10));
        int index = r*7+c;
        if (!setTiles[index])
        {
            iView.setImage(nextTileImage);
            iView.setOpacity(1.0);
            iView.setRotate(currentRotation);
            setTiles[index] = true;
            previewTile = null;
            chooseNextTile();
            switchPlayers();
        }
        else {}
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {  
        tLocation = getClass().getResource("assets/T.png").toString();
        tImage = new Image(tLocation,90,90,false,true);
        iLocation = getClass().getResource("assets/I.png").toString();
        iImage = new Image(iLocation,90,90,false,true);
        lLocation = getClass().getResource("assets/L.png").toString();
        lImage = new Image(lLocation,90,90,false,true);
        
        random = new Random();
        chooseNextTile();
        
        setTiles = new boolean[49];
    
        previewTile = null;
        
        currentPlayer = -1;
        switchPlayers();
    }

    private void chooseNextTile()
    {
        int rand = random.nextInt(3);
            switch (rand)
            {
                case 0:
                    nextTileImage = tImage;
                    break;
                case 1:
                    nextTileImage = lImage;
                    break;
                case 2:
                    nextTileImage = iImage;
                    break;
            }
        currentTileImageView.setImage(nextTileImage);
        currentRotation = 0;
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
        // Rotate the preview if it's available
        if (previewTile != null)
        {
            previewTile.setRotate(currentRotation);
        }
    }
    
    public void rotateTileCounterClockwise()
    {
        //System.out.println("You unrotated me!");
        currentRotation = (currentRotation - 90) % 360;
        currentTileImageView.setRotate(currentRotation);
        // Rotate the preview if it's available
        if (previewTile != null)
        {
            previewTile.setRotate(currentRotation);
        }
    }
}
