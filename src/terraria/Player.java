/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package terraria;

import DLibX.DConsole;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author temacinh
 */
public class Player {

    protected ArrayList<Inventory> inventory = new ArrayList();
    
    
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

    protected double TERMINAL_VELOCITY = 0.4;
    protected double GRAVITY = 0.1;
    protected int LEFT_DIRECTION = 0;
    protected int RIGHT_DIRECTION = 1;

    //List of presets for what to do if getting something
    protected int STAND_STILL = 0;
    protected int MOVE = 1;
    protected int DISSAPPEAR = 2;
    protected int SHOW = 3;
    
    protected int SLOT_EMPTY  = -1;

    protected double scrollX = 0;
    protected double scrollY = 0;

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
        
        for(int i = 0; i < 10; i++){
          inventory.add(new Inventory(0, 0, dc));
        }
    }

    public void draw() {
        dc.drawImage(currentImage, xCoordinates, yCoordinates);
    }

    public void show() {
        setX(450);
        setY(450);
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

    public void dissappear() {
        setX(10000);
        setY(10000);
    }

    public void move() {
        if (direction == LEFT_DIRECTION) {
            scrollX += 0.25;
            currentImage = moveLeft[costumeNumber].getImage();
            costumeNumber += 1;
            if (costumeNumber + 1 > moveLeft.length) {
                costumeNumber = 0;
            }
        } else if (direction == RIGHT_DIRECTION) {
            scrollX -= 0.25;
            currentImage = moveRight[costumeNumber].getImage();
            costumeNumber += 1;
            if (costumeNumber + 1 > moveRight.length) {
                costumeNumber = 0;
            }
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
    
    public void changeVelocity(double v){
     velocity = v;
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
    
    public void riseUp(double h){
      velocity += h;
    }
            
    public void goUp(){
       scrollY += 1;
    }
    
    public void goDown(){
       scrollY -= 0.5;
    }
    
    public void setHealth(double h){
     health = h;
    }
    
    public void changeHealth(double c){
     health -= c;
    }
    
    public double getHealth(){
     return health;
    }

    public void stay() {
        velocity = 0;
    }

    public int getX() {
        return xCoordinates;
    }

    public double getY() {
        return yCoordinates;
    }

    public void setX(int x) {
        xCoordinates = x;
    }

    public void setY(int y) {
        yCoordinates = y;
    }
    
    public void setSlot(int slot, int id, int amount){
      inventory.remove(slot - 1);
      inventory.add(slot - 1, new Inventory(id, amount, dc));
    }
    
}
