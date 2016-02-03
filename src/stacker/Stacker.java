/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stacker;

import environment.ApplicationStarter;
import java.awt.Dimension;

/**
 *
 * @author leonsurwald
 */
public class Stacker {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ApplicationStarter.run(new String[0], "Stacker", new Dimension(450, 850), new GameSurface());
    }
    
}
