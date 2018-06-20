/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DuelAI2;

import java.util.List;

/**
 *
 * @author Nick
 */
public class DrawArena extends Arena {

    void initializeArena() {

        selectRandomState();
        initializeGame();

        redTeam.initialize();

        playerRed.net = redTeam.king.net;
        playerBlue.net = blueTeam.king.net;

    }

    void selectRandomState() {

        List<GameState> gsList = GameState.gameStateList;

//        double stateGen = Constants.rand.nextDouble() * 4.0;
//        GameState gs = gsList.get((int) stateGen % 4);
        
        GameState gs = gsList.get(0);   // just use 1 of the states for now

        redBallState = gs.redBallState;
        blueBallState = gs.blueBallState;
        bluePlayerState = gs.bluePlayerState;
        redPlayerState = gs.redPlayerState;

    }
    double startTime = System.currentTimeMillis();

    public void GameLoop() {

        isPlaying = iterateGame();

        if (!isPlaying) {

            isPlaying = true;
            selectRandomState();
            initializeGame();

        }

    }
}
