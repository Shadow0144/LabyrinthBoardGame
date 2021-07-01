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

/**
 *
 * @author Corbi
 */
public class AdvancedAI
{
    private final int MILLISECONDS_TO_WAIT = 1000;
    
    private final Player player;
    private final Game game;
    private Board boardClone;
    
    private Timer timer;
    private TimerTask timerTask;
    
    private final Random rand;
    
    private enum aiState
    {
        predicting,
        rotating,
        placing,
        moving,
        none
    };
    private aiState currentState;
    private aiState predictingState;
    private boolean targetFound;
    
    private int rotation;
    private int arrowNum;
    private Tile targetTile;
    
    private int maxMoves;
    private int maxRotation;
    private int maxArrowNum;
    
    public AdvancedAI(Player aiPlayer, Game game)
    {
        this.player = aiPlayer;
        this.game = game;
        this.currentState = aiState.predicting;
        this.predictingState = aiState.none;
        this.rand = new Random();
        this.rotation = 0;
        this.arrowNum = 0;
        this.targetFound = false;
    }
    
    /**
     * Kills the AI timers
     */
    public void killTimer()
    {
        timerTask.cancel();
        timer.cancel();
    }
    
    /**
     * Sets up and performs the AI's turn
     */
    public void performTurn()
    {
        this.rotation = 0;
        this.arrowNum = 0;
        this.targetTile = null;
        this.targetFound = false;
        this.maxMoves = 0;
        this.maxRotation = 0;
        this.maxArrowNum = 0;
        this.predictingState = aiState.rotating;
        this.currentState = aiState.predicting;
        predictNextAction();
    }
    
    /**
     * Sets up the timer to perform the predicted best action
     */
    private void performPredictedActions()
    {
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
    
    /**
     * Tests every possible move and selects the best action
     */
    public void predictNextAction()
    {
        Platform.runLater(() -> {
            while (currentState == aiState.predicting)
            {
                switch (predictingState)
                {
                    case rotating:
                        boardClone = new Board(game.getBoard());
                        switch (rotation)
                        {
                            case 0:
                                // Do nothing
                                break;
                            case 1:
                                boardClone.getTileSet().rotateNextTileClockwise();
                                break;
                            case 2:
                                boardClone.getTileSet().rotateNextTileCounterClockwise();
                                break;
                            case 3:
                                boardClone.getTileSet().rotateNextTileClockwise();
                                boardClone.getTileSet().rotateNextTileClockwise();
                                break;
                        }
                        predictingState = aiState.placing;
                        break;
                    case placing:
                        Board.ArrowPosition place = Board.ArrowPosition.values()[arrowNum];
                        if (boardClone.isInsertAvailable(place))
                        {
                            boardClone.insertTile(place);
                        }
                        else {}
                        predictingState = aiState.moving;
                        break;
                    case moving:
                        Tile startTile = boardClone.getTile(
                                game.findPlayerRow(player.getPlayerNumber()-1),
                                game.findPlayerCol(player.getPlayerNumber()-1));
                        startTile.showPaths(0);
                        int moves = findTarget(startTile, boardClone);
                        if (moves > maxMoves)
                        {
                            maxMoves = moves;
                            maxRotation = rotation;
                            maxArrowNum = arrowNum;
                        }
                        else {}
                        rotation++;
                        if (rotation == 4)
                        {
                            rotation = 0;
                            arrowNum++;
                            if (!game.getBoard().isInsertAvailable(arrowNum))
                            {
                                arrowNum++;
                            }
                            else {}
                        }
                        else {}
                        if (targetFound || arrowNum == 12)
                        {
                            predictingState = aiState.rotating;
                            currentState = aiState.rotating;
                            performPredictedActions();
                        }
                        else
                        {
                            predictingState = aiState.rotating;
                        }
                    case none:
                        // Do nothing - some kind of error though
                        break;
            }
                    }
        });
    }
    
    /**
     * Performs a step of the selected move
     */
    public void performNextAction()
    {
        Platform.runLater(() -> {
            switch (currentState)
            {
                case rotating:
                    int rotate;
                    if (targetFound)
                    {
                        rotate = rotation;
                    }
                    else
                    {
                        rotate = maxRotation; //rand.nextInt(4);
                    }
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
                    if (targetFound)
                    {
                        Board.ArrowPosition place = Board.ArrowPosition.values()[arrowNum];
                        game.insertTile(place);
                    }
                    else
                    {
                        Board.ArrowPosition place = Board.ArrowPosition.values()[maxArrowNum];
                        game.insertTile(place);
                    }
                    currentState = aiState.moving;
                    break;
                case moving:
                    if (targetFound)
                    {
                        findTarget(player.getCurrentTile(), game.getBoard());
                    }
                    else // Move to random tile if the target tile was not found
                    {
                        LinkedList<Tile> tiles = game.getAvailableTiles();
                        int index = rand.nextInt(tiles.size());
                        targetTile = tiles.get(index);
                    }
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
    
    /**
     * Finds the target on a board
     * @param startTile The tile the current player is on
     * @param board The board to search on
     * @return The number of accessible ti
     */
    public int findTarget(Tile startTile, Board board)
    {
        targetTile = startTile;
        LinkedList<Tile> tiles = board.getAccessibleTiles();
        Treasure playerTreasure = player.getCurrentTreasure();
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
        return tiles.size();
    }
}
