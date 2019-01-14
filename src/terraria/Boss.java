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
public class Boss {

    protected double xCoordinates;
    protected double yCoordinates;
    protected int health;
    protected String currentImage;
    protected int costumeNumber;
    protected double costumebuffer;

    protected DConsole dc;
    protected Engine main;
    protected Grid mGrid;
    protected Player player;



    public Boss(int x, int y, DConsole d, Engine e, Grid g, Player p) {
        this.xCoordinates = x;
        this.yCoordinates = y;
        this.dc = d;
        this.main = e;
        this.mGrid = g;
        this.player = p;
    }

    public void draw() {
            dc.drawImage(currentImage, xCoordinates + player.scrollX * 16, yCoordinates + player.scrollY * 16);
    }
}
