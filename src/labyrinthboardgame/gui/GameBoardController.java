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
import labyrinthboardgame.logic.Player;
import labyrinthboardgame.logic.TileSet;

/**
 *
 * @author Corbi
 */
public class GameBoardController implements Initializable
{ 
    @FXML
    private PlayerIconTray playerIconTray;
    @FXML
    private Tile nextTile;
    @FXML
    private ImageView playerWonImageView;
    @FXML
    private Label playerWonText;
    @FXML
    private LabyrinthGameBoard gameBoard;
    
    private ArrayList<Player> players;
    private int currentPlayer;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        currentPlayer = 0;
        players = new ArrayList<Player>();
    }
    
    /**
     * Sets up the game board, filling in the tiles
     * @param tileSet The set of tiles to fill the game board with
     */
    public void setupBoard(TileSet tileSet)
    {
        gameBoard.setGameBoardController(this);
        gameBoard.setupTiles(tileSet);
        updateNextTile();
    }
    
    /**
     * Stores a reference to a player,
     * links the player to its icon and adds the player's character to the game board,
     * and sets the current player (i.e. player 1) as active
     * @param player - the player to store and update
     */
    public void addPlayer(Player player)
    {
        player.setIcon(playerIconTray.getIcon(player.getPlayerNumber()));
        gameBoard.addPlayerCharacterToBoard(player);
        players.add(player);
        players.get(currentPlayer).setActive();
    }
    
    /**
     * Checks if any players have won and displays a message if they have
     * Otherwise switches the next player to active
     */
    public void switchPlayers()
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
    
    /**
     * Updates the next tile previews when rotated
     */
    public void rotateTileClockwise()
    {
        nextTile.rotateClockwise();
        gameBoard.rotatePreviewClockwise();
    }
    
    /**
     * Updates the next tile previews when rotated
     */
    public void rotateTileCounterClockwise()
    {
        nextTile.rotateCounterClockwise();
        gameBoard.rotatePreviewCounterClockwise();
    }
    
    /**
     * Updates the player icon tray to display the next tile
     */
    public void updateNextTile()
    {
        nextTile = gameBoard.getNextTile();
        playerIconTray.updateNextTile(nextTile);
    }
    
    /**
     * Enables drawing paths available to the current player
     */
    public void showPaths()
    {
        players.get(currentPlayer).showPaths();
    }
    
    public Player getCurrentPlayer()
    {
        return players.get(currentPlayer);
    }
}
