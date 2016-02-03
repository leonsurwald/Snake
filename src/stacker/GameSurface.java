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
import java.awt.Font;
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
    private GameState gameState = GameState.MENU;

    public GameSurface() {

        grid = new Grid(7, 15, 50, 50, new Point(50, 50), Color.DARK_GRAY);

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

        if (gameState == GameState.GAME) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                stackData.stopMovement();
                AudioPlayer.play("/stacker/woosh.wav/");
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            gameState = GameState.PAUSE;
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            gameState = GameState.GAME;
        }
    }

    @Override
    public void keyReleasedHandler(KeyEvent e) {

    }

    @Override
    public void environmentMouseClicked(MouseEvent e) {
//        System.out.println("Mouse clicked at " + e.getPoint());
//        System.out.println("Mouse clicked in cell " + grid.getCellLocationFromSystemCoordinate(e.getPoint()));
    }

    @Override
    public void paintEnvironment(Graphics graphics) {

        switch (gameState) {
            case MENU:

                this.setBackground(Color.RED);
                graphics.setFont(new Font("Impact", Font.BOLD, 15));
                graphics.drawString("HIT ENTER TO START", 10, 20);

                break;

            case PAUSE:

                this.setBackground(Color.RED);
                graphics.setFont(new Font("Impact", Font.BOLD, 15));
                graphics.drawString("HIT ENTER TO RESUME", 10, 20);

                break;

            case GAME:

                this.setBackground(Color.BLACK);

                graphics.setColor(Color.red);
                graphics.setFont(new Font("Impact", Font.BOLD, 15));
                graphics.drawString("HIT ESCAPE TO PAUSE", 10, 20);
                if (grid != null) {
                    grid.paintComponent(graphics);
                }

                if (stackData != null) {
                    stackData.draw(graphics);
                }

                break;

            case GAMEOVER:

                graphics.drawString("GAME OVER!", 10, 20);

                break;

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
