/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.gui;

import javafx.geometry.Insets;
import labyrinthboardgame.logic.Tile;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import labyrinthboardgame.logic.Player;
import labyrinthboardgame.logic.TileSet;

/**
 *
 * @author Corbi
 */
public class LabyrinthGameBoard extends GridPane
{
    private GameBoardController gbController;
    private Tile[][] tiles;
    private Tile nextTile;
    private TileSet tileSet;
    
    private InsertTileButton[] arrows;
    private InsertTileButton disabledArrow;
    
    /**
     * The main game board, which holds all the tiles and treasures and players
     */
    public LabyrinthGameBoard()
    {
        super();
        
        createArrows();
        addEmptyTiles();
    }
    
    /**
     * Get a reference to the controller
     * @param controller The controller for handling input and events
     */
    public void setGameBoardController(GameBoardController controller)
    {
        gbController = controller;
    }
    
    /**
     * Inserts the next tile into the game board, moving all tiles in that line, and updating the new next tile
     * @param position Where and which direction to insert the next tile
     */
    public void insertTile(InsertTileButton.ArrowPosition position)
    {
        switch (position)
        {
            // Top
            case TopLeft:
                placeTileVertical(1, true);
                disabledArrow = arrows[6];
                break;
            case TopCenter:
                placeTileVertical(3, true);
                disabledArrow = arrows[7];
                break;
            case TopRight:
                placeTileVertical(5, true);
                disabledArrow = arrows[8];
                break;
            // Left
            case LeftTop:
                placeTileHorizontal(1, true);
                disabledArrow = arrows[9];
                break;
            case LeftCenter:
                placeTileHorizontal(3, true);
                disabledArrow = arrows[10];
                break;
            case LeftBottom:
                placeTileHorizontal(5, true);
                disabledArrow = arrows[11];
                break;
            // Bottom
            case BottomLeft:
                placeTileVertical(1, false);
                disabledArrow = arrows[0];
                break;
            case BottomCenter:
                placeTileVertical(3, false);
                disabledArrow = arrows[1];
                break;
            case BottomRight:
                placeTileVertical(5, false);
                disabledArrow = arrows[2];
                break;
            // Right
            case RightTop:
                placeTileHorizontal(1, false);
                disabledArrow = arrows[3];
                break;
            case RightCenter:
                placeTileHorizontal(3, false);
                disabledArrow = arrows[4];
                break;
            case RightBottom:
                placeTileHorizontal(5, false);
                disabledArrow = arrows[5];
                break;
        }
        updateTileNeighbors(); // Refresh the paths between tiles
        
        // Disable all the arrows and move into the player move phase
        for (int i = 0; i < 12; i++)
        {
            arrows[i].disable();
        }
        
        // Display the paths the player can take
        gbController.showPaths();
    }
    
    /**
     * Places the next tile along a column, moving all the other tiles up or down
     * @param column The column to insert the new tile into
     * @param fromAbove If true, the tiles all move down; else, up
     */
    public void placeTileVertical(int column, boolean fromAbove)
    {
        int i = (fromAbove) ? 6 : 0;
        int j = column;
        int next = (fromAbove) ? -1 : +1;
        Tile temp = tiles[i][j]; // The tile to remove
        this.getChildren().remove(temp.getTileView());
        for (int count = 0; count < 6; count++)
        {
            tiles[i][j] = tiles[i+next][j];
            this.getChildren().remove(tiles[i][j].getTileView());
            this.add(tiles[i][j].getTileView(), j+1, i+1);
            i += next;
        }
        Tile newTile = tileSet.getNextTile();
        temp.movePlayers(newTile); // Move any players off the old tile and onto the new one
        tiles[i][j] = newTile;
        this.add(tiles[i][j].getTileView(), j+1, i+1);
        tileSet.setNextTile(temp); // The new next tile
        nextTile = temp; // Update the local reference
        
        gbController.updateNextTile();
    }
    
