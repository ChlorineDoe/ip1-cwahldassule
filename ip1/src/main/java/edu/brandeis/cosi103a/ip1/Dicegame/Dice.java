package edu.brandeis.cosi103a.ip1.Dicegame;

import java.util.Random;

public class Dice {

    int lower;
    int upper;

    public Dice(int lower, int upper) {
        this.lower = lower;
        this.upper = upper;
    }
    
    public Integer roll(){
        if(this.lower > this.upper){
            System.out.println("Invalid Dice Values");
            throw new IllegalArgumentException("Invalid Dice Values");
        }
        
        Random rand = new Random();
        int diceRoll = rand.nextInt(this.upper) + this.lower;

        return diceRoll;

    }
}
