/** Game.java
*   Author: Sasha Isler si2423
*   
*   
*   Game class for playing crazy eights in commandline
*   To be used with Player, Card, Deck classes
*
*/


import java.util.Scanner;
import java.util.ArrayList;
import java.lang.Math;

class Game{

    private char currentSuit; // need in case an 8 is played
    private Card faceup; 
    private Scanner input;
    private Player p1;
    private ArrayList<Card> compHand;
    private Deck cards;
    private int winner;
    boolean playAgain;
    String[] suits = {"♠", "♥", "♣", "♦"};
    
    // sets up the Game object for play
    public Game(){
        p1 = new Player();
        input = new Scanner(System.in);
        compHand = new ArrayList<Card>();
        cards = new Deck();
        winner = 0;
        playAgain = false;
        setUp();
    }

    // Plays a game of crazy eights. 
    // Returns true to continue playing and false to stop playing
    public boolean play(){
        while (true) {
            // Have player choose a card
            Card playedCard = p1.playsTurn(cards);

            // check if the deck is empty
            if (gameOver() != -1) break;

            // Check if card is valid. If it isn't, make the player choose
            // a valid option. Break if deck runs out of cards (game over)
            if (isValidGameOver(playedCard)) break;
            
            // Display faceup card

            // Check if game is over
            if (gameOver() != -1) break;
            
            // Have the computer play a card
            faceup = computerTurn();
            if (faceup != null) compHand.remove(faceup);
            
            // Check if game is over
            if (gameOver() != -1) break;

            // Display card the computer played
            System.out.println("\n*** Computer played: " + 
            faceup.toString() + " ***");

            faceup();
            
        }

        return playAgain();
    }

    // Carries out computer turn
    // If a wild was played, chooses a random suit
    // Displays to the user if a card was drawn, keeps drawing cards
    // until the deck is empty or card was drawn that is able to be played
    private Card computerTurn(){
        int valid = 0;
        for (int idx = 0; idx < compHand.size(); idx++) {
            valid = validCode(compHand.get(idx));
            if (valid == 0) {
                return compHand.get(idx);
            } else if (valid == 1) { 
                //choose random suit
                currentSuit = suits[(int)(Math.random() * 4)].charAt(0);
                return compHand.get(idx);
            } else if (idx == compHand.size()-1) {
                System.out.println("\n*** Computer player drew a card ***");
                if (!cards.canDeal()) {
                    System.out.println("ran out of cards");
                    break;
                } else compHand.add(cards.deal());
                if (!cards.canDeal()) {
                    System.out.println("ran out of cards");
                    break;
                }
            }
        }
        // This will only happen if there are no valid cards and
        // also no more cards in the deck. 
        return null;
    }
    
    // Carries out initial setUp for the game
    private void setUp() {
        // First, shuffle the deck
        cards.shuffle();

        // Then, deal 7 cards to both p1 and the computer,
        // alternating between the two with each dealt card
        for (int i = 0; i < 7; i++) {
            p1.addCard(cards.deal());
            compHand.add(cards.deal());
        }

        // Turn the top card of the deck faceup
        faceup = cards.deal();
        currentSuit = faceup.getSuit();
        System.out.println("\nGood Luck!\n");

        faceup();
    }

    // The point of this method is to check if the card the player played is
    // valid. The while loop will keep prompting the player to choose a valid
    // card until they do so. This method returns true if the game is over
    private boolean isValidGameOver(Card playedCard) {
        Card cardToCheck = playedCard;
        while(true) {
            if (validCode(cardToCheck) == -1) {
                System.out.println("\nPlease choose a valid card. ");
                faceup();
                cardToCheck = p1.playsTurn(cards);
            
                // Check if game is over
                if (gameOver() != -1) return true; 
            } else if (validCode(cardToCheck) == 1) {
                wild();
                break;
            } else break;
        }

        // Set the face up card to the players confirmed valid choice
        faceup = cardToCheck;

        // Remove the card from the players hand
        p1.removeCard(cardToCheck);

        // Display the new faceup card
        faceup();

        return false;
    }

    // Returns the code (defined below) of the card played
    // Returns a code based on the validity of the card, or if it is a wild
    private int validCode(Card cardPlayed) {
        // 0: valid    1: played a wild    -1: not valid
        int validCode = -1;
        // Check if the suit or rank match ()
        if (cardPlayed.getRank() == 8) {
            validCode = 1;
        } else if (faceup.getRank() == 8 && 
        (cardPlayed.getSuit() == currentSuit)){
            validCode = 0;
        } else if ( (cardPlayed.getSuit() == faceup.getSuit() || 
            cardPlayed.getRank() == faceup.getRank()) 
            && faceup.getRank() != 8) {
            validCode = 0;
        }
        return validCode;
    }

    // Prompts the user to choose a suit if they played a wild
    private void wild() {
        System.out.println("You played a wild. Please choose a suit");
        System.out.println("1: spade, 2: heart, 3: club, 4: diamond ");
        currentSuit = suits[input.nextInt() - 1].charAt(0);
    }

    // Checks if the conditions for the game to be over are met
    // Returns a code based on the conditions
    private int gameOver() {        
        // 0: human wins   1: comp wins   -1: game isn't over
        if (p1.getHand().size() == 0 
        || (!cards.canDeal() && p1.getHand().size() < compHand.size()) ) {
            winner = 0;
        } else if (compHand.size() == 0
        || (!cards.canDeal() && p1.getHand().size() > compHand.size()) ) {
            winner = 1;
        } else {
            winner = -1;
        }

        return winner;
    }

    // Displays the faceup card
    // Displays the suit as well if an 8 was played
    public void faceup() {
        System.out.println("\n~~ The faceup card is: " 
        + faceup.toString() + " ~~");
        if (faceup.getRank() == 8) {
            System.out.println("The current suit is " + currentSuit);
        }
    }

    // After the game is over, tells the player who won
    // and asks if they want to play again
    private boolean playAgain() {
        boolean playAgain = false;
        if (winner == 0) {
            System.out.println("\nCongrats! You won!");
        } else {
            System.out.println("\nYou lost. Better luck next time!");
        }
        System.out.println("Would you like to play again? y/n ");
        String answer = input.nextLine();

// This seems like an unnecessary statement (line 218) but for some reason,
// input.nextLine() doesn't always work without it. See:
// https://www.freecodecamp.org/news/java-scanner-nextline-call-gets-skipped-solved/
// for more info. My code would sometimes work and sometimes not work without
// it so I figured I may as well just include it to be safe.
        answer = input.nextLine();

        if (answer.equals("y")) playAgain = true;

        return playAgain;
    }
}