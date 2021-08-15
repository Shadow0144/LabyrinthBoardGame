/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.gui;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * 
 * @author Corbi
 */
public final class LabyrinthBoardGame extends Application
{    
    private SceneController sceneController;
    
    /**
     * Starts the program and moves to the main menu
     * @param stage Reference to the Stage
     * @throws Exception 
     */
    @Override
    public void start(Stage stage) throws Exception
    {
        sceneController = new SceneController(stage);
        sceneController.moveToMainMenuScene();
        
        stage.setTitle("Labyrinth Board Game");
        stage.setResizable(false);
        stage.show();
    }
    
    /**
     * Stops the game and kills all AI threads
     */
    @Override
    public void stop()
    {
        sceneController.killAllTimers();
    }

    /**
     * @param args The command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
