/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame.gui;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import labyrinthboardgame.logic.Board;
import labyrinthboardgame.logic.Game;
import labyrinthboardgame.logic.Player;

/**
 *
 * @author Corbi
 */
public final class BoardView extends StackPane
{
    private InsertTileButton[] arrows;
    private int disabledArrow;
    
    private final GridPane boardPane;
    private final GridPane characterPane;
    
    private final PlayerCharacter[] characters;
    
    /**
     * The main game board, which holds all the tiles and treasures and players
     */
    public BoardView()
    {
        super();
        
        characters = new PlayerCharacter[4];
        
        boardPane = new GridPane();
        characterPane = new GridPane();
        
        boardPane.setGridLinesVisible(true);
        boardPane.setBackground(new Background(new BackgroundFill(Color.rgb(119, 119, 119), 
                CornerRadii.EMPTY, Insets.EMPTY)));
        AnchorPane.setBottomAnchor(boardPane, 0.0);
        AnchorPane.setLeftAnchor(boardPane, 0.0);
        AnchorPane.setTopAnchor(boardPane, 0.0);
        AnchorPane.setRightAnchor(boardPane, 0.0);
        
        for (int i = 0; i < 9; i++)
        {
            ColumnConstraints colConstraint = new ColumnConstraints();
            colConstraint.setPrefWidth(TileView.TILE_SIZE + TileView.TILE_PADDING);
            boardPane.getColumnConstraints().add(colConstraint);
            
            ColumnConstraints cColConstraint = new ColumnConstraints();
            cColConstraint.setPrefWidth(TileView.TILE_SIZE + TileView.TILE_PADDING);
            characterPane.getColumnConstraints().add(cColConstraint);
        }
        for (int j = 0; j < 9; j++)
        {
            RowConstraints rowConstraint = new RowConstraints();
            rowConstraint.setPrefHeight(TileView.TILE_SIZE + TileView.TILE_PADDING);
            boardPane.getRowConstraints().add(rowConstraint);
            
            RowConstraints cRowConstraint = new RowConstraints();
            cRowConstraint.setPrefHeight(TileView.TILE_SIZE + TileView.TILE_PADDING);
            characterPane.getRowConstraints().add(cRowConstraint);
        }
        
        // Click through
        characterPane.setPickOnBounds(false);
        characterPane.setMouseTransparent(true);
        
        getChildren().add(boardPane);
        getChildren().add(characterPane);
        
        addEmptyTiles();
    }
    
    /**
     * Returns the board pane
     * @return The board pane
     */
    public GridPane getBoardPane()
    {
        return boardPane;
    }
    
    /**
     * Returns the character pane
     * @return The character pane
     */
    public GridPane getCharacterPane()
    {
        return characterPane;
    }
    
    /**
     * Creates a player character from a player
     * @param playerIndex The index of the player
     * @param player The player to make a character for
     */
    public void createPlayerCharacter(int playerIndex, Player player)
    {
        characters[playerIndex] = new PlayerCharacter(player);
    }
    
    /**
     * Returns the player character
     * @param playerIndex The index of player
     * @return The player character
     */
    public PlayerCharacter getPlayerCharacter(int playerIndex)
    {
        return characters[playerIndex];
    }
    
    /**
     * Returns a character tile view at row and col
     * @param row The row of the character tile view
     * @param col The column of the character tile view
     * @return A character tile view at row and col
     */
    public CharacterTileView getCharacterTileView(int row, int col)
    {
        CharacterTileView rCTileView = null;
        if (row >= 0 && row <= 6 && col >= 0 && col <= 6)
        {
            for (Node view : characterPane.getChildren())
            {
                Integer r = boardPane.getRowIndex(view);
                Integer c = boardPane.getColumnIndex(view);
                if (r != null && r == (row+1) && c != null && c == (col+1))
                {
                    rCTileView = ((CharacterTileView)(view));
                    break;
                }
                else {}
            }
        }
        else {}
        return rCTileView;
    }
    
