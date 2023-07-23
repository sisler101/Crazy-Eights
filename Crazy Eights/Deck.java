/** Deck.java
*   Author: Sasha Isler- si2423
*   
*   Models a typical deck of playing cards
*   To be used with Card class
*
*/
import java.lang.Math;


class Deck{

    private Card[] deck; // contains the cards to play with
    private int top; // controls the "top" of the deck to deal from

    //spade, heart, club, diamond
    private String[] suits = {"♠", "♥", "♣", "♦"};

    // constructs a default Deck
    public Deck(){
        deck = new Card[52];
        int idx = 0;
        for (int i = 1; i <= 13; i++) {
            for (int j = 0; j < 4; j++) {                
                deck[idx++] = new Card(suits[j].charAt(0), i);
            }
        }
        top = deck.length-1;
    }

    // Deals the top card off the deck
    public Card deal(){
        // First check to see if there are cards remaining
        if (canDeal()) {
            top--;
            return deck[top+1];
        }
        return null;
    }


    // returns true provided there is a card left in the deck to deal
    // If the index of the top card is negative then the last card
    // has already been dealt and therefore the deck is empty
    public boolean canDeal(){
        boolean cardLeft = true;
        if (top < 0) {
            cardLeft = false;
        }
        return cardLeft;
    }

    // Shuffles the deck
    // I use a for loop and random numbers to randomize the deck
    //through swapping numbers at randomly generated indices
    public void shuffle(){
        int randIdx1;
        int randIdx2;
        Card temp;
        for (int i = 0; i < 10000000; i++) {
            randIdx1 = (int)(Math.random() * 52);
            randIdx2 = (int)(Math.random() * 52);
            temp = deck[randIdx1];
            deck[randIdx1] = deck[randIdx2];
            deck[randIdx2] = temp;
        }
    }

    // Returns a string representation of the whole deck
    public String toString(){
        String deckString = "";
        int idx = 1;
        for (Card c : deck) {
            deckString += c.toString() + " | ";
            if (idx % 4 == 0) {
                deckString += "\n";
            }
            idx++;
        }
       return (deckString);
    }

}