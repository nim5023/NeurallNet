/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DuelAI2;

import static DuelAI2.Constants.*;

/**
 *
 * @author Nick
 */
class Arena {

    BallState blueBallState;
    BallState redBallState;
    PlayerState bluePlayerState;
    PlayerState redPlayerState;
    
    public Persist persist = new Persist();
    
    Ball ballRed;
    Ball ballBlue;
    Player playerRed;
    Player playerBlue;
    
    boolean isPlaying;
    
    int numMoves = 0;
    
    Team redTeam = new Team(Constants.Color.RED);
    Team blueTeam = new Team(Constants.Color.BLUE);

    public void initializeGame() {

        ballRed = new Ball(Constants.Color.RED);
        ballBlue = new Ball(Constants.Color.BLUE);

        ballRed.setInitialPosition(redBallState);
        ballBlue.setInitialPosition(blueBallState);

        playerRed = new Player(ballRed, ballBlue, Constants.Color.RED);
        playerBlue = new ScriptPlayer(ballBlue, ballRed, Constants.Color.BLUE);

        playerRed.setInitialPosition(redPlayerState);
        playerBlue.setInitialPosition(bluePlayerState);

        playerRed.setOppPlayer(playerBlue);
        playerBlue.setOppPlayer(playerRed);

        playerRed.net = redTeam.king.net;

        isPlaying = true;
        numMoves = 0;
    }
    boolean isRedDead = false;
    boolean isBlueDead = false;

    boolean iterateGame() {

        playerRed.iterate();
        playerBlue.iterate();

        ballRed.move();
        ballBlue.move();

        if (playerRed.checkIsDead()) {
            isRedDead = true;
        }

        if (playerBlue.checkIsDead()) {
            isBlueDead = true;
        }

        if (isRedDead || isBlueDead) {
            isBlueDead = false;
            isRedDead = false;

            return false;
        }

        if (numMoves++ > NUM_MOVES_IN_GAME) {
            return false;
        }

        return true;
    }
}
