/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package terraria;

import DLibX.DConsole;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.util.Random;

/**
 *
 * @author temacinh
 */
public class Player {

    protected int inventorySlot1;
    protected int inventorySlot2;
    protected int inventorySlot3;
    protected int inventorySlot4;
    protected int inventorySlot5;
    protected int inventorySlot6;
    protected int inventorySlot7;
    protected int inventorySlot8;
    protected int inventorySlot9;
    protected int inventorySlot10;

    protected int xCoordinates;
    protected double yCoordinates;
    protected Sprite[] stand;
    protected Sprite[] moveLeft;
    protected Sprite[] moveRight;
    protected Sprite[] jumpUp;
    protected String currentImage;
    protected int costumeNumber = 0;
    protected int direction;
    protected double velocity = 0;
    protected int jumpingTimer = 0;
    protected double health;

    protected double TERMINAL_VELOCITY = 0.6;
    protected final double GRAVITY = 0.01;
    protected int LEFT_DIRECTION = 0;
    protected int RIGHT_DIRECTION = 1;

    //List of presets for what to do if getting something
    protected int STAND_STILL = 0;
    protected int MOVE = 1;
    protected int DISSAPPEAR = 2;
    protected int SHOW = 3;

    protected int SLOT_EMPTY = -1;

    protected double scrollX = 0;
    protected double scrollY = 0;
    
    protected int directionStanding = 0;

    protected DConsole dc;

    public Player(int x, double y, Sprite[] s, Sprite[] left, Sprite[] right, Sprite[] jump, String img, DConsole d) {
        this.xCoordinates = x;
        this.yCoordinates = y;
        this.stand = s;
        this.moveLeft = left;
        this.moveRight = right;
        this.currentImage = img;
        this.jumpUp = jump;
        this.dc = d;
    }

    public void draw() {
        dc.drawImage(currentImage, xCoordinates, yCoordinates);
    }



    public void stand() {
        if (direction == LEFT_DIRECTION) {
            currentImage = stand[LEFT_DIRECTION].getImage();
            costumeNumber = 0;
        } else if (direction == RIGHT_DIRECTION) {
            currentImage = stand[RIGHT_DIRECTION].getImage();
            costumeNumber = 0;
        }
    }

    public void changeDirection(int d) {
        direction = d;
    }


    public void move() {
        if (direction == LEFT_DIRECTION) {
            scrollX += 0.25;
            currentImage = moveLeft[costumeNumber].getImage();
            costumeNumber += 1;
            if (costumeNumber + 1 > moveLeft.length) {
                costumeNumber = 0;
            }
            directionStanding = LEFT_DIRECTION;
        } else if (direction == RIGHT_DIRECTION) {
            scrollX -= 0.25;
            currentImage = moveRight[costumeNumber].getImage();
            costumeNumber += 1;
            if (costumeNumber + 1 > moveRight.length) {
                costumeNumber = 0;
            }
            directionStanding = RIGHT_DIRECTION;
        }
    }

    public void moveWithoutChanging() {
        if (direction == LEFT_DIRECTION) {
            scrollX += 0.25;

        } else if (direction == RIGHT_DIRECTION) {
            scrollX -= 0.25;
        }
    }

    public void fall() {
        velocity += velocity + GRAVITY;
        if (velocity > TERMINAL_VELOCITY) {
            velocity = TERMINAL_VELOCITY;
        }
        scrollY -= 1;
    }

    public void jump() {
        if (direction == LEFT_DIRECTION) {
            currentImage = jumpUp[0].getImage();
        } else if (direction == RIGHT_DIRECTION) {
            currentImage = jumpUp[1].getImage();
        }
    }

    public void updateVelocity(double value){
      velocity = value;
    }
    
    public void updatePosition(){
      scrollY += velocity;
    }
    
    public void updateGravity(){
      velocity -= GRAVITY;
      if(velocity < TERMINAL_VELOCITY * -1){
         velocity = TERMINAL_VELOCITY * -1;
      }
    }
    
    public void riseUp(double height){
       velocity += height;
       scrollY += velocity;
    }
    
    public void dissappear(){
       xCoordinates = 100000;
    }
    
    public void show(){
       xCoordinates = 446;
    }

    public void updateVelocity(int v) {
        velocity = v;
    }

    public void setHealth(double h) {
        health = h;
    }

    public void changeHealth(double c) {
        health -= c;
    }

    public double getHealth() {
        return health;
    }



    public void setSlot(int slot, int id) {
        switch (slot) {
            case 1:
                inventorySlot1 = id;
            case 2:
                inventorySlot2 = id;
            case 3:
                inventorySlot3 = id;
            case 4:
                inventorySlot4 = id;
            case 5:
                inventorySlot5 = id;
            case 6:
                inventorySlot6 = id;
            case 7:
                inventorySlot7 = id;
            case 8:
                inventorySlot8 = id;
            case 9:
                inventorySlot9 = id;
            case 10:
                inventorySlot10 = id;
        }
    }

    public int getSlot(int slot) {
        switch (slot) {
            case 1:
                return inventorySlot1;
            case 2:
                return inventorySlot2;
            case 3:
                return inventorySlot3;
            case 4:
                return inventorySlot4;
            case 5:
                return inventorySlot5;
            case 6:
                return inventorySlot6;
            case 7:
                return inventorySlot7;
            case 8:
                return inventorySlot8;
            case 9:
                return inventorySlot9;
            case 10:
                return inventorySlot10;
            default:
                return -1;
        }
    }
}
