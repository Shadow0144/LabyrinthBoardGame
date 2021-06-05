/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.logic;

import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import labyrinthboardgame.gui.InsertTileButton;

/**
 *
 * @author Corbi
 */
public class BasicAI
{
    private final int MILLISECONDS_TO_WAIT = 1000;
    
    private final Player player;
    private final Game game;
    
    private Timer timer;
    private TimerTask timerTask;
    
    private final Random rand;
    
    private enum aiState
    {
        rotating,
        placing,
        moving,
        none
    };
    private aiState currentState;
    
    public BasicAI(Player aiPlayer, Game game)
    {
        this.player = aiPlayer;
        this.game = game;
        this.currentState = aiState.none;
        this.rand = new Random();
    }
    
    public void killTimer()
    {
        timerTask.cancel();
        timer.cancel();
    }
    
    public void performTurn()
    {
        this.currentState = aiState.rotating;
        this.timer = new Timer();
        this.timerTask = new java.util.TimerTask() 
            {
                @Override
                public void run() 
                {
                    performNextAction();
                }
            };
        timer.schedule(
            timerTask,
            MILLISECONDS_TO_WAIT,
            MILLISECONDS_TO_WAIT
        );
    }
    
    public void performNextAction()
    {
        Platform.runLater(() -> {
            switch (currentState)
            {
                case rotating:
                    int rotate = rand.nextInt(4);
                    switch (rotate)
                    {
                        case 0:
                            // Do nothing
                            break;
                        case 1:
                            game.rotateNextTileClockwise();
                            break;
                        case 2:
                            game.rotateNextTileCounterClockwise();
                            break;
                        case 3:
                            game.rotateNextTileClockwise();
                            game.rotateNextTileClockwise();
                            break;
                    }
                    currentState = aiState.placing;
                    break;
                case placing:
                    boolean placed = false;
                    do
                    {   
                        InsertTileButton.ArrowPosition place = InsertTileButton.ArrowPosition.values()[rand.nextInt(12)];
                        if (game.isInsertAvailable(place))
                        {
                            game.insertTile(place);
                            placed = true;
                        }
                        else {}
                    }
                    while (!placed);
                    currentState = aiState.moving;
                    break;
                case moving:
                    Tile targetTile = player.getCurrentTile();
                    LinkedList<Tile> tiles = game.getAvailableTiles();
                    Treasure playerTreasure = player.getCurrentTreasure();
                    boolean targetFound = false;
                    if (playerTreasure != null) // Treasures remain
                    {
                        for (Tile tile : tiles)
                        {
                            Treasure tileTreasure = tile.getTreasure();
                            if (tileTreasure != null && tileTreasure.getTreasureType() == playerTreasure.getTreasureType())
                            {
                                targetTile = tile;
                                targetFound = true;
                                break;
                            }
                            else {}
                        }
                    }
                    else // Return to start
                    {
                        for (Tile tile : tiles)
                        {
                            int tilePlayer = tile.getPlayer();
                            if (tilePlayer == player.getPlayerNumber())
                            {
                                targetTile = tile;
                                targetFound = true;
                                break;
                            }
                            else {}
                        }
                    }
                    if (!targetFound) // Move to random tile if the target tile was not found
                    {
                        int index = rand.nextInt(tiles.size());
                        targetTile = tiles.get(index);
                                
                    }
                    else {}
                    game.movePlayerToTile(targetTile);
                    timerTask.cancel();
                    timer.cancel();
                    currentState = aiState.none;
                case none:
                    // Do nothing - some kind of error though
                    break;
        }
        });
    }
}
