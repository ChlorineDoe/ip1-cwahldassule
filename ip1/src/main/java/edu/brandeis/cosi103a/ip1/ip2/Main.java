package edu.brandeis.cosi103a.ip1.ip2;

import java.util.List;
import java.util.Random;

public class Main {

    public static void turn(Player player, Board board, int strategy, int turnCount) {
        // At the start of each turn, the player draws 5 cards
        List<Card> hand = player.drawTurnHand();

        // Display drawn cards
        System.out.println("Player drew: " + hand);

        // Calculate the total value of cards in hand
        int totalValue = 0;
        for (Card card : hand) {
            totalValue += card.getValue();
        }

        //buy phase
        if (totalValue >= 8 && board.getCardsRemaining("framework") > 0) {
            Card boughtCard = board.removeCard("framework");
            player.getDeck().addToDiscard(boughtCard);
            player.addPoints(boughtCard.getValue());
            System.out.println("Player bought: " + boughtCard);
        }
        else if (board.getCardsRemaining("framework") <= 4) {
            if(strategy == 1){
                if(turnCount % 2 == 0){
                    // Purchase points
                    Card boughtCard = buyPoints(player, board, totalValue);
                    if (boughtCard != null) {
                        System.out.println("Player bought: " + boughtCard);
                    } else {
                        System.out.println("Player could not afford any automation cards");
                    }
                } else {
                    // Purchase money
                    Card boughtCard = buyMoney(player, board, totalValue);
                    if (boughtCard != null) {
                        System.out.println("Player bought: " + boughtCard);
                    } else {
                        System.out.println("Player could not afford any cryptocurrency cards");
                    }
                }
            } else {
                // Randomly choose between buying money (1) or buying points (2)
                Random random = new Random();
                int choice = random.nextInt(2) + 1; // Randomly choose 1 or 2
                
                if (choice == 1) {
                    Card boughtCard = buyMoney(player, board, totalValue);
                    if (boughtCard != null) {
                        System.out.println("Player bought: " + boughtCard);
                    } else {
                        System.out.println("Player could not afford any cryptocurrency cards");
                    }
                } else {
                    Card boughtCard = buyPoints(player, board, totalValue);
                    if (boughtCard != null) {
                        System.out.println("Player bought: " + boughtCard);
                    } else {
                        System.out.println("Player could not afford any automation cards");
                    }
                }
            }    
        }else{
            // Purchase the highest value cryptocurrency card the player can afford
            Card boughtCard = buyMoney(player, board, totalValue);
            if (boughtCard != null) {
                System.out.println("Player bought: " + boughtCard);
            } else {
                System.out.println("Player could not afford any cryptocurrency cards");
            }
        }
        

    }

    public static Card buyPoints(Player player, Board board, int totalValue){
        // Buy the automation card with the highest point value the player can afford
        if (totalValue >= 8 && board.getCardsRemaining("framework") > 0) {
            Card boughtCard = board.removeCard("framework");
            if (boughtCard != null) {
                player.getDeck().addToDiscard(boughtCard);
                player.addPoints(boughtCard.getValue());
                return boughtCard;
            }
        } else if (totalValue >= 5 && board.getCardsRemaining("module") > 0) {
            Card boughtCard = board.removeCard("module");
            if (boughtCard != null) {
                player.getDeck().addToDiscard(boughtCard);
                player.addPoints(boughtCard.getValue());
                return boughtCard;
            }
        } else if (totalValue >= 2 && board.getCardsRemaining("method") > 0) {
            Card boughtCard = board.removeCard("method");
            if (boughtCard != null) {
                player.getDeck().addToDiscard(boughtCard);
                player.addPoints(boughtCard.getValue());
                return boughtCard;
            }
        }
        return null; // No card was bought
    }

    public static Card buyMoney(Player player, Board board, int totalValue){
       if (totalValue >= 6 && board.getCardsRemaining("dogecoin") > 0) {
                Card boughtCard = board.removeCard("dogecoin");
                if (boughtCard != null) {
                    player.getDeck().addToDiscard(boughtCard);
                    return boughtCard;
                }
            } else if (totalValue >= 3 && board.getCardsRemaining("ethereum") > 0) {
                Card boughtCard = board.removeCard("ethereum");
                if (boughtCard != null) {
                    player.getDeck().addToDiscard(boughtCard);
                    return boughtCard;
                }
            } else if (totalValue >= 0 && board.getCardsRemaining("bitcoin") > 0) {
                Card boughtCard = board.removeCard("bitcoin");
                if (boughtCard != null) {
                    player.getDeck().addToDiscard(boughtCard);
                    return boughtCard;
                }
            }
        return null; // No card was bought
    }

    public static void main(String[] args) {
        int turnCount = 0;
        Player player1 = new Player(new Deck());
        Player player2 = new Player(new Deck());

        Board board = new Board();

    // Each player begins with a starter deck, consisting of 7 Bitcoins and 3 Methods. These
    // cards are distributed to each player at the beginning of the game, from the supply
    
        for (int i = 0; i < 7; i++) {
            player1.getDeck().addToDrawPile(board.removeCard("bitcoin"));
            player2.getDeck().addToDrawPile(board.removeCard("bitcoin"));
        }
        for (int i = 0; i < 3; i++) {
            player1.getDeck().addToDrawPile(board.removeCard("method"));
            player1.addPoints(1);
            player2.getDeck().addToDrawPile(board.removeCard("method"));
            player2.addPoints(1);
        }

        // Game loop - alternate between players
        boolean gameRunning = true;
        boolean player1Turn = true;
        
        while (gameRunning) {
            if (player1Turn) {
                turn(player1, board, 1, turnCount);
            } else {
                turn(player2, board, 2, turnCount);
            }
            turnCount++;
            player1Turn = !player1Turn;
            if(board.getCardsRemaining("framework") == 0){
                gameRunning = false;
            }
        }
    }
}
