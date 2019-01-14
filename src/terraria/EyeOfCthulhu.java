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

public class EyeOfCthulhu extends Boss {

    protected Sprite[] phaseOne = {new Sprite("eyeofcthulhu_1.png"), new Sprite("eyeofcthulhu_2.png"), new Sprite("eyeofcthulhu_3.png")};
    protected int secondTimer = 0;

    public EyeOfCthulhu(int x, int y, DConsole d, Engine e, Grid g, Player p) {
        super(x, y, d, e, g, p);
    }

    public void phaseOne() {
        flyToPlayer();
    }

    public void flyToPlayer() {
        if (xCoordinates + player.scrollX * 16 > player.xCoordinates) {
            xCoordinates -= 2.5;
        } else if (xCoordinates + player.scrollX * 16 < player.xCoordinates) {
            xCoordinates += 2.5;
        }
        currentImage = phaseOne[costumeNumber].getImage();
        costumebuffer += 0.1;
        if (costumebuffer >= 2) {
            costumeNumber += 1;
            costumebuffer = 0;
        }

        if (costumeNumber + 1 > phaseOne.length) {
            costumeNumber = 0;
        }
    }

    public void launchAtPlayer() {
        if (!(yCoordinates >= player.yCoordinates - 20)) {
            yCoordinates += 1;
        }
    }

}
