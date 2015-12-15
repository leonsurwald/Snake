/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stacker;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author leonsurwald
 */
public class Block {

    public void draw(Graphics graphics) {
        graphics.setColor(color);
        graphics.fill3DRect(cellData.getCellTopLeftX(x, y), cellData.getCellTopLeftY(x, y), cellData.getCellWidth(), cellData.getCellHeight(), true);

    }

//<editor-fold defaultstate="collapsed" desc="Constructors">
    Block(int x, int y, CellDataProviderIntf cellData) {
        this.x = x;
        this.y = y;

        this.cellData = cellData;
    }

    Block(Point location, CellDataProviderIntf cellData) {
        this.x = location.x;
        this.y = location.y;

        this.cellData = cellData;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Properties">
    private int y;
    private int x;
    private CellDataProviderIntf cellData;
    private Color color = Color.CYAN;

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    public Point getLocation() {
        return new Point(x, y);
    }

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
//</editor-fold>
}
