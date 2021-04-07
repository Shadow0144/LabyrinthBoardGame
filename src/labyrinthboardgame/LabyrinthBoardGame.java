/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 *
 * @author Corbi
 */
public class LabyrinthBoardGame extends Application {
    
    Player[] players;
    TileSet tileSet;
    TreasureSet treasureSet;
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameBoard.fxml"));
        Parent root = loader.load();
        GameBoardController controller = loader.getController();
        tileSet = new TileSet();
        controller.setupBoard(tileSet);
        
        Scene scene = new Scene(root);
        
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
        
        stage.setScene(scene);
        stage.setTitle("Labyrinth Board Game");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
