/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import labyrinthboardgame.logic.Player;

/**
 * FXML Controller class
 *
 * @author Corbi
 */
public final class PlayerSelectController implements Initializable
{
    @FXML
    private PlayerSelectorBox playerSelectorBox;
    @FXML
    private Slider treasuresSlider;
    @FXML
    private Button startButton;
    
    private SceneController sceneController;
    
    private int numberOfPlayers;
    private int[] treasures;
    
    private boolean doNotUpdateSlider; // Don't store the changed slider value when we are changing it by code

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        treasures = new int[3];
        treasures[0] = 12; // 2 players
        treasures[1] = 8; // 3 players
        treasures[2] = 6; // 4 players
        
        playerSelectorBox.setController(this);
        
        doNotUpdateSlider = false;
        treasuresSlider.valueProperty().addListener(new ChangeListener<Number>()
        {
                @Override
                public void changed(
                   ObservableValue<? extends Number> observableValue, 
                   Number oldValue, 
                   Number newValue) 
                { 
                    if (!doNotUpdateSlider)
                    {
                      switch (numberOfPlayers)
                        {
                            case 0:
                                // Do nothing
                                break;
                            case 1:
                                // Do nothing
                                break;
                            case 2:
                                treasures[0] = newValue.intValue();
                                break;
                            case 3:
                                treasures[1] = newValue.intValue();
                                break;
                            case 4:
                                treasures[2] = newValue.intValue();
                                break;
                        }
                    }
                    else {}
                }
        });
        
        numberOfPlayers = 4;
    }   
    
    /**
     * Sets the scene controller for changing scenes
     * @param sc The controller for the scenes
     */
    public void setSceneController(SceneController sc)
    {
        sceneController = sc;
    }
    
    public void increasePlayers()
    {
        numberOfPlayers++;
        updateTreasureSlider();
        if (numberOfPlayers > 1)
        {
            startButton.setDisable(false);
        }
        else {}
    }
    
    public void decreasePlayers()
    {
        numberOfPlayers--;
        updateTreasureSlider();
        if (numberOfPlayers < 2)
        {
            startButton.setDisable(true);
        }
        else {}
    }
    
    private void updateTreasureSlider()
    {
        doNotUpdateSlider = true;
        switch (numberOfPlayers)
        {
            case 0:
                treasuresSlider.setDisable(true);
                break;
            case 1:
                treasuresSlider.setDisable(true);
                break;
            case 2:
                treasuresSlider.setDisable(false);
                treasuresSlider.setMax(12);
                treasuresSlider.setValue(treasures[0]);
                break;
            case 3:
                treasuresSlider.setDisable(false);
                treasuresSlider.setMax(8);
                treasuresSlider.setValue(treasures[1]);
                break;
            case 4:
                treasuresSlider.setDisable(false);
                treasuresSlider.setMax(6);
                treasuresSlider.setValue(treasures[2]);
                break;
        }
        doNotUpdateSlider = false;
    }
    
    /**
     * Moves to the Game Scene
     * @param e Unused
     */
    public void moveToGameScene(ActionEvent e)
    {
        try
        {
            Player[] players = new Player[4];
            int treasureCount = treasures[numberOfPlayers - 2];
            sceneController.moveToGameScene(players, treasureCount);
        }
        catch (Exception ex)
        {
            System.out.println("Error! Failed to move to Game Scene.");
        }
    }
}
