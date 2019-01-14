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
    
    protected Sprite[] library;
    
    protected String currentImage;
    protected int spriteNumber = 0;
    protected double costumeBuffer = 0;
    protected int groundDetection = 0;
    
    protected DConsole dc;
    protected Grid mGrid;
    protected Player player;
    
    protected final double GRAVITY = 0.01;
    protected double TERMINAL_VELOCITY = 1;
    protected double velocity  = 0;
    
    protected int LEFT_DIRECTION = 1;
    protected int RIGHT_DIRECTION = 0;
    
    protected int secondTimer = 0;
    protected int jumpingTimer = 0;
    protected int movingTimer = 0;
    
    public Enemy(double x, double y, int h, Sprite[] sprite, DConsole d, Grid g, String i, Player p){
         this.xCoordinates = x;
         this.yCoordinates = y;
         this.health = h;
         this.library = sprite;
         this.dc = d;
         this.mGrid = g;
         this.currentImage = i;
         this.player = p;
    }

    public void spawnAtLocation(double x, double y){
       xCoordinates = x;
       yCoordinates = y;
       
       dc.drawImage(currentImage, xCoordinates, yCoordinates);
    }
    
    public void draw(){
     dc.drawImage(currentImage, xCoordinates + player.scrollX * 16, yCoordinates + player.scrollY * 16);
    }
    
    public void fall() {
        velocity += velocity + GRAVITY;
        if (velocity > TERMINAL_VELOCITY) {
            velocity = TERMINAL_VELOCITY;
        }
        yCoordinates += 1;
    }
    
    public void jump() {
            currentImage = library[1].getImage();
    }
    
     public void updateVelocity(double value){
      velocity = value;
    }
     
     public void updatePosition(){
      yCoordinates += velocity;
    }
     
     public void updateGravity(){
      velocity += GRAVITY;
      if(velocity > TERMINAL_VELOCITY){
         velocity = TERMINAL_VELOCITY;
      }
    }
     
     public void move(int d){
       if(d == LEFT_DIRECTION){
        xCoordinates -= 1;
       }
       else if(d == RIGHT_DIRECTION){
         xCoordinates += 1;
       }
     }
     
      public void riseUp(double height){
       velocity -= height;
       yCoordinates += velocity;
    }
      
      public void animate(){
       if(groundDetection == 0){
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
      
}