    /**
     * Returns a tile view at row and col
     * @param row The row of the tile view
     * @param col The column of the tile view
     * @return A tile view at row and col
     */
    public TileView getTileView(int row, int col)
    {
        TileView rTileView = null;
        if (row >= 0 && row <= 6 && col >= 0 && col <= 6)
        {
            for (Node view : boardPane.getChildren())
            {
                Integer r = boardPane.getRowIndex(view);
                Integer c = boardPane.getColumnIndex(view);
                if (r != null && r == (row+1) && c != null && c == (col+1))
                {
                    rTileView = ((TileView)(view));
                    break;
                }
                else {}
            }
        }
        else {}
        return rTileView;
    }
    
    /**
     * Sets up the arrows and the reference for updating the preview tile
     * @param game A reference to the game for updating the preview tile
     */
    public void setupArrows(Game game)
    {
        arrows = new InsertTileButton[12];
        
        // Top
        arrows[0] = new InsertTileButton(game, Board.ArrowPosition.TopLeft);
        boardPane.add(arrows[0], 2, 0);
        arrows[1] = new InsertTileButton(game, Board.ArrowPosition.TopCenter);
        boardPane.add(arrows[1], 4, 0);
        arrows[2] = new InsertTileButton(game, Board.ArrowPosition.TopRight);
        boardPane.add(arrows[2], 6, 0);
        // Left
        arrows[3] = new InsertTileButton(game, Board.ArrowPosition.LeftTop);
        boardPane.add(arrows[3], 0, 2);
        arrows[4] = new InsertTileButton(game, Board.ArrowPosition.LeftCenter);
        boardPane.add(arrows[4], 0, 4);
        arrows[5] = new InsertTileButton(game, Board.ArrowPosition.LeftBottom);
        boardPane.add(arrows[5], 0, 6);
        // Bottom
        arrows[6] = new InsertTileButton(game, Board.ArrowPosition.BottomLeft);
        boardPane.add(arrows[6], 2, 8);
        arrows[7] = new InsertTileButton(game, Board.ArrowPosition.BottomCenter);
        boardPane.add(arrows[7], 4, 8);
        arrows[8] = new InsertTileButton(game, Board.ArrowPosition.BottomRight);
        boardPane.add(arrows[8], 6, 8);
        // Right
        arrows[9] = new InsertTileButton(game, Board.ArrowPosition.RightTop);
        boardPane.add(arrows[9], 8, 2);
        arrows[10] = new InsertTileButton(game, Board.ArrowPosition.RightCenter);
        boardPane.add(arrows[10], 8, 4);
        arrows[11] = new InsertTileButton(game, Board.ArrowPosition.RightBottom);
        boardPane.add(arrows[11], 8, 6);
        
        disabledArrow = -1;
    }
    
    /**
     * Disables the arrow buttons and stores the button opposite the last
     * one pressed so it remains disabled when the others are enabled
     * @param disabled The arrow button to remain disabled
     */
    public void disableArrows(int disabled)
    {
        for (int i = 0; i < 12; i++)
        {
            arrows[i].disable();
        }
        disabledArrow = disabled;
    }
    
    /**
     * Re-enables the arrow buttons (aside from the button opposite the one
     * last pressed)
     * @param human If human, enable the buttons for pressing; otherwise
     * the change is only visual
     */
    public void enableArrows(boolean human)
    {
        for (int i = 0; i < 12; i++)
        {
            if (i != disabledArrow)
            {
                arrows[i].enable(human);
            }
            else {}
        }
    }
    
    /**
     * Update the next tile image and all previews when rotated
     */
    public void rotatePreviewClockwise()
    {
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
        for (int i = 0; i < 12; i++)
        {
            arrows[i].rotatePreviewCounterClockwise();
        }
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
        boardPane.add(emptyTilePane, i, j);
    }
    
    /**
     * Enables viewing which tiles can be moved to
     */
    public void showPaths()
    {
        boardPane.getChildren().forEach(view -> {
            if (view instanceof TileView)
            {
                ((TileView)view).showPath();
            }
            else {}
        });
    }
    
    /**
     * Disables viewing which tiles can be moved to
     */
    public void hidePaths()
    {
        boardPane.getChildren().forEach(view -> {
            if (view instanceof TileView)
            {
                ((TileView)view).hidePath();
            }
            else {}
        });
    }
}
