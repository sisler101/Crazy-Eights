/** Player.java
*   Author: Sasha Isler si2423
*   
*   Player class as part of Crazy Eights
*   To be used with Game, Card, Deck classes
*
*/

import java.util.ArrayList;
import java.util.Scanner;

class Player{
    
    private ArrayList<Card> hand; // the player's hand
    private Scanner input;

    public Player(){
        hand = new ArrayList<Card>();
        input = new Scanner(System.in);
    }

    // Adds a card to the player's hand
    public void addCard(Card c){
        hand.add(c);
    }
   
    // Covers all the logic regarding a human player's turn
    // public so it may be called by the Game class
    public Card playsTurn(Deck deck){
        while (true) {
            // Have player input card rank and suit
            System.out.print("\nChoose the number of a card/action: "); 
            System.out.print(displayOptions());
            int option = input.nextInt();

            // Check if the player chose to draw a card
            if (option == hand.size() + 1 && deck.canDeal()){
                Card newCard = deck.deal();
                addCard(newCard);
                System.out.println("\nYou drew a " + newCard.toString());
                if (!deck.canDeal()) {
                    System.out.println("No more cards in the deck. Game over");
                    return null;
                }
            } else if (option > hand.size() + 1){
                System.out.println("Please choose a valid number");
            } else { return hand.get(option-1); } 
        }
    }

    // Accessor for the players hand
    public ArrayList<Card> getHand(){
        return hand;
    }

    // Returns a printable string representing the player's hand
    public String handToString(){
        String handString = "";
        for (int i = 0; i <= hand.size(); i++) {
            handString += hand.get(i).toString() + ", ";
        }
        return handString.substring(0, -2);
    }

    // Displays the options the player has to choose from on their turn
    public String displayOptions(){
        String handString = "";
        int i;
        System.out.println("\nYour hand:");
        for (i = 0; i < hand.size(); i++) {
            if (i+1 >= 10) {
                handString += i+1 + ")   " + hand.get(i).toString() + "\n";
            } else {
                handString += i+1 + ")    " + hand.get(i).toString() + "\n";
            }
        }
        return handString + "\n" + (i + 1) + ": draw a card\n\n";
    }

    public void removeCard(Card playedCard) {
        hand.remove(playedCard);
    }

}
