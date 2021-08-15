/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 *
 * @author Corbi
 */
public class ConfigurationManager 
{
    public static int TILE_ANIMATION_SPEED;
    public static int CHARACTER_ANIMATION_SPEED;
    
    private final static int DEFAULT_TILE_ANIMATION_SPEED = 500;
    private final static int DEFAULT_CHARACTER_ANIMATION_SPEED = 1000;
    
    public static void loadConfiguration()
    {
        File file = new File("labyrinth.config");
        if (file.exists())
        {
            try (BufferedReader inputStream = new BufferedReader(new FileReader(file)))
            {
                String config = inputStream.readLine();
                StringTokenizer tokenizer = new StringTokenizer(config, "|");
                TILE_ANIMATION_SPEED = Integer.parseInt(tokenizer.nextToken());
                CHARACTER_ANIMATION_SPEED = Integer.parseInt(tokenizer.nextToken());
            }
            catch (IOException ex)
            {
                TILE_ANIMATION_SPEED = DEFAULT_TILE_ANIMATION_SPEED;
                CHARACTER_ANIMATION_SPEED = DEFAULT_CHARACTER_ANIMATION_SPEED;
                System.out.println(ex);
            }
        }
        else
        {
            TILE_ANIMATION_SPEED = DEFAULT_TILE_ANIMATION_SPEED;
            CHARACTER_ANIMATION_SPEED = DEFAULT_CHARACTER_ANIMATION_SPEED;
            saveConfiguration();
        }
    }
    
    public static void saveConfiguration()
    {
        File file = new File("labyrinth.config");
        try (FileWriter outputStream = new FileWriter(file))
        {
            outputStream.write(TILE_ANIMATION_SPEED + "|" + CHARACTER_ANIMATION_SPEED);
        }
        catch (IOException ex)
        {
            System.out.println(ex);
        }
    }
}