    /**
     * Places the next tile along a row, moving all the other tiles left or right
     * @param row The row to insert the new tile into
     * @param fromLeft If true, the tiles all move right; else, left
     */
    public void placeTileHorizontal(int row, boolean fromLeft)
    {
        int i = row;
        int j = (fromLeft) ? 6 : 0;
        int next = (fromLeft) ? -1 : +1;
        Tile temp = tiles[i][j]; // The tile to remove
        this.getChildren().remove(temp.getTileView());
        for (int count = 0; count < 6; count++)
        {
            tiles[i][j] = tiles[i][j+next];
            this.getChildren().remove(tiles[i][j].getTileView());
            this.add(tiles[i][j].getTileView(), j+1, i+1);
            j += next;
        }
        Tile newTile = tileSet.getNextTile();
        temp.movePlayers(newTile); // Move any players off the old tile and onto the new one
        tiles[i][j] = newTile;
        this.add(tiles[i][j].getTileView(), j+1, i+1);
        tileSet.setNextTile(temp); // The new next tile
        nextTile = temp; // Update the local reference
        
        gbController.updateNextTile();
    }
    
    /**
     * Return a copy of the next tile for displaying a preview
     * @return A copy of the next tile to insert
     */
    public Tile getNextTile()
    {
        return new Tile(nextTile);
    }
    
    /**
     * Update the next tile image and all previews when rotated
     */
    public void rotatePreviewClockwise()
    {
        nextTile.rotateClockwise();
        for (int i = 0; i < 12; i++)
        {
            arrows[i].rotatePreviewClockwise();
        }
    }
    
    /**
     * Update the next tile image and all previews when rotated
     */
    public void rotatePreviewCounterClockwise()
    {
        nextTile.rotateCounterClockwise();
        for (int i = 0; i < 12; i++)
        {
            arrows[i].rotatePreviewCounterClockwise();
        }
    }
    
