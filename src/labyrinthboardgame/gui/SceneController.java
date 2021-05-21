/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.gui;

import java.io.File;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import labyrinthboardgame.logic.Game;
import labyrinthboardgame.logic.GameLoader;
import labyrinthboardgame.logic.GameSaver;
import labyrinthboardgame.logic.Player;

/**
 *
 * @author Corbi
 */
public final class SceneController 
{
    private final Stage currentStage;
    
    public SceneController(Stage stage)
    {
        currentStage = stage;
    }
    
    public Stage getStage()
    {
        return currentStage;
    }
    
    /**
     * Changes the current scene to be the main menu scene
     * @throws Exception 
     */
    public void moveToMainMenuScene() throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        MainMenuController controller = loader.getController();
        controller.setSceneController(this);
        currentStage.setScene(scene);
    }
    
    /**
     * Changes the current scene to be the player select scene
     * @throws Exception 
     */
    public void moveToPlayerSelectScene() throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PlayerSelect.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        PlayerSelectController controller = loader.getController();
        controller.setSceneController(this);
        currentStage.setScene(scene);
    }
    
    /**
     * Changes the current scene to be the game scene
     * Sets up the board, treasures, and players
     * @param players The players in this game
     * @param treasures The number of treasures per player
     * @throws Exception 
     */
    public void moveToGameScene(Player[] players, int treasures) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameBoard.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        GameBoardController controller = loader.getController();
        controller.setupController(this, players, treasures);
        
        // Create a listener for handling rotating tiles with the keyboard
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode()== KeyCode.R) 
            {
                if (key.isShiftDown())
                {
                    controller.rotateTileCounterClockwise();
                }
                else
                {
                    controller.rotateTileClockwise();
                }
            }
        });
        
        currentStage.setScene(scene);
    }
    
    /**
     * Changes the current scene to be the game scene
     * Sets up the board, treasures, and players from a save file
     * @param save The save to load from
     * @throws Exception 
     */
    public void moveToGameScene(File save) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameBoard.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        GameBoardController controller = loader.getController();
        
        Game game = GameLoader.loadGame(save.toString(), controller);
        controller.setupController(this, game);
        
        // Create a listener for handling rotating tiles with the keyboard
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode()== KeyCode.R) 
            {
                if (key.isShiftDown())
                {
                    controller.rotateTileCounterClockwise();
                }
                else
                {
                    controller.rotateTileClockwise();
                }
            }
        });
        
        currentStage.setScene(scene);
    }
}
