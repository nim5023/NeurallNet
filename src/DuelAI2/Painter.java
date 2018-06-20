/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DuelAI2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import static DuelAI2.Constants.*;

/**
 *
 * @author Nick
 */
public class Painter {

    boolean[] goodNodes = new boolean[350];
    boolean[] goodNextNodes = new boolean[350];

    void resetArrToFalse(boolean[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = false;
        }
    }
    Font nameFont = new Font("TimesRoman", Font.PLAIN, 30);
    Font netFont = new Font("TimesRoman", Font.PLAIN, 10);

    void paintGame(Graphics g, DrawArena drawArena) {

        g.setFont(nameFont);
        
        drawArena.playerRed.draw(g);
        drawArena.ballRed.draw(g);

        drawArena.playerBlue.draw(g);
        drawArena.ballBlue.draw(g);

        int x = DRAW_X_OFFSET - WALL_WIDTH;
        int y = DRAW_Y_OFFSET - WALL_WIDTH;
        int length = 2 * WALL_WIDTH + (WALL_LENGTH * PIXEL_PER_UNIT);

        g.setColor(Color.decode("#009900"));

        g.fillRect(x, y, WALL_WIDTH, length);

        g.fillRect(x, y, length, WALL_WIDTH);

        g.fillRect(x + length - WALL_WIDTH, y, WALL_WIDTH, length);

        g.fillRect(x, y + length - WALL_WIDTH, length, WALL_WIDTH);
        
        
        g.drawString("Press 'n' to view neural net",
                DRAW_X_OFFSET + 2 * (WALL_LENGTH * PIXEL_PER_UNIT),
                DRAW_Y_OFFSET + (WALL_LENGTH * PIXEL_PER_UNIT) / 2);

    }

    Color getColorWeight(double layer, double weight) {

        double value = weight;
        value /= MAX_WEIGHT;

        return getColor(value);

    }

    Color getColor(double value) {

        Color clr = null;
        int clrInt = (int) (255.0 * value);

        if (clrInt > 0) {

            if (clrInt > 255) {
                clrInt = 255;
            }

            clr = new Color(0, clrInt, 0, 255);
            
        } else {

            clrInt *= -1;
            if (clrInt > 255) {
                clrInt = 255;
            }

            clr = new Color(clrInt, 0, 0, 255);
        }
        return clr;
    }

    void paintLayer(boolean setOutput, Graphics2D g, double[] layer, double[] nextLayer, int y, int nextY, double[][] weights) {

        int layerGap = 1400 / (layer.length + 1);

        int nextLayerGap = 0;
        if (nextLayer != null) {
            nextLayerGap = 1400 / (nextLayer.length + 1);
        }

        int diameter = 10;

        for (int i = 0; i < layer.length; i++) {

            Color clr = getColor(layer[i]);

            if (nextLayer != null) {
                for (int j = 0; j < nextLayer.length; j++) {
                    if ((setOutput || goodNodes[j]) || !SHOW_ONLY_DIRECTION) {

                        double thisWeight = Math.abs(weights[i][j] / MAX_WEIGHT);
                        if (DRAW_ACTIVATION) {
                            thisWeight = Math.abs(layer[i] * weights[i][j] / MAX_WEIGHT);
                        }

                        if (thisWeight > DRAW_THRESHOLD) {

                            boolean canDraw = true;
                            if (setOutput && SHOW_ONLY_DIRECTION && j != 0) {
                                canDraw = false;
                            }

                            if (setOutput) {
                                if (j == 0) {
                                    goodNextNodes[i] = true;
                                }
                            } else {
                                goodNextNodes[i] = true;
                            }


                            Color clrWeight = getColorWeight(layer[i], weights[i][j]);
                            g.setColor(clrWeight);

                            if (canDraw) {
                                g.drawLine(
                                        (i * layerGap) + layerGap + diameter / 2,
                                        y + diameter,
                                        (j * nextLayerGap) + nextLayerGap + diameter / 2,
                                        nextY);
                            }
                        }

                    }
                }
            }

            g.setColor(clr);
            g.fillOval((i * layerGap) + layerGap, y, diameter, diameter);

        }



        resetArrToFalse(goodNodes);

        for (int i = 0; i < goodNextNodes.length; i++) {
            goodNodes[i] = goodNextNodes[i];
        }

        resetArrToFalse(goodNextNodes);
    }

    void paintNet(Graphics2D g, DrawArena drawArena) {

        Player player = drawArena.playerRed;

        if (player.net != null) {

            int gap = 150;
            int displace = 50;

            int xlayerGap = 1400 / 17;
            int ylayerGap = 40;
            g.setFont(netFont);
            g.setColor(Color.GREEN);

            g.drawString("xPos",        xlayerGap, ylayerGap);
            g.drawString("yPos",        (xlayerGap * 2), ylayerGap);
            g.drawString("dir",         (xlayerGap * 3), ylayerGap);
            g.drawString("hasBall",     (xlayerGap * 4), ylayerGap);
            g.drawString("OpxPos",      (xlayerGap * 5), ylayerGap);
            g.drawString("OpyPos",      (xlayerGap * 6), ylayerGap);
            g.drawString("OpDir",       (xlayerGap * 7), ylayerGap);
            g.drawString("OpHasBall",   (xlayerGap * 8), ylayerGap);
            g.drawString("BxPos",       (xlayerGap * 9), ylayerGap);
            g.drawString("ByPos",       (xlayerGap * 10), ylayerGap);
            g.drawString("Bdir",        (xlayerGap * 11), ylayerGap);
            g.drawString("Bvel",        (xlayerGap * 12), ylayerGap);
            g.drawString("OpBxPos",     (xlayerGap * 13), ylayerGap);
            g.drawString("OpByPos",     (xlayerGap * 14), ylayerGap);
            g.drawString("OpBdir",      (xlayerGap * 15), ylayerGap);
            g.drawString("OpBvel",      (xlayerGap * 16), ylayerGap);
            
            
            g.drawString("Direction",  (xlayerGap * 4), ylayerGap * 17);
            g.drawString("Ball Direction", (xlayerGap * 8), ylayerGap * 17);
            g.drawString("Shoot Confidence", (xlayerGap * 12), ylayerGap * 17);

            
            g.drawString("MAX_WEIGHT " + MAX_WEIGHT, 200, 775);
            g.drawString("DRAW_THRESHOLD " + DRAW_THRESHOLD, 200, 800);

            
            g.drawString("'i' 'o': Change Max Weight ", 500, 775);
            g.drawString("'k' 'l': Change Draw Threshold ", 500, 800);

            g.drawString("'n': View Game", 500, 825);

            paintLayer(false, g, player.net.outputs, null, displace + (gap * 4), 0, null);
            paintLayer(true, g, player.net.hidden_3, player.net.outputs, displace + (gap * 3), displace + (gap * 4), player.net.h3_out);
            paintLayer(false, g, player.net.hidden_2, player.net.hidden_3, displace + (gap * 2), displace + (gap * 3), player.net.h2_h3);
            paintLayer(false, g, player.net.hidden_1, player.net.hidden_2, displace + gap, displace + (gap * 2), player.net.h1_h2);
            paintLayer(false, g, player.net.inputs, player.net.hidden_1, displace, displace + gap, player.net.inpt_h1);

        }

    }
}
