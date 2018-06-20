/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DuelAI2;

import static DuelAI2.Constants.*;
import java.awt.Graphics;
import java.util.Random;

/**
 *
 * @author nmiller
 */
public class Player  extends Piece{
    
    Ball myBall;
    Ball oppBall;
    
    Player oppPlayer;
    
    Net net;
    
    int numPoints = 0;
    int numMoves = 0;
    
    
    boolean hasBall = false;
    boolean hasHitWall = false;    
    boolean gotTheBall = false;
    
    boolean isDead = false;
    boolean isWinner = false;
    
    
    double ballClosestDistanceToOpp = 200.0;
    double closestDistanceToMyBall = 200.0;
    
    
    Player(Ball myBall, Ball oppBall, Color color){
       
        this.color = color;
        
        this.myBall = myBall;
        this.oppBall = oppBall;
        
        this.radius = PLAYER_RADIUS;
        
        this.vel = PLAYER_VEL;
        
        numPoints = 0;
    }
    
    public void setOppPlayer(Player oppPlayer){        
        this.oppPlayer = oppPlayer;
    }
    
    double convertPosToInput(double pos){
        return 2.0 * pos / (double)WALL_LENGTH;
    }
    
    double convertDirToInput(double direction){
        return direction / (2.0 * Math.PI);
    }
    
    
    double convertBooleanToInput(boolean bool){
        double inpt = -1.0;
        if(bool)
            inpt = 1.0;
        return inpt;
    }
        
    public double[] getNetInputs(){
        
        double[] inputs = new double[NUM_INPUT_NODES];
        
        inputs[0] = convertPosToInput(xPos);
        inputs[1] = convertPosToInput(yPos);
        
        inputs[2] = convertDirToInput(direction);
        
        inputs[3] = convertBooleanToInput(hasBall);
        
        
        inputs[4] =  convertPosToInput(oppPlayer.xPos);
        inputs[5] =  convertPosToInput(oppPlayer.yPos);
        
        inputs[6] =  convertDirToInput(oppPlayer.direction);
        
        inputs[7] =  convertBooleanToInput(oppPlayer.hasBall);
        
        
        inputs[8] = convertPosToInput(myBall.xPos);
        inputs[9] = convertPosToInput(myBall.yPos);
        inputs[10] = convertDirToInput(myBall.direction);
        inputs[11] = myBall.vel / BALL_VEL;        
         
        
        inputs[12] =  convertPosToInput(oppBall.xPos);
        inputs[13] =  convertPosToInput(oppBall.yPos);
        inputs[14] =  convertDirToInput(oppBall.direction);
        inputs[15] =  oppBall.vel / BALL_VEL;        
        
        return inputs;
    }
    
    public void iterate(){
        
        net.setInputs(getNetInputs());
        net.runNet();
        
        direction = net.outputs[0] * 2 * Math.PI;
        double shootConfidence = net.outputs[2];
        
        if(shootConfidence > 0.3 && hasBall){
            myBall.direction = net.outputs[1] * 2 * Math.PI;
            throwBall();
        }
        
        move();
        pickUpBall();      
    }
    
    public void pickUpBall(){
        
        double distanceToBall = getDistance(xPos, myBall.xPos, yPos, myBall.yPos);
        
        while (closestDistanceToMyBall - distanceToBall > 1.0){
//            numPoints += 100;
            closestDistanceToMyBall -= 1.0;
        }
        
        if(isCollide(myBall))
            if(!myBall.isMoving && !hasBall){
                
//                if(!gotTheBall)
//                    numPoints += 500;  
//                else
//                    numPoints += 3;
                
                hasBall = true;  
                gotTheBall = true;
            }
    }
    
    
    @Override
    public void move(){  
       numPoints+=1000;
       numMoves++;
       super.move();
       if(hasBall){
           myBall.xPos = xPos;
           myBall.yPos = yPos;
       }
       
       if(myBall.isMoving){
        double ballDistanceToOpp = getDistance(oppPlayer.xPos, myBall.xPos, oppPlayer.yPos, myBall.yPos);
           while (ballClosestDistanceToOpp - ballDistanceToOpp > 1.0){
//                numPoints += 1;
                ballClosestDistanceToOpp -= 1;
            }
       }
    }
            
    
    public void throwBall(){
        if(hasBall){
            numPoints++;
            hasBall = false;
            myBall.isMoving = true;
            myBall.vel = BALL_VEL;            
        }
    }
    
    public boolean isHitWall(){
        if(xPos < -WALL_LENGTH/2 + PLAYER_RADIUS ||
           xPos >  WALL_LENGTH/2 - PLAYER_RADIUS ||
           yPos < -WALL_LENGTH/2 + PLAYER_RADIUS ||
           yPos >  WALL_LENGTH/2 - PLAYER_RADIUS ){
                return true;
            }
        
        return false;        
    }
    
    double distToOppBallAtDeath = 0.0;
    
    public boolean checkIsDead(){
        
        if(isHitWall()){
            hasHitWall = true;
            isDead = true;
        }
        
        if(color == Color.RED)  // Let blue chase red.
            if(isCollide(oppBall)){
                if(!oppPlayer.hasBall){
                    isDead = true;

                    distToOppBallAtDeath = getDistance(xPos, oppBall.xPos, yPos, oppBall.yPos);

                    numPoints += (distToOppBallAtDeath / (this.radius + oppBall.radius)) * 500.0;

    //                if(color == Color.BLUE)
    //                    System.out.println("Red Shot Blue!");                
                }
            }
        
        if(isDead){
//           numPoints -= 30000;
           oppPlayer.isWinner = true;
        }
        
        return isDead;
    }
    
    
        
    
        public void setInitialPosition(PlayerState playerState){
            
           xPos = 0;
           if(playerState == PlayerState.BOTTOM){
               yPos = -WALL_LENGTH / 4;
           }
           if(playerState == PlayerState.TOP){
               yPos = WALL_LENGTH / 4;
           }
        }
    
    
    @Override
    public void draw(Graphics g){
        
        java.awt.Color c = null;
        if(color == Color.RED)
            c = java.awt.Color.RED;
        if(color == Color.BLUE)
           c = java.awt.Color.decode("#00CED1");
        
        g.setColor(c); 
        
        int xDraw = (int)(WALL_LENGTH / 2.0) + DRAW_X_OFFSET + xPos - (int)(PLAYER_RADIUS);
        int yDraw = (int)(WALL_LENGTH / 2.0) + DRAW_Y_OFFSET - yPos - (int)(PLAYER_RADIUS);
        
        g.fillOval(xDraw, yDraw, (int)(PLAYER_RADIUS * PIXEL_PER_UNIT * 2), (int)(PLAYER_RADIUS  * PIXEL_PER_UNIT * 2)); 
        g.setColor(java.awt.Color.decode("#000000")); 
        g.drawOval(xDraw, yDraw, (int)(PLAYER_RADIUS * PIXEL_PER_UNIT * 2), (int)(PLAYER_RADIUS  * PIXEL_PER_UNIT * 2)); 
    }
      
    
    
}
