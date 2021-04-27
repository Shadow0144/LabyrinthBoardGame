/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import labyrinthboardgame.logic.Player;
import labyrinthboardgame.logic.TileSet;
import labyrinthboardgame.logic.TreasureSet;

/**
 * 
 * @author Corbi
 */
public class LabyrinthBoardGame extends Application
{
    private Player[] players;
    private TileSet tileSet;
    private TreasureSet treasureSet;
    
    private static LabyrinthBoardGame instance;
    public static LabyrinthBoardGame getInstance()
    {
        return instance;
    }
    
    private Stage currentStage;
    
    @Override
    public void start(Stage stage) throws Exception
    {
        instance = this;
        
        currentStage = stage;
        moveToMainMenuScene();
        
        currentStage.setTitle("Labyrinth Board Game");
        currentStage.show();
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
        currentStage.setScene(scene);
    }
    
    /**
     * Changes the current scene to be the game scene
     * Sets up the board, treasures, and players
     * @throws Exception 
     */
    public void moveToGameScene() throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameBoard.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        GameBoardController controller = loader.getController();
        
        // Create a new set of tiles and fill the board with them
        tileSet = new TileSet();
        controller.setupBoard(tileSet);
        
        // Set up the players and treasures
        int playerCount = 4;
        treasureSet = new TreasureSet();
        players = new Player[playerCount];
        for (int i = 0; i < playerCount; i++)
        {
            players[i] = new Player(i+1);
            controller.addPlayer(players[i]);
            treasureSet.assignTreasuresToPlayer(players[i], 0 / playerCount);
            players[i].showNextTreasure();
        }
        
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
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
