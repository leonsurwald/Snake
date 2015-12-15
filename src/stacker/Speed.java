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
public enum Speed {
    SLOW(5), MEDIUM(4), FAST(3), CRAZY(2);
    
    
    private Speed(int value){
        this.value = value;
    }
    
//<editor-fold defaultstate="collapsed" desc="Properties">
    private int value;
    
    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }
//</editor-fold>
}
