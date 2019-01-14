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

public class Enemy {
    
    protected double xCoordinates;
    protected double yCoordinates;
    
    protected int health;
    
    private Sprite [] moveLeft;
    private Sprite [] moveRight;
    private Sprite [] jump;
    
    private String currentImage;
    
    protected DConsole dc;
    protected Engine main;
    protected Grid mGrid;
    
    public Enemy(int h, Sprite[] ml, Sprite[] mr, Sprite[] j, DConsole d, Engine e, Grid g, String i){
         this.health = h;
         this.moveLeft = ml;
         this.moveRight = mr;
         this.jump = j;
         this.dc = d;
         this.main = e;
         this.mGrid = g;
         this.currentImage = i;
    }

    public void spawnAtLocation(double x, double y){
       xCoordinates = x;
       yCoordinates = y;
       
       dc.drawImage(currentImage, xCoordinates, yCoordinates);
    }
    
    public void spawn(){
     dc.drawImage(currentImage, xCoordinates, yCoordinates);
    }
    
}


