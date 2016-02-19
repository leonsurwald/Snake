/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stacker;

import audio.AudioPlayer;
import audio.Playlist;
import audio.SoundManager;
import audio.Source;
import audio.Track;
import environment.Environment;
import grid.Grid;
import images.ResourceTools;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
//import java.util.ArrayList;

// bottom 3 rows 3 blocks; middle 6 rows 2 blocks; 6 top rows 1 block
/**
 *
 * @author leonsurwald
 */
class GameSurface extends Environment implements CellDataProviderIntf, StackDataEventListenerIntf {

    Image pauseScreen;
    Image playScreen;
    Image gameOverScreen;
    Image wonScreen;

    Grid grid;
//    private ArrayList<Block> blocks;
    private StackData stackData;
    private GameState gameState = GameState.MENU;

    public GameSurface() {

        grid = new Grid(7, 15, 50, 50, new Point(50, 50), Color.DARK_GRAY);

        pauseScreen = ResourceTools.loadImageFromResource("stacker/pause.png");
        playScreen = ResourceTools.loadImageFromResource("stacker/play.png");
        gameOverScreen = ResourceTools.loadImageFromResource("stacker/scull.png");
        wonScreen = ResourceTools.loadImageFromResource("stacker/won.png");

        stackData = new StackData(grid.getRows(), grid.getColumns(), this, this);
        stackData.resetGame();

        setUpSound();

    }

//
//    public void addBlock() {
//        if (blocks == null) {
//            blocks = new ArrayList<>();
//        }
//
//        blocks.add(new Block(grid.getRandomGridLocation(), this));
//    }
    SoundManager soundManager;
    public static final String SOUND_Woosh = "Woosh";

    public void setUpSound() {
        //set up a list of tracks
        ArrayList<Track> tracks = new ArrayList<>();
        tracks.add(new Track(SOUND_Woosh, Source.RESOURCE, "/stacker/woosh.wav"));
        Playlist playlist = new Playlist(tracks);
        soundManager = new SoundManager(playlist);
    }

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

        } else if (e.getKeyCode() == KeyEvent.VK_ENTER & gameState == GameState.MENU) {
            gameState = GameState.GAME;

        } else if (e.getKeyCode() == KeyEvent.VK_ENTER & gameState == GameState.PAUSE) {
            gameState = GameState.GAME;

        } else if (e.getKeyCode() == KeyEvent.VK_0) {
            gameState = GameState.WON;

        } else if (e.getKeyCode() == KeyEvent.VK_ENTER && gameState == GameState.GAMEOVER) {
            gameState = GameState.RESTART;
        }
    }

    @Override
    public void keyReleasedHandler(KeyEvent e
    ) {

    }

    @Override
    public void environmentMouseClicked(MouseEvent e
    ) {

    }

    @Override
    public void paintEnvironment(Graphics graphics
    ) {

        switch (gameState) {
            case MENU:

                this.setBackground(Color.RED);

                graphics.setColor(Color.BLACK);
                graphics.setFont(new Font("Impact", Font.BOLD, 15));
                graphics.drawString("HIT ENTER TO START", 10, 20);
                graphics.drawImage(playScreen, 70, 200, 300, 300, this);
                break;

            case PAUSE:

                this.setBackground(Color.RED);

                graphics.setColor(Color.BLACK);
                graphics.setFont(new Font("Impact", Font.BOLD, 15));
                graphics.drawString("HIT ENTER TO RESUME", 10, 20);
                graphics.drawImage(pauseScreen, 70, 200, 300, 300, this);
                break;

            case GAME:

                this.setBackground(Color.BLACK);

                graphics.setColor(Color.RED);
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

                this.setBackground(Color.BLACK);

                graphics.setColor(Color.RED);
                graphics.setFont(new Font("Impact", Font.BOLD, 15));
                graphics.drawString("PRESS ENTER TO RESTART", 10, 20);
                graphics.drawImage(gameOverScreen, 70, 200, 300, 300, this);

                break;

            case RESTART:

                stackData.resetGame();

                gameState = GameState.GAME;

                break;

            case WON:

                this.setBackground(Color.BLACK);

                graphics.setColor(Color.RED);
                graphics.setFont(new Font("Impact", Font.BOLD, 15));
                graphics.drawString("PRESS ENTER TO RESTART!", 10, 20);
                graphics.drawImage(wonScreen, 70, 200, 300, 300, this);

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

//<editor-fold defaultstate="collapsed" desc="StackDataEventListenerIntf">
    @Override
    public void onEvent(String eventType
    ) {
//        System.out.println("Event = " + eventType);

        if (eventType.equals(StackDataEventListenerIntf.EVENT_GAME_OVER)) {
            gameState = GameState.GAMEOVER;
        }
        if (eventType.equals(StackDataEventListenerIntf.EVENT_GAME_WON)) {
            gameState = GameState.WON;
        }
    }
//</editor-fold>

}
