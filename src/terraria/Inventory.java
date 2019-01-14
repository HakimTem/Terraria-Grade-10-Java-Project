/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package terraria;

import DLibX.DConsole;
import java.awt.Font;
import java.util.ArrayList;

/**
 *
 * @author hemac
 */
public class Inventory {
    
    protected Player player;
    protected DConsole dc;
    
    private int amount = 0;
    private int id = 0;

    public Inventory(int i, int a, DConsole d){
        this.id = i;
        this.amount = a;
        this.dc = d;
    }
    
    public int getId(){
     return id;
    }
    
    public int getAmount(){
     return amount;
    }
}
