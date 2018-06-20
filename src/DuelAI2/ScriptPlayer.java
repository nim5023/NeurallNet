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
public class ScriptPlayer extends Player {

    ScriptPlayer(Ball myBall, Ball oppBall, Constants.Color color) {
        super(myBall, oppBall, color);
    }

    @Override
    public void iterate() {

        double distToOpp = getDistance(xPos, oppPlayer.xPos, yPos, oppPlayer.yPos);

        if (hasBall) {
            direction = Math.atan2(oppPlayer.yPos - yPos, oppPlayer.xPos - xPos);
        } else {
            direction = Math.atan2(myBall.yPos - yPos, myBall.xPos - xPos);
        }

        if (distToOpp < 250.0 && hasBall) {
            myBall.direction = Math.atan2(oppPlayer.yPos - yPos, oppPlayer.xPos - xPos);
            throwBall();
        }

        move();
        pickUpBall();
    }
}
