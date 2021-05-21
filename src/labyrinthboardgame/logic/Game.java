/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.logic;

import labyrinthboardgame.gui.BoardView;
import labyrinthboardgame.gui.GameBoardController;
import labyrinthboardgame.gui.InsertTileButton;
import labyrinthboardgame.gui.PlayerIconTray;

/**
 *
 * @author Corbi
 */
public final class Game 
{
    private int currentPlayer;
    private final Player[] players;
    
    private final TileSet tileSet;
    private final TreasureSet treasureSet;
    
    private final Board gameBoard;
    
    private final GameBoardController controller;
    
    public Game(GameBoardController controller, Player[] players, int treasureCount, 
            BoardView gameBoardView, PlayerIconTray playerIconTray)
    {
        this.controller = controller;
        
        // Set up the treasures
        treasureSet = new TreasureSet();
        
        // Create a new set of tiles and fill the board with them
        tileSet = new TileSet();
        gameBoard = new Board(this, tileSet, gameBoardView, playerIconTray);
        
        // Set up the players
        this.players = players;
        setupPlayers(treasureCount, playerIconTray);
    }
    
    private void setupPlayers(int treasureCount, PlayerIconTray playerIconTray)
    {
        // Set up the players
        currentPlayer = -1;
        for (int i = 0; i < 4; i++)
        {
            if (players[i].inGame())
            {
                if (currentPlayer == -1) // Set to the first available player
                {
                    currentPlayer = i;
                }
                else {}
                players[i].setIcon(playerIconTray.getPlayerIcon(i+1));
                treasureSet.assignTreasuresToPlayer(players[i], treasureCount);
                players[i].showNextTreasure();
                addPlayerCharacterToBoard(players[i]);
            }
            else // Hide inactive players
            {
                playerIconTray.removePlayerIcon(i+1); // Pass the player number
            }
        }
        players[currentPlayer].setActive();
    }
    
    public void switchPlayers()
    {
        if (!players[currentPlayer].getHasWon())
        {
            players[currentPlayer].setInactive();
            do
            {
                currentPlayer = (currentPlayer + 1) % 4;
            }
            while (!players[currentPlayer].inGame()); // Skip players not in the game
            players[currentPlayer].setActive();
        }
        else 
        {
            controller.setWinningPlayer(currentPlayer);
        }
    }
    
    public int getCurrentPlayer()
    {
        return currentPlayer;
    }
    
    public Board getBoard()
    {
        return gameBoard;
    }
    
    public Tile getNextTile()
    {
        return tileSet.getNextTile();
    }
    
    /**
     * Adds the player characters to their starting locations on the board
     * @param player The player to be added to the board
     */
    public void addPlayerCharacterToBoard(Player player)
    {
        player.moveCharacter(gameBoard.getStartingTile(player.getPlayerNumber()));
    }
    
    public void insertTile(InsertTileButton.ArrowPosition arrowPosition)
    {
        gameBoard.insertTile(arrowPosition);
        // Switch to next phase
        players[currentPlayer].showPaths();
    }
    
    /**
     * Moves a player's character to a new tile and switches to the next player
     * @param tile The tile the player's character moves to
     */
    public void movePlayerToTile(Tile tile)
    {
        players[currentPlayer].moveCharacter(tile);
        switchPlayers(); // After moving, switch players
        
        // Keep the the tile insert arrows disabled if the game is over
        // Otherwise enable them
        if (!players[currentPlayer].getHasWon())
        {
            gameBoard.enableArrows();
        }
        else {}
    }
    
    public void rotateNextTileClockwise()
    {
        tileSet.rotateNextTileClockwise();
    }
    
    public void rotateNextTileCounterClockwise()
    {
        tileSet.rotateNextTileClockwise();
    }
}
