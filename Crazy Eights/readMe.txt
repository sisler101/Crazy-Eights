Sasha Isler

Card.java:
- in toString i use if statements to print out strings for cards with faces

Deck.java:
- in the constructor i use a nested for loop to create every
rank with every possible suit
- in deal, i first check to make sure it is possible to deal and
then i move the index top and return the card that was previously the
top card
- i return null if a card is not able to be drawn and deal with the affects
of an empty deck in the game class
- in can deal i make sure the top card is less than 0
- in shuffle i use math.random to move cards to random places and use temporary
variables to ensure no cards are lost in this process

player.java:
- i use if statements in playsTurn to make sure the player is choosing
an option available. if the player chose to draw, i deal them a new card
assuming there are still card available in the deck (i check this condition)

my game class is heavily commented and explains all the logic for my code