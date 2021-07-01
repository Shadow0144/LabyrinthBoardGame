/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.logic;

import java.util.LinkedList;
import labyrinthboardgame.gui.GameBoardController;
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
    
    private final Board gameBoard;
    
    private final GameBoardController controller;
    
    public Game(GameBoardController controller, Player[] players, int treasureCount)
    {
        this.controller = controller;
        
        // Create a new set of tiles and fill the board with them
        tileSet = new TileSet();
        GUIConnector.setupBoard(controller.getGameBoardView(), controller.getPlayerIconTray());
        gameBoard = new Board(this, tileSet);
        
        // Set up the players
        this.players = players;
        setupPlayers(treasureCount, controller.getPlayerIconTray());
    }
    
    public Game(GameBoardController controller, Player[] players, int currentPlayer, Tile[][] tiles, Tile nextTile)
    {
        this.controller = controller;
        
        tileSet = new TileSet(tiles, nextTile);
        GUIConnector.setupBoard(controller.getGameBoardView(), controller.getPlayerIconTray());
        gameBoard = new Board(this, tileSet);
        
        // Set up the players
        this.players = players;
        this.currentPlayer = currentPlayer;
        setupLoadedPlayers(controller.getPlayerIconTray());
    }
    
    /**
     * Kills all AI timers when the game ends
     */
    public void killAllTimers()
    {
        for (Player player : players)
        {
            player.killAllTimers();
        }
    }
    
    /**
     * Sets up the players
     * @param treasureCount How many treasures each player should have
     * @param playerIconTray A reference to the PlayerIconTray
     */
    private void setupPlayers(int treasureCount, PlayerIconTray playerIconTray)
    {
        // Set up the treasures
        TreasureSet treasureSet = new TreasureSet();
        
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
                players[i].setupAI(this);
            }
            else // Hide inactive players
            {
                playerIconTray.removePlayerIcon(i+1); // Pass the player number
            }
        }
        players[currentPlayer].setActive();
        controller.updateCurrentTreasure(players[currentPlayer].getCurrentTreasure(),
                players[currentPlayer].getPlayerColor());
        players[currentPlayer].performTurn(); // Perform the AI player's turn if necessary
    }
    /**
     * Sets up players when the game has been loaded from a file
     * @param playerIconTray A reference to the PlayerIconTray
     */
    private void setupLoadedPlayers(PlayerIconTray playerIconTray)
    {
        for (int i = 0; i < 4; i++)
        {
            if (players[i].inGame())
            {
                // Move the player character onto the board
                Tile loadedTile = gameBoard.getTile(players[i].getLoadedX(), players[i].getLoadedY());
                players[i].moveCharacter(loadedTile);
                players[i].showNextTreasure();
                players[i].setupAI(this);
            }
            else // Hide inactive players
            {
                playerIconTray.removePlayerIcon(i+1); // Pass the player number
            }
        }
        players[currentPlayer].setActive();
        controller.updateCurrentTreasure(players[currentPlayer].getCurrentTreasure(),
                players[currentPlayer].getPlayerColor());
        players[currentPlayer].performTurn(); // Perform the AI player's turn if necessary
    }
    
    /**
     * Moves to the next player and checks if a player has won
     */
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
            controller.updateCurrentTreasure(players[currentPlayer].getCurrentTreasure(),
                    players[currentPlayer].getPlayerColor());
            
            players[currentPlayer].performTurn(); // Perform the AI player's turn if necessary
        }
        else 
        {
            controller.setWinningPlayer(currentPlayer);
        }
    }
    
    /**
     * Returns the current player
     * @return The current player
     */
    public int getCurrentPlayer()
    {
        return currentPlayer;
    }
    
    /**
     * Gets the player at the index playerIndex
     * @param playerIndex The index of the player (i.e. 0-3)
     * @return The player at the index playerIndex
     */
    public Player getPlayer(int playerIndex)
    {
        return players[playerIndex];
    }
    
    /**
     * Returns the reference to the board
     * @return The reference to the board
     */
    public Board getBoard()
    {
        return gameBoard;
    }
    
    /**
     * Returns the next tile
     * @return The next tile
     */
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
    
    /**
     * Returns if the arrow button can be used
     * @param arrowPosition The arrow button to check
     * @return If the arrow button can be used
     */
    public boolean isInsertAvailable(Board.ArrowPosition arrowPosition)
    {
        return gameBoard.isInsertAvailable(arrowPosition);
    }
    
    /**
     * Inserts a tile at the position indicated by the arrow button
     * @param arrowPosition The arrow button used
     */
    public void insertTile(Board.ArrowPosition arrowPosition)
    {
        gameBoard.insertTile(arrowPosition);
        // Switch to next phase
        players[currentPlayer].showPaths();
    }
    
    /**
     * Gets a list of tiles marked as accessible
     * @return A list of tiles marked as accessible
     */
    public LinkedList<Tile> getAvailableTiles()
    {
        return gameBoard.getAccessibleTiles();
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
            gameBoard.enableArrows((players[currentPlayer].getPlayerType() == Player.PlayerType.human));
        }
        else {}
    }
    
    /**
     * Rotates the next tile clockwise
     */
    public void rotateNextTileClockwise()
    {
        tileSet.rotateNextTileClockwise();
    }
    
    /**
     * Rotates the next tile counterclockwise
     */
    public void rotateNextTileCounterClockwise()
    {
        tileSet.rotateNextTileCounterClockwise();
    }
    
    /**
     * Finds the row of the tile the player is currently on
     * @param playerIndex The index (i.e. 0-3) of the player to locate
     * @return The row the player is currently on
     */
    public int findPlayerRow(int playerIndex)
    {
        return gameBoard.findTileRow(players[playerIndex].getCurrentTile());
    }
    
    /**
     * Finds the column of the tile the player is currently on
     * @param playerIndex The index (i.e. 0-3) of the player to locate
     * @return The column the player is currently on
     */
    public int findPlayerCol(int playerIndex)
    {
        return gameBoard.findTileCol(players[playerIndex].getCurrentTile());
    }
}
