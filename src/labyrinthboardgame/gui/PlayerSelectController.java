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
import javafx.scene.image.ImageView;
import labyrinthboardgame.logic.Player;

/**
 * FXML Controller class
 *
 * @author Corbi
 */
public final class PlayerSelectController implements Initializable
{
    @FXML
    private ImageView player1ImageView;
    @FXML
    private ImageView player2ImageView;
    @FXML
    private ImageView player3ImageView;
    @FXML
    private ImageView player4ImageView;
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
    
    /**
     * Adjusts the maximum number of treasures available and updates the characters
     * @param playerNumber The player number (i.e. 1, 2, 3, or 4)
     */
    public void addPlayer(int playerNumber)
    {
        numberOfPlayers++;
        updateTreasureSlider();
        if (numberOfPlayers > 1)
        {
            startButton.setDisable(false);
        }
        else {}
        switch (playerNumber) // Reenable the character
        {
            case 1:
                player1ImageView.setVisible(true);
                break;
            case 2:
                player2ImageView.setVisible(true);
                break;
            case 3:
                player3ImageView.setVisible(true);
                break;
            case 4:
                player4ImageView.setVisible(true);
                break;
        }
    }
    
    /**
     * Adjusts the maximum number of treasures available and updates the characters
     * @param playerNumber The player number (i.e. 1, 2, 3, or 4)
     */
    public void removePlayer(int playerNumber)
    {
        numberOfPlayers--;
        updateTreasureSlider();
        if (numberOfPlayers < 2)
        {
            startButton.setDisable(true);
        }
        else {}
        switch (playerNumber) // Disable the character
        {
            case 1:
                player1ImageView.setVisible(false);
                break;
            case 2:
                player2ImageView.setVisible(false);
                break;
            case 3:
                player3ImageView.setVisible(false);
                break;
            case 4:
                player4ImageView.setVisible(false);
                break;
        }
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
            Player[] players = playerSelectorBox.getPlayers();
            int treasureCount = treasures[numberOfPlayers - 2];
            sceneController.moveToGameScene(players, treasureCount);
        }
        catch (Exception ex)
        {
            System.out.println("Error! Failed to move to Game Scene.");
            System.out.println(ex);
        }
    }
}
