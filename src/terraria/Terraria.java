/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package terraria;

//All the imports that are used to make this project
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

/**
 *
 * @author temacinh
 * 
 * This is an attempt at a remake of the popular game Terraria
 * It includes many unique features like random terrain generation based on see input 
 * or even hitboxes and working inventory
 * I hope this project gets a 100 in my tech class
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
    static Player player = new Player(453, 445, standingLibrary, leftLibrary, rightLibrary,
            jumpingLibrary, "player_standing_left.png", dc);

    //Create an array of all the bosses
    public static ArrayList<EyeOfCthulhu> eye = new ArrayList<EyeOfCthulhu>();

    //Create an inventory object that tracks whats in the inventory
    public static Inventory playerInventory = new Inventory(player, dc);

    //Create an engine object which does everything like generation of the world to detecting hitboxes
    public static Engine terraria = new Engine(terrariaGrid, dc, player);

    //Create a list of all the terrain blocks location
    public static ArrayList<Integer> terrainLocation = new ArrayList<Integer>();

    //Create a fastnoise object for generating Perlin Noise random like terrain
    public static FastNoise terrain = new FastNoise(100);

    //Variables used for moving through time in the perlin noise thingy
    public static float xoff = 0;

    /**
     * @param args the command line arguments
     */
    
    //Everything happens in here!
    public static void main(String[] args) {
        // TODO code application logic hered

        //Set the origin to be the center of every object
        dc.setOrigin(DConsole.ORIGIN_CENTER);

        //Set the player health to be 100 at first
        player.setHealth(100);

        //Clear the bosses list
        eye.clear();

        //Set the Perlin Noise to be a certain wave
        terrain.SetNoiseType(FastNoise.NoiseType.PerlinFractal);
        terrain.SetFrequency(0.001f);

        //Record all the y values of the terrain based on Perlin Noise functions
        for (int i = 0; i < 1000; i++) {
            int y = (int) Math.round(800 + terrain.GetPerlinFractal(xoff, 0) * 300);
            terrainLocation.add(y);
            xoff += 10;
        }

        //Loop for everything
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

            //Draw the forest background
            dc.drawImage("forest_background.png", 450, 280);

            //Sets the paint to white
            dc.setPaint(Color.WHITE);

            //Show all the mouse locations and the scrolling position for easy coding
            dc.drawString(mouseX, 600, 100);
            dc.drawString(mouseY, 600, 150);
            dc.drawString(scrollX, 600, 200);
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

            //Add the terrain in to the mix
            for (int i = 0; i < 1000; i++) {
                terraria.addBlock("grass_2.png", i, (int) Math.round(terrariaGrid.getGridYPosition(terrainLocation.get(i))));
                for (int j = 0; j < 10; j++) {
                    terraria.addBlock("dirt_18.png", i, (int) Math.round(terrariaGrid.getGridYPosition(terrainLocation.get(i))) + j + 1);
                }

            }

            //Adds a tree
            terraria.addTree(10, 40, 49);

            //Draw the position of the mouse 
            dc.drawString(Math.round(mouseX / 16 + 0.5), 600, 300);
            dc.drawString(Math.round(terrariaGrid.getGridYPosition(mouseY)), 600, 350);

            //Render only a part of the world so it dosn't lag that much
            terraria.renderWorld();

            //Draws and generates all the blocks in the world that are visible
            terraria.generateWorld();

            //Checks if the plaer is touching the ground
            groundDetection = terraria.ifOnGround();
            wallLeftDetection = terraria.ifTouchingLeftWall();
            wallRightDetection = terraria.ifTouchingRightWall();

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
            if ((dc.isKeyPressed(65) || dc.isKeyPressed(37)) && groundDetection >= 1 && !(scrollX >= 30) && wallRightDetection == 0) {
                player.changeDirection(player.LEFT_DIRECTION);
                player.move();
            } else if ((dc.isKeyPressed(68) || dc.isKeyPressed(39)) && groundDetection >= 1 && !(scrollX <= -80) && wallLeftDetection == 0) {
                player.changeDirection(player.RIGHT_DIRECTION);
                player.move();
            } else if ((dc.isKeyPressed(65) || dc.isKeyPressed(37)) && groundDetection == 0 && !(scrollX >= 30) && wallRightDetection == 0) {
                player.changeDirection(player.LEFT_DIRECTION);
                player.moveWithoutChanging();
            } else if ((dc.isKeyPressed(68) || dc.isKeyPressed(39)) && groundDetection == 0 && !(scrollX <= -80) && wallLeftDetection == 0) {
                player.changeDirection(player.RIGHT_DIRECTION);
                player.moveWithoutChanging();
            }

            //If a key is pressed then jump
            if (dc.getKeyPress(' ') && groundDetection >= 1) {
                jumpingTimer = 30;
                player.jump();
            } else if (groundDetection >= 1 && !(dc.isKeyPressed(65)) && !(dc.isKeyPressed(37))
                    && !(dc.isKeyPressed(68)) && !(dc.isKeyPressed(39))) {
                player.stand();
            }

            //f not touching any ground switch sprites and go down
            if (groundDetection == 0) {
                player.goDown();
                player.jump();
            }

            //If the jump has been initiated then start going up for a certain amount of time
            if (jumpingTimer > 0) {
                player.jump();
                player.goUp();
                jumpingTimer--;
            }

            //If the r button is pressed then respawn at a certain position
            if (dc.isKeyPressed('r')) {
                player.scrollX = 0;
                player.scrollY = 0;
            }

            //If the e button is pressed then spawn in an eye of cthulhu
            if (dc.isKeyPressed('e') && eye.isEmpty()) {
                eye.add(new EyeOfCthulhu(200, 200, dc, terraria, terrariaGrid, player));
            }

            //if the i button is pressed add something to the inventory
            if (dc.isKeyPressed('i')) {
                player.setSlot(1, 1765);
            }

            //Add the second timer for the deteration of rapid key press
            secondTimer += 1;

            //For each boss fly to the player
            eye.forEach(item -> {
                item.flyToPlayer();
            });

        }
    }

}
