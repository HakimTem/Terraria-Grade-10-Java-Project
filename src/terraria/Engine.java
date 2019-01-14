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
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.lang.Math;
import java.util.ArrayList;
import java.awt.AlphaComposite;
import static terraria.Terraria.dc;
import static terraria.Terraria.slimeSprites;

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

    protected ArrayList<Block> world = new ArrayList<>();
    protected ArrayList<Block> worldRender = new ArrayList<>();
    protected ArrayList<Enemy> enemies = new ArrayList<>();
    protected ArrayList<Integer> terrainLocation = new ArrayList<>();
    protected ArrayList<Double> treeLocation = new ArrayList<>();
    protected ArrayList<Enemy> mobs = new ArrayList<>();
    protected ArrayList<Enemy> entityRender = new ArrayList<>();
    protected ArrayList<Projectile> projectiles = new ArrayList<>();
    protected ArrayList<Projectile> projectileRender = new ArrayList();
    
    protected Random random = new Random();
    
    protected int opacity = 100;
    
    protected FastNoise terrain = new FastNoise();
    
    protected int treeSecond = 0;
    
    protected final int FACE_LEFT = 10;
    protected final int FACE_RIGHT = 11;
    protected final int FACE_BOTH = 12;
    
    protected final int FULL = 3;
    protected final int PARTIALLY_FULL = 4;
    
    protected int gameTrigger;


    public Engine(Grid g, DConsole d, Player p, int seed) {
        this.mGrid = g;
        this.dc = d;
        this.player = p;
        this.terrain.SetSeed(seed);
    }

    public void broadcast(String name) {
        signal = name;
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
    
    public void addTreeWithDetail(int startingx, int startingy, int treeStump, int treeHeight, int amountOfLeftBranches, int amountOfRightBranches, boolean ifFull, int typeOfTop){
        switch(treeStump){
            
            case FACE_RIGHT: addBackgroundBlock("tree_11.png", startingx, startingy); 
                             addBackgroundBlock("tree_12.png", startingx + 1, startingy); break;
            
            case FACE_LEFT:  addBackgroundBlock("tree_14.png", startingx, startingy);
                             addBackgroundBlock("tree_13.png", startingx - 1, startingy); break;
                
            case FACE_BOTH:  addBackgroundBlock("tree_15.png", startingx, startingy);
                             addBackgroundBlock("tree_13.png", startingx - 1, startingy);   
                             addBackgroundBlock("tree_12.png", startingx + 1, startingy); break;
                
            default: addBackgroundBlock("tree_11.png", startingx, startingy);
                     addBackgroundBlock("tree_12.png", startingx + 1, startingy);   break; 
        }
        
      
            for(int i = 0; i < treeHeight; i++){
              addBackgroundBlock("tree_10.png", startingx, startingy - i - 1);
            }
            
            for(int i = 0; i < amountOfLeftBranches; i++){
            if(ifFull){
              addBackgroundBlock("tree_branch_full_1.png", startingx - 1, startingy - i * 2);
            }
            
            else{
              addBackgroundBlock("tree_branch_empty_2.png", startingx - 1, startingy - i * 2);
            }
            
    }
            for(int i = 0; i < amountOfRightBranches; i++){
            if(ifFull){
              addBackgroundBlock("tree_branch_full_2.png", startingx - 1, startingy - i * 2);
            }
            
            else{
              addBackgroundBlock("tree_branch_empty_1.png", startingx - 1, startingy - i * 2);
            }
    }
            
            switch(typeOfTop){
                case FULL:  addBackgroundBlock("tree_top_1.png", startingx, startingy - treeHeight - 1); break;
                case PARTIALLY_FULL:  addBackgroundBlock("tree_top_2.png", startingx, startingy - treeHeight - 1); break;
                default: addBackgroundBlock("tree_top_1.png", startingx, startingy - treeHeight - 1); break;
            }
            
    }
    
    public void addCorruptTreeWithDetail(int startingx, int startingy, int treeStump, int treeHeight, int amountOfLeftBranches, int amountOfRightBranches, boolean ifFull, int typeOfTop){
        switch(treeStump){
            
            case FACE_RIGHT: addBackgroundBlock("tree_corr_2.png", startingx, startingy); 
                             addBackgroundBlock("tree_corr_5.png", startingx + 1, startingy); break;
            
            case FACE_LEFT:  addBackgroundBlock("tree_corr_3.png", startingx, startingy);
                             addBackgroundBlock("tree_corr_6.png", startingx - 1, startingy); break;
                
            case FACE_BOTH:  addBackgroundBlock("tree_corr_4.png", startingx, startingy);
                             addBackgroundBlock("tree_corr_5.png", startingx - 1, startingy);   
                             addBackgroundBlock("tree_corr_6.png", startingx + 1, startingy); break;
                
            default: addBackgroundBlock("tree_corr_1.png", startingx, startingy);
                     addBackgroundBlock("tree_corr_5.png", startingx + 1, startingy);   break; 
        }
        
      
            for(int i = 0; i < treeHeight; i++){
              addBackgroundBlock("tree_corr_1.png", startingx, startingy - i - 1);
            }
            
            for(int i = 0; i < amountOfLeftBranches; i++){
            if(ifFull){
              addBackgroundBlock("tree_corr_branch_full_2.png", startingx - 1, startingy - i * 2);
            }
            
            else{
              addBackgroundBlock("tree_corr_branch_empty_2.png", startingx - 1, startingy - i * 2);
            }
            
    }
            for(int i = 0; i < amountOfRightBranches; i++){
            if(ifFull){
              addBackgroundBlock("tree_corr_branch_full_1.png", startingx - 1, startingy - i * 2);
            }
            
            else{
              addBackgroundBlock("tree_corr_branch_empty_1.png", startingx - 1, startingy - i * 2);
            }
    }
            
            switch(typeOfTop){
                case FULL:  addBackgroundBlock("tree_corr_top_1.png", startingx, startingy - treeHeight - 1); break;
                case PARTIALLY_FULL:  addBackgroundBlock("tree_top_2.png", startingx, startingy - treeHeight - 1); break;
                default: addBackgroundBlock("tree_top_1.png", startingx, startingy - treeHeight - 1); break;
            }
            
    }
    
    public void addForestBiome(int startingPosition, int length, int closenessOfTrees, int howFarDirt, int frequencyOfTrees, int heightOfTrees){
      //Add the terrain in to the mix
            for (int i = startingPosition; i < length + startingPosition; i++) {
                treeSecond += 1;
                
                    addBlock("grass_2.png", i, (int) Math.round(mGrid.getGridYPosition(terrainLocation.get(i))));
                for (int j = 0; j < howFarDirt; j++) {
                    addBlock("dirt_18.png", i, (int) Math.round(mGrid.getGridYPosition(terrainLocation.get(i))) + j + 1);
                }
                
                if(treeLocation.get(i) > frequencyOfTrees && treeSecond >= closenessOfTrees){
                  treeSecond = 0;
                  addTreeWithDetail(i, (int) Math.round(mGrid.getGridYPosition(terrainLocation.get(i))) - 1, FACE_LEFT, heightOfTrees, 0, 0, false, FULL);
                }

            }
    }
    
    public void addCorruptionBiome(int startingPosition, int length, int closenessOfTrees, int howFarDirt, int frequencyOfTrees, int heightOfTrees){
      //Add the terrain in to the mix
            for (int i = startingPosition; i < length + startingPosition; i++) {
                treeSecond += 1;
                
                    addBlock("corruption_grasss_1.png", i, (int) Math.round(mGrid.getGridYPosition(terrainLocation.get(i))));
                for (int j = 0; j < howFarDirt; j++) {
                    addBlock("dirt_18.png", i, (int) Math.round(mGrid.getGridYPosition(terrainLocation.get(i))) + j + 1);
                }
                
                if(treeLocation.get(i) > frequencyOfTrees && treeSecond >= closenessOfTrees){
                  treeSecond = 0;
                  addCorruptTreeWithDetail(i, (int) Math.round(mGrid.getGridYPosition(terrainLocation.get(i))) - 1, FACE_LEFT, heightOfTrees, 1, 2, true, FULL);
                }

            }
    }
    
    public void addEnemy(int x, int y, String type){
      switch(type){
          case "slime": Slime slime = new Slime(x, y, 45, slimeSprites, dc, mGrid, "npc_1_1.png", player);
                        mobs.add(slime); break;
      }  
    }
    
    public void addProjectile(double x, double y, double lx, double ly, int id){
      projectiles.add(new Projectile(x, y, lx, ly, id, dc, player));
    }
    
    
    public void planTerrain(float choasFactor, int lengthOfBiome){
      //Set the Perlin Noise to be a certain wave
        terrain.SetNoiseType(FastNoise.NoiseType.PerlinFractal);
        terrain.SetFrequency(choasFactor);

           //Variables used for moving through time in the perlin noise thingy
           float xoff = 0;
           float xoff2 = 1000;

          //Record all the y values of the terrain based on Perlin Noise functions
           for (int i = 0; i < lengthOfBiome; i++) {

            terrain.SetFrequency(choasFactor);
            int y = (int) Math.round(800 + terrain.GetPerlinFractal(xoff, 0) * 300);
            

            terrain.SetFrequency(choasFactor);
            double treeDeterminer = 50 + terrain.GetPerlinFractal(xoff2, 0) * 50;
            
            terrainLocation.add(y);
            treeLocation.add(treeDeterminer);
            xoff += 10;
            xoff2 += 10;
        }
    }

    public void renderWorld() {
        world.forEach(item -> {
            if (!(mGrid.getXPosition(item.gridX) >= 910 || mGrid.getYPosition(item.gridY) >= 610 || mGrid.getXPosition(item.gridX) <= -10 || mGrid.getYPosition(item.gridY) <= -10)) {
                worldRender.add(item);
            }
        });
        
        mobs.forEach(item -> {
            if(!(item.xCoordinates + player.scrollX * 16 >= 1000 || item.xCoordinates + player.scrollX * 16 <= -100 || item.yCoordinates + player.scrollY * 16 >= 700 || item.yCoordinates + player.scrollY * 16 <= -100)){
                 entityRender.add(item);
            }
        });
        
        projectiles.forEach(item -> {
         if(!(item.xCoordinates >= 1000 || item.xCoordinates <= -100 || item.yCoordinates >= 700 || item.yCoordinates <= -100)){
                 projectileRender.add(item);
                 System.out.println("Added Projectile to Render");
         }
        });
        
        
    }
    
    public void animateNPC(){
       entityRender.forEach(item ->{
           item.animate();
       });
    }

    public void generateWorld() {
        worldRender.forEach(item -> {
            item.draw();
        });
        entityRender.forEach(item -> {
            item.draw();
        });
        
        projectileRender.forEach(item -> {
            item.draw();
        });
    }
    
    public void setSeed(int seed){
     terrain.SetSeed(seed);
    }

    public void clearWorld() {
        world.clear();
        worldRender.clear();
        entityRender.clear();
        projectileRender.clear();
    }
    
    public void clearEnemies(){
      mobs.forEach(item -> {
          item.groundDetection = 0;
        });
      
    }

    public Block getBlock(double x, double y) {
        for (Block b : worldRender) {
            if (x == (int) Math.round(b.gridX) && y == (int) Math.round(b.gridY)) {
                return b;
            }
        }
        return new Block(x, y, dc, mGrid, "air.png", player);
    }
    
    public Block getBlockByGrid(double x, double y) {
        for (Block b : worldRender) {
            if (Math.round(x) == Math.round(b.gridX) && Math.round(y) == Math.round(b.gridY)) {
                return b;
            }
        }
        return new Block(x, y, dc, mGrid, "air.png", player);
    }
    
    public Enemy getEnemy(Enemy e){
       for(Enemy s : mobs){
         if(e == s){
           return s;
         }
       }
       return new Enemy(5000, 50000, 50000, null, null, null, null, null);
    }
    
    public Projectile getProjectile(Projectile p){
    for(Projectile s : projectiles){
         if(p == s){
           return s;
         }
       }
       return new Projectile(50000, 50000, 5000, 50000, 50000, null, null);
    }
    

    public int ifPlayerOnGround() {
        ground = 0;
        worldRender.forEach(item -> {
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

    public int ifPlayerTouchingLeftWall() {
        leftwall = 0;
        worldRender.forEach(item -> {
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

    public int ifPlayerTouchingRightWall() {
        rightwall = 0;
        worldRender.forEach(item -> {
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
    
    public void ifEnemyOnGround(){
      entityRender.forEach(item -> {
       worldRender.forEach(block -> {
            if (!(item.yCoordinates + player.scrollY * 16 >= mGrid.getYPosition(block.gridY) - 15) || !(item.yCoordinates + player.scrollY * 16 <= mGrid.getYPosition(block.gridY) + 15) || !(item.xCoordinates + player.scrollX * 16 <= mGrid.getXPosition(block.gridX) + 8) || !(item.xCoordinates + player.scrollX * 16 >= mGrid.getXPosition(block.gridX) - 8) || block instanceof BackgroundBlock){     
                 item.groundDetection += 0;
            } else {
                 item.groundDetection += 1;
                System.out.println("Touching Ground");
            }
        });
      });
    }
    
    public void startGame(){
      player.dissappear();
      dc.drawImage("fade-out.png", 450, 300);
    }
    
    public void gravityEnemies(){
        
        entityRender.forEach(item -> {
          if (item.groundDetection >= 1) {
                    getEnemy(item).updateVelocity(0);
                }

                if (item.groundDetection == 0) {
                    getEnemy(item).jump();
                    getEnemy(item).updateGravity();
                }
                
                if(item.groundDetection >= 1 && item.secondTimer > 250){
                   getEnemy(item).riseUp(1);
                   getEnemy(item).secondTimer = 0;
                }
                
                if(item.groundDetection == 0){
                  moveEnemies();
                }
                
                
                getEnemy(item).updatePosition();
                getEnemy(item).secondTimer ++;
        });
        
    
    }
    
    
    public void moveEnemies(){
     entityRender.forEach(item -> {
         if(item.xCoordinates > player.xCoordinates){
              getEnemy(item).move(item.LEFT_DIRECTION);
     }
         else if(item.xCoordinates < player.xCoordinates){
             getEnemy(item).move(item.RIGHT_DIRECTION);
         }
     });
     
    }
    
    public void moveProjectiles(){
      projectileRender.forEach(item -> {
           getProjectile(item).move();
      });
    }
    
}


