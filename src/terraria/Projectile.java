/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package terraria;

import DLibX.DConsole;
import java.awt.geom.AffineTransform;
import java.lang.Math;

/**
 *
 * @author temacinh
 */
public class Projectile {
    
    protected double xCoordinates;
    protected double yCoordinates;
    
    protected double locationX; 
    protected double locationY;
    
    protected double xChange;
    protected double yChange;
    
    protected int LEFT_DIRECTION = 0;
    protected int RIGHT_DIRECTION = 1;
    protected int UP_DIRECTION = 2;
    protected int DOWN_DIRECTION = 3;
    protected int angle;
    
    protected Player player;
    
    protected int id;
    
    protected DConsole dc;
    
    public Projectile(double x, double y, double lx, double ly, int i, DConsole d, Player p){
       this.xCoordinates = x;
       this.yCoordinates = y;
       this.locationX = lx;
       this.locationY = ly;
       this.id = i;
       this.dc = d;
       this.player  = p;
       
       double oppositeSide;
       double adjacentSide;
       
       if(y > ly){
         oppositeSide = y - ly;
       }
       else if(y < ly){
         oppositeSide = ly - y;
       }
       else {
         oppositeSide = ly - y;
       }
       
       if(x > lx){
         adjacentSide = x - lx;
       }
       else if(x < lx){
         adjacentSide = lx - x;
       }
       else {
       adjacentSide = lx - x;
       }
       
    
       this.angle = (int)Math.round(Math.toDegrees((Math.atan2(oppositeSide, adjacentSide))));
       
       this.xChange = (lx - x)/((Math.sqrt(Math.pow(lx - x, 2) + Math.pow(y - ly, 2))) / 20);
       this.yChange = (y - ly)/((Math.sqrt(Math.pow(lx - x, 2) + Math.pow(y - ly, 2))) / 20);
       
    }
    
    public void draw(){
      dc.setTransform(AffineTransform.getRotateInstance(Math.toRadians(angle - 90)));
      dc.drawImage("Sprites/Projectile_" + id + ".png", xCoordinates, yCoordinates);
      dc.setTransform(AffineTransform.getRotateInstance(Math.toRadians(0)));
    }
    
    public void move(){
      xCoordinates += xChange;
      yCoordinates -= yChange;
    }
    public void rotate(int a){
      angle = a;
    }

}
