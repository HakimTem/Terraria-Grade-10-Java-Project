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
public class Projectile {
    protected double xCoordinates; 
    protected double yCoordinates;
    
    protected int id;
    protected String image;
    
    protected DConsole dc;
    
    protected Player player;
    
    public Projectile(double x, double y, int id, DConsole d, Player p){
      this.xCoordinates = x;
      this.yCoordinates = y;
      this.id = id;
      image = "Sprites/Projectile_" + id + ".png";
      this.dc = d;
      this.player = p;
    }
    
    public void draw(){
      dc.drawImage(image, xCoordinates + player.scrollX * 16, yCoordinates + player.scrollY * 16);
    }
    
    
}
