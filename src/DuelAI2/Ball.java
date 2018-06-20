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
public class Ball extends Piece {

    boolean isMoving = false;
    final double acc = BALL_ACC;
    Random rand = new Random();

    Ball(Color color) {
        this.color = color;

        this.vel = 0.0;
        this.radius = BALL_RADIUS;
    }

    @Override
    public void move() {
        if (isMoving) {
            super.move();

            vel -= acc;
            if (vel < 0.0) {
                vel = 0.0;
                isMoving = false;
            }

            bouceOffWall();
        }
    }

    public void setInitialPosition(BallState ballState) {

        if (ballState == BallState.BOTTOM_LEFT) {
            xPos = -WALL_LENGTH / 3;
            yPos = -WALL_LENGTH / 3;
        }

        if (ballState == BallState.BOTTOM_RIGHT) {
            xPos = WALL_LENGTH / 3;
            yPos = -WALL_LENGTH / 3;
        }

        if (ballState == BallState.TOP_LEFT) {
            xPos = -WALL_LENGTH / 3;
            yPos = WALL_LENGTH / 3;
        }

        if (ballState == BallState.TOP_RIGHT) {
            xPos = WALL_LENGTH / 3;
            yPos = WALL_LENGTH / 3;
        }

    }

    @Override
    public void draw(Graphics g) {

        java.awt.Color c = null;
        if (color == Color.RED) {
            c = java.awt.Color.RED;
        }
        if (color == Color.BLUE) {
            c = java.awt.Color.decode("#00CED1");
        }

        g.setColor(c);

        int xDraw = (int) (WALL_LENGTH / 2.0) + DRAW_X_OFFSET + xPos - (int) (BALL_RADIUS);
        int yDraw = (int) (WALL_LENGTH / 2.0) + DRAW_Y_OFFSET - yPos - (int) (BALL_RADIUS);

        g.fillOval(xDraw, yDraw, (int) (BALL_RADIUS * 2.0 * PIXEL_PER_UNIT), (int) (BALL_RADIUS * 2.0 * PIXEL_PER_UNIT));
        g.setColor(java.awt.Color.decode("#000000"));
        g.drawOval(xDraw, yDraw, (int) (BALL_RADIUS * 2.0 * PIXEL_PER_UNIT), (int) (BALL_RADIUS * 2.0 * PIXEL_PER_UNIT));
    }

    private void bounceX() {
        if (vel > 0.0) {
            double xVel = Math.cos(direction) * vel;
            xVel *= -1.0;
            direction = Math.acos(xVel / vel);
        }
    }

    private void bounceY() {
        if (vel > 0.0) {
            double yVel = Math.sin(direction) * vel;
            yVel *= -1.0;
            direction = Math.asin(yVel / vel);
        }
    }

    public void bouceOffWall() {
        if (xPos < -WALL_LENGTH / 2 + BALL_RADIUS) {
            bounceX();
            xPos = (int) (BALL_RADIUS - WALL_LENGTH / 2);
        }
        if (xPos > WALL_LENGTH / 2 - BALL_RADIUS) {
            bounceX();
            xPos = (int) (WALL_LENGTH / 2 - BALL_RADIUS);
        }
        if (yPos < -WALL_LENGTH / 2 + BALL_RADIUS) {
            bounceY();
            yPos = (int) (-WALL_LENGTH / 2 + BALL_RADIUS);
        }
        if (yPos > WALL_LENGTH / 2 - BALL_RADIUS) {
            bounceY();
            yPos = (int) (WALL_LENGTH / 2 - BALL_RADIUS);
        }
    }
}
