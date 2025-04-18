/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.gui;

import java.io.File;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;
import labyrinthboardgame.logic.GUIConnector;
import labyrinthboardgame.logic.Game;
import labyrinthboardgame.logic.GameLoader;
import labyrinthboardgame.logic.Player;

/**
 *
 * @author Corbi
 */
public final class SceneController 
{
    private final Stage currentStage;
    
    private GameBoardController gbController;
    
    public SceneController(Stage stage)
    {
        currentStage = stage;
        gbController = null;
    }
    
    /**
     * Returns the current stage
     * @return The current stage
     */
    public Stage getStage()
    {
        return currentStage;
    }
    
    /**
     * Kills all AI timers when the scene ends
     */
    public void killAllTimers()
    {
        if (gbController != null)
        {
            gbController.killAllTimers();
        }
        else {}
        gbController = null;
    }
    
    /**
     * Changes the current scene to be the main menu scene
     * @throws IOException
     */
    public void moveToMainMenuScene() throws IOException
    {
        killAllTimers();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        MainMenuController controller = loader.getController();
        controller.setSceneController(this);
        currentStage.setScene(scene);
    }
    
    /**
     * Changes the current scene to be the player select scene
     * @throws IOException 
     */
    public void moveToPlayerSelectScene() throws IOException
    {
        killAllTimers();
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
     * @param connector Connects the logic and GUI packages
     * @throws IOException 
     */
    public void moveToGameScene(Player[] players, int treasures, GUIConnector connector) throws IOException
    {
        killAllTimers();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameBoard.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        GameBoardController controller = loader.getController();
        controller.setupController(this, players, treasures, connector);
        
        // Create a listener for handling rotating tiles with the keyboard and mouse
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            switch (key.getCode()) {
                case R:
                    if (key.isShiftDown())
                    {
                        controller.rotateTileCounterClockwise();
                    }
                    else
                    {
                        controller.rotateTileClockwise();
                    }   break;
                case H:
                    controller.showTreasure();
                    break;
                default:
                    break;
            }
        });
        scene.addEventHandler(ScrollEvent.SCROLL, (scroll) -> {
            if (scroll.getDeltaY() > 0) 
            {
                controller.rotateTileCounterClockwise();
            }
            else
            {
                controller.rotateTileClockwise();
            }
        });
        
        scene.addEventHandler(KeyEvent.KEY_RELEASED, (key) -> {
            if (key.getCode() == KeyCode.H)
            {
                controller.hideTreasure();
            }
            else {}
        });
        
        gbController = controller;
        currentStage.setScene(scene);
    }
    
    /**
     * Changes the current scene to be the game scene
     * Sets up the board, treasures, and players from a save file
     * @param save The save to load from
     * @param connector Connects the logic and GUI packages
     * @throws IOException 
     */
    public void moveToGameScene(File save, GUIConnector connector) throws IOException
    {
        killAllTimers();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameBoard.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        GameBoardController controller = loader.getController();
        
        Game game = GameLoader.loadGame(save.toString(), controller);
        controller.setupController(this, game, connector);
        
        // Create a listener for handling rotating tiles with the keyboard and mouse
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            switch (key.getCode()) {
                case R:
                    if (key.isShiftDown())
                    {
                        controller.rotateTileCounterClockwise();
                    }
                    else
                    {
                        controller.rotateTileClockwise();
                    }   break;
                case H:
                    controller.showTreasure();
                    break;
                default:
                    break;
            }
        });
        scene.addEventHandler(ScrollEvent.SCROLL, (scroll) -> {
            if (scroll.getDeltaY() > 0) 
            {
                controller.rotateTileCounterClockwise();
            }
            else
            {
                controller.rotateTileClockwise();
            }
        });
        
        scene.addEventHandler(KeyEvent.KEY_RELEASED, (key) -> {
            if (key.getCode() == KeyCode.H)
            {
                controller.hideTreasure();
            }
            else {}
        });
        
        gbController = controller;
        currentStage.setScene(scene);
    }
}
