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
    
    @Override
    public void start(Stage stage) throws Exception
    {
        sceneController = new SceneController(stage);
        sceneController.moveToMainMenuScene();
        
        stage.setTitle("Labyrinth Board Game");
        stage.show();
    }
    
    @Override
    public void stop()
    {
        sceneController.killAllTimers();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
