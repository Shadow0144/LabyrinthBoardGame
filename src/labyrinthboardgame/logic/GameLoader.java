/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.StringTokenizer;
import labyrinthboardgame.gui.GameBoardController;

/**
 *
 * @author Corbi
 */
public class GameLoader 
{
    /**
     * Loads a game from a save file
     * @param fileName The name of the file to load
     * @param controller A reference to the GameBoardController
     * @return A game loaded from the file
     */
    public static Game loadGame(String fileName, GameBoardController controller)
    {
        Player[] players = new Player[4];
        Tile[][] tiles = new Tile[7][7];
        Tile nextTile = null;
        int currentPlayer = -1;
        
        try (BufferedReader inputStream = new BufferedReader(new FileReader(fileName)))
        {
            // Skip the first two lines
            String next;
            inputStream.readLine();
            inputStream.readLine();
            
            // Begin constructing the tiles
            for (int i = 0; i < 7; i++)
            {
                for (int j = 0; j < 7; j++)
                {
                    next = inputStream.readLine();
                    tiles[i][j] = loadTile(next, i, j);
                } // for (int j = 0; j < 7; j++)
                inputStream.readLine(); // Toss out next line
            } // for (int i = 0; i < 7; i++)
            inputStream.readLine(); // Toss out next line
            next = inputStream.readLine(); // Get the next tile
            nextTile = loadTile(next, -1, -1);
            
            next = inputStream.readLine(); // Get the current player
            next = next.replaceAll("[^\\d.]", "");
            currentPlayer = Integer.parseInt(next);
            
            inputStream.readLine(); // Toss out next line
            // Load the players
            for (int i = 0; i < 4; i++)
            {
                players[i] = getPlayerFromStrings((i+1), inputStream, controller);
            }
            
            // The rest of the lines aren't necessary
        }
        catch (IOException ex)
        {
            System.out.println(ex);
        }
        
        Game game = new Game(controller, players, currentPlayer, tiles, nextTile);
        
        return game;
    }
    
    /**
     * Loads a tile from a String
     * @param tileString The String to parse and create a tile from
     * @param i The current row
     * @param j The current column
     * @return A new tile constructed from the String
     */
    private static Tile loadTile(String tileString, int i, int j)
    {
        Tile rTile; 
        
        StringTokenizer tokenizer = new StringTokenizer(tileString, ",");
        Tile.Shape shape = getShapeFromString(tokenizer.nextToken()); 
        int rotation = getRotationFromString(tokenizer.nextToken());
        Treasure treasure = getTreasureFromString(tokenizer.nextToken());
        if (treasure != null)
        {
            rTile = new Tile(shape, rotation, treasure);
        }
        else
        {
            if (i == 0 && j == 0)
            {
                rTile = new Tile(shape, rotation, 1);
            }
            else if (i == 0 && j == 6)
            {
                rTile = new Tile(shape, rotation, 2);
            }
            else if (i == 6 && j == 0)
            {
                rTile = new Tile(shape, rotation, 3);
            }
            else if (i == 6 && j == 6)
            {
                rTile = new Tile(shape, rotation, 4);
            }
            else 
            {
                rTile = new Tile(shape, rotation);
            }
        } // else (treasure == null)
        
        return rTile;
    }
    
    /**
     * Gets the shape of a tile from a String
     * @param shapeString The String to load the shape from
     * @return The shape of the loaded tile
     */
    private static Tile.Shape getShapeFromString(String shapeString)
    {
        Tile.Shape rShape = null;
        shapeString = shapeString.replaceAll("[^\\d.]", "");
        int shape = Integer.parseInt(shapeString);
        switch (shape)
        {
            case 0:
                rShape = Tile.Shape.I;
                break;
            case 1:
                rShape = Tile.Shape.L;
                break;
            case 2:
                rShape = Tile.Shape.T;
                break;
        }
        return rShape;
    }
    
    /**
     * Gets the rotation of a tile from a String
     * @param rotationString The String to load the rotation from
     * @return The rotation of the loaded tile
     */
    private static int getRotationFromString(String rotationString)
    {
        rotationString = rotationString.replaceAll("[^\\d.]", "");
        int rotation = Integer.parseInt(rotationString) * 90;
        return rotation;
    }
    
    /**
     * Gets the treasure of a tile from a String
     * @param treasureString The String to load the treasure from
     * @return The treasure of the loaded tile
     */
    private static Treasure getTreasureFromString(String treasureString)
    {
        Treasure rTreasure = null;
        
        treasureString = treasureString.replaceAll("[^\\d.]", "");
        int treasureNumber = Integer.parseInt(treasureString);
        
        if (treasureNumber > 0)
        {
            treasureNumber--;
            rTreasure = new Treasure(Treasure.TreasureType.values()[treasureNumber]);
        }
        else {}
        
        return rTreasure;
    }
    
    /**
     * Loads a player from a String
     * @param playerNumber The number of the player (i.e. 1-4)
     * @param inputStream The stream to load from
     * @param controller A reference to the GameBoardController
     * @return A loaded player
     * @throws IOException 
     */
    private static Player getPlayerFromStrings(int playerNumber,
            BufferedReader inputStream, GameBoardController controller) throws IOException
    {
        inputStream.readLine(); // Skip this line
        
        boolean inGame = inputStream.readLine().contains("t");
        
        String name = inputStream.readLine();
        name = name.substring(15, name.length() - 2);
        
        String aiString = inputStream.readLine();
        aiString = aiString.replaceAll("[^\\d.]", "");
        int AI = Integer.parseInt(aiString) + 1;
        AI = (inGame) ? AI : 0;
        Player.PlayerType playerType = Player.PlayerType.values()[AI];
        
        String positionString = inputStream.readLine();
        StringTokenizer positionTokenizer = new StringTokenizer(positionString, ",");
        String xString = positionTokenizer.nextToken().replaceAll("[^\\d.]", "");
        int x = Integer.parseInt(xString);
        String yString = positionTokenizer.nextToken().replaceAll("[^\\d.]", "");
        int y = Integer.parseInt(yString);
        
        LinkedList<Treasure> treasures = new LinkedList<Treasure>();
        String treasuresString = inputStream.readLine();
        StringTokenizer treasuresTokenizer = new StringTokenizer(treasuresString, ",");
        while (treasuresTokenizer.hasMoreTokens())
        {
            String treasureString = treasuresTokenizer.nextToken();
            treasureString = treasureString.replaceAll("[^\\d.]", "");
            if (treasureString.length() != 0) // Add a treasure if the string contains a number
            {
                int treasure = Integer.parseInt(treasureString);
                treasures.add(new Treasure(Treasure.TreasureType.values()[treasure-1]));
            }
            else {}
        }
        
        inputStream.readLine(); // Skip this line
        
        Player loadedPlayer = new Player(playerNumber, playerType, name);
        loadedPlayer.setIcon(controller.getPlayerIconTray().getPlayerIcon(playerNumber));
        for (int i = 0; i < treasures.size(); i++)
        {
            loadedPlayer.assignTreasure(treasures.get(i));
        }
        
        loadedPlayer.setLoadedTilePosition(x, y);
        
        return loadedPlayer;
    }
}
