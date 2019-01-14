/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package terraria;

//All the imports that are used to make this project
import DLibX.DConsole;
import java.applet.Applet;
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
import java.applet.AudioClip;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author temacinh
 *
 * This is an attempt at a remake of the popular game Terraria It includes many
 * unique features like random terrain generation based on see input or even
 * hitboxes and working inventory I hope this project gets a 100 in my tech
 * class
 */
public class Terraria {

    //Create the DConsole variable
    static DConsole dc = new DConsole();

    //Create the variables for tracking the mouse
    public static int mouseX;
    public static int mouseY;

    //Create the scroll x and y variables
    public static double scrollX = 0;
    public static double scrollY = 0;

    //Create a timer variable for no rapid key presses
    public static int secondTimer = 0;

    //Create umping variables used for how long and how high to jump
    public static int jumpingTimer = 0;
    public static int jump = 50;

    //Create detection variables that detect if the player is touching a wall or the ground
    public static int groundDetection = 0;
    public static int wallLeftDetection = 0;
    public static int wallRightDetection = 0;

    //ALL THE PLAYERS SPRITES
    public static Sprite standLeft = new Sprite("player_standing_left.png");
    public static Sprite standRight = new Sprite("player_standing_right.png");
    public static Sprite moveLeft1 = new Sprite("player_rightfoot1.png");
    public static Sprite moveRight1 = new Sprite("player_rightfoot1_right.png");
    public static Sprite jumpLeft = new Sprite("terraria_player_spritesheet.png");
    public static Sprite jumpRight = new Sprite("terraria_player_jumpright.png");

    //The sprite libraries for the player
    public static Sprite[] standingLibrary = {standLeft, standRight};
    public static Sprite[] leftLibrary = {standLeft, moveLeft1};
    public static Sprite[] rightLibrary = {standRight, moveRight1};
    public static Sprite[] jumpingLibrary = {jumpLeft, jumpRight};
    static Sprite[] player_sprites = {standLeft, standRight};

    //Make the grid for everything
    static public Grid terrariaGrid = new Grid(100, 100, dc);

    //Create the player object
    static Player player = new Player(446, 445, standingLibrary, leftLibrary, rightLibrary,
            jumpingLibrary, "player_standing_left.png", dc);

    //Create an array of all the bosses
    public static ArrayList<EyeOfCthulhu> eye = new ArrayList<EyeOfCthulhu>();

    //Create an inventory object that tracks whats in the inventory
    public static Inventory playerInventory = new Inventory(player, dc);

    //Create an engine object which does everything like generation of the world to detecting hitboxes
    public static Engine terraria = new Engine(terrariaGrid, dc, player, 752);

    public static int mainMenu = 0;
    public static Random random = new Random();
    public static int treeSecond = 0;

    public static float opacity = 1;

    public static int logoNumber = random.nextInt(7) + 1;

    public static int timer = 0;
    
    public static int angle;
    public static int scale;
    public static double angleChange = 1;
    
    public static String seed = "";
    
