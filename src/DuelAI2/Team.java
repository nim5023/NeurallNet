/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DuelAI2;

import static DuelAI2.Constants.*;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Nick
 */
public class Team {
    
    Persist persist = new Persist();
    
    Brain king = new Brain();
    Brain prince = new Brain();
    Color color;
    
    int maxKingPoints = Integer.MIN_VALUE;
    
    int generation = 1985;
    int savedGeneration = generation;
    
    boolean isDeadAtEnd = true;
    
    Queue<Boolean> tieCount = new LinkedList<>();

    Team(Color c) {
        color = c;
        king.score = Integer.MIN_VALUE;

        for (int i = 0; i < 5; i++) {
            tieCount.add(true);
        }
    }

    public void printScores() {
        System.out.println(color.toString() + " King: " + king.score);
        System.out.println(color.toString() + " Prince: " + prince.score);
        System.out.println("");
    }
    int moveOnCount = 0;

    public void endStep() {

        if (king.score > maxKingPoints) {
            maxKingPoints = king.score;
//            System.out.println(color.toString() + " King points: " + king.score + "   generation:" + generation + " \t modifier: " + king.net.mutateModifier);
        }

        tieCount.remove();
        if (king.score == prince.score) {
            tieCount.offer(true);
        } else {
            tieCount.offer(false);
        }

        if (king.score < prince.score) {
            moveOnCount = 0;
        }       

        if (king.score >= prince.score) {
            moveOnCount++;
        }
        
        if(!isDeadAtEnd){
            System.out.println("***************** Complete ************************");
        }
        
        // force a net restart if there is no progress after 2000 tries
        if (moveOnCount > 2000) {
            System.out.println("DONE WAITING");
            System.out.println(" final points: " + king.score + "   generation:" + generation + " \t modifier: " + king.net.mutateModifier);
            isDeadAtEnd = false;
        }

        if (!isDeadAtEnd) {
            moveOnCount = 0;
            maxKingPoints = 0;
        } else {
            mutateTeam();
        }

        resetScores();

    }

    public void persistGeneration() {
        if (generation != savedGeneration) {
            savedGeneration = generation;
            persist.save(king.net, generation, color);
        }
    }

    public void mutateTeam() {

        if (prince.score >= king.score) {
            king.net = prince.net;
            generation++;
        }

        king.net.ties = getTotalTieCount();

        prince.net = king.net.createMutant();

    }

    int getTotalTieCount() {
        int rtn = 0;
        for (Boolean b : tieCount) {
            if (b) {
                rtn++;
            }
        }
        return rtn;
    }

    public void resetScores() {
        prince.score = 0;
        king.score = 0;
    }

    public void initialize() {

        Net initalNet = persist.get( generation, color );
//        Net initalNet = new Net();
//        initalNet.initWeights();

        king.net = initalNet;
        prince.net = initalNet.createMutant();
        
        resetScores();
    }

}

class Brain {

    public Net net;
    public int score = 0;
}