/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.logic;

import java.util.LinkedList;

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
    
    private final GUIConnector connector;
    
    public Game(Player[] players, int treasureCount, GUIConnector connector)
    {        
        this.connector = connector;
        
        // Create a new set of tiles and fill the board with them
        tileSet = new TileSet();
        gameBoard = new Board(this, tileSet, connector);
        
        // Set up the players
        this.players = players;
        setupPlayers(treasureCount);
    }
    
    public Game(Player[] players, int currentPlayer, Tile[][] tiles, Tile nextTile, GUIConnector connector)
    {
        this.connector = connector;
        tileSet = new TileSet(tiles, nextTile);
        gameBoard = new Board(this, tileSet, connector);
        
        // Set up the players
        this.players = players;
        this.currentPlayer = currentPlayer;
        setupLoadedPlayers();
    }
    
    /**
     * Returns a reference to the GUIConnector used
     * @return A reference to the GUIConnector used
     */
    public GUIConnector getConnector()
    {
        return connector;
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
     */
    private void setupPlayers(int treasureCount)
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
                treasureSet.assignTreasuresToPlayer(players[i], treasureCount);
                players[i].showNextTreasure();
                connector.createPlayerCharacter(i, players[i]);
                addPlayerCharacterToBoard(players[i]);
                players[i].setupAI(this);
            }
            else // Hide inactive players
            {
                connector.removePlayerIcon(i+1); // Pass the player number
            }
        }
        for (int i = 0; i < 4; i++)
        {
            players[i].setupIcon();
        }
        players[currentPlayer].setActive();
        connector.updateCurrentTreasure(players[currentPlayer].getCurrentTreasure(),
                players[currentPlayer].getPlayerNumber());
        players[currentPlayer].performTurn(); // Perform the AI player's turn if necessary
    }
    
    /**
     * Sets up players when the game has been loaded from a file
     */
    private void setupLoadedPlayers()
    {
        for (int i = 0; i < 4; i++)
        {
            if (players[i].inGame())
            {
                // Move the player character onto the board
                Tile loadedTile = gameBoard.getTile(players[i].getLoadedX(), players[i].getLoadedY());
                connector.createPlayerCharacter(i, players[i]);
                players[i].moveCharacter(loadedTile);
                players[i].showNextTreasure();
                players[i].setupAI(this);
            }
            else // Hide inactive players
            {
                connector.removePlayerIcon(i+1); // Pass the player number
            }
        }
        for (int i = 0; i < 4; i++)
        {
            players[i].setupIcon();
        }
        players[currentPlayer].setActive();
        connector.updateCurrentTreasure(players[currentPlayer].getCurrentTreasure(),
                players[currentPlayer].getPlayerNumber());
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
            connector.updateCurrentTreasure(players[currentPlayer].getCurrentTreasure(),
                    players[currentPlayer].getPlayerNumber());
            
            players[currentPlayer].performTurn(); // Perform the AI player's turn if necessary
        }
        else 
        {
            connector.setWinningPlayer(currentPlayer);
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
     * Returns the tile with the current treasure
     * @return The tile with the current treasure
     */
    public Tile getTreasureTile()
    {
        return gameBoard.getTreasureTile(players[currentPlayer].getCurrentTreasure());
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
        
        connector.hidePaths();
    }
    
    /**
     * Moves a player's character to a new tile without switching to the next player;
     * used when a tile slides off the board
     * @param tile The tile the player's character moves to
     * @param playerIndex The index of the player to move
     */
    public void movePlayerToTile(Tile tile, int playerIndex)
    {
        players[playerIndex].moveCharacter(tile);
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
