/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stacker;

import java.awt.Graphics;

/**
 *
 * @author leonsurwald
 */
public final class StackData {

    public StackData(int rows, int columns, CellDataProviderIntf cellData, StackDataEventListenerIntf stackDataEvent) {
        gameGrid = new Block[rows][columns];
        this.cellData = cellData;
        this.stackDataEvent = stackDataEvent;
    }

    public void draw(Graphics graphics) {
        for (int row = 0; row < getGameGrid().length; row++) {
            for (int column = 0; column < getGameGrid()[row].length; column++) {
                if (getGameGrid()[row][column] != null) {
                    getGameGrid()[row][column].draw(graphics);
                }

            }
        }
    }

    public void stopMovement() {

        if (getCurrentRow() <= 0) {
            setDirection(Direction.STOP);
        }

        eliminateBlocks();

        if (getCurrentRow() > 0) {
            setCurrentRow(getCurrentRow() - 1);

            int blocksToAdd = 3;

            if (getCurrentRow() <= getChangeBlocksToAddFrom3to2()) {
                blocksToAdd = 2;
            }

            if (getCurrentRow() <= getChangeBlocksToAddFrom2to1()) {
                blocksToAdd = 1;
            }

            if (currentRow < gameGrid.length - 1) {
                blocksToAdd = Math.min(countBlocks(currentRow + 1), blocksToAdd);
            }

            //if blocksToAdd = 0, then end the game
            if (blocksToAdd <= 0) {
                stackDataEvent.onEvent(StackDataEventListenerIntf.EVENT_GAME_OVER);
            }

            if (getCurrentRow() < 0) {
                stackDataEvent.onEvent(StackDataEventListenerIntf.EVENT_GAME_WON);
            }

            addBlocksToRow(getCurrentRow(), blocksToAdd);

        }
    }

    public int countBlocks(int row) {
        int counter = 0;
        for (int column = 0; column < gameGrid[row].length; column++) {
            if (gameGrid[row][column] != null) {
                counter++;
            }
        }
        return counter;
    }

    public void eliminateBlocks() {
        if (getCurrentRow() < getGameGrid().length - 1) {
            for (int column = 0; column < getGameGrid()[getCurrentRow()].length; column++) {
                if ((getGameGrid()[getCurrentRow()][column] != null) && (getGameGrid()[getCurrentRow() + 1][column] == null)) {
                    getGameGrid()[getCurrentRow()][column] = null;

                }
            }
        }
//        //check if blocks in row below have same x values
//        //if not xx blocks with new x values in current row
//        //remaining number of blocks is the new value of numberToAdd
    }

    public void resetGame() {

        for (int row = 0; row < getGameGrid().length; row++) {
            for (int column = 0; column < getGameGrid()[row].length; column++) {    //getGameGrid()[row].length
                getGameGrid()[row][column] = null;

            }
        }

        addBlocksToRow(getGameGrid().length - 1, 3);

        this.currentRow = getGameGrid().length - 1;

    }

    public void addBlocksToRow(int row, int numberToAdd) {
        //clean out any existing blocks in that currentRow
        // start at position 0, add numberToAdd blocks, moving to the right each time
        // make sure you don't go outside of structure

        int addCounter = 0;
        if (getGameGrid() != null) {
            for (int column = 0; column < getGameGrid()[row].length; column++) {
                //clear the cell
                getGameGrid()[row][column] = null;

                //add a block if required
                if (addCounter < numberToAdd) {
                    addCounter++;
                    getGameGrid()[row][column] = new Block(column, row, getCellData());
                }
            }
        }

    }

    public void move() {
        if (moveDelayCounter >= speed.getValue()) {
            moveBlocks();
            moveDelayCounter = 0;
        } else {
            moveDelayCounter++;
        }
        changeSpeed();
    }

    public void changeSpeed() {

        if (getCurrentRow() == 0) {
            setSpeed(Speed.CRAZY);
        } else if (getCurrentRow() == 1) {
            setSpeed(Speed.FAST);
        } else if (getCurrentRow() <= 7) {
            setSpeed(Speed.MEDIUM);
        } else if (getCurrentRow() <= 15) {
            setSpeed(Speed.SLOW);
        }
    }

