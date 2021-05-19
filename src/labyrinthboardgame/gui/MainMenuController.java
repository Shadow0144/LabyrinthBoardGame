/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author Corbi
 */
public final class MainMenuController implements Initializable
{    
    private SceneController sceneController;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Do nothing
    }    
    
    public void setSceneController(SceneController sc)
    {
        sceneController = sc;
    }
    
    /**
     * Moves to the Player Select Scene
     * @param e Unused
     */
    public void moveToPlayerSelectScene(ActionEvent e)
    {
        try
        {
            sceneController.moveToPlayerSelectScene();
        }
        catch (Exception ex)
        {
            System.out.println("Error! Failed to move to Player Select Screen.");
        }
    }
    
    /**
     * Opens the dialog to load a game
     * @param e Unused
     */
    public void loadGame(ActionEvent e)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JSON save", "*.json"));
        fileChooser.showOpenDialog(sceneController.getStage());
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
