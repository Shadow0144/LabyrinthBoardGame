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

/**
 * FXML Controller class
 *
 * @author Corbi
 */
public class MainMenuController implements Initializable
{
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Do nothing
    }    
    
    /**
     * Moves to the Player Select Scene
     * @param e Unused
     */
    public void moveToPlayerSelectScene(ActionEvent e)
    {
        try
        {
            LabyrinthBoardGame.getInstance().moveToPlayerSelectScene();
        }
        catch (Exception ex)
        {
            System.out.println("Error! Failed to move to Player Select Screen.");
        }
    }
}
