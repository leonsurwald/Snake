/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stacker;

import environment.Environment;
import grid.Grid;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 *
 * @author leonsurwald
 */
class Jungle extends Environment {

    Grid grid;

    public Jungle() {
        this.setBackground(Color.BLACK);
        grid = new Grid(7, 16, 30, 30, new Point(350, 50), Color.CYAN);
    }

    @Override
    public void initializeEnvironment() {

    }

    int counter;

    @Override
    public void timerTaskHandler() {                            //Moving Objects, checks for intersection
//        System.out.println("Hey dude! " + ++counter);
    }

    @Override
    public void keyPressedHandler(KeyEvent e) {
//        System.out.println("Key Event " + e.getKeyChar());
//        System.out.println("Key Event " + e.getKeyCode());
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            System.out.println("GO LEFT");
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            System.out.println("GO RIGHT");
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            System.out.println("GO UP");
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            System.out.println("GO DOWN");
        }
    }

    @Override
    public void keyReleasedHandler(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A) {
            System.out.println("GO LEFT");
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            System.out.println("GO RIGHT");
        } else if (e.getKeyCode() == KeyEvent.VK_W) {
            System.out.println("GO UP");
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            System.out.println("GO DOWN");
        }
    }

    @Override
    public void environmentMouseClicked(MouseEvent e) {
        System.out.println("Mouse clicked at " + e.getPoint());
        System.out.println("Mouse clicked in cell " + grid.getCellLocationFromSystemCoordinate(e.getPoint()));
    }

    @Override
    public void paintEnvironment(Graphics graphics) {
         
        if (grid != null) {
            grid.paintComponent(graphics);
        }
    }

}
