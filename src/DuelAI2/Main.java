/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DuelAI2;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nick
 */
public class Main {
    
     public static void main(String[] args) throws InterruptedException {

        Team redTeam = new Team(Constants.Color.RED);
        Team blueTeam = new Team(Constants.Color.BLUE);

        redTeam.initialize();

        List<Thread> threadList = new ArrayList<>();
        List<ThreadArena> arenaList = new ArrayList<>();

        ThreadArena princeThreadArena = new ThreadArena(redTeam.prince, blueTeam.prince, redTeam, blueTeam);
        ThreadArena kingThreadArena = new ThreadArena(redTeam.king, blueTeam.king, redTeam, blueTeam);

        arenaList.add(princeThreadArena);
        arenaList.add(kingThreadArena);

        List<GameState> gameStateList = GameState.gameStateList;

        while (true) {

//            for (int gsl = 0; gsl < gameStateList.size(); gsl++) {
                
                for (int i = 0; i < arenaList.size(); i++) {
                    arenaList.get(i).blueBallState = gameStateList.get(0).blueBallState;
                    arenaList.get(i).redBallState = gameStateList.get(0).redBallState;
                    arenaList.get(i).bluePlayerState = gameStateList.get(0).bluePlayerState;
                    arenaList.get(i).redPlayerState = gameStateList.get(0).redPlayerState;
                }

                redTeam.isDeadAtEnd = true;
                
                while(redTeam.isDeadAtEnd){

                    for (int i = 0; i < arenaList.size(); i++) {                        
                        arenaList.get(i).initializeThreadArena();
                        threadList.add(new Thread(arenaList.get(i)));
                    }
                    
                    for (int i = 0; i < threadList.size(); i++) {
                        threadList.get(i).start();
                    }

                    for (int i = 0; i < threadList.size(); i++) {
                        threadList.get(i).join();
                    }

                    threadList.clear();

                    for (int i = 0; i < arenaList.size(); i++) {
                        arenaList.get(i).tallyThreadScores();
                    }

                    redTeam.isDeadAtEnd = kingThreadArena.playerRed.isDead;
                    
                    redTeam.endStep();
                    blueTeam.resetScores();                 
                    
                }
                
                redTeam.persistGeneration();
                
                redTeam.initialize();
                redTeam.generation = 0;
                redTeam.savedGeneration = 0;
                redTeam.king.net.mutateModifier = 0.01;

//            }

        }
        
    }
     
}
