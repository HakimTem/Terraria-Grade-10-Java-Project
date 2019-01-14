/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package terraria;

import DLibX.DConsole;
import java.awt.Font;

/**
 *
 * @author hemac
 */
public class Button {

    protected int xCoordinates;
    protected int yCoordinates;
    protected int length;
    protected int width;
    protected DConsole dc;
    protected Engine main;

    public Button(int x, int y, int l, int w, DConsole d, Engine e) {
        this.xCoordinates = x;
        this.yCoordinates = y;
        this.length = l;
        this.width = w;
        this.dc = d;
        this.main = e;
    }

    public void drawAsRect() {
        dc.drawRect(xCoordinates, yCoordinates, length, width);
    }

    public void drawAsWord(String word, Font f) {
        dc.setFont(f);
        dc.drawString(word, xCoordinates, yCoordinates);
    }

    public boolean isClick() {

        if (dc.getMouseXPosition() >= xCoordinates - length / 2 && dc.getMouseXPosition() <= xCoordinates + length / 2
                && dc.getMouseYPosition() >= yCoordinates - width && dc.getMouseYPosition() <= yCoordinates + width && dc.isMouseButton(1)) {
            return true;
        } else {
            return false;
        }
    }

}
