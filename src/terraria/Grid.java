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
/**
 *
 * @author hemac
 */
public class Grid {
    
    protected double numberOfRows;
    protected double numberOfColumns;
    protected String image;
    protected DConsole dc;
    
    public Grid(double rows, double columns, DConsole d) {
       numberOfRows = rows;
       numberOfColumns = columns;
       d = dc;
    }
    
    public void changeGridSize(double rows, double columns){
     rows = numberOfRows;
     columns = numberOfColumns;
    }
    
    
    public double getYPosition(double gridX){
     gridX = ((1600/numberOfColumns) * gridX) - ((1600/numberOfColumns)/2);
     return gridX;
    }
    public double getXPosition(double gridY){
     gridY = ((1600/numberOfRows) * gridY) - ((1600/numberOfRows)/2);
     return gridY;
    }
    
    
   public double getGridXPosition(double x){
     x = (x/16) + 0.5;
     return x;
   } 
   
   public double getGridYPosition(double y){
     y = (y/16) + 0.5;
     return y;
   }
    

  
}