    /**
     * Fill the board with tiles
     * @param tileSet The set of free-moving tiles to insert into the board
     */
    public void setupTiles(TileSet tileSet)
    {                
        tiles = new Tile[7][7];
        //tileSet = new TileSet();
        this.tileSet = tileSet;
        
        tiles[0][0] = new Tile(Tile.Shape.L, 90, 4); // Red
        tiles[0][1] = tileSet.getNextInitialTile(); // Random
        tiles[0][2] = new Tile(Tile.Shape.T, 0, new Treasure(Treasure.TreasureType.s24)); // Book
        tiles[0][3] = tileSet.getNextInitialTile(); // Random
        tiles[0][4] = new Tile(Tile.Shape.T, 0, new Treasure(Treasure.TreasureType.s18)); // Money
        tiles[0][5] = tileSet.getNextInitialTile(); // Random
        tiles[0][6] = new Tile(Tile.Shape.L, 180, 1); // Yellow
        
        tiles[1][0] = tileSet.getNextInitialTile(); // Random
        tiles[1][1] = tileSet.getNextInitialTile(); // Random
        tiles[1][2] = tileSet.getNextInitialTile(); // Random
        tiles[1][3] = tileSet.getNextInitialTile(); // Random
        tiles[1][4] = tileSet.getNextInitialTile(); // Random
        tiles[1][5] = tileSet.getNextInitialTile(); // Random
        tiles[1][6] = tileSet.getNextInitialTile(); // Random
        
        tiles[2][0] = new Tile(Tile.Shape.T, 270, new Treasure(Treasure.TreasureType.s6)); // Map
        tiles[2][1] = tileSet.getNextInitialTile(); // Random
        tiles[2][2] = new Tile(Tile.Shape.T, 270, new Treasure(Treasure.TreasureType.s9)); // Crown
        tiles[2][3] = tileSet.getNextInitialTile(); // Random
        tiles[2][4] = new Tile(Tile.Shape.T, 0, new Treasure(Treasure.TreasureType.s5)); // Keys
        tiles[2][5] = tileSet.getNextInitialTile(); // Random
        tiles[2][6] = new Tile(Tile.Shape.T, 90, new Treasure(Treasure.TreasureType.s17)); // Skull
        
        tiles[3][0] = tileSet.getNextInitialTile(); // Random
        tiles[3][1] = tileSet.getNextInitialTile(); // Random
        tiles[3][2] = tileSet.getNextInitialTile(); // Random
        tiles[3][3] = tileSet.getNextInitialTile(); // Random
        tiles[3][4] = tileSet.getNextInitialTile(); // Random
        tiles[3][5] = tileSet.getNextInitialTile(); // Random
        tiles[3][6] = tileSet.getNextInitialTile(); // Random
        
        tiles[4][0] = new Tile(Tile.Shape.T, 270, new Treasure(Treasure.TreasureType.s22)); // Ring
        tiles[4][1] = tileSet.getNextInitialTile(); // Random
        tiles[4][2] = new Tile(Tile.Shape.T, 180, new Treasure(Treasure.TreasureType.s21)); // Chest
        tiles[4][3] = tileSet.getNextInitialTile(); // Random
        tiles[4][4] = new Tile(Tile.Shape.T, 90, new Treasure(Treasure.TreasureType.s2)); // Emerald
        tiles[4][5] = tileSet.getNextInitialTile(); // Random
        tiles[4][6] = new Tile(Tile.Shape.T, 90, new Treasure(Treasure.TreasureType.s4)); // Sword
        
        tiles[5][0] = tileSet.getNextInitialTile(); // Random
        tiles[5][1] = tileSet.getNextInitialTile(); // Random
        tiles[5][2] = tileSet.getNextInitialTile(); // Random
        tiles[5][3] = tileSet.getNextInitialTile(); // Random
        tiles[5][4] = tileSet.getNextInitialTile(); // Random
        tiles[5][5] = tileSet.getNextInitialTile(); // Random
        tiles[5][6] = tileSet.getNextInitialTile(); // Random
        
        tiles[6][0] = new Tile(Tile.Shape.L, 0, 3); // Green
        tiles[6][1] = tileSet.getNextInitialTile(); // Random
        tiles[6][2] = new Tile(Tile.Shape.T, 180, new Treasure(Treasure.TreasureType.s15)); // Candelabra
        tiles[6][3] = tileSet.getNextInitialTile(); // Random
        tiles[6][4] = new Tile(Tile.Shape.T, 180, new Treasure(Treasure.TreasureType.s23)); // Helmet
        tiles[6][5] = tileSet.getNextInitialTile(); // Random
        tiles[6][6] = new Tile(Tile.Shape.L, 270, 2); // Blue
        
        // Add listeners for moving the players
        for (int i = 0; i < 7; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                this.add(tiles[i][j].getTileView(), j+1, i+1);
                tiles[i][j].setListener(this);
            }
        }
        