    public static Sprite slimeOne = new Sprite("npc_1_1.png");
    public static Sprite slimeTwo = new Sprite("npc_1_2.png");
    public static Sprite[] slimeSprites = {slimeOne, slimeTwo};
    
    
    
    
    /**
     * @param args the command line arguments
     */
    //Everything happens in here!
    public static void main(String[] args) {
        // TODO code application logic hered

        //Set the origin to be the center of every object
        dc.setOrigin(DConsole.ORIGIN_CENTER);

        dc.registerFont("Andy Bold.ttf");
        
        //Set the player health to be 100 at first
        player.setHealth(100);

        //Clear the bosses list
        eye.clear();

        

        while(true){
        if (mainMenu == 0) {
            while (true) {
                
                
                dc.clear();

                player.dissappear();
                dc.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
                dc.drawImage("logo_" + logoNumber + ".png", 450, 300);
                dc.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                dc.drawImage("fade-out.png", 450, 300);

                
                dc.redraw();

                if (timer > 100) {
                    timer = 0;
                    mainMenu = 1;
                    break;
                }

                opacity -= 0.01f;

                if (opacity <= 0) {
                    opacity = 0;
                }

                timer++;
                dc.pause(20);
            }
        }
        
      

        if (mainMenu == 1) {
            while (true) {
                dc.clear();

                dc.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
                dc.drawImage("logo_" + logoNumber + ".png", 450, 300);
                dc.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                dc.drawImage("fade-out.png", 450, 300);

                dc.redraw();

                dc.pause(20);

                if (timer > 100) {
                    timer = 0;
                    mainMenu = 2;
                    break;
                }

                opacity += 0.01f;

                if (opacity > 1) {
                    opacity = 1;
                }

                timer++;
            }
        }
        
        try {
                    File url = new File("test.wav"); 
                    AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioIn);
                    clip.start();
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(Terraria.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Terraria.class.getName()).log(Level.SEVERE, null, ex);
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(Terraria.class.getName()).log(Level.SEVERE, null, ex);
                }

        if (mainMenu == 2) {
            while (true) {
                Button playButton = new Button(450, 300, 100, 100, dc, terraria);
                
                dc.clear();

                dc.drawImage("title_screen_background.png", 450, 300);
                
                dc.setTransform(AffineTransform.getRotateInstance(Math.toRadians(angle)));
                dc.drawImage("Logo.png", 450, 75);
                dc.setTransform(AffineTransform.getRotateInstance(Math.toRadians(0)));
                
                dc.setFont(new Font("Andy Bold", Font.BOLD, 30));
                dc.setPaint(Color.WHITE);
                dc.drawString("Play", 450, 250);
                
                dc.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                dc.drawImage("fade-out.png", 450, 300);
                dc.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
                
                

                dc.redraw();

                dc.pause(20);

                opacity -= 0.01f;

                if (opacity < 0) {
                    opacity = 0;
                }

                angle += angleChange;
               
                
                if(angle > 30){
                 angleChange *= -1;
                }
                else if(angle < -30){
                 angleChange *= -1;
                }
                
               if(playButton.isClick()){
                 mainMenu = 3;
                 timer = 0;
                 break;
               }
                  
            }
        }
        
        if(mainMenu == 3){
          while(true){
            dc.clear();
            
            Button playButton = new Button(300, 130, 30, 30, dc, terraria);
            Button seedButton = new Button(500, 130, 30, 30, dc, terraria);
            
            dc.drawImage("title_screen_background.png", 450, 300);
            
            dc.drawImage("Chat_Back.png", 450, 300);
            
            dc.drawImage("ButtonPlay.png", 300, 130);
            dc.drawImage("ButtonSeed.png", 500, 130);
            
            dc.redraw();
            
            dc.pause(20);
            
            if(playButton.isClick()){
              timer = 0;
              mainMenu = 5;
              break;
            }
            
            if(seedButton.isClick()){
              timer = 0;
              mainMenu = 4;
              break;
            }
          }
        }
        
        if(mainMenu == 4){
          while(true){
            dc.clear();  
              
            dc.drawImage("title_screen_background.png", 450, 300);
            dc.drawImage("typing_thing.png", 450, 300);
            
            dc.setFont(new Font("Andy Bold", Font.BOLD, 80));
            dc.drawString(seed, 450, 300);
            
            dc.redraw();
            dc.pause(20);
            
            
            
            
            if(dc.getKeyPress('1')){
              seed += "1";
            }
            else if(dc.getKeyPress('2')){
              seed += "2";
            }
            else if(dc.getKeyPress('3')){
              seed += "3";
            }
            else if(dc.getKeyPress('4')){
              seed += "4";
            }
            else if(dc.getKeyPress('5')){
              seed += "5";
            }
            else if(dc.getKeyPress('6')){
              seed += "6";
            }
            else if(dc.getKeyPress('7')){
              seed += "7";
            }
            else if(dc.getKeyPress('8')){
              seed += "8";
            }
            else if(dc.getKeyPress('9')){
              seed += "9";
            }
            else if(dc.getKeyPress('0')){
              seed += "0";
            }
            else if(dc.getKeyPress(8)){
              seed = seed.substring(0, seed.length() - 1);
            }
            else if(dc.getKeyPress(16)){
              mainMenu = 3;  
              terraria.setSeed(Integer.parseInt(seed));
              break;
            }
            
            
          }
        }

        if (mainMenu == 5) {
            //Loop for everything
            player.show();
            terraria.planTerrain(0.001f, 2000);
            terraria.addEnemy(100, 750, "slime");
 
            
            while (true) {
                
                
                //Everytime set the scroll variables to be the player
                scrollX = player.scrollX;
                scrollY = player.scrollY;

                //Everytime set the mouse variables to be the position of the real life mouse
                mouseX = dc.getMouseXPosition();
                mouseY = dc.getMouseYPosition();

                //Clears the screen
                dc.clear();
                

                //clears the world and the renderer everytime
                terraria.clearWorld();
                terraria.clearEnemies();

                //Draw the forest background
                dc.drawImage("forest_background.png", 450, 280);

                //Sets the paint to white
                dc.setPaint(Color.WHITE);

                //Show all the mouse locations and the scrolling position for easy coding
                dc.drawString(player.velocity, 600, 150);
                dc.drawString(secondTimer, 600, 200);
                dc.drawString(scrollY, 600, 250);

                //If the players health is a certain thing show the number of hearts to a certain thing
                if (player.getHealth() >= 20) {
                    dc.drawImage("Heart.png", 700, 50);
                }
                if (player.getHealth() >= 40) {
                    dc.drawImage("Heart.png", 725, 50);
                }
                if (player.getHealth() >= 60) {
                    dc.drawImage("Heart.png", 750, 50);
                }
                if (player.getHealth() >= 80) {
                    dc.drawImage("Heart.png", 775, 50);
                }
                if (player.getHealth() >= 100) {
                    dc.drawImage("Heart.png", 800, 50);
                }

                terraria.addForestBiome(0, 100, 4, 20, 55, 8);
                terraria.addCorruptionBiome(100, 1000, 4, 20, 68, 8);
           

                //Draw the position of the mouse 
               
                dc.drawString(Math.round(terraria.mobs.get(0).yCoordinates + player.scrollY * 16), 600, 350);

                //Render only a part of the world so it dosn't lag that much
                terraria.renderWorld();
                
                
                 
                terraria.animateNPC();

                //Draws and generates all the blocks in the world that are visible
                terraria.generateWorld();

                //Checks if the plaer is touching the ground
                groundDetection = terraria.ifPlayerOnGround();
                wallLeftDetection = terraria.ifPlayerTouchingLeftWall();
                wallRightDetection = terraria.ifPlayerTouchingRightWall();
                terraria.ifEnemyOnGround();

                //For each boss draw it
                eye.forEach(item -> {
                    item.draw();
                });

                //Draw the players inventory
                playerInventory.draw();

                //Draw the players sprite
                player.draw();
                

                //Redraws everything. DONT DELETE
                dc.redraw();

                //Pauses the game for 20 miliseconds everytime
                dc.pause(20);

                //If a key is pressed the player will move in that direction and ifnot touching the ground will change sprite
                if ((dc.isKeyPressed(65) || dc.isKeyPressed(37)) && groundDetection >= 1 && wallRightDetection == 0) {
                    player.changeDirection(player.LEFT_DIRECTION);
                    player.move();
                } else if ((dc.isKeyPressed(68) || dc.isKeyPressed(39)) && groundDetection >= 1 && wallLeftDetection == 0) {
                    player.changeDirection(player.RIGHT_DIRECTION);
                    player.move();
                } else if ((dc.isKeyPressed(65) || dc.isKeyPressed(37)) && groundDetection == 0 && wallRightDetection == 0) {
                    player.changeDirection(player.LEFT_DIRECTION);
                    player.moveWithoutChanging();
                } else if ((dc.isKeyPressed(68) || dc.isKeyPressed(39)) && groundDetection == 0 && wallLeftDetection == 0) {
                    player.changeDirection(player.RIGHT_DIRECTION);
                    player.moveWithoutChanging();
                }

                if (groundDetection >= 1) {
                    player.updateVelocity(0);
                }

                if (groundDetection == 0) {
                    player.jump();
                    player.updateGravity();
                }

                if (dc.getKeyPress(' ') && groundDetection >= 1) {
                    secondTimer = 0;
                    jumpingTimer = 1;
                }

                if (jumpingTimer > 0) {
                    player.riseUp(0.3);
                    jumpingTimer--;
                }
                
                if(groundDetection >= 1 && !(dc.isKeyPressed(37)) && !(dc.isKeyPressed(39)) && !(dc.isKeyPressed(65)) && !(dc.isKeyPressed(68))){
                  player.stand();
                }
 

                //If the r button is pressed then respawn at a certain position
                if (dc.isKeyPressed('r')) {
                    player.scrollX = 0;
                    player.scrollY = 0;
                }

                //If the e button is pressed then spawn in an eye of cthulhu
                if (dc.getKeyPress('e')) {
                    eye.add(new EyeOfCthulhu(200, 500, dc, terraria, terrariaGrid, player));
                }

                //if the i button is pressed add something to the inventory
                if (dc.isKeyPressed('i')) {
                    player.setSlot(1, 3827);
                }
                
                if(dc.getMouseButton(1) && player.inventorySlot1 == 3827 && secondTimer > 40){
                   terraria.addProjectile(player.xCoordinates + 10, player.yCoordinates, mouseX, mouseY, 684);
                   secondTimer = 0;
                }
               

                //Add the second timer for the deteration of rapid key press
                secondTimer += 1;

                //For each boss fly to the player
                eye.forEach(item -> {
                    item.flyToPlayer();
                });

                player.updatePosition();
                terraria.gravityEnemies();
                terraria.moveProjectiles();
                
                if(dc.getKeyPress(1)){
                 switch(player.inventorySlot1){
                    case 3827:
                        
                 }
                 
                 
                }
            }
        }
        }
    }

    public static double map(double value, double low1, double low2, double high1, double high2) {
        double Y = (value - low1) / (low2 - low1) * (high2 - high1) + high1;
        return Y;
    }

    

}
