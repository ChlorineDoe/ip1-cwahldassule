package edu.brandeis.cosi103a.ip1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App 
{

    public static void turn(Player player, Dice dice){
        System.out.println("Player: " + player.name + "             Score: " + player.score);
        int turns = 3;
        Scanner input = new Scanner(System.in);

        while(turns > 0){
            int roll = dice.roll();

            if(turns > 1){
                System.out.println("Result : " + roll + "   Do you want to roll again? (Y/N): ");
                String response = input.nextLine();

                if(response.equals("N")){
                    turns = 0;
                    player.addScore(roll);
                }
            }else{
                System.out.println("Result: " + roll);
                player.addScore(roll);
            }
          turns --;
        }
        

        System.out.println("Total score: " + player.score);
        System.out.println("--------------------------------------------------");

    }
    public static void main( String[] args )
    {
        Scanner input = new Scanner(System.in);
        List<Player> playerList = new ArrayList<>();

        System.out.print("Player 1, Enter your name: ");
        String name = input.nextLine();
        Player player1 = new Player(10, name);
        playerList.add(player1);

        System.out.print("Player 2, Enter your name: ");
        String name2 = input.nextLine();
        Player player2 = new Player(10, name2);
        playerList.add(player2);

        Dice dice = new Dice(1, 6);

        while(true){
            int gameOver = 0;
            for(Player player : playerList){
                if(!player.gameOver()){
                    System.out.println("Turn - " + player.name + ": " + (player.turns+ 1) + "/" + player.maxTurns);
                    turn(player, dice);
                    player.updateTurn();
                }else{
                    gameOver += 1;
                }
            }
            if(gameOver  == playerList.size()){
                break;
            }
        }
        System.out.println("Game Over!");
        System.out.println("");

        List<Integer> winnerScore = new ArrayList<>();
        List<Player> winners = new ArrayList<>();
        List<Integer> loserScore = new ArrayList<>();
        List<Player> losers = new ArrayList<>();

        for(Player player : playerList){
            if(winnerScore.isEmpty()){
                winnerScore.add(player.score);
                winners.add(player);
            }else{
                if(player.score == winnerScore.get(0) && !winners.contains(player)){
                    winnerScore.add(player.score);
                    winners.add(player);
                } else if(player.score > winnerScore.get(0)){
                    winnerScore.clear();
                    winnerScore.add(player.score);
                    winners.clear();
                    winners.add(player);
                } else{
                    loserScore.add(player.score);
                    losers.add(player);
                }
            }
            
            
        }

        System.out.println("Winner(s): ");
        for(int i = 0; i < winnerScore.size(); i++){
            System.out.println(winners.get(i).name + "      score: " + winnerScore.get(i));
        }

        System.out.println("Not so winner(s):  ");
        for(int i = 0; i < loserScore.size(); i++){
            System.out.println(losers.get(i).name + "      score: " + loserScore.get(i));
        }
    }
}
