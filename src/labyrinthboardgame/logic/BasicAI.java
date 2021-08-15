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
public class BasicAI
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
    
    //private int maxMoves;
    private int maxRotation;
    private int maxArrowNum;
    
    private final int MAX_GUESSES = 3;
    private int guesses;
    private int minDistance;
    private Tile closestTile;
    
    public BasicAI(Player aiPlayer, Game game)
    {
        this.player = aiPlayer;
        this.game = game;
        this.currentState = aiState.predicting;
        this.predictingState = aiState.none;
        this.rand = new Random();
        this.rotation = 0;
        this.arrowNum = 0;
        this.minDistance = 0;
        this.closestTile = null;
        this.targetFound = false;
        this.guesses = 0;
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
        //this.maxMoves = 0;
        this.maxRotation = 0;
        this.maxArrowNum = 0;
        this.predictingState = aiState.rotating;
        this.currentState = aiState.predicting;
        this.minDistance = Integer.MAX_VALUE;
        this.closestTile = null;
        this.guesses = 0;
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
                        boardClone = new Board(game.getBoard(), GUIConnector.getFakeGUI());
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
                        //int moves = findTarget(startTile, boardClone);
                        //if (moves > maxMoves)
                        int distance = findTarget(startTile, boardClone);
                        if (distance < minDistance)
                        {
                            minDistance = distance;
                            //maxMoves = moves;
                            maxRotation = rotation;
                            maxArrowNum = arrowNum;
                        }
                        else {}
                        rotation++;
                        if (rotation == 4)
                        {
                            rotation = 0;
                            do 
                            {
                                arrowNum = rand.nextInt(12); // Choose a random spot to try
                                guesses++;
                            }
                            while (!game.getBoard().isInsertAvailable(arrowNum));
                        }
                        else {}
                        if (targetFound || guesses >= MAX_GUESSES)
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
                    int rotate = maxRotation; //rand.nextInt(4);
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
                    Board.ArrowPosition place = Board.ArrowPosition.values()[maxArrowNum];
                    game.insertTile(place);
                    currentState = aiState.moving;
                    break;
                case moving:
                    if (targetFound)
                    {
                        findTarget(player.getCurrentTile(), game.getBoard());
                    }
                    else // Move to closest tile if the target tile was not found
                    {
                        findClosestTile(player.getCurrentTile(), game.getBoard());
                        targetTile = closestTile;
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
     * @return The number of accessible tiles
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
    
    public void findClosestTile(Tile startTile, Board board)
    {
        closestTile = startTile;
        int minDist = Integer.MAX_VALUE;
        LinkedList<Tile> tiles = board.getAccessibleTiles();
        Treasure playerTreasure = player.getCurrentTreasure();
        if (playerTreasure != null) // Treasures remain
        {
            Tile target = board.getTreasureTile(playerTreasure);
            if (target != null)
            {
                int targetRow = target.getRow();
                int targetCol = target.getCol();
                if (targetRow != -1 && targetCol != -1)
                {
                    for (Tile tile : tiles)
                    {
                        int dist = Math.abs(tile.getRow() - targetRow) +
                                Math.abs(tile.getCol() - targetCol);
                        if (dist < minDist)
                        {
                            minDist = dist;
                            minDistance = dist;
                            closestTile = tile;
                        }
                        else {}
                    }
                }
                else {}
            }
            else {}
        }
        else // Return to start
        {
            Tile target = board.getStartingTile(player.getPlayerNumber());
            int targetRow = target.getRow();
            int targetCol = target.getCol();
            for (Tile tile : tiles)
            {
                int dist = Math.abs(tile.getRow() - targetRow) +
                                Math.abs(tile.getCol() - targetCol);
                if (dist < minDist)
                {
                    minDist = dist;
                    minDistance = dist;
                    closestTile = tile;
                }
                else {}
            }
        }
    }
}