    private void moveBlocks() {

        // check if you're at the end of the currentRow
        //if yes reverse the direction
        if (getDirection() == Direction.RIGHT) {
            //if there is a block in the rightmost column, then we need to reverse direction
            if (getGameGrid()[getCurrentRow()][getGameGrid()[getCurrentRow()].length - 1] != null) {
                setDirection(Direction.LEFT);
            }
        } else { // direct MUST be left
            //if there is a block in the lefttmost column, then we need to reverse direction
            if (getGameGrid()[getCurrentRow()][0] != null) {
                setDirection(Direction.RIGHT);

            }
        }

        //if moving to the right increase the column number for all the blocks by 1
        if (getDirection() == Direction.RIGHT) {
            for (int column = getGameGrid()[getCurrentRow()].length - 2; column >= 0; column--) {
//                if there is a block, then move it to the RIGHT
                if (getGameGrid()[getCurrentRow()][column] != null) {
                    getGameGrid()[getCurrentRow()][column + 1] = getGameGrid()[getCurrentRow()][column];
                    getGameGrid()[getCurrentRow()][column] = null;
                    getGameGrid()[getCurrentRow()][column + 1].setX(column + 1);
                }
            }
        } else if (getDirection() == Direction.STOP) {

        } else if (getDirection() == Direction.LEFT) {
            //if moving to the left decrease the column number for all the blocks by 1
            for (int column = 1; column < getGameGrid()[getCurrentRow()].length; column++) {
//
                if (getGameGrid()[getCurrentRow()][column] != null) {
                    getGameGrid()[getCurrentRow()][column - 1] = getGameGrid()[getCurrentRow()][column];
                    getGameGrid()[getCurrentRow()][column] = null;
                    getGameGrid()[getCurrentRow()][column - 1].setX(column - 1);
                }
            }
        }

    }

    public void gameOver() {

    }

    public void restart(int row) {

    }

    //<editor-fold defaultstate="collapsed" desc="Properties">
    private CellDataProviderIntf cellData;
    private final StackDataEventListenerIntf stackDataEvent;

    private Speed speed = Speed.SLOW;
    private int moveDelayCounter = 0;

    private int currentRow = 14;                      //how to ask the grid for the row number? game grid? why isn't it grid.getRows()
    private Direction direction = Direction.RIGHT;
    private GameState state;
    private int changeBlocksToAddFrom3to2 = 11;
    private int changeBlocksToAddFrom2to1 = 5;
    private Block[][] gameGrid;

    /**
     * @return the cellData
     */
    public CellDataProviderIntf getCellData() {
        return cellData;
    }

    /**
     * @param cellData the cellData to set
     */
    public void setCellData(CellDataProviderIntf cellData) {
        this.cellData = cellData;
    }

    /**
     * @return the speed
     */
    public Speed getSpeed() {
        return speed;
    }

    /**
     * @param speed the speed to set
     */
    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    /**
     * @return the currentRow
     */
    public int getCurrentRow() {
        return currentRow;
    }

    /**
     * @param currentRow the currentRow to set
     */
    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    /**
     * @return the direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * @return the state
     */
    public GameState getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(GameState state) {
        this.state = state;
    }

    /**
     * @return the gameGrid
     */
    public Block[][] getGameGrid() {
        return gameGrid;
    }

    /**
     * @param gameGrid the gameGrid to set
     */
    public void setGameGrid(Block[][] gameGrid) {
        this.gameGrid = gameGrid;
    }

    /**
     * @return the changeBlocksToAddFrom3to2
     */
    public int getChangeBlocksToAddFrom3to2() {
        return changeBlocksToAddFrom3to2;
    }

    /**
     * @param changeBlocksToAddFrom3to2 the changeBlocksToAddFrom3to2 to set
     */
    public void setChangeBlocksToAddFrom3to2(int changeBlocksToAddFrom3to2) {
        this.changeBlocksToAddFrom3to2 = changeBlocksToAddFrom3to2;
    }

    /**
     * @return the changeBlocksToAddFrom2to1
     */
    public int getChangeBlocksToAddFrom2to1() {
        return changeBlocksToAddFrom2to1;
    }

    /**
     * @param changeBlocksToAddFrom2to1 the changeBlocksToAddFrom2to1 to set
     */
    public void setChangeBlocksToAddFrom2to1(int changeBlocksToAddFrom2to1) {
        this.changeBlocksToAddFrom2to1 = changeBlocksToAddFrom2to1;
    }
//</editor-fold>

}
