/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stacker;

import grid.Grid;
import java.awt.Graphics;

/**
 *
 * @author leonsurwald
 */
public class StackData {

    Grid grid;

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
    private int currentRow = 14;                      //how to ask the grid for the row number? game grid? why isn't it grid.getRows()
    private Direction direction = Direction.RIGHT;

    private Block[][] gameGrid;

    public void stopMovement() {

        if (currentRow >= 0) {
            currentRow--;
        }
        if (currentRow <= 14) {
            addBlocksToRow(currentRow, 3);
        }
        if (currentRow <= 11) {
            addBlocksToRow(currentRow, 2);
        }
        if (currentRow <= 5) {
            addBlocksToRow(currentRow, 1);
        }

    }

    public void eliminateBlocks() {
        //check if blocks in row below have same x values
        //if not delete blocks with new x values in current row
        //remaining number of blocks is the new value of numberToAdd
    }

    public void addBlocksToRow(int row, int numberToAdd) {
        //clean out any existing blocks in that currentRow
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

        // check if you're at the end of the currentRow
        //if yes reverse the direction
        if (direction == Direction.RIGHT) {
            //if there is a block in the rightmost column, then we need to reverse direction
            if (gameGrid[currentRow][gameGrid[currentRow].length - 1] != null) {
                direction = Direction.LEFT;
            }
        } else { // direct MUST be left
            //if there is a block in the lefttmost column, then we need to reverse direction
            if (gameGrid[currentRow][0] != null) {
                direction = Direction.RIGHT;

            }
        }

        //if moving to the right increase the column number for all the blocks by 1
        if (direction == Direction.RIGHT) {
            for (int column = gameGrid[currentRow].length - 2; column >= 0; column--) {
//                if there is a block, then move it to the RIGHT
                if (gameGrid[currentRow][column] != null) {
                    gameGrid[currentRow][column + 1] = gameGrid[currentRow][column];
                    gameGrid[currentRow][column] = null;
                    gameGrid[currentRow][column + 1].setX(column + 1);
                }
            }
        }

        //if moving to the left decrease the column number for all the blocks by 1
        if (direction == Direction.LEFT) {
            for (int column = 1; column < gameGrid[currentRow].length; column++) {
//
                if (gameGrid[currentRow][column] != null) {
                    gameGrid[currentRow][column - 1] = gameGrid[currentRow][column];
                    gameGrid[currentRow][column] = null;
                    gameGrid[currentRow][column - 1].setX(column - 1);
                }
            }
        }

        // make it move automagically
        // make different speeds )(later)
        // set currentRow logic (stop, elimnate, new currentRow)
        // differetn block counts for (later)
    }

}
