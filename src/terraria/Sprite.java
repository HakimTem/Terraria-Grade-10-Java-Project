/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package terraria;

/**
 *
 * @author hemac
 */
public class Sprite {
    
    protected String url;
    
    public Sprite(String u){
     this.url = u;
    }
    
    public String getImage(){
     return url;
    }
    
    
}
