/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package terraria;

import DLibX.DConsole;
import java.util.ArrayList;

/**
 *
 * @author hemac
 */
public class Inventory {
    
    protected Player player;
    protected DConsole dc;
    
    protected ArrayList<String> holdableItems = new ArrayList<String>();
    
    public Inventory(Player p, DConsole d){
      player = p;
      dc = d;
      
      for(int i = 0; i < 3000; i++){
      holdableItems.add("Sprites/Item_" + i + ".png");
      }
    }
    
    public void draw(){
      for(int i = 0; i < 10; i++){
        int x = 50 + i * 55;
        int y = 50;
        dc.drawImage("main_inventory.png", x, y);
      }
      
        dc.drawImage(holdableItems.get(player.getSlot(1)), 50, 50);
    }
}
