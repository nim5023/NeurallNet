/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DuelAI2;

import static DuelAI2.Constants.*;
import java.awt.Graphics;

/**
 *
 * @author nmiller
 */
public class Piece {
    
    Color color;
    
    int xPos = 0;
    int yPos;
    
    double direction;
    double vel = 0.0;
    double radius = 0.0;   
        
    public void move(){        
        double xDelta = Math.cos(direction) * vel;
        double yDelta = Math.sin(direction) * vel;
        
        xPos += xDelta;
        yPos += yDelta;        
    }
    
    public void draw(Graphics g){
        
    }
    
    public boolean isCollide(Piece otherPiece){        
        if(getDistance(xPos, otherPiece.xPos, yPos, otherPiece.yPos) <= radius + otherPiece.radius){
            return true;
        }
        return false;        
    }
    
}
