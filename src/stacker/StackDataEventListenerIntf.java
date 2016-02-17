/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stacker;

/**
 *
 * @author leonsurwald
 */
public interface StackDataEventListenerIntf {
    public static final String EVENT_GAME_OVER = "GAMEOVER";
    public static final String EVENT_GAME_WON = "WON";

    
    public void onEvent(String eventType);
}
