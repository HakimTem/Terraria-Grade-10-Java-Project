/*
 * To change this license header, choose License Headers in Project Properties.
 * To chang this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package terraria;

import DLibX.DConsole;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author hemac
 */
public class Block {

    protected DConsole dc;
    protected Grid mGrid;
    protected double gridX;
    protected double gridY;

    protected int groundDetection = 0;
    protected int leftWallDetection = 0;
    protected int rightWallDetection = 0;
    protected Player player;
    protected String currentImage;

    protected int boundryLeftX;
    protected int boundryLeftY;
    
    
    public Block(double x, double y, DConsole d, Grid g, String url, Player p){
        this.dc = d;
        this.gridX = x;
        this.gridY = y;
        this.mGrid = g;
        this.player = p;
        this.currentImage = url;
    }


    public void draw() {
        dc.drawImage(currentImage, mGrid.getXPosition(gridX), mGrid.getYPosition(gridY));
    }

    public double getXPosition() {
        return mGrid.getXPosition(gridX) + player.scrollX;
    }

    public double getYPosition() {
        return mGrid.getYPosition(gridY) + player.scrollY;
    }

}
