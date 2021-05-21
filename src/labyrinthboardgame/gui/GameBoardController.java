/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.gui;

import java.io.File;
import labyrinthboardgame.logic.Tile;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import labyrinthboardgame.logic.Game;
import labyrinthboardgame.logic.Player;

/**
 *
 * @author Corbi
 */
public final class GameBoardController implements Initializable
{ 
    @FXML
    private PlayerIconTray playerIconTray;
    @FXML
    private VBox playerWonDisplay;
    @FXML
    private Label playerWonText;
    @FXML
    private BoardView gameBoardView;
    
    private SceneController sceneController;
    
    private Game game;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        
    }
    
    /**
     * Setups the game with the players and treasures picked
     * @param sc Pointer to the controller for controlling the scene
     * @param players The players in the game
     * @param treasureCount The amount of treasures per player
     */
    public void setupController(SceneController sc, Player[] players, int treasureCount)
    {
        sceneController = sc;
        game = new Game(this, players, treasureCount, gameBoardView, playerIconTray);
        gameBoardView.setupArrows(game);
    }
    
    public void setWinningPlayer(int player)
    {
        playerWonDisplay.setVisible(true);
        switch (player) // Current player (as opposed to player number) starts at 0
        {
            case 0:
                playerWonText.setText("Yellow Player Wins!");
                break;
            case 1:
                playerWonText.setText("Blue Player Wins!");
                break;
            case 2:
                playerWonText.setText("Green Player Wins!");
                break;
            case 3:
                playerWonText.setText("Red Player Wins!");
                break;
        }
    }
    
    /**
     * Updates the next tile previews when rotated
     */
    public void rotateTileClockwise()
    {
        game.rotateNextTileClockwise();
    }
    
    /**
     * Updates the next tile previews when rotated
     */
    public void rotateTileCounterClockwise()
    {
        game.rotateNextTileCounterClockwise();
    }
    
    /**
     * Updates the player icon tray to display the next tile
     */
    public void updateNextTile()
    {
        /*nextTile = gameBoard.getNextTile();
        playerIconTray.updateNextTile(nextTile);*/
    }
    
    /**
     * Moves to the Main Menu Scene
     * @param e Unused
     */
    public void moveToMainMenuScene(ActionEvent e)
    {
        try
        {
            sceneController.moveToMainMenuScene();
        }
        catch (Exception ex)
        {
            System.out.println("Error! Failed to move to Main Menu Screen.");
        }
    }
    
    /**
     * Opens the dialog to save the game
     * @param e Unused
     */
    public void save(ActionEvent e)
    {
        
    }
    
    /**
     * Opens the dialog to load a game
     * @param e Unused
     */
    public void load(ActionEvent e)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JSON save", "*.json"));
        File save = fileChooser.showOpenDialog(sceneController.getStage());
        if (save != null)
        {
            try
        {
            sceneController.moveToGameScene(save);
        }
        catch (Exception ex)
        {
            System.out.println("Error! Failed to move to Main Menu Screen.");
            System.out.println(ex);
        }
        }
        else {}
    }
    
    /**
     * Quits the program
     * @param e Unused
     */
    public void exit(ActionEvent e)
    {
        System.exit(0);
    }
}
