/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.gui;

import labyrinthboardgame.logic.Tile;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import labyrinthboardgame.logic.Player;
import labyrinthboardgame.logic.TileSet;

/**
 *
 * @author Corbi
 */
public class GameBoardController implements Initializable {
    
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
    
    private ArrayList<Player> players;
    private int currentPlayer;
    @FXML
    private ImageView playerWonImageView;
    @FXML
    private Label playerWonText;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        currentPlayer = 0;
        players = new ArrayList<Player>();
    }
    
    public void setupBoard(TileSet tileSet, int players)
    {
        gameBoard.setGameBoardController(this);
        gameBoard.setupTiles(tileSet);
        updateNextTile();
    }
    
    public void addPlayer(int playerNumber, Player player)
    {
        switch (playerNumber)
        {
            case 1:
                player1Pane.getChildren().add(player.getDisplay());
                break;
            case 2:
                player2Pane.getChildren().add(player.getDisplay());
                break;
            case 3:
                player3Pane.getChildren().add(player.getDisplay());
                break;
            case 4:
                player4Pane.getChildren().add(player.getDisplay());
                break;
        }
        gameBoard.addPlayerCharacterToBoard(player);
        players.add(player);
        players.get(currentPlayer).setActive();
    }
    
    private void switchPlayers()
    {
        if (!players.get(currentPlayer).getHasWon())
        {
            players.get(currentPlayer).setInactive();
            currentPlayer = (currentPlayer + 1) % players.size();
            players.get(currentPlayer).setActive();
        }
        else 
        {
            playerWonImageView.setVisible(true);
            playerWonText.setVisible(true);
            switch (currentPlayer)
            {
                case 1:
                    playerWonText.setText("Yellow Player Wins!");
                    break;
                case 2:
                    playerWonText.setText("Blue Player Wins!");
                    break;
                case 3:
                    playerWonText.setText("Green Player Wins!");
                    break;
                case 4:
                    playerWonText.setText("Red Player Wins!");
                    break;
            }
        }
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
        nextTilePane.getChildren().add(nextTile.getTileView());
    }
    
    public void showPaths()
    {
        players.get(currentPlayer).showPaths();
    }
    
    public void movedPlayer()
    {
        switchPlayers();
    }
    
    public Player getCurrentPlayer()
    {
        return players.get(currentPlayer);
    }
}
