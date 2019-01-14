/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package terraria;

import DLibX.DConsole;
import java.awt.Font;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import static terraria.Terraria.terraria;

/**
 *
 * @author hemac
 */
public class Engine {

    protected Grid mGrid;
    protected DConsole dc;
    protected String signal;
    protected Player player;

    protected int ground = 0;
    protected int leftwall = 0;
    protected int rightwall = 0;

    protected ArrayList<Block> world = new ArrayList<Block>();
    protected ArrayList<Block> render = new ArrayList<Block>();
    
    protected ArrayList<Projectile> projectiles = new ArrayList();
    protected ArrayList<Projectile> projectileRender = new ArrayList();

    
    public Engine(Grid g, DConsole d, Player p) {
        this.mGrid = g;
        this.dc = d;
        this.player = p;
        dc.registerFont("Andy Bold.ttf");
    }

    public void startScreen() {
        dc.drawImage("title_screen_background.png", 450, 350);
    }
    
    

    public void broadcast(String name) {
        signal = name;
    }
    
    public void addProjectile(int x, int y, int id){
      projectiles.add(new Projectile(x, y, id, dc, player));
    }
    
    public void addBlock(String texture, double x, double y){
                double xc = x + player.scrollX;
                double yc = y + player.scrollY;
                world.add(new Block(xc, yc, dc, mGrid, texture, player));
    }

    public void addBlocksInSquareArea(String image, int length, double startingX, double startingY) {
        for (int i = 0; i < length; i++) {
            double x = i + startingX + player.scrollX;
            double y = i + startingY + player.scrollY;
            world.add(new Block(x, y, dc, mGrid, image, player));
        }
    }

    public void addBlocksInRectangularArea(String image, int length, int width, double startingX, double startingY) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                double x = j + startingX + player.scrollX;
                double y = i + startingY + player.scrollY;
                world.add(new Block(x, y, dc, mGrid, image, player));
            }
        }
    }

    public void addBackgroundBlock(String image, double x, double y) {
        double xc = x + player.scrollX;
        double yc = y + player.scrollY;
        world.add(new BackgroundBlock(xc, yc, dc, mGrid, image, player));
    }
    
    public void addTree(int h, int startingx, int startingy){
            addBackgroundBlock("tree_11.png", startingx, startingy);
            for(int i = 0; i < h; i++){
              addBackgroundBlock("tree_10.png", startingx, startingy - i - 1);
            }
            addBackgroundBlock("tree_12.png", startingx + 1, startingy);
            addBackgroundBlock("tree_branch_empty_1.png", startingx + 1, startingy - h/2);
            addBackgroundBlock("tree_top_1.png", startingx, startingy - h - 1);
    }

    public void renderWorld() {
        world.forEach(item -> {
            if (!(mGrid.getXPosition(item.gridX) >= 910 || mGrid.getYPosition(item.gridY) >= 610 || mGrid.getXPosition(item.gridX) <= -10 || mGrid.getYPosition(item.gridY) <= -10)) {
                render.add(item);
            }
        });
        
        projectiles.forEach(item -> {
          if(!(item.xCoordinates + player.scrollX * 16 >= 910 || item.yCoordinates + player.scrollY * 16 >= 610 ||item.xCoordinates + player.scrollX * 16 <= -10 || item.yCoordinates + player.scrollY * 16 <= -10)) {
                projectileRender.add(item);
            }
        });
    }
    

    public void generateWorld() {
        render.forEach(item -> {
            item.draw();
        });
        
        projectileRender.forEach(item -> {
          dc.setTransform(AffineTransform.getRotateInstance(Math.toRadians(90)));
          item.draw();
          dc.setTransform(AffineTransform.getRotateInstance(Math.toRadians(0)));
        });
    }

    public void clearWorld() {
        world.clear();
        render.clear();
        projectileRender.clear();
    }
    
    public void drawInventory(){
        for(int i = 0; i < 10; i++){
        int x = 50 + i * 61;
        int y = 50;
        dc.drawImage("main_inventory.png", x, y);
      }
        
        for(int i = 0; i < 10; i++){
        int x = 50 + i * 61 - 20;
        int y = 50 - 20;
        dc.setFont(new Font("Andy Bold", 14, 14));
        dc.drawString(i, x, y);
      }
        
        for(int i = 0; i < 10; i++){
           dc.drawImage("Sprites/Item_" + player.inventory.get(i).getId() + ".png", 50, 50);
        }
       
    }

    public Block getBlock(double x, double y) {
        for (Block b : render) {
            if (x == (int) Math.round(b.gridX) && y == (int) Math.round(b.gridY)) {
                return b;
            }
        }
        return new Block(x, y, dc, mGrid, "air.png", player);
    }

    public int ifOnGround() {
        ground = 0;
        render.forEach(item -> {
            if (!(player.yCoordinates >= mGrid.getYPosition(item.gridY) - 30) || !(player.yCoordinates <= mGrid.getYPosition(item.gridY) + 30) || !(player.xCoordinates <= mGrid.getXPosition(item.gridX) + 8) || !(player.xCoordinates >= mGrid.getXPosition(item.gridX) - 8) || item instanceof BackgroundBlock) {
                item.groundDetection = 0;
                ground += item.groundDetection;
            } else {
                item.groundDetection = 1;
                ground += item.groundDetection;
            }
        });
        return ground;
    }

    public int ifTouchingLeftWall() {
        leftwall = 0;
        render.forEach(item -> {
            if ("air.png".equals(getBlock(29, 29).currentImage) || getBlock(29, 29) instanceof BackgroundBlock) {
                item.leftWallDetection = 0;
                leftwall += item.leftWallDetection;
            } else {
                item.leftWallDetection = 1;
                leftwall += item.leftWallDetection;
            }
        });
        return leftwall;
    }

    public int ifTouchingRightWall() {
        rightwall = 0;
        render.forEach(item -> {
            if ("air.png".equals(getBlock(28, 29).currentImage) || getBlock(28, 29) instanceof BackgroundBlock) {
                item.rightWallDetection = 0;
                rightwall += item.rightWallDetection;
            } else {
                item.rightWallDetection = 1;
                rightwall += item.rightWallDetection;
            }
        });
        return rightwall;
    }

}
