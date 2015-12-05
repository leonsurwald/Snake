/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stacker;

import java.awt.Graphics;
import stacker.Block;

/**
 *
 * @author leonsurwald
 */
public class StackData {

    public void draw(Graphics graphics) {
        for (int row = 0; row < gameGrid.length; row++) {
            for (int column = 0; column < gameGrid[row].length; column++) {
                if (gameGrid[row][column] != null) {
                    gameGrid[row][column].draw(graphics);
                }
            }
        }
    }

    public StackData(int rows, int columns, CellDataProviderIntf cellData) {
        gameGrid = new Block[rows][columns];

        this.cellData = cellData;
    }

    private final CellDataProviderIntf cellData;
    private Speed speed = Speed.SLOW;
    private int currentRow = 14;
    private Direction direction = Direction.RIGHT;

    private Block[][] gameGrid;

    public void addBlocksToRow(int row, int numberToAdd) {
        //clean out any existing blocks in that row
        // start at position 0, add numberToAdd blocks, moving to the right each time
        // make sure you don't go outside of structure

        int addCounter = 0;
        if (gameGrid != null) {
            for (int column = 0; column < gameGrid[row].length; column++) {
                //clear the cell
                gameGrid[row][column] = null;

                //add a block if required
                if (addCounter < numberToAdd) {
                    addCounter++;
                    gameGrid[row][column] = new Block(column, row, cellData);
                }
            }
        }

    }

    public void move() {
        // check if you're at the end of the row 
        //if yes reverse the direction
        if (direction == Direction.RIGHT) {
            //if there is a block in the rightmost column, then we need to reverse direction
            if (gameGrid[ currentRow ][ gameGrid[currentRow].length ] != null) {
                direction = Direction.LEFT;
            }
        } else { // direct MUST be left
            //if there is a block in the lefttmost column, then we need to reverse direction
            if (gameGrid[ currentRow ][ 0 ] != null) {
                direction = Direction.RIGHT;
            }
        }
        
        

        //if moving to the right increase the column number for all the blocks by 1
        
        if (direction == Direction.RIGHT) {
            for (int column = gameGrid[currentRow].length - 2; column < 0; column--) {
//                if there is a block, then move it to the RIGHT
                if (gameGrid[currentRow][column] != null) {
                   gameGrid[currentRow][column + 1] = gameGrid[currentRow][column];
                   gameGrid[currentRow][column] = null;
                   gameGrid[currentRow][column + 1].setX(column + 1);
                }
            }
        }
        
        //if moving to the left decrease the column number for all the blocks by 1

    }
}
