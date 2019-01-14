/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package terraria;

import DLibX.DConsole;

/**
 *
 * @author hemac
 */
public class Slime extends Enemy{
    
    public Slime(double x, double y, int h, Sprite [] library, DConsole d, Grid g, String image, Player p){
     super(x, y, h, library, d, g, image, p);
    }
    
    public void animate(){
     currentImage = library[spriteNumber].getImage();
        costumeBuffer += 0.3;
        if (costumeBuffer >= 2) {
            spriteNumber += 1;
            costumeBuffer = 0;
        }

        if (spriteNumber + 1 > library.length) {
            spriteNumber = 0;
        }
    }
    
}
