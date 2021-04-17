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
public class LabyrinthBoardGame extends Application {
    
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
    
    public void moveToMainMenuScene() throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        currentStage.setScene(scene);
    }
    
    public void moveToPlayerSelectScene() throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PlayerSelect.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        currentStage.setScene(scene);
    }
    
    public void moveToGameScene() throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameBoard.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        GameBoardController controller = loader.getController();
        
        tileSet = new TileSet();
        int playerCount = 4;
        controller.setupBoard(tileSet, playerCount);
        
        treasureSet = new TreasureSet();
        players = new Player[playerCount];
        for (int i = 0; i < playerCount; i++)
        {
            players[i] = new Player(i+1);
            treasureSet.assignTreasuresToPlayer(players[i], 24 / playerCount);
            players[i].showNextTreasure();
            controller.addPlayer(i+1, players[i]);
        }
        
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
