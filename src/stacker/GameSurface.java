/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stacker;

import audio.AudioPlayer;
import environment.Environment;
import grid.Grid;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
//import java.util.ArrayList;

// bottom 3 rows 3 blocks; middle 6 rows 2 blocks; 6 top rows 1 block
/**
 *
 * @author leonsurwald
 */
class GameSurface extends Environment implements CellDataProviderIntf {

    Grid grid;
//    private ArrayList<Block> blocks;
    private StackData stackData;

    public GameSurface() {
        this.setBackground(Color.BLACK);
        grid = new Grid(7, 15, 30, 30, new Point(350, 50), Color.DARK_GRAY);

        stackData = new StackData(grid.getRows(), grid.getColumns(), this);
        stackData.addBlocksToRow(14, 3);

    }

//
//    public void addBlock() {
//        if (blocks == null) {
//            blocks = new ArrayList<>();
//        }
//
//        blocks.add(new Block(grid.getRandomGridLocation(), this));
//    }
    @Override
    public void initializeEnvironment() {

    }

    int counter;
    double moveDelay = 0;
    double moveDelayLimit = 4;

    @Override
    public void timerTaskHandler() {                            //Moving Objects, checks for intersection
        if (stackData != null) {
            stackData.move();
//            if (moveDelay >= moveDelayLimit) {
//                stackData.move();
//                moveDelay = 0;
//            } else {
//                moveDelay++;
//            }
        }

    }

    @Override
    public void keyPressedHandler(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            stackData.stopMovement();
            AudioPlayer.play("/stacker/gunshot.wav/");
        }
        
        if (e.getKeyCode() == KeyEvent.VK_1) {
            stackData.setSpeed(Speed.SLOW);
        }else if (e.getKeyCode() == KeyEvent.VK_2) {
            stackData.setSpeed(Speed.MEDIUM);
        } else if (e.getKeyCode() == KeyEvent.VK_3) {
            stackData.setSpeed(Speed.FAST);
        } else if (e.getKeyCode() == KeyEvent.VK_4) {
            stackData.setSpeed(Speed.CRAZY);
        }
        
        
        
    }

    @Override
    public void keyReleasedHandler(KeyEvent e) {
//        if (e.getKeyCode() == KeyEvent.VK_A) {
//            System.out.println("GO LEFT");
//        } else if (e.getKeyCode() == KeyEvent.VK_D) {
//            System.out.println("GO RIGHT");
//        } else if (e.getKeyCode() == KeyEvent.VK_W) {
//            System.out.println("GO UP");
//        } else if (e.getKeyCode() == KeyEvent.VK_S) {
//            System.out.println("GO DOWN");
//        }
//        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
//            stackData.eliminateBlocks();   //change to ask grid
//        }
    }

    @Override
    public void environmentMouseClicked(MouseEvent e) {
//        System.out.println("Mouse clicked at " + e.getPoint());
//        System.out.println("Mouse clicked in cell " + grid.getCellLocationFromSystemCoordinate(e.getPoint()));
    }

    @Override
    public void paintEnvironment(Graphics graphics) {

        if (grid != null) {
            grid.paintComponent(graphics);
        }

        if (stackData != null) {
            stackData.draw(graphics);
        }

    }

//<editor-fold defaultstate="collapsed" desc="CellDataProviderIntf">
    @Override
    public int getCellHeight() {
        return grid.getCellHeight();
    }

    @Override
    public int getCellWidth() {
        return grid.getCellWidth();
    }

    @Override
    public int getCellTopLeftX(int x, int y
    ) {
        return grid.getCellSystemCoordinate(x, y).x;
    }

    @Override
    public int getCellTopLeftY(int x, int y
    ) {
        return grid.getCellSystemCoordinate(x, y).y;
    }
//</editor-fold>

}
