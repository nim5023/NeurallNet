/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DuelAI2;

public class ThreadArena extends Arena implements Runnable{
    
    
     Brain blueThreadBrain;
     Brain redThreadBrain;
        
        ThreadArena(Brain redBrain, Brain blueBrain, Team redTeam, Team blueTeam){
            
            this.redTeam = redTeam;
            this.blueTeam = blueTeam;
            
            redThreadBrain = redBrain;
            blueThreadBrain = blueBrain;
        } 
    
      @Override
        public void run(){
            threadLoop();
        }
    
        public void threadLoop(){
            while(isPlaying){
                 isPlaying = iterateGame();            
            }
           
        }
    
        void tallyThreadScores(){
            redThreadBrain.score += playerRed.numPoints;
            blueThreadBrain.score += playerBlue.numPoints;
        }
        
        
        void initializeThreadArena(){
        
            initializeGame();
            
            playerRed.net =  redThreadBrain.net;
            playerBlue.net =  blueThreadBrain.net;
            
        }
        
        
}
