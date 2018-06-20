/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DuelAI2;

import static DuelAI2.Constants.*;
import java.io.Serializable;
import java.util.Arrays;

/**
 *
 * @author nmiller
 */
public class Net implements Serializable{
    
    int ties = 0;
    
    private double sigmoid(double x){
        return  (2.0/(1.0 + Math.pow(Math.E,(-x/10.0)))) - 1.0 ;
    }
    
    double[] inputs = new double[NUM_INPUT_NODES];
    double[] hidden_1 = new double[NUM_HIDDEN_1_NODES];
    double[] hidden_2 = new double[NUM_HIDDEN_2_NODES];
    double[] hidden_3 = new double[NUM_HIDDEN_3_NODES];
    double[] outputs = new double[NUM_OUTPUT_NODES];
    
    double[][] inpt_h1 = new double[NUM_INPUT_NODES][NUM_HIDDEN_1_NODES];
    double[][] h1_h2 = new double[NUM_HIDDEN_1_NODES][NUM_HIDDEN_2_NODES];
    double[][] h2_h3 = new double[NUM_HIDDEN_2_NODES][NUM_HIDDEN_3_NODES];
    double[][] h3_out = new double[NUM_HIDDEN_3_NODES][NUM_OUTPUT_NODES];
    
    public void initWeights(){
        
        assignRandoms(NUM_INPUT_NODES, NUM_HIDDEN_1_NODES, inpt_h1);
        assignRandoms(NUM_HIDDEN_1_NODES, NUM_HIDDEN_2_NODES, h1_h2);
        assignRandoms(NUM_HIDDEN_2_NODES, NUM_HIDDEN_3_NODES, h2_h3);
        assignRandoms(NUM_HIDDEN_3_NODES, NUM_OUTPUT_NODES, h3_out);
        
    }
    
    public void assignRandoms(int aSize,int bSize, double[][] arr){        
        for(int i = 0; i < aSize; i++)
            for(int j = 0; j < bSize; j++)
                arr[i][j] = (rand.nextDouble() * 10.0) - 5.0;
    }
    
    public void setInputs(double[] inputs){
        this.inputs = inputs;
    }
    
    public void runNet(){
        
        runLayer(NUM_INPUT_NODES,       NUM_HIDDEN_1_NODES,     inpt_h1,    inputs,     hidden_1);
        runLayer(NUM_HIDDEN_1_NODES,    NUM_HIDDEN_2_NODES,     h1_h2,      hidden_1,   hidden_2);
        runLayer(NUM_HIDDEN_2_NODES,    NUM_HIDDEN_3_NODES,     h2_h3,      hidden_2,   hidden_3);
        runLayer(NUM_HIDDEN_3_NODES,    NUM_OUTPUT_NODES,       h3_out,     hidden_3,   outputs);
        
    }
    
    public void runLayer(int inptSize,int outSize, double[][] weights, double[] input, double[] output){
         
        for(int j = 0;  j < outSize; j++){
            double sum = 0.0;
         
            for(int i = 0; i < inptSize; i++){
                double value = input[i];
                double weight = weights[i][j];
                sum += value*weight;
            }
            
            output[j] = sigmoid(sum);
        }
                
    }
    
    
    double mutateModifier = 0.01;
    
    public void mutate(double[][] arr){        
        
        if(ties >= 3)
            mutateModifier *= 1.1;
        if(ties <= 0)
            mutateModifier *= 0.9;
        
        double savedModifier = mutateModifier;

        boolean isHugeMutate = false;
        if( rand.nextDouble() < 0.05){
            isHugeMutate = true;
            mutateModifier = 1.0;
        }
                
        
        for(int i = 0; i < arr.length; i++)
            for(int j = 0; j < arr[i].length; j++){
                
                double mutateChance = rand.nextDouble();
                
                if(mutateChance > 0.9 || isHugeMutate){
                    
                    double linearMutate = (rand.nextDouble() - 0.5 ) * mutateModifier;
                    double percOfCrrWeight = arr[i][j] / 10.0;
                    double percentMutate = percOfCrrWeight * (rand.nextDouble() - 0.5 ) * mutateModifier;
                    
                    arr[i][j] = arr[i][j] + percentMutate + linearMutate;
                    
                } else if(mutateChance < CHANCE_BIG_MUTATE){
//                    arr[i][j] = (rand.nextDouble() - 0.5) * 20.0;
                }
            }
        
        mutateModifier = savedModifier;
        
    }
    
    
    public static double[][] deepCopy(double[][] original) {
    
        final double[][] result = new double[original.length][];
        for (int i = 0; i < original.length; i++) {
            result[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return result;
}
    
    
    
    
    public Net createMutant(){
        
        Net mutant = new Net();
        mutant.mutateModifier = mutateModifier;
        
        mutant.inpt_h1 = deepCopy(this.inpt_h1);
        mutant.h1_h2 = deepCopy(this.h1_h2);
        mutant.h2_h3 = deepCopy(this.h2_h3);
        mutant.h3_out = deepCopy(this.h3_out);
        
        mutate(mutant.inpt_h1);
        mutate(mutant.h1_h2);
        mutate(mutant.h2_h3);
        mutate(mutant.h3_out);
        
        return mutant;
        
    }
    
    
    
}
