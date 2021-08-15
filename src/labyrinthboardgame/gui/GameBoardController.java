/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import labyrinthboardgame.logic.ConfigurationManager;
import labyrinthboardgame.logic.GUIConnector;
import labyrinthboardgame.logic.Game;
import labyrinthboardgame.logic.GameSaver;
import labyrinthboardgame.logic.Player;
import labyrinthboardgame.logic.Treasure;

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
    @FXML
    private VBox tileMovementDisplay;
    @FXML
    private TextField tileMovementTextField;
    @FXML
    private VBox characterMovementDisplay;
    @FXML
    private TextField characterMovementTextField;
    @FXML
    private VBox controlsDisplay;
    @FXML
    private VBox aboutDisplay;
    
    private SceneController sceneController;
    
    private GUIConnector connector;
    private Game game;
    
    /**
     * Initializes
     * @param location Unused
     * @param resources Unused
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        // Restrict these fields to only integers
        UnaryOperator<Change> tFilter = tChange -> {
            String tText = tChange.getText();
            if (tText.matches("[0-9]*"))
            {
                return tChange;
            }
            else {}
            return null;
        };
        TextFormatter<String> tTextFormatter = new TextFormatter<>(tFilter);
        tileMovementTextField.setTextFormatter(tTextFormatter);
        UnaryOperator<Change> cFilter = cChange -> {
            String cText = cChange.getText();
            if (cText.matches("[0-9]*"))
            {
                return cChange;
            }
            else {}
            return null;
        };
        TextFormatter<String> cTextFormatter = new TextFormatter<>(cFilter);
        characterMovementTextField.setTextFormatter(cTextFormatter);
    }
    
    /**
     * Setups the game with the players and treasures picked
     * @param sc Pointer to the controller for controlling the scene
     * @param players The players in the game
     * @param treasureCount The amount of treasures per player
     * @param connector Connects the logic and GUI packages
     */
    public void setupController(SceneController sc, Player[] players, int treasureCount, GUIConnector connector)
    {
        sceneController = sc;
        this.connector = connector;
        connector.setupBoard(this);
        game = new Game(players, treasureCount, connector);
        gameBoardView.setupArrows(game);
    }
    
    /**
     * Setups the game with the players and treasures picked
     * @param sc Pointer to the controller for controlling the scene
     * @param loadedGame A game loaded from a save
     */
    public void setupController(SceneController sc, Game loadedGame)
    {
        sceneController = sc;
        game = loadedGame;
        gameBoardView.setupArrows(game);
    }
    
    /**
     * Kills all timers used by AIs
     */
    public void killAllTimers()
    {
        if (game != null)
        {
            game.killAllTimers();
        }
        else {}
    }
    
    /**
     * Displays the winning player
     * @param playerName The winning player's name
     */
    public void setWinningPlayer(String playerName)
    {
        playerWonDisplay.setVisible(true);
        playerWonText.setText(playerName + " Wins!");
    }
    
    /**
     * Returns the BoardView
     * @return The BoardView
     */
    public BoardView getGameBoardView()
    {
        return gameBoardView;
    }
    
    /**
     * Returns the PlayerIconTray
     * @return The PlayerIconTray
     */
    public PlayerIconTray getPlayerIconTray()
    {
        return playerIconTray;
    }
    
    /**
     * Returns a reference to the game
     * @return A reference to the game
     */
    public Game getGame()
    {
        return game;
    }
    
    /**
     * Updates the next tile previews when rotated
     */
    public void rotateTileClockwise()
    {
        connector.rotatePreviewTileClockwise();
    }
    
    /**
     * Updates the next tile previews when rotated
     */
    public void rotateTileCounterClockwise()
    {
        connector.rotatePreviewTileCounterClockwise();
    }
    
    /**
     * Updates the player icon tray with the current treasure
     * @param treasure The current treasure
     * @param playerColor The current player's color
     */
    public void updateCurrentTreasure(Treasure treasure, Color playerColor)
    {
        playerIconTray.updateCurrentTreasure(treasure, playerColor);
    }
    
    /**
     * Makes the current treasure visible in the tray
     */
    public void showTreasure()
    {
        playerIconTray.showTreasure();
        connector.highlightTreasure(game.getTreasureTile());
    }
    
    /**
     * Makes the current treasure invisible in the tray
     */
    public void hideTreasure()
    {
        playerIconTray.hideTreasure();
        connector.unhighlightTreasure(game.getTreasureTile());
    }
    
    /**
     * Moves to the Main Menu Scene
     * @param e Unused
     */
    public void moveToMainMenuScene(ActionEvent e)
    {
        try
        {
            if (game != null)
            {
                game.killAllTimers();
            }
            else {}
            sceneController.moveToMainMenuScene();
        }
        catch (IOException ex)
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
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Game");
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JSON save", "*.json"));
        File save = fileChooser.showSaveDialog(sceneController.getStage());
        
        if (save != null)
        {
            GameSaver.saveGame(save, game);
        }
        else {}
    }
    
    /**
     * Opens the dialog to load a game
     * @param e Unused
     */
    public void load(ActionEvent e)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Saved Game");
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JSON save", "*.json"));
        File open = fileChooser.showOpenDialog(sceneController.getStage());
        
        if (open != null)
        {
            try
            {
                sceneController.moveToGameScene(open);
            }
            catch (IOException ex)
            {
                System.out.println("Error! Failed to move to Game Screen.");
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
    
    /**
     * Closes any open dialogs
     */
    private void closeDialogs()
    {
        cancelCharacterMovement(null);
        cancelTileMovement(null);
        closeControls(null);
        closeAbout(null);
    }
    
    /**
     * Opens the menu to adjust the animation speed of tiles
     * @param e Unused
     */
    public void tileMovement(ActionEvent e)
    {
        closeDialogs();
        tileMovementTextField.setText("" + ConfigurationManager.TILE_ANIMATION_SPEED);
        tileMovementDisplay.setVisible(true);
    }
    
    /**
     * Accepts the new value for tile movement speed and closes the window
     * @param e Unused
     */
    public void acceptTileMovement(ActionEvent e)
    {
        tileMovementDisplay.setVisible(false);
        int speed = Integer.parseInt(tileMovementTextField.getText());
        ConfigurationManager.TILE_ANIMATION_SPEED = speed;
        ConfigurationManager.saveConfiguration();
    }
    
    /**
     * Cancels the new value for tile movement speed and closes the window
     * @param e Unused
     */
    public void cancelTileMovement(ActionEvent e)
    {
        tileMovementDisplay.setVisible(false);
    }
    
    /**
     * Opens the menu to adjust the animation speed of characters
     * @param e Unused
     */
    public void characterMovement(ActionEvent e)
    {
        closeDialogs();
        characterMovementTextField.setText("" + ConfigurationManager.CHARACTER_ANIMATION_SPEED);
        characterMovementDisplay.setVisible(true);
    }
    
    /**
     * Accepts the new value for tile movement speed and closes the window
     * @param e Unused
     */
    public void acceptCharacterMovement(ActionEvent e)
    {
        characterMovementDisplay.setVisible(false);
        int speed = Integer.parseInt(characterMovementTextField.getText());
        ConfigurationManager.CHARACTER_ANIMATION_SPEED = speed;
        ConfigurationManager.saveConfiguration();
    }
    
    /**
     * Cancels the new value for tile movement speed and closes the window
     * @param e Unused
     */
    public void cancelCharacterMovement(ActionEvent e)
    {
        characterMovementDisplay.setVisible(false);
    }
    
    /**
     * Opens the dialog to display the controls
     * @param e Unused
     */
    public void controls(ActionEvent e)
    {
        closeDialogs();
        controlsDisplay.setVisible(true);
    }
    
    /**
     * Closes the dialog to display the controls
     * @param e Unused
     */
    public void closeControls(ActionEvent e)
    {
        controlsDisplay.setVisible(false);
    }
    
    /**
     * Opens the dialog to display about the game
     * @param e Unused
     */
    public void about(ActionEvent e)
    {
        closeDialogs();
        aboutDisplay.setVisible(true);
    }
    
    /**
     * Closes the dialog to display about the game
     * @param e Unused
     */
    public void closeAbout(ActionEvent e)
    {
        aboutDisplay.setVisible(false);
    }
}
