/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.logic;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

/**
 *
 * @author Corbi
 */
public class GameSaver 
{
    public static void saveGame(String fileName, Game game)
    {
        String JSON = "";
        JSON += "{\n";
        
        JSON += "  \"field\": [\n";
        for (int i = 0; i < 7; i++)
        {
            JSON += addBoardRow(i, game);
            JSON += (i != 6) ? "," : "";
            JSON += "\n";
        }
        JSON += "  ],\n";
        JSON += "  \"freeWayCard\": ";
        JSON += addNextTile(game);
        
        JSON += "  \"currentPlayer\": " + game.getCurrentPlayer() + ",\n";
        JSON += "  \"players\": [\n";
        for (int i = 0; i < 4; i++)
        {
            JSON += addPlayerRow(i, game);
            JSON += (i != 3) ? "," : "";
            JSON += "\n";
        }
        JSON += "  ]\n";
        
        JSON += "}";
        
        try (FileWriter outputStream = new FileWriter(fileName))
        {
            outputStream.write(JSON);
        }
        catch (IOException ex)
        {
            System.out.println(ex);
        }
    }
    
    private static String addBoardRow(int row, Game game)
    {
        String r = "";
        r += "    [";
        for (int i = 0; i < 7; i++)
        {
            Tile tile = game.getBoard().getTile(row, i);
            r += (i == 0) ? " " : "      ";
            r += "{";
            r += "\"type:\": " + convertShapeToInt(tile.getTileShape()) + ", ";
            r += "\"rotated\": " + (tile.getRotation() / 90) + ", ";
            r += "\"treasure\": " + convertTreasureToInt(tile.getTreasure());
            r += "}";
            r += (i != 6) ? "," : "";
            r += "\n";
        }
        r += "    ]";
        return r;
    }
    
    private static String addNextTile(Game game)
    {
        Tile tile = game.getNextTile();
        String r = "";
        r += "{";
        r += "\"type\": " + convertShapeToInt(tile.getTileShape()) + ", ";
        r += "\"rotated\": " + (tile.getRotation() / 90) + ", ";
        r += "\"treasure\": " + convertTreasureToInt(tile.getTreasure()) + ", ";
        r += "\"position\": {\"x\": -1, \"y\": -1}";
        r += "},\n";
        return r;
    }
    
    private static String addPlayerRow(int playerIndex, Game game)
    {
        Player player = game.getPlayer(playerIndex);
        String r = "";
        r += "    {\n";
        r += "      \"involved\": " + player.inGame() + ",\n";
        r += "      \"name\": \"" + player.getPlayerName() + "\",\n";
        r += "      \"directedBy\": " + convertAIToInt(player.getPlayerType()) + ",\n";
        r += "      \"position\": {";
        r += "\"x\": " + game.findPlayerRow(playerIndex) + ", ";
        r += "\"y\": " + game.findPlayerCol(playerIndex) + "},\n";
        r += "      \"treasureCards\": [";
        LinkedList<Treasure> playerTreasures = player.getTreasures();
        int size = playerTreasures.size();
        for (int i = 0; i < size; i++)
        {
            r += "" + convertTreasureToInt(playerTreasures.get(i));
            r += (i != (size - 1)) ? ", " : "";
        }
        r += "]\n";
        r += "    }";
        return r;
    }
    
    private static int convertShapeToInt(Tile.Shape shape)
    {
        int r = -1;
        switch (shape)
        {
            case I:
                r = 0;
                break;
            case L:
                r = 1;
                break;
            case T:
                r = 2;
                break;
        }
        return r;
    }
    
    private static int convertTreasureToInt(Treasure treasure)
    {
        int r;
        if (treasure != null)
        {
            r = treasure.getTreasureType().ordinal() + 1;
            r = (r > 24) ? 0 : r;
        }
        else
        {
            r = 0;
        }
        return r;
    }
    
    private static int convertAIToInt(Player.PlayerType playerType)
    {
        return playerType.ordinal();
    }
}
