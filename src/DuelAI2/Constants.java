/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DuelAI2;

import java.util.Random;

/**
 *
 * @author nmiller
 */
public class Constants {

    public enum Color {

        RED, BLUE
    };

    public enum NetState {

        INIT, READ, MUTATE
    };
    
    public final static Random rand = new Random();
    
    public final static double PLAYER_RADIUS = 20.0;
    public final static double PLAYER_VEL = 7.0;
    // 7 SEVEN
    public final static double BALL_RADIUS = 10.0;
    public final static double BALL_VEL = 50.0;
    public final static double BALL_ACC = 3.0;
    public final static int WALL_LENGTH = 500;
    public final static int WALL_WIDTH = 20;
    public final static int NUM_INPUT_NODES = 16;
    public final static int NUM_HIDDEN_1_NODES = 100;
    public final static int NUM_HIDDEN_2_NODES = 100;
    public final static int NUM_HIDDEN_3_NODES = 100;
    public final static int NUM_OUTPUT_NODES = 3;
    public final static int NUM_TIAS = 1;
    public final static int TOTAL_NUM_WEIGHTS = NUM_INPUT_NODES * NUM_HIDDEN_1_NODES
            + NUM_HIDDEN_1_NODES * NUM_HIDDEN_2_NODES
            + NUM_HIDDEN_2_NODES * NUM_HIDDEN_3_NODES
            + NUM_HIDDEN_3_NODES * NUM_OUTPUT_NODES;
    
    public final static double CHANCE_BIG_MUTATE = 0.05 / (double) (TOTAL_NUM_WEIGHTS);
    public static final int NUM_MOVES_IN_GAME = 1000;
    public final static int DRAW_X_OFFSET = 52;
    public final static int DRAW_Y_OFFSET = 52;
    public final static int PIXEL_PER_UNIT = 1;
    static double MAX_WEIGHT = 8.0;
    static double DRAW_THRESHOLD = 0.60;
    static boolean SHOW_ONLY_DIRECTION = false;
    static boolean DRAW_ACTIVATION = false;
    static boolean DRAW_NET = false;

    public final static double getDistance(int x1, int x2, int y1, int y2) {
        return Math.sqrt((double) (Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)));
    }
}
