package com.condor.shuffleup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class Hearts {
    private int turnNumber;                                 //current turn
    private int suit;                                       //current suit of hand
    private String turnInfo = "";
    private int currentPlayer;
    private int startPlayer;                                //player who starts a pile
    private int currentRound = 0;                           //keeps track of the current round
    private int playTilPoints;                              //game ends after reaching this number of points
    private boolean tricksBroken;                           // have hearts/Queen of spades been played out of suit

    private ArrayList<Player> players = new ArrayList<>();  //list of players
    private ArrayList<Card> pile = new ArrayList<>();       //Card pile changes each round
    private Map<Card, Integer> pileMap = new HashMap<>();   //used to figure out which player played a card
    private int gamemode;                                   //not currently used
    private int numberOfPlayers;
    private boolean newPile = true;
    private boolean newRound;                                       //is true when a new round starts.

    private OnNewPileListener listener;                     //notifies screen that pile has changed

    //Used to compare cards and make life easier
    private final static int HEARTS = 100,
            DIAMONDS = 200,
            SPADES = 300,
            CLUBS = 400;

    private final static int JACK = 11,
            QUEEN = 12,
            KING = 13,
            ACE = 14;

    //set up the game
    Hearts(int playerCount, int mode, int playTilPoints, ArrayList<String> playerNameList){
        numberOfPlayers = playerCount;
        gamemode = mode;
        turnNumber = 0;
        this.playTilPoints = playTilPoints;

        for (int i = 0; i<numberOfPlayers; i++){
            players.add(new Player(playerNameList.get(i)));
        }

        newRound();
    }

    public void setOnNewPileChangeListener(OnNewPileListener listener)
    {
        this.listener = listener;
    }

    //used to update the screen when a new pile is created.
    private void setNewPile(boolean newPile){
        this.newPile = newPile;

        //checks if a new round needs to start if game is not over
        if (isRoundOver() && getCurrentPlayerCardNumber() == 0 && !IsGameOver()){
            newRound();
        }




        if(listener != null)
        {
            listener.onNewPileChange(newPile);
        }
    }

    public int getNumberOfPlayers(){
        return numberOfPlayers;
    }

    public ArrayList<Card> getPile(){
        return pile;
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }


    public Player getCurrentPlayer(){
        return players.get(currentPlayer);
    }

    public int getPlayerNumber(){
        return currentPlayer;
    }

    public String getSuitString() {

        String text = "Current Suit: ";

        //If no suit, return who's turn it is
        if (newPile){
            return turnInfo;
        }
        switch (suit) {
            case HEARTS:
                return text + "Hearts";
            case SPADES:
                return text + "Spades";
            case CLUBS:
                return text + "Clubs";
            case DIAMONDS:
                return text + "Diamonds";
        }
        return "Error";
    }

    //Returns strings for the current round or the winner of the game
    public String getRoundInfo(){
        if (!IsGameOver()){
            return ("Pile\n" + "(Round " + (currentRound) + ")" );
        }
        else {
            return ("Game Over\n" + this.getWinner() + " won the game");
        }
    }

    //determines the winner of the game
    private String getWinner(){
        ArrayList<Integer> highestScoreList = new ArrayList<>();
        for (int i = 0; i<numberOfPlayers; i++){
            highestScoreList.add(players.get(i).getScore());
        }

        for (int i = 0; i<numberOfPlayers; i++){
            if (players.get(i).getScore() == Collections.min(highestScoreList)){
                return players.get(i).getPlayerName();
            }
        }

        return "Error";
    }

    //Gets the number of cards a the current player has left
    //Used to ensure round/game does not immediately end before everyone has played cards
    private int getCurrentPlayerCardNumber(){
        return players.get(currentPlayer).getHand().size();
    }

    //updates the scores at the end of the round
    private void updateScores(){
        int score = 0;

        //stores cards of the original suit for the round
        ArrayList<Card> currentSuitCards = new ArrayList<>();

        for (int i = 0; i< pile.size(); i++){
            if (suit == pile.get(i).getSuit()){
                currentSuitCards.add(pile.get(i));
            }
        }

        //gets highest value of the suit played
        Card highestCard = Collections.max(currentSuitCards);

        //determines the player who played the highest card
        int player = pileMap.get(highestCard);

        //adds up all the points
        for (int i = 0; i< pile.size(); i++){
            if (pile.get(i).getSuit() == HEARTS){
                score++;
            }
            if (pile.get(i).getSuit() == SPADES && pile.get(i).getValue() == QUEEN){
                score += 13;
            }
        }

        //resets start player and adds points
        players.get(player).addPoints(score);
        startPlayer = player;
        currentPlayer = startPlayer;
    }

    //Sets up a new round
    private void newRound(){
        CardDeck deck = new CardDeck();
        deck.shuffle();
        currentRound++;
        tricksBroken = false; //tricks have not been broken at beginning of a round

        //Determines the number of cards each player gets
        int cardPerPlayer = 52/numberOfPlayers;

        //Need to implement a case for a deck not divisible by the number of players...

        //deals cards
        for(int i = 0; i<numberOfPlayers; i++){

            for (int j = 0; j< cardPerPlayer; j++){
                players.get(i).addCard(deck.dealCard());

                //checks to see which player has the two of clubs and makes them the first player
                if (players.get(i).getHand().get(j).getSuit() == CLUBS && players.get(i).getHand().get(j).getValue() == 2){
                    startPlayer = i;
                    currentPlayer = startPlayer;
                    suit = CLUBS;
                    setNewPile(true);
                    turnInfo = (getCurrentPlayer().getPlayerName()) + " leads";
                    newRound = true;
                }
            }
            players.get(i).sortHand();
        }
    }



    public void PlayCard(Card choice){

        //Can play card as long as the game is not over and the player has cards left in deck
        if (!IsGameOver() && getCurrentPlayerCardNumber() != 0){

            if (isValidPlay(choice)){
                Turn(choice);
            }
        }

    }

    //Checks if a card is a valid play
    protected boolean isValidPlay(Card choice){

        //makes sure the two of clubs is played at the beginning of a round
        if (newRound){
            if (choice.getSuit() == CLUBS && choice.getValue() == 2){
                newRound = false;
                return true;
            }
            else{
                return false;
            }

        }


        //checks to see if card played is the right suit or it's a new hand (can play any suit)
        if (choice.getSuit() == suit || newPile){

            //If tricks have not been broken can't lead a heart unless only have hearts
            if (!tricksBroken && choice.getSuit() == HEARTS){

                //if hand has anything but hearts, can't play heart
                for (int i = 0; i<players.get(currentPlayer).getHand().size(); i++){
                    if (players.get(currentPlayer).getHand().get(i).getSuit() == HEARTS){
                        return false;
                    }
                }

                //return true if only have hearts
                return true;
            }

            //tricks are broken if queen of spades is played
            if (choice.getSuit() == SPADES && choice.getValue() == QUEEN){
                tricksBroken = true;
            }

            //return true if card is correct suit or new pile
            return true;
        }

        // checks to see if player has suit in hand
        // if player doesn't have the current suit, they can play anything
        else{
            for (int i = 0; i<players.get(currentPlayer).getHand().size(); i++){
                if (players.get(currentPlayer).getHand().get(i).getSuit() == suit){
                    return false;
                }
            }

            //hearts have been broken
            if (choice.getSuit() == HEARTS || (choice.getSuit() == SPADES && choice.getValue() == QUEEN)){
                tricksBroken = true;
            }

            return true;
        }
    }

    //Logic when a user plays a card
    private void Turn(Card choice){

        //if new pile, set the suit to the suit of the card plaid
        if (newPile){

            suit = choice.getSuit();
            setNewPile(false);

        }

        //remove card from hand and add it to the pile
        pile.add(players.get(currentPlayer).playCard(choice));
        //Used for updating scores.
        pileMap.put(choice, currentPlayer);

        //if the start player isn't 0, it wraps around to the beginning until all players have played
        if (startPlayer > 0){
            if (currentPlayer == numberOfPlayers-1){
                currentPlayer = 0;
            }
            else{
                currentPlayer++;
            }
        }
        else{
            currentPlayer++;
        }

        turnNumber++;

        //end of pile
        if (turnNumber == numberOfPlayers){
            updateScores();

            turnNumber = 0;
            pile.clear();
            pileMap.clear();
            turnInfo = (getCurrentPlayer().getPlayerName()) + " leads";
            setNewPile(true);

        }

    }

    //Used to determine if a round is over
    //sums scores to see if at 26
    private boolean isRoundOver(){
        int sumOfPoints = 0;
        for (int i = 0; i<numberOfPlayers; i++){
            sumOfPoints += players.get(i).getScore();
        }

        return sumOfPoints%26 == 0;
    }

    //Checks if the game is over
    private boolean IsGameOver(){

        //Adds all players scores to a list to check for highest score
        ArrayList<Integer> highestScoreList = new ArrayList<>();
        for (int i = 0; i<numberOfPlayers; i++){
            highestScoreList.add(players.get(i).getScore());
        }

        //if player(s) have reached threshold of points, end game
        if (Collections.max(highestScoreList) >= playTilPoints){
            //finish hand before game is over
            return getCurrentPlayerCardNumber() == 0;
        }
        return false;
    }
}
