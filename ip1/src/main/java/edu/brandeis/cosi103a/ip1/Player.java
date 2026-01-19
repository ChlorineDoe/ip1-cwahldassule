package edu.brandeis.cosi103a.ip1;

public class Player {
    int score;
    int turns;
    int maxTurns;
    String name;

    public Player(int maxTurns, String name) {
        this.maxTurns = maxTurns;
        this.score = 0;
        this.turns = 0;
        this.name = name;
    }

    public void addScore(int points){
        score += points;
    }

    public void updateTurn(){
        turns += 1;
    }

    public boolean gameOver(){
        if(turns >= maxTurns){
            return true;
        }else{
            return false;
        }
    }
}
