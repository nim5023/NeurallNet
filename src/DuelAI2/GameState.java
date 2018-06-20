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


enum BallState{TOP_RIGHT, TOP_LEFT, BOTTOM_RIGHT, BOTTOM_LEFT};
enum PlayerState{TOP, BOTTOM};

public class GameState {
    
    BallState blueBallState;
    BallState redBallState;

    PlayerState bluePlayerState;
    PlayerState redPlayerState;    
    
    final static List<GameState> gameStateList = new ArrayList<>();
    
    static {           
        GameState gs = new GameState();
        gs.blueBallState = BallState.TOP_RIGHT;
        gs.bluePlayerState = PlayerState.TOP;
        gs.redBallState = BallState.BOTTOM_LEFT;
        gs.redPlayerState = PlayerState.BOTTOM;
        gameStateList.add(gs);


        GameState gs2 = new GameState();
        gs2.blueBallState = BallState.BOTTOM_RIGHT;
        gs2.bluePlayerState = PlayerState.TOP;
        gs2.redBallState = BallState.TOP_LEFT;
        gs2.redPlayerState = PlayerState.BOTTOM;
        gameStateList.add(gs2);


        GameState gs3 = new GameState();
        gs3.blueBallState = BallState.BOTTOM_LEFT;
        gs3.bluePlayerState = PlayerState.BOTTOM;
        gs3.redBallState = BallState.TOP_RIGHT;
        gs3.redPlayerState = PlayerState.TOP;
        gameStateList.add(gs3);


        GameState gs4 = new GameState();
        gs4.blueBallState = BallState.TOP_LEFT;
        gs4.bluePlayerState = PlayerState.BOTTOM;
        gs4.redBallState = BallState.BOTTOM_RIGHT;
        gs4.redPlayerState = PlayerState.TOP;
        gameStateList.add(gs4);

    }

    
}
