/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Corbi
 */
public class PlayerSelectController implements Initializable
{
    @FXML
    private PlayerSelector player1Selector;
    @FXML
    private PlayerSelector player2Selector;
    @FXML
    private PlayerSelector player3Selector;
    @FXML
    private PlayerSelector player4Selector;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    /**
     * Moves to the Game Scene
     * @param e Unused
     */
    public void moveToGameScene(ActionEvent e)
    {
        try
        {
            LabyrinthBoardGame.getInstance().moveToGameScene();
        }
        catch (Exception ex)
        {
            System.out.println("Error! Failed to move to Game Scene.");
        }
    }
}
