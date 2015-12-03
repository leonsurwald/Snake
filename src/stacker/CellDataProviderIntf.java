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
public interface CellDataProviderIntf {
    public int getCellHeight();
    public int getCellWidth();
    
    public int getCellTopLeftX(int x, int y);
    public int getCellTopLeftY(int x, int y);
    
}