        nextTile = tileSet.getNextTile();
        nextTile.setListener(this); // Add a listener to the last remaining tile as well
        updateTileNeighbors(); // Update the paths between tiles
    }
    
    /**
     * Updates the paths between tiles
     */
    private void updateTileNeighbors()
    {
        for (int i = 0; i < 7; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                Tile top = (i > 0) ? tiles[i-1][j] : null;
                Tile right = (j < 6) ? tiles[i][j+1] : null;
                Tile bottom = (i < 6) ? tiles[i+1][j] : null;
                Tile left = (j > 0) ? tiles[i][j-1] : null;
                tiles[i][j].updateConnectedNeighbors(top, right, bottom, left);
            }
        }
    }
    
    /**
     * Adds the player characters to their starting locations on the board
     * @param player The player to be added to the board
     */
    public void addPlayerCharacterToBoard(Player player)
    {
        switch (player.getPlayerNumber())
        {
            case 1:
                player.moveCharacter(tiles[0][6]);
                break;
            case 2:
                player.moveCharacter(tiles[6][6]);
                break;
            case 3:
                player.moveCharacter(tiles[6][0]);
                break;
            case 4:
                player.moveCharacter(tiles[0][0]);
                break;
        }
    }
    
    /**
     * Returns the current player
     * @return The current player
     */
    public Player getCurrentPlayer()
    {
        return gbController.getCurrentPlayer();
    }
    
    /**
     * Moves a player's character to a new tile and switches to the next player
     * @param tile The tile the player's character moves to
     */
    public void movePlayerToTile(Tile tile)
    {
        getCurrentPlayer().moveCharacter(tile);
        gbController.switchPlayers(); // After moving, switch players
        
        // Disable the tile insert arrows if the game is over
        if (!getCurrentPlayer().getHasWon())
        {
            for (int i = 0; i < 12; i++)
            {
                if (arrows[i] != disabledArrow)
                {
                    arrows[i].enable();
                }
                else {}
            }
        }
    }
    
    /**
     * Creates and adds the insert tile buttons around the border
     */
    private void createArrows()
    {
        arrows = new InsertTileButton[12];
        
        // Top
        arrows[0] = new InsertTileButton(this, InsertTileButton.ArrowPosition.TopLeft);
        this.add(arrows[0], 2, 0);
        arrows[1] = new InsertTileButton(this, InsertTileButton.ArrowPosition.TopCenter);
        this.add(arrows[1], 4, 0);
        arrows[2] = new InsertTileButton(this, InsertTileButton.ArrowPosition.TopRight);
        this.add(arrows[2], 6, 0);
        // Left
        arrows[3] = new InsertTileButton(this, InsertTileButton.ArrowPosition.LeftTop);
        this.add(arrows[3], 0, 2);
        arrows[4] = new InsertTileButton(this, InsertTileButton.ArrowPosition.LeftCenter);
        this.add(arrows[4], 0, 4);
        arrows[5] = new InsertTileButton(this, InsertTileButton.ArrowPosition.LeftBottom);
        this.add(arrows[5], 0, 6);
        // Bottom
        arrows[6] = new InsertTileButton(this, InsertTileButton.ArrowPosition.BottomLeft);
        this.add(arrows[6], 2, 8);
        arrows[7] = new InsertTileButton(this, InsertTileButton.ArrowPosition.BottomCenter);
        this.add(arrows[7], 4, 8);
        arrows[8] = new InsertTileButton(this, InsertTileButton.ArrowPosition.BottomRight);
        this.add(arrows[8], 6, 8);
        // Right
        arrows[9] = new InsertTileButton(this, InsertTileButton.ArrowPosition.RightTop);
        this.add(arrows[9], 8, 2);
        arrows[10] = new InsertTileButton(this, InsertTileButton.ArrowPosition.RightCenter);
        this.add(arrows[10], 8, 4);
        arrows[11] = new InsertTileButton(this, InsertTileButton.ArrowPosition.RightBottom);
        this.add(arrows[11], 8, 6);
        
        disabledArrow = null;
    }
    
    /**
     * Fills in the empty space around the board that goes unused
     */
    private void addEmptyTiles()
    {
        addEmptyTile(0, 0);
        addEmptyTile(0, 1);
        addEmptyTile(0, 3);
        addEmptyTile(0, 5);
        addEmptyTile(0, 7);
        addEmptyTile(0, 8);
        
        addEmptyTile(1, 0);
        addEmptyTile(3, 0);
        addEmptyTile(5, 0);
        addEmptyTile(7, 0);
        addEmptyTile(8, 0);
        
        addEmptyTile(8, 1);
        addEmptyTile(8, 3);
        addEmptyTile(8, 5);
        addEmptyTile(8, 7);
        addEmptyTile(8, 8);
        
        addEmptyTile(1, 8);
        addEmptyTile(3, 8);
        addEmptyTile(5, 8);
        addEmptyTile(7, 8);
    }
    
    /**
     * Adds decoration around the border of the board at i, j
     * @param i The row to add the empty tile
     * @param j The column to add the empty tile
     */
    private void addEmptyTile(int i, int j)
    {
        StackPane emptyTilePane = new StackPane();
        emptyTilePane.setBackground(new Background(new BackgroundFill(Color.rgb(119, 98, 82), 
                CornerRadii.EMPTY, Insets.EMPTY)));
        this.add(emptyTilePane, i, j);
    }
}
